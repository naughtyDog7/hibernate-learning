package uz.dev.caveatemptor.entity;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.NaturalId;
import org.hibernate.annotations.NaturalIdCache;
import org.hibernate.envers.NotAudited;
import uz.dev.caveatemptor.entity.billingdetails.BillingDetails;
import uz.dev.caveatemptor.util.Constants;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "users")
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@NaturalIdCache
//@Audited
public class User {

    @Id
    @GeneratedValue(generator = Constants.ID_GENERATOR)
    private long id;

    @NotNull
    @NaturalId
    @Column(updatable = false)
    private String username;

    @NotNull
    private String firstName;

    @NotNull
    private String lastName;

    @NotNull
    private String email;

    @OneToMany(mappedBy = "buyer")
    private Set<Item> boughtItems = new HashSet<>();

    @Column(name = "userRank")
    private int rank;

    @OneToOne(
            fetch = FetchType.LAZY,
            cascade = CascadeType.PERSIST)
    @JoinColumn(foreignKey = @ForeignKey(name = "FK_SHIPPING_ADDRESS_ID"))
    @NotAudited
    private Address shippingAddress;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(foreignKey = @ForeignKey(name = "FK_DEFAULT_BILLING_ID"))
    @NotAudited
    private BillingDetails defaultBilling;

    public User() {
    }

    public User(String username, String firstName, String lastName, String email) {
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

    public void setDefaultBilling(BillingDetails defaultBilling) {
        this.defaultBilling = defaultBilling;
    }

    public void setShippingAddress(Address shippingAddress) {
        this.shippingAddress = shippingAddress;
    }

    public long getId() {
        return id;
    }

    public BillingDetails getDefaultBilling() {
        return defaultBilling;
    }

    public Address getShippingAddress() {
        return shippingAddress;
    }

    public void addBoughtItem(Item item) {
        boughtItems.add(item);
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        User user = (User) o;
        return getUsername().equals(user.getUsername());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getUsername());
    }

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", rank=" + rank +
                '}';
    }
}
