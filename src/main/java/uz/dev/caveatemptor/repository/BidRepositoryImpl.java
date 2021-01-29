package uz.dev.caveatemptor.repository;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import uz.dev.caveatemptor.entity.Bid;
import uz.dev.caveatemptor.entity.Item;

@Repository
@Slf4j
public class BidRepositoryImpl extends GenericRepositoryImpl<Bid, Long> implements BidRepository {
    public BidRepositoryImpl() {
        setEntityClass(Bid.class);
    }

    @Override
    public Bid getHighestBid(Item item) {
        return entityManager
                .createQuery("SELECT b FROM Bid b WHERE b.item = :item " +
                "ORDER BY b.amount.value DESC", Bid.class)
                .setParameter("item", item)
                .setMaxResults(1)
                .getSingleResult();
    }
}
