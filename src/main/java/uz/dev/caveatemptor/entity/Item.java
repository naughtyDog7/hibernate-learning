package uz.dev.caveatemptor.entity;

import org.hibernate.annotations.Columns;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Formula;
import org.hibernate.annotations.Type;
import uz.dev.caveatemptor.entity.monetaryamount.MonetaryAmount;
import uz.dev.caveatemptor.util.Constants;

import javax.persistence.*;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "item")
public class Item {
    @Id
    @GeneratedValue(generator = Constants.ID_GENERATOR)
    private long id;

    @Size(
            min = 2,
            max = 255,
            message = "Name is required: 2 - 255 characters"
    )
    private String name;
    private String description;
    @Formula("substr(description, 1, 12) || '...'")
    private String shortDescription;
    @CreationTimestamp
    private LocalDateTime createdOn;
    private LocalDateTime auctionStart;

    @Future
    private LocalDateTime auctionEnd;
    private boolean verified;
    @Type(type = "monetary_amount_usd")
    @Columns(columns = {
            @Column(name = "INITIAL_PRICE_AMOUNT"),
            @Column(name = "INITIAL_PRICE_CURRENCY", length = 3)
    })
    private MonetaryAmount initialPrice;


    @Type(type = "monetary_amount_eur")
    @Columns(columns = {
            @Column(name = "BUY_NOW_PRICE_AMOUNT"),
            @Column(name = "BUY_NOW_PRICE_CURRENCY", length = 3)
    })
    private MonetaryAmount buyNowPrice;

    @NotNull
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private AuctionType auctionType;

    @ElementCollection
    @CollectionTable(name = "BID")
    private Set<Bid> bids = new HashSet<>();

    private Dimensions dimensions;
    private Weight weight;

    @ElementCollection
    @CollectionTable(name = "IMAGE")
    private Set<Image> images = new HashSet<>();

    public Item() {
    }

    public Item(String name, String description, MonetaryAmount initialPrice, AuctionType auctionType) {
        this.name = name;
        this.description = description;
        this.initialPrice = initialPrice;
        this.auctionType = auctionType;
    }

    public long getId() {
        return id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void addBid(Bid bid) {
        bids.add(bid);
    }

    public void deleteBid(Bid bid) {
        bids.remove(bid);
    }

    public void deleteAllBids() {
        bids.clear();
    }

    public Set<Bid> getBids() {
        return Collections.unmodifiableSet(bids);
    }

    public void setBuyNowPrice(MonetaryAmount buyNowPrice) {
        this.buyNowPrice = buyNowPrice;
    }

    public void setDimensions(Dimensions dimensions) {
        this.dimensions = dimensions;
    }

    public void setWeight(Weight weight) {
        this.weight = weight;
    }

    public void addImage(Image image) {
        images.add(image);
    }

    @Override
    public String toString() {
        return "Item{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", createdOn=" + createdOn +
                ", auctionStart=" + auctionStart +
                ", auctionEnd=" + auctionEnd +
                ", verified=" + verified +
                ", initialPrice=" + initialPrice +
                ", auctionType=" + auctionType +
                '}';
    }
}
