package uz.dev.caveatemptor.entity;

import uz.dev.caveatemptor.entity.billingdetails.BillingDetails;
import uz.dev.caveatemptor.util.Constants;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

@Entity
public class User {
    @Id
    @GeneratedValue(generator = Constants.ID_GENERATOR)
    private long id;

    @NotNull
    private String username;

    @NotNull
    private String firstName;

    @NotNull
    private String lastName;

    @OneToMany(mappedBy = "buyer")
    private Set<Item> boughtItems = new HashSet<>();

    @OneToOne(
            fetch = FetchType.LAZY,
            optional = false,
            cascade = CascadeType.PERSIST)
    @JoinColumn(unique = true)
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

    public User(String username, String firstName, String lastName) {
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
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
