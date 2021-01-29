package uz.dev.caveatemptor;

import net.ttddyy.dsproxy.support.ProxyDataSourceBuilder;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.util.ReflectionUtils;
import uz.dev.caveatemptor.entity.*;
import uz.dev.caveatemptor.entity.billingdetails.BillingDetails;
import uz.dev.caveatemptor.entity.billingdetails.CreditCard;
import uz.dev.caveatemptor.entity.monetaryamount.MonetaryAmount;
import uz.dev.caveatemptor.repository.BidRepository;
import uz.dev.caveatemptor.repository.ItemRepository;
import uz.dev.caveatemptor.repository.UserRepository;

import javax.persistence.EntityManager;
import javax.sql.DataSource;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.Currency;
import java.util.Locale;


@ExtendWith(SpringExtension.class)
@DataJpaTest(includeFilters = @ComponentScan.Filter(classes = Repository.class), showSql = true)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles({"h2", "cache"})
//@Import(MainTest.DatasourceProxyBeanPostProcessor.class)
class MainTest {


    Item item;
    User seller;

    @Autowired
    private TestEntityManager testEntityManager;

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private BidRepository bidRepository;

    @Autowired
    private UserRepository userRepository;

    private static long userId = 230;

    @BeforeAll
    static void setup() {
        Locale.setDefault(Locale.US);
    }

    @BeforeEach
    void saveItem() {
        seller = createNewUser();
        seller.setRank(1);
        userRepository.save(seller);
        User bidder1 = createNewUser();
        User bidder2 = createNewUser();
        userRepository.save(bidder1);
        userRepository.save(bidder2);
        item = createNewItem(seller);
        itemRepository.makePersistent(item);
        bidRepository.makePersistent(new Bid(item, MonetaryAmount.fromString("1.6e6 USD"), bidder1));
        bidRepository.makePersistent(new Bid(item, MonetaryAmount.fromString("1.4e6 USD"), bidder2));
        EntityManager em = testEntityManager.getEntityManager();
        em.flush();
    }

    @Test
    @Commit
    void main() {
        
    }

    private void addImages(Item item) {
        item.addImage(new Image("Foo", "foo.jpg", 640, 480));
        item.addImage(new Image("Bar", "bar.jpg", 800, 600));
        item.addImage(new Image("Baz", "baz.jpg", 1024, 768));
    }

    private Category createNewCategory() {
        return new Category("category1");
    }

    private Item createNewItem(User seller) {
        MonetaryAmount initialPrice = new MonetaryAmount(BigDecimal.valueOf(1_200_000L), Currency.getInstance("USD"));
        MonetaryAmount buyNowPrice = new MonetaryAmount(BigDecimal.valueOf(1_800_000L), Currency.getInstance("EUR"));
        Weight weight = new Weight("weight", "kg", 2);
        Dimensions dimensions = new Dimensions("dimensions", "cm", 10, 14, 10);
        Item item = new Item("Golden egg", "The most high priced egg in the world", initialPrice, AuctionType.HIGHEST_BID, seller);
        item.setBuyNowPrice(buyNowPrice);
        item.setWeight(weight);
        item.setDimensions(dimensions);
        return item;
    }

    private User createNewUser() {
        User user = new User("muzappar" + userId, "Muzappar", "Muzapov", "muzappar" + userId++ + "@gmail.com");
        BillingDetails bd = new CreditCard("8600-1234-5678-9011", "12", "2028");
        user.setDefaultBilling(bd);
        return user;
    }

    @Component
    public static class DatasourceProxyBeanPostProcessor implements BeanPostProcessor {

        @Override
        public Object postProcessBeforeInitialization(final Object bean, final String beanName) throws BeansException {
            return bean;
        }

        @Override
        public Object postProcessAfterInitialization(final Object bean, final String beanName) throws BeansException {
            if (bean instanceof DataSource) {
                ProxyFactory factory = new ProxyFactory(bean);
                factory.setProxyTargetClass(true);
                factory.addAdvice(new ProxyDataSourceInterceptor((DataSource) bean));
                return factory.getProxy();
            }
            return bean;
        }
    }

    private static class ProxyDataSourceInterceptor implements MethodInterceptor {
        private final DataSource dataSource;

        public ProxyDataSourceInterceptor(DataSource dataSource) {
            this.dataSource = ProxyDataSourceBuilder.create(dataSource)
                    .name("DataSourceLogger")
                    .asJson()
                    .countQuery()
                    .logQueryToSysOut()
                    .build();
        }

        @Override
        public Object invoke(MethodInvocation methodInvocation) throws Throwable {
            Method proxyMethod = ReflectionUtils.findMethod(dataSource.getClass(),
                    methodInvocation.getMethod().getName());
            if (proxyMethod != null) {
                return proxyMethod.invoke(dataSource, methodInvocation.getArguments());
            }
            return methodInvocation.proceed();
        }
    }
}