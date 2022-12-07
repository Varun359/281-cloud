package com.cmpe281.team12.ccrs.model.payload;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.sql.Date;
import java.util.Objects;

public class GenerateInvoiceRequest {

    @JsonProperty
    private String accountId;

    @JsonProperty
    private Date startDate;

    @JsonProperty
    private Date endDate;

    @JsonCreator
    public GenerateInvoiceRequest(String accountId, Date startDate, Date endDate) {
        this.accountId = accountId;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public String getAccountId() {
        return accountId;
    }

    public Date getStartDate() {
        return startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("GenerateInvoiceRequest{");
        sb.append("accountId='").append(accountId).append('\'');
        sb.append(", startDate=").append(startDate);
        sb.append(", endDate=").append(endDate);
        sb.append('}');
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GenerateInvoiceRequest that = (GenerateInvoiceRequest) o;
        return Objects.equals(accountId, that.accountId) && Objects.equals(startDate, that.startDate) && Objects.equals(endDate, that.endDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(accountId, startDate, endDate);
    }
}