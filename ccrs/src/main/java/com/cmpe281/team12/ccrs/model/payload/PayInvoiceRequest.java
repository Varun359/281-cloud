package com.cmpe281.team12.ccrs.model.payload;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

public class PayInvoiceRequest {

    @JsonProperty
    private Long invoiceId;

    @JsonProperty
    private String accountId;

    @JsonProperty
    private Double paidAmount;

    @JsonCreator
    public PayInvoiceRequest(Long invoiceId, String accountId, Double paidAmount) {
        this.invoiceId = invoiceId;
        this.accountId = accountId;
        this.paidAmount = paidAmount;
    }

    public Long getInvoiceId() {
        return invoiceId;
    }

    public String getAccountId() {
        return accountId;
    }

    public Double getPaidAmount() {
        return paidAmount;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("PayInvoiceRequest{");
        sb.append("invoiceId=").append(invoiceId);
        sb.append(", accountId='").append(accountId).append('\'');
        sb.append(", paidAmount=").append(paidAmount);
        sb.append('}');
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PayInvoiceRequest that = (PayInvoiceRequest) o;
        return Objects.equals(invoiceId, that.invoiceId) && Objects.equals(accountId, that.accountId) && Objects.equals(paidAmount, that.paidAmount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(invoiceId, accountId, paidAmount);
    }
}