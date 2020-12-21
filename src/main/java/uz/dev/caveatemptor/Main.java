package uz.dev.caveatemptor;

import org.hibernate.Session;
import org.hibernate.Transaction;
import uz.dev.caveatemptor.entity.billingdetails.BillingDetails;
import uz.dev.caveatemptor.entity.billingdetails.CreditCard;

import java.util.Locale;

import static uz.dev.caveatemptor.dao.SessionFactoryConfigurer.getSessionFactory;

public class Main {

    static {
        Locale.setDefault(Locale.US);
    }

    public static void main(String[] args) {
        Session session = getSessionFactory().getCurrentSession();
        Transaction tx = session.beginTransaction();
        BillingDetails bd = new CreditCard("8600-1234-5678-9011", "12", "2028");
        bd.setOwner("Muzap");
        System.out.println("===========================");
        System.out.println("Persist:");
        session.persist(bd);
        System.out.println("===========================");
        tx.commit();
        session = getSessionFactory().getCurrentSession();
        tx = session.beginTransaction();
        System.out.println("===========================");
        System.out.println("Load:");
        System.out.println(session.createQuery("SELECT cc FROM CreditCard cc", CreditCard.class).list());
        System.out.println("===========================");
        tx.commit();
    }
}
