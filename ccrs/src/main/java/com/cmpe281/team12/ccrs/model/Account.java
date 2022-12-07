package com.cmpe281.team12.ccrs.model;

import com.cmpe281.team12.ccrs.model.payload.CreateAccountRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import java.sql.Date;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "account")
@ApiModel(description = "Represents a single Account object model")
public class Account {

    @Id
    @Column(name = "account_id")
    @Access(AccessType.PROPERTY)
    @ApiModelProperty(notes = "The account ID for this account")
    private String accountId;

    @Column(name = "created_at")
    @ApiModelProperty(notes = "Date when this account was created")
    private Date createdAt;

    @Column(name = "address")
    @ApiModelProperty(notes = "Street address for account")
    private String address;

    @Column(name = "city")
    @ApiModelProperty(notes = "City for this account")
    private String city;

    @Column(name = "state")
    @ApiModelProperty(notes = "State for this account")
    private String state;

    @Column(name = "zip")
    @ApiModelProperty(notes = "Zipcode for this account")
    private String zip;

    @Column(name = "business_name")
    @ApiModelProperty(notes = "The business name associated with this account")
    private String businessName;

    public String getBusinessName() {
        return businessName;
    }

    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public Account() {
        super();
    }

    public Account(String address, String city, String state, String zip, String businessName) {
        this.accountId = UUID.randomUUID().toString();
        this.createdAt = new Date(System.currentTimeMillis());
        this.address = address;
        this.city = city;
        this.state = state;
        this.zip = zip;
        this.businessName = businessName;
    }

    public Account(CreateAccountRequest request) {
        this(request.getAddress(),
                request.getCity(),
                request.getState(),
                request.getZip(),
                request.getBusinessName());
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Account{");
        sb.append("accountId='").append(accountId).append('\'');
        sb.append(", createdAt=").append(createdAt);
        sb.append(", address='").append(address).append('\'');
        sb.append(", city='").append(city).append('\'');
        sb.append(", state='").append(state).append('\'');
        sb.append(", zip='").append(zip).append('\'');
        sb.append(", businessName='").append(businessName).append('\'');
        sb.append('}');
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Account account = (Account) o;
        return Objects.equals(accountId, account.accountId) &&
                Objects.equals(createdAt, account.createdAt) &&
                Objects.equals(address, account.address) &&
                Objects.equals(city, account.city) &&
                Objects.equals(state, account.state) &&
                Objects.equals(zip, account.zip) &&
                Objects.equals(businessName, account.businessName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(accountId, createdAt, address, city, state, zip, businessName);
    }
}