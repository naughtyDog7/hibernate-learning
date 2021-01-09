package uz.dev.caveatemptor;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import uz.dev.caveatemptor.entity.*;
import uz.dev.caveatemptor.entity.audit.AuditLogInterceptor;
import uz.dev.caveatemptor.entity.billingdetails.BillingDetails;
import uz.dev.caveatemptor.entity.billingdetails.CreditCard;
import uz.dev.caveatemptor.entity.monetaryamount.MonetaryAmount;
import uz.dev.caveatemptor.util.Constants;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.Locale;

import static uz.dev.caveatemptor.dao.SessionFactoryConfigurer.getSessionFactory;

class MainTest {

    Item item;
    User seller;

    private static long userId = 230;

    @BeforeAll
    static void setup() {
        Locale.setDefault(Locale.US);
    }

    @BeforeEach
    void saveItem() {
        Session session = getSessionFactory().getCurrentSession();
        Transaction tx = session.beginTransaction();
        seller = createNewUser();
        seller.setRank(1);
        session.persist(seller);
        item = createNewItem(seller);
        session.persist(item);
        User bidder1 = createNewUser();
        User bidder2 = createNewUser();
        session.persist(bidder1);
        session.persist(bidder2);
        item.addBid(new Bid(MonetaryAmount.fromString("1.4e6 USD"), bidder1));
        item.addBid(new Bid(MonetaryAmount.fromString("1.6e6 USD"), bidder2));
        tx.commit();
    }

    @Test
    void main() {
        Session session = getSessionFactory().getCurrentSession();
        Transaction tx = session.beginTransaction();
        System.out.println(session.createNamedQuery(Constants.QueryNames.SELECT_ITEM_BY_LIKE_NAME)
                .setParameter("name", "G%")
                .getResultList());
        tx.commit();
    }

    private Session getSessionWithInterceptor() {
        AuditLogInterceptor interceptor = new AuditLogInterceptor();
        Session session = getSessionFactory()
                .withOptions()
                .interceptor(interceptor)
                .openSession();
        interceptor.setCurrentSession(session);
        interceptor.setCurrentUserId(12301);
        return session;
    }

    private void addImages(Item item) {
        item.addImage(new Image("Foo", "foo.jpg", 640, 480));
        item.addImage(new Image("Bar", "bar.jpg", 800, 600));
        item.addImage(new Image("Baz", "baz.jpg", 1024, 768));
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

}