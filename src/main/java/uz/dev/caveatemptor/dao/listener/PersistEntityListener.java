package uz.dev.caveatemptor.dao.listener;

import javax.persistence.PostPersist;

public class PersistEntityListener {

    @PostPersist
    void logPersisted(Object entity) {
        System.out.println("Entity persisted: " + entity);
    }
}
