package uz.dev.caveatemptor.entity;

import org.hibernate.annotations.Columns;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Immutable;
import org.hibernate.annotations.Type;
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

    @CreationTimestamp
    private LocalDateTime createdOn;

    @ManyToOne(fetch = FetchType.LAZY)
    private User bidder;

    protected Bid() {
    }

    public Bid(MonetaryAmount amount, User bidder) {
        this.amount = amount;
        this.bidder = bidder;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Bid bid = (Bid) o;
        return amount.equals(bid.amount) && Objects.equals(createdOn, bid.createdOn);
    }

    @Override
    public int hashCode() {
        return Objects.hash(amount, createdOn);
    }
}
