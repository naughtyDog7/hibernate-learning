package uz.dev.caveatemptor.entity;

import org.hibernate.annotations.Columns;
import org.hibernate.annotations.Immutable;
import org.hibernate.annotations.Type;
import uz.dev.caveatemptor.entity.audit.Auditable;
import uz.dev.caveatemptor.entity.monetaryamount.MonetaryAmount;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

@Embeddable
@Immutable
public class Bid implements Serializable {

    @Type(type = "monetary_amount")
    @Columns(columns = {
            @Column(name = "AMOUNT", nullable = false),
            @Column(name = "CURRENCY", nullable = false)
    })
    private MonetaryAmount amount;

    private LocalDateTime createdOn;

    @ManyToOne(fetch = FetchType.LAZY)
    private User bidder;

    protected Bid() {
    }

    public Bid(MonetaryAmount amount, User bidder) {
        this.amount = amount;
        this.bidder = bidder;
        this.createdOn = LocalDateTime.now();
    }

    public MonetaryAmount getAmount() {
        return amount;
    }

    public User getBidder() {
        return bidder;
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
}
