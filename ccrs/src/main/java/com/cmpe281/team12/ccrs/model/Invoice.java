package com.cmpe281.team12.ccrs.model;

import com.cmpe281.team12.ccrs.model.payload.CreateInvoiceRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import java.sql.Date;
import java.util.Objects;

@Entity
@Table(name = "invoice")
@ApiModel(description = "Represents a single Invoice object model")
public class Invoice {

    @Id
    @Column(name = "invoice_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Access(AccessType.PROPERTY)
    @ApiModelProperty(notes = "The invoice ID for this account")
    private Long invoiceId;

    @Column(name = "account_id")
    private String accountId;

    @Column(name = "issued_at")
    @ApiModelProperty(notes = "Date when the invoice was issued")
    private Date issuedAt;

    @Column(name = "due_date")
    @ApiModelProperty(notes = "Date when the invoice is due")
    private Date dueDate;

    @Column(name = "amount")
    @ApiModelProperty(notes = "Amount due")
    private Double amountDue;

    @Column(name = "status")
    @ApiModelProperty(notes = "Invoice Status")
    private String status;

    public Invoice() {
        super();
    }

    public Invoice(CreateInvoiceRequest request) {
        this.accountId = request.getAccountId();
        this.dueDate = request.getDueDate();
        this.amountDue = request.getAmountDue();
        this.issuedAt = new Date(System.currentTimeMillis());
        this.status = InvoiceStatus.OPEN.name();
    }

    public Invoice(String accountId, Date issuedAt, Date dueDate, Double amountDue, String status) {
        this.accountId = accountId;
        this.issuedAt = issuedAt;
        this.dueDate = dueDate;
        this.amountDue = amountDue;
        this.status = status;
    }

    public Long getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(Long invoiceId) {
        this.invoiceId = invoiceId;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public Date getIssuedAt() {
        return issuedAt;
    }

    public void setIssuedAt(Date issuedAt) {
        this.issuedAt = issuedAt;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public Double getAmountDue() {
        return amountDue;
    }

    public void setAmountDue(Double amountDue) {
        this.amountDue = amountDue;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Invoice{");
        sb.append("invoiceId=").append(invoiceId);
        sb.append(", accountId='").append(accountId).append('\'');
        sb.append(", issuedAt=").append(issuedAt);
        sb.append(", dueDate=").append(dueDate);
        sb.append(", amountDue=").append(amountDue);
        sb.append(", status='").append(status).append('\'');
        sb.append('}');
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Invoice invoice = (Invoice) o;
        return Objects.equals(invoiceId, invoice.invoiceId) &&
                Objects.equals(accountId, invoice.accountId) &&
                Objects.equals(issuedAt, invoice.issuedAt) &&
                Objects.equals(dueDate, invoice.dueDate) &&
                Objects.equals(amountDue, invoice.amountDue) &&
                Objects.equals(status, invoice.status);
    }

    @Override
    public int hashCode() {
        return Objects.hash(invoiceId, accountId, issuedAt, dueDate, amountDue, status);
    }
}