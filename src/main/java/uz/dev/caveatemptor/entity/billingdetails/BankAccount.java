package uz.dev.caveatemptor.entity.billingdetails;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Objects;

@Entity
@DiscriminatorValue("BA")
public class BankAccount extends BillingDetails {

    @NotNull
    private String account;

    @NotNull
    private String bankName;

    @NotNull
    private String swift;

    public BankAccount() {
    }

    public BankAccount(String account, String bankName, String swift) {
        this.account = account;
        this.bankName = bankName;
        this.swift = swift;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BankAccount that = (BankAccount) o;
        return account.equals(that.account) && bankName.equals(that.bankName) && swift.equals(that.swift);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), account, bankName, swift);
    }
}
