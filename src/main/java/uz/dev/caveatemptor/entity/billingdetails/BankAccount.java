package uz.dev.caveatemptor.entity.billingdetails;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@PrimaryKeyJoinColumn(name = "BANK_ACCOUNT_ID")
public class BankAccount extends BillingDetails {

    @NotNull
    private String account;

    @NotNull
    private String bankName;

    @NotNull
    private String swift;

    public BankAccount() {
    }
}
