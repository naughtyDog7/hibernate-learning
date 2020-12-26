package uz.dev.caveatemptor.entity;

import lombok.Getter;
import uz.dev.caveatemptor.entity.zipcode.Zipcode;
import uz.dev.caveatemptor.entity.zipcode.ZipcodeFactory;

import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

@Embeddable
@Getter
public class City {

    @NotNull
    private Zipcode zipcode;

    @NotNull
    private String name;

    @NotNull
    private String country;

    public City() {
    }

    public City(String name, String country, String zipcode) {
        this.name = name;
        this.country = country;
        this.zipcode = ZipcodeFactory.fromString(zipcode);
    }
}
