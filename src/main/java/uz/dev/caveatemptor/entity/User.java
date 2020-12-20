package uz.dev.caveatemptor.entity;

import uz.dev.caveatemptor.entity.zipcode.ZipcodeConverter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @NotNull
    @Column(nullable = false)
    private String username;
    @NotNull
    @Column(nullable = false)
    private String firstName;
    @NotNull
    @Column(nullable = false)
    private String lastName;

    @NotNull
    @AttributeOverrides({
            @AttributeOverride(name = "street", column = @Column(name = "HOME_STREET", nullable = false)),
            @AttributeOverride(name = "city.name", column = @Column(name = "HOME_CITY", nullable = false)),
            @AttributeOverride(name = "city.zipcode", column = @Column(name = "HOME_ZIPCODE", length = 5, nullable = false)),
            @AttributeOverride(name = "city.country", column = @Column(name = "HOME_COUNTRY", nullable = false))
    })
    @Convert(converter = ZipcodeConverter.class,
            attributeName = "city.zipcode")
    private Address homeAddress;

    @NotNull
    @AttributeOverrides({
            @AttributeOverride(name = "street", column = @Column(name = "BILLING_STREET", nullable = false)),
            @AttributeOverride(name = "city.name", column = @Column(name = "BILLING_CITY", nullable = false)),
            @AttributeOverride(name = "city.zipcode", column = @Column(name = "BILLING_ZIPCODE", length = 5, nullable = false)),
            @AttributeOverride(name = "city.country", column = @Column(name = "BILLING_COUNTRY", nullable = false))
    })
    @Convert(converter = ZipcodeConverter.class,
            attributeName = "city.zipcode")
    private Address billingAddress;

}
