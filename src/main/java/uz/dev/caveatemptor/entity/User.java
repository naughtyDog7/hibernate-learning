package uz.dev.caveatemptor.entity;

import uz.dev.caveatemptor.entity.billingdetails.BillingDetails;
import uz.dev.caveatemptor.util.Constants;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(generator = Constants.ID_GENERATOR)
    private long id;

    @NotNull
    @Column(unique = true, columnDefinition = "username(15)")
    private String username;

    @NotNull
    private String firstName;

    @NotNull
    private String lastName;

    @NotNull
    @Column(unique = true, columnDefinition = "emailAddress(255)")
    private String email;

    @OneToMany(mappedBy = "buyer")
    private Set<Item> boughtItems = new HashSet<>();

    @OneToOne(
            fetch = FetchType.LAZY,
            cascade = CascadeType.PERSIST)
    @JoinColumn(unique = true, foreignKey = @ForeignKey(name = "FK_SHIPPING_ADDRESS_ID"))
    private Address shippingAddress;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    private BillingDetails defaultBilling;

    public User() {
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

    public User(String username, String firstName, String lastName, String email) {
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                '}';
    }
}
