package uz.dev.caveatemptor.repository.listener;

import javax.persistence.PostPersist;

public class PersistEntityListener {

    @PostPersist
    void logPersisted(Object entity) {
        System.out.println("Entity persisted: " + entity);
    }
}
