package de.dpunkt.myaktion.model;

import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Embeddable
public class Account {

    @NotNull
    @Size(min = 5, max = 60, message = "{account.name.size}")
    private String name;

    @NotNull
    @Size(min = 4, max = 40, message = "{account.nameOfBank.size}")
    private String nameOfBank;

    @NotNull
    @Pattern(regexp = "[A-Z]{2}[0-9]{2}[A-Z0-9]{12,30}", message = "{account.iban.pattern}")
    private String iban;

    public Account() {
        this(null, null, null);
    }

    public Account(String name, String nameOfBank, String iban) {
        super();
        this.name = name;
        this.nameOfBank = nameOfBank;
        this.iban = iban;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNameOfBank() {
        return nameOfBank;
    }

    public void setNameOfBank(String nameOfBank) {
        this.nameOfBank = nameOfBank;
    }

    public String getIban() {
        return iban;
    }

    public void setIban(String iban) {
        this.iban = iban;
    }


}
