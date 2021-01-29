package uz.dev.caveatemptor.repository;

import uz.dev.caveatemptor.entity.Item;
import uz.dev.caveatemptor.entity.ItemBidSummary;

import java.util.List;

public interface ItemRepository extends GenericRepository<Item, Long> {
    List<Item> findAll(ItemFetchStrategy fetchStrategy);
    List<Item> findByName(String name);
    List<ItemBidSummary> getSummaries();
}
