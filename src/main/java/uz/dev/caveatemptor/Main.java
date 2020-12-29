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
        User user = createNewUser();
        Address shipping = new Address("hmStreet", "hmCity", "hmCountry", "12345");
        user.setShippingAddress(shipping);
        session.persist(user);
        Category category = createNewCategory();
        Item item = createNewItem();
        item.setBuyer(user);
        session.persist(item);
        session.persist(category);
        CategorizedItem categorizedItem = new CategorizedItem("muzappar", category, item);
        session.persist(categorizedItem);
        tx.commit();

        System.out.println("\n\n\n=================================\n\n");
        session = getSessionFactory().getCurrentSession();
        tx = session.beginTransaction();
        Item newItem = session.load(Item.class, item.getId());
        System.out.println(newItem.getBuyer());
        tx.commit();

        /*System.out.println("====================================");
        session = getSessionFactory().getCurrentSession();
        tx = session.beginTransaction();
        user = session.load(User.class, user.getId());
        System.out.println(user);
        System.out.println(user.getDefaultBilling().getClass());
        tx.commit();
        System.out.println("====================================");*/
    }

    private static void addImages(Item item) {
        item.addImage(new Image("Foo", "foo.jpg", 640, 480));
        item.addImage(new Image("Bar", "bar.jpg", 800, 600));
        item.addImage(new Image("Baz", "baz.jpg", 1024, 768));
        item.addImage(new Image("Baz", "baz.jpg", 1024, 768));
    }

    private static Category createNewCategory() {
        return new Category("category1");
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

    private static User createNewUser() {
        User user = new User("muzappar228", "Muzappar", "Muzapov");
        BillingDetails bd = new CreditCard("8600-1234-5678-9011", "12", "2028");
        user.setDefaultBilling(bd);
        return user;
    }
}
