package uz.dev.caveatemptor.entity;

import uz.dev.caveatemptor.entity.zipcode.ZipcodeConverter;
import uz.dev.caveatemptor.util.Constants;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
public class Address {
    @Id
    @GeneratedValue(generator = Constants.ID_GENERATOR)
    private long id;

    @NotNull
    private String street;
    @NotNull
    @AttributeOverride(name = "zipcode", column = @Column(name = "SHIPPING_ZIPCODE", nullable = false))
    @Convert(converter = ZipcodeConverter.class, attributeName = "zipcode")
    private City city;

    public Address() {
    }

    public Address(String street, String city, String country, String zipcode) {
        this.street = street;
        this.city = new City(city, country, zipcode);
    }

    public long getId() {
        return id;
    }
}
