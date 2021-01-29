package uz.dev.caveatemptor.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.dev.caveatemptor.dto.BidDto;
import uz.dev.caveatemptor.entity.Bid;
import uz.dev.caveatemptor.entity.Item;
import uz.dev.caveatemptor.entity.User;
import uz.dev.caveatemptor.entity.monetaryamount.MonetaryAmount;
import uz.dev.caveatemptor.service.AuctionService;

@RestController
@RequestMapping("/bid")
@Slf4j
public class BidController {

    private final AuctionService auctionService;

    public BidController(AuctionService auctionService) {
        this.auctionService = auctionService;
    }

    @PostMapping
    BidDto placeNewBid(@RequestBody BidDto bidDto) {
        Item item = auctionService.getItemReference(bidDto.getItemId());
        User bidder = auctionService.getUserReference(bidDto.getBidderId());
        MonetaryAmount amount = new MonetaryAmount(bidDto.getAmountValue(), bidDto.getAmountCurrency());
        Bid bid = new Bid(item, amount, bidder);
        return BidDto.fromBid(auctionService.placeBid(bid));
    }
}
