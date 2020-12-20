package uz.dev.caveatemptor.entity;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Formula;

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
    @GeneratedValue(strategy = GenerationType.AUTO)
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
    private MonetaryAmount initialPrice;

    @NotNull
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private AuctionType auctionType;

    @Access(AccessType.FIELD)
    @OneToMany(mappedBy = "item")
    private Set<Bid> bids = new HashSet<>();

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

    public Set<Bid> getBids() {
        return Collections.unmodifiableSet(bids);
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
