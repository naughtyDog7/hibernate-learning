package uz.dev.caveatemptor.entity;

import org.hibernate.annotations.*;
import uz.dev.caveatemptor.dao.listener.PersistEntityListener;
import uz.dev.caveatemptor.entity.monetaryamount.MonetaryAmount;
import uz.dev.caveatemptor.util.Constants;

import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.Table;
import javax.persistence.*;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "item")
@EntityListeners(PersistEntityListener.class)
@Check(constraints = "AUCTIONEND > AUCTIONSTART")
//@Audited
@Filter(name = Constants.Filters.LIMIT_BY_USER_RANK,
        condition = ":" + Constants.QueryParams.CURRENT_USER_RANK +
                " >= (SELECT u.RANK FROM Users u where u.ID = SELLER_ID)")
public class Item {
    @Id
    @GeneratedValue(generator = Constants.ID_GENERATOR)
    private long id;

    @Version
    private long version;

    @Size(
            min = 2,
            max = 255,
            message = "Name is required: 2 - 255 characters"
    )
    private String name;
    private String description;
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

    private Dimensions dimensions;
    private Weight weight;

    @ElementCollection
    @CollectionTable(name = "IMAGE")
    private Set<Image> images = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SELLER_ID", foreignKey = @ForeignKey(name = "FK_SELLER_ID"))
    @NotNull
    private User seller;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinTable(
            name = "ITEM_BUYER",
            joinColumns = @JoinColumn(name = "ITEM_ID"),
            inverseJoinColumns = @JoinColumn(nullable = false),
            foreignKey = @ForeignKey(name = "FK_ITEM_ID"),
            inverseForeignKey = @ForeignKey(name = "FK_BUYER_ID")
    )
    private User buyer;

    public Item() {
    }

    public Item(String name, String description, MonetaryAmount initialPrice, AuctionType auctionType, User seller) {
        this.name = name;
        this.description = description;
        this.initialPrice = initialPrice;
        this.auctionType = auctionType;
        this.seller = seller;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public void setBuyer(User buyer) {
        this.buyer = buyer;
        buyer.addBoughtItem(this);
    }

    public User getBuyer() {
        return buyer;
    }

    public void addImage(Image image) {
        images.add(image);
    }

    @Override
    public String toString() {
        return "Item '" + getName();
    }
}
