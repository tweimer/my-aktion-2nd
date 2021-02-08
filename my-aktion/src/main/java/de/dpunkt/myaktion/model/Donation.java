package de.dpunkt.myaktion.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@NamedQueries({
        @NamedQuery(name = Donation.findByStatus, query = "SELECT d FROM Donation d WHERE d.status = :status")
})
@Entity
public class Donation {
    public static final String findByStatus = "Donation.findByStatus";

    @NotNull(message = "{donation.amount.notNull}")
    @DecimalMin(value = "1.00", message = "{donation.amount.decimalMin}")
    private Double amount;

    @NotNull
    @Size(min = 5, max = 40, message = "{donation.donorName.size}")
    private String donorName;

    @NotNull
    private Boolean receiptRequested;

    @NotNull
    private Status status;

    @NotNull
    @Embedded
    private Account account;

    @NotNull
    @ManyToOne
    private Campaign campaign;

    @GeneratedValue
    @Id
    private Long id;

    public enum Status {
        TRANSFERRED, IN_PROCESS;
    }

    public Donation() {
        this.account = new Account();
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getDonorName() {
        return donorName;
    }

    public void setDonorName(String donorName) {
        this.donorName = donorName;
    }

    public Boolean getReceiptRequested() {
        return receiptRequested;
    }

    public void setReceiptRequested(Boolean receiptRequested) {
        this.receiptRequested = receiptRequested;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Campaign getCampaign() {
        return campaign;
    }

    public void setCampaign(Campaign campaign) {
        this.campaign = campaign;
    }
}
