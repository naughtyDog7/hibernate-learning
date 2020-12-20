package uz.dev.caveatemptor.dao;

import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.service.ServiceRegistry;
import uz.dev.caveatemptor.entity.*;
import uz.dev.caveatemptor.entity.zipcode.ZipcodeConverter;

public class SessionFactoryConfigurer {

    private static SessionFactory sessionFactory;

    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            synchronized (SessionFactoryConfigurer.class) {
                if (sessionFactory == null) {
                    ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                            .loadProperties("hibernate.properties")
                            .build();
                    MetadataSources metadataSources = new MetadataSources(serviceRegistry)
                            .addAnnotatedClass(Item.class)
                            .addAnnotatedClass(Bid.class)
                            .addAnnotatedClass(User.class)
                            .addAnnotatedClass(ItemBidSummary.class)
                            .addAnnotatedClass(MonetaryAmountConverter.class)
                            .addAnnotatedClass(ZipcodeConverter.class);
                    Metadata metadata = metadataSources.buildMetadata();
                    sessionFactory = metadata.buildSessionFactory();
                }
            }
        }
        return sessionFactory;
    }
}