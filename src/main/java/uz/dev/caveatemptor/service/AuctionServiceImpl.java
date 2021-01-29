package uz.dev.caveatemptor.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.dev.caveatemptor.entity.Bid;
import uz.dev.caveatemptor.entity.Item;
import uz.dev.caveatemptor.entity.User;
import uz.dev.caveatemptor.repository.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

@Service
@Transactional
@Slf4j
public class AuctionServiceImpl implements AuctionService {

    private final ItemRepository itemRepository;
    private final BidRepository bidRepository;
    private final UserRepository userRepository;

    @Autowired
    public AuctionServiceImpl(ItemRepository itemRepository,
                              BidRepository bidRepository,
                              UserRepository userRepository) {
        this.itemRepository = itemRepository;
        this.bidRepository = bidRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Item getItem(long id) {
        return itemRepository.findById(id);
    }

    @Override
    public Item saveItem(Item item) {
        itemRepository.makePersistent(item);
        return item;
    }

    @Override
    public List<Item> getItems(ItemFetchStrategy fetch) {
        return itemRepository.findAll(fetch);
    }

    @Override
    public Bid placeBid(Bid bid) {
        Item item = bid.getItem();
        validateBid(bid, item);
        bidRepository.makePersistent(bid);
        itemRepository.checkVersion(item, VersionUpdate.INCREMENT);
        return bid;
    }

    private void validateBid(Bid bid, Item item) {
        Objects.requireNonNull(bid, "Bid cannot be null");
        validateGreaterThanZero(bid);
        Bid highestBid = bidRepository.getHighestBid(item);
        if (highestBid != null) {
            validateGreaterThanHighestBid(bid, highestBid);
        }
    }

    private void validateGreaterThanZero(Bid bid) {
        if (bidAmountLessOrEqualToZero(bid)) {
            throw new IllegalArgumentException("Bid cannot contain negative or zero amount value");
        }
    }

    private boolean bidAmountLessOrEqualToZero(Bid bid) {
        return bid.getAmount().getValue().compareTo(BigDecimal.ZERO) < 1;
    }

    private void validateGreaterThanHighestBid(Bid bid, Bid highestBid) {
        BigDecimal highestBidAmount = highestBid.getAmount().getValue();
        BigDecimal newBidAmount = bid.getAmount().getValue();
        if (newBidLessThanOrEqualToHighest(newBidAmount, highestBidAmount)) {
            throw new IllegalArgumentException("New bid should be greater than highest bid: ("
                    + newBidAmount + " <= " + highestBidAmount + ')');
        }
    }

    private boolean newBidLessThanOrEqualToHighest(BigDecimal newBidAmount, BigDecimal highestBidAmount) {
        return newBidAmount.compareTo(highestBidAmount) < 1;
    }

    @Override
    public Item getItemReference(long itemId) {
        return itemRepository.getReference(itemId);
    }

    @Override
    public User getUserReference(long userId) {
        return userRepository.getOne(userId);
    }
}
