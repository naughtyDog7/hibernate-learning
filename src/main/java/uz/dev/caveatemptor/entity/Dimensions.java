package uz.dev.caveatemptor.entity;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

@Embeddable
@AttributeOverride(name = "name", column = @Column(name = "DIMENSIONS_NAME"))
@AttributeOverride(name = "symbol", column = @Column(name = "DIMENSIONS_SYMBOL"))
public class Dimensions extends Measurement {

    @NotNull
    private long depth;

    @NotNull
    private long height;

    @NotNull
    private long width;

    public Dimensions() {
    }

    public Dimensions(String name, String symbol, long depth, long height, long width) {
        super(name, symbol);
        this.depth = depth;
        this.height = height;
        this.width = width;
    }
}
