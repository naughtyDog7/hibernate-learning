package uz.dev.caveatemptor.entity;

import lombok.Getter;
import uz.dev.caveatemptor.entity.zipcode.Zipcode;

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
}
