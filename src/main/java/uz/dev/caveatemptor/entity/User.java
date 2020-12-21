package uz.dev.caveatemptor.entity;

import uz.dev.caveatemptor.entity.zipcode.ZipcodeConverter;
import uz.dev.caveatemptor.util.Constants;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "user")
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
