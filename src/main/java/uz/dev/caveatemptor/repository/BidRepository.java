package uz.dev.caveatemptor.repository;

import uz.dev.caveatemptor.entity.Bid;
import uz.dev.caveatemptor.entity.Item;

public interface BidRepository extends GenericRepository<Bid, Long> {

    Bid getHighestBid(Item item);
}
