package uz.dev.caveatemptor.repository;

import org.springframework.stereotype.Repository;
import uz.dev.caveatemptor.entity.Item;
import uz.dev.caveatemptor.entity.ItemBidSummary;
import uz.dev.caveatemptor.entity.Item_;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.ParameterExpression;
import javax.persistence.criteria.Root;
import java.util.List;

@Repository
public class ItemRepositoryImpl extends GenericRepositoryImpl<Item, Long> implements ItemRepository {

    public ItemRepositoryImpl() {
        setEntityClass(Item.class);
    }

    @Override
    public List<Item> findAll(ItemFetchStrategy fetchStrategy) {
        String query = "SELECT i FROM Item i ";
        if (fetchStrategy == ItemFetchStrategy.WITH_BIDS)
            query += "JOIN FETCH i.bids";
        return entityManager.createQuery(query, Item.class).getResultList();
    }

    @Override
    public List<Item> findByName(String name) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Item> query = cb.createQuery(Item.class);
        Root<Item> i = query.from(Item.class);
        ParameterExpression<String> nameParam = cb.parameter(String.class);
        query.select(i).where(cb.equal(i.get(Item_.name), nameParam));
        return entityManager.createQuery("SELECT i FROM Item i WHERE i.name = :name", Item.class)
                .setParameter(nameParam, name)
                .getResultList();
    }

    @Override
    public List<ItemBidSummary> getSummaries() {
        return entityManager.createQuery("SELECT s FROM ItemBidSummary s", ItemBidSummary.class)
                .getResultList();
    }
}
