package uz.dev.caveatemptor.entity;

import lombok.ToString;
import uz.dev.caveatemptor.entity.billingdetails.BillingDetails;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@ToString
public class User {
    @Id
    private long id;

    @NotNull
    private String username;

    @NotNull
    private String firstName;

    @NotNull
    private String lastName;


    public User() {
    }

    @OneToOne(fetch = FetchType.LAZY,
            optional = false)
    @PrimaryKeyJoinColumn
    private Address shippingAddress;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    private BillingDetails defaultBilling;

    public User(long id, String username, String firstName, String lastName) {
        this.id = id;
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
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
}
