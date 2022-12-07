package com.cmpe281.team12.ccrs.model.payload;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.sql.Date;
import java.util.Objects;

public class CreateInvoiceRequest {

    @JsonProperty
    private String accountId;

    @JsonProperty
    private Date dueDate;

    @JsonProperty
    private Double amountDue;

    @JsonCreator
    public CreateInvoiceRequest(String accountId, Date dueDate, Double amountDue) {
        this.accountId = accountId;
        this.dueDate = dueDate;
        this.amountDue = amountDue;
    }

    public String getAccountId() {
        return accountId;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public Double getAmountDue() {
        return amountDue;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("CreateInvoiceRequest{");
        sb.append("accountId='").append(accountId).append('\'');
        sb.append(", dueDate=").append(dueDate);
        sb.append(", amountDue=").append(amountDue);
        sb.append('}');
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CreateInvoiceRequest that = (CreateInvoiceRequest) o;
        return Objects.equals(accountId, that.accountId) &&
                Objects.equals(dueDate, that.dueDate) &&
                Objects.equals(amountDue, that.amountDue);
    }

    @Override
    public int hashCode() {
        return Objects.hash(accountId, dueDate, amountDue);
    }
}