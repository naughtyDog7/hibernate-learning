package uz.dev.caveatemptor;

import org.hibernate.Session;
import org.hibernate.Transaction;
import uz.dev.caveatemptor.entity.AuctionType;
import uz.dev.caveatemptor.entity.Item;
import uz.dev.caveatemptor.entity.monetaryamount.MonetaryAmount;

import java.math.BigDecimal;
import java.util.Currency;

import static uz.dev.caveatemptor.dao.SessionFactoryConfigurer.getSessionFactory;

public class Main {
    public static void main(String[] args) {
        Session session = getSessionFactory().getCurrentSession();
        Transaction tx = session.beginTransaction();
        MonetaryAmount initialPrice = new MonetaryAmount(BigDecimal.valueOf(1200000), Currency.getInstance("EUR"));
        Item item = new Item("Golden Egg 600k", "This is the most high priced egg in the world",
                initialPrice, AuctionType.HIGHEST_BID);
        item.setBuyNowPrice(new MonetaryAmount(BigDecimal.valueOf(2000000), Currency.getInstance("USD")));
        session.persist(item);
        tx.commit();

        session = getSessionFactory().getCurrentSession();
        tx = session.beginTransaction();
        item = session.find(Item.class, item.getId());
        item.setName("Golden Egg 600k+");
        System.out.println(item.getShortDescription());
        tx.commit();
    }
}
