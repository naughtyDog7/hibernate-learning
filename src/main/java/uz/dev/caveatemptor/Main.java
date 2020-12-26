package uz.dev.caveatemptor;

import org.hibernate.Session;
import org.hibernate.Transaction;
import uz.dev.caveatemptor.entity.*;
import uz.dev.caveatemptor.entity.billingdetails.BillingDetails;
import uz.dev.caveatemptor.entity.billingdetails.CreditCard;
import uz.dev.caveatemptor.entity.monetaryamount.MonetaryAmount;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.Locale;

import static uz.dev.caveatemptor.dao.SessionFactoryConfigurer.getSessionFactory;

public class Main {

    static {
        Locale.setDefault(Locale.US);
    }

    public static void main(String[] args) {
        Session session = getSessionFactory().getCurrentSession();
        Transaction tx = session.beginTransaction();
        Address shippingAddress = new Address("hmStreet", "hmCity", "hmCountry", "12345");
        session.persist(shippingAddress);
        User user = createNewUser(shippingAddress.getId());
        user.setShippingAddress(shippingAddress);
        session.persist(user);
        tx.commit();

        System.out.println("====================================");
        session = getSessionFactory().getCurrentSession();
        tx = session.beginTransaction();
        user = session.load(User.class, user.getId());
        System.out.println(user);
        tx.commit();
        System.out.println("====================================");
    }

    private static void addImages(Item item) {
        item.addImage(new Image("Foo", "foo.jpg", 640, 480));
        item.addImage(new Image("Bar", "bar.jpg", 800, 600));
        item.addImage(new Image("Baz", "baz.jpg", 1024, 768));
        item.addImage(new Image("Baz", "baz.jpg", 1024, 768));
    }

    private static Item createNewItem() {
        MonetaryAmount initialPrice = new MonetaryAmount(BigDecimal.valueOf(1_200_000L), Currency.getInstance("USD"));
        MonetaryAmount buyNowPrice = new MonetaryAmount(BigDecimal.valueOf(1_800_000L), Currency.getInstance("EUR"));
        Weight weight = new Weight("weight", "kg", 2);
        Dimensions dimensions = new Dimensions("dimensions", "cm", 10, 14, 10);
        Item item = new Item("Golden egg", "The most high priced egg in the world", initialPrice, AuctionType.HIGHEST_BID);
        item.setBuyNowPrice(buyNowPrice);
        item.setWeight(weight);
        item.setDimensions(dimensions);
        return item;
    }

    private static User createNewUser(long id) {
        User user = new User(id, "muzappar228", "Muzappar", "Muzapov");
        BillingDetails bd = new CreditCard("8600-1234-5678-9011", "12", "2028");
        user.setDefaultBilling(bd);
        return user;
    }
}
