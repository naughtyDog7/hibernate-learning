package uz.dev.caveatemptor.entity;

import lombok.Getter;

import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotNull;

@MappedSuperclass
@Getter
public abstract class Measurement {

    @NotNull
    protected String name;

    @NotNull
    protected String symbol;

    public Measurement() {
    }

    public Measurement(@NotNull String name, @NotNull String symbol) {
        this.name = name;
        this.symbol = symbol;
    }
}
