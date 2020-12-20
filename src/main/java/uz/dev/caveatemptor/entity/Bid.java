package uz.dev.caveatemptor.entity;

import org.hibernate.annotations.Immutable;
import uz.dev.caveatemptor.entity.monetaryamount.MonetaryAmount;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@Table(name = "bid")
@Immutable
public class Bid {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private MonetaryAmount amount;
    private LocalDateTime createdOn;

    @NotNull
    @ManyToOne
    private Item item;

    public Bid() {
    }

    public Bid(MonetaryAmount amount, LocalDateTime createdOn, @NotNull Item item) {
        this.amount = amount;
        this.createdOn = createdOn;
        this.item = item;
        item.addBid(this);
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }
}
