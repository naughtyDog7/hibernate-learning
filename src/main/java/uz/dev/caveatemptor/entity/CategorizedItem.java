package uz.dev.caveatemptor.entity;

import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "CATEGORY_ITEM")
public class CategorizedItem {

    @Embeddable
    static class Id implements Serializable {
        @Column(name = "CATEGORY_ID")
        private long categoryId;
        @Column(name = "ITEM_ID")
        private long itemId;

        public Id() {
        }

        public Id(long categoryId, long itemId) {
            this.categoryId = categoryId;
            this.itemId = itemId;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Id id = (Id) o;
            return categoryId == id.categoryId && itemId == id.itemId;
        }

        @Override
        public int hashCode() {
            return Objects.hash(categoryId, itemId);
        }
    }

    @EmbeddedId
    private Id id = new Id();

    @Column(updatable = false)
    @NotNull
    private String addedBy;

    @Column(updatable = false, nullable = false)
    @CreationTimestamp
    private LocalDateTime addedOn;

    @ManyToOne
    @JoinColumn(
            name = "CATEGORY_ID",
            insertable = false, updatable = false
    )
    private Category category;

    @ManyToOne
    @JoinColumn(
            name = "ITEM_ID",
            insertable = false, updatable = false
    )
    private Item item;

    protected CategorizedItem() {
    }

    public CategorizedItem(String addedByUsername, Category category, Item item) {
        this.category = category;
        this.item = item;
        this.addedBy = addedByUsername;

        this.id.categoryId = category.getId();
        this.id.itemId = item.getId();
    }
}
