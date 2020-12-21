package uz.dev.caveatemptor.entity.billingdetails;

import uz.dev.caveatemptor.util.Constants;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class BillingDetails {

    @Id
    @GeneratedValue(generator = Constants.ID_GENERATOR)
    private long id;

    @NotNull
    protected String owner;

    public BillingDetails() {
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getOwner() {
        return owner;
    }

    public long getId() {
        return id;
    }
}
