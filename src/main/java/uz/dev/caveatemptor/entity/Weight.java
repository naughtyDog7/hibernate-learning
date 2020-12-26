package uz.dev.caveatemptor.entity;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

@Embeddable
@AttributeOverride(name = "name", column = @Column(name = "WEIGHT_NAME"))
@AttributeOverride(name = "symbol", column = @Column(name = "WEIGHT_SYMBOL"))
public class Weight extends Measurement {

    @NotNull
    @Column(name = "WEIGHT", nullable = false)
    private long value;

    public Weight() {
    }

    public Weight(@NotNull String name, @NotNull String symbol, @NotNull long value) {
        super(name, symbol);
        this.value = value;
    }
}
