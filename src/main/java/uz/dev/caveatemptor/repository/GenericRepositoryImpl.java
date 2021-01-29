package uz.dev.caveatemptor.repository;

import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import java.io.Serializable;
import java.util.List;

public abstract class GenericRepositoryImpl<T, ID extends Serializable>
        implements GenericRepository<T, ID> {

    @PersistenceContext
    protected EntityManager entityManager;

    protected Class<T> entityClass;

    @Override
    public T findById(ID id) {
        return findById(id, LockModeType.NONE);
    }

    @Override
    public T findById(ID id, LockModeType lock) {
        return entityManager.find(entityClass, id);
    }

    @Override
    public T getReference(ID id) {
        return entityManager.getReference(entityClass, id);
    }

    @Override
    public List<T> findAll() {
        CriteriaQuery<T> query = entityManager.getCriteriaBuilder().createQuery(entityClass);
        query.select(query.from(entityClass));
        return entityManager.createQuery(query).getResultList();
    }

    @Override
    public long getCount() {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> query = cb.createQuery(Long.class);
        query.select(cb.count(query.from(entityClass)));
        return entityManager.createQuery(query).getSingleResult();
    }

    @Override
    public void makePersistent(T entity) {
        entityManager.persist(entity);
    }

    @Override
    public void remove(T entity) {
        entityManager.remove(entity);
    }

    @Override
    public void checkVersion(T entity, VersionUpdate versionUpdate) {
        entityManager.lock(entity,
                versionUpdate == VersionUpdate.INCREMENT
                        ? LockModeType.OPTIMISTIC_FORCE_INCREMENT
                        : LockModeType.OPTIMISTIC);
    }

    protected void setEntityClass(Class<T> entityClass) {
        this.entityClass = entityClass;
    }
}
