package uz.dev.caveatemptor.entity.billingdetails;

import uz.dev.caveatemptor.util.Constants;

import javax.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "BD_TYPE", length = 2)
public abstract class BillingDetails {

    @Id
    @GeneratedValue(generator = Constants.ID_GENERATOR)
    private long id;

    public BillingDetails() {
    }

    public long getId() {
        return id;
    }
}
