package uz.dev.caveatemptor.entity;

import org.hibernate.annotations.Immutable;
import org.hibernate.annotations.Subselect;
import org.hibernate.annotations.Synchronize;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Immutable
@Synchronize({"item", "bid"})
@Subselect(
        value = "SELECT i.id AS itemId, i.name, " +
                "count(b.id) as numberOfBids " +
                "FROM item i LEFT OUTER JOIN bid b ON i.id = b.item_Id " +
                "GROUP BY i.id, i.name"
)
public class ItemBidSummary {
    @Id
    private long itemId;
    private String name;
    private long numberOfBids;

    public String getName() {
        return name;
    }

    public long getNumberOfBids() {
        return numberOfBids;
    }

    @Override
    public String toString() {
        return "ItemBidSummary{" +
                "itemId=" + itemId +
                ", name='" + name + '\'' +
                ", numberOfBids=" + numberOfBids +
                '}';
    }
}
