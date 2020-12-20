package uz.dev.caveatemptor.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

@Embeddable
@Getter
@Setter
public class Address {
    @NotNull
    private String street;
    @NotNull
    private City city;

}
