package uz.dev.caveatemptor.controller;

import org.springframework.web.bind.annotation.*;
import uz.dev.caveatemptor.dto.ItemNameUpdate;
import uz.dev.caveatemptor.entity.Item;
import uz.dev.caveatemptor.repository.ItemFetchStrategy;
import uz.dev.caveatemptor.service.AuctionService;

import java.util.List;

@RestController
@RequestMapping("/item")
public class ItemController {

    private final AuctionService auctionService;

    public ItemController(AuctionService auctionService) {
        this.auctionService = auctionService;
    }

    @GetMapping
    List<Item> getAllItems() {
        return auctionService.getItems(ItemFetchStrategy.STANDARD);
    }

    @PatchMapping
    Item updateItemName(@RequestBody ItemNameUpdate nameUpdate) {
        Item item = auctionService.getItem(nameUpdate.getId());
        item.setName(nameUpdate.getName());
        return auctionService.saveItem(item);
    }
}
