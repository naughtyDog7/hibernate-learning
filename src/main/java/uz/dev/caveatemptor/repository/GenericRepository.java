package uz.dev.caveatemptor.repository;

import javax.persistence.LockModeType;
import java.io.Serializable;
import java.util.List;

public interface GenericRepository<T, ID extends Serializable> {
    T findById(ID id);

    T findById(ID id, LockModeType lock);

    T getReference(ID id);

    List<T> findAll();

    long getCount();

    void makePersistent(T entity);

    void remove(T entity);

    void checkVersion(T entity, VersionUpdate versionUpdate);
}
