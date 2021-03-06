package uz.dev.caveatemptor.entity;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Columns;
import org.hibernate.annotations.Immutable;
import org.hibernate.annotations.Type;
import uz.dev.caveatemptor.entity.monetaryamount.MonetaryAmount;
import uz.dev.caveatemptor.util.Constants;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Immutable
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
public class Bid implements Serializable {

    @Id
    @GeneratedValue(generator = Constants.ID_GENERATOR)
    private long id;

    @Type(type = "monetary_amount")
    @Columns(columns = {
            @Column(name = "AMOUNT", nullable = false),
            @Column(name = "CURRENCY", nullable = false)
    })
    private MonetaryAmount amount;

    private LocalDateTime createdOn;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(foreignKey = @ForeignKey(name = "FK_ITEM_ID"))
    private Item item;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(foreignKey = @ForeignKey(name = "FK_BIDDER_ID"))
    private User bidder;

    protected Bid() {
    }

    public Bid(Item item, MonetaryAmount amount, User bidder) {
        this.item = item;
        this.amount = amount;
        this.bidder = bidder;
        this.createdOn = LocalDateTime.now();
    }

    public long getId() {
        return id;
    }

    public MonetaryAmount getAmount() {
        return amount;
    }

    public User getBidder() {
        return bidder;
    }

    public Item getItem() {
        return item;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Bid)) return false;
        Bid bid = (Bid) o;
        return getAmount().equals(bid.getAmount()) && Objects.equals(getBidder().getId(), bid.getBidder().getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getAmount(), getBidder().getId());
    }

    @Override
    public String toString() {
        return "Bid made by user with id " + getBidder().getId() + " with amount: " + getAmount();
    }
}
