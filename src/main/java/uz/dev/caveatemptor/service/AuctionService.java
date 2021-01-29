package uz.dev.caveatemptor.service;

import uz.dev.caveatemptor.entity.Bid;
import uz.dev.caveatemptor.entity.Item;
import uz.dev.caveatemptor.entity.User;
import uz.dev.caveatemptor.repository.ItemFetchStrategy;

import java.util.List;

public interface AuctionService {
    List<Item> getItems(ItemFetchStrategy fetch);

    Item getItem(long id);

    Item saveItem(Item item);

    Bid placeBid(Bid bid);

    Item getItemReference(long itemId);

    User getUserReference(long userId);
}
