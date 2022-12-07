package com.cmpe281.team12.ccrs.model;

import com.cmpe281.team12.ccrs.model.payload.CreateBusinessUserRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import java.sql.Date;
import java.util.Objects;

@Entity
@Table(name = "business_user")
@ApiModel(description = "Represents a single Business User object model")
public class BusinessUser {

    @Id
    @Column(name = "user_id")
    @ApiModelProperty(notes = "The business user ID for this account")
    private Long userId;

    @Column(name = "account_id")
    private String accountId;

    @Column(name = "created_at")
    @ApiModelProperty(notes = "Date when this user was created")
    private Date createdAt;

    @Column(name = "updated_at")
    @ApiModelProperty(notes = "Date when this user was updated")
    private Date updatedAt;

    @Column(name = "fname")
    @ApiModelProperty(notes = "First name")
    private String fname;

    @Column(name = "lname")
    @ApiModelProperty(notes = "Last name")
    private String lname;

    public BusinessUser() {
        super();
    }

    public BusinessUser(String accountId, String fname, String lname) {
        this.accountId = accountId;
        this.fname = fname;
        this.lname = lname;

        Date now = new Date(System.currentTimeMillis());
        this.createdAt = now;
        this.updatedAt = now;
    }

    public BusinessUser(CreateBusinessUserRequest request) {
        this(request.getAccountId(), request.getFname(), request.getLname());
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("BusinessUser{");
        sb.append("userId='").append(userId).append('\'');
        sb.append(", account=").append(accountId);
        sb.append(", createdAt=").append(createdAt);
        sb.append(", updatedAt=").append(updatedAt);
        sb.append(", fname='").append(fname).append('\'');
        sb.append(", lname='").append(lname).append('\'');
        sb.append('}');
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BusinessUser that = (BusinessUser) o;
        return Objects.equals(userId, that.userId) &&
                Objects.equals(accountId, that.accountId) &&
                Objects.equals(createdAt, that.createdAt) &&
                Objects.equals(updatedAt, that.updatedAt) &&
                Objects.equals(fname, that.fname) &&
                Objects.equals(lname, that.lname);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, accountId, createdAt, updatedAt, fname, lname);
    }
}