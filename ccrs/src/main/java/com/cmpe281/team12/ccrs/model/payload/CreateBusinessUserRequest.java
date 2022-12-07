package com.cmpe281.team12.ccrs.model.payload;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

public class CreateBusinessUserRequest {

    @JsonProperty
    private String accountId;

    @JsonProperty
    private String fname;

    @JsonProperty
    private String lname;

    @JsonProperty
    private String email;

    @JsonProperty
    private String password;

    @JsonCreator
    public CreateBusinessUserRequest(String accountId, String fname, String lname, String email, String password) {
        this.accountId = accountId;
        this.fname = fname;
        this.lname = lname;
        this.email = email;
        this.password = password;
    }

    public String getAccountId() {
        return accountId;
    }

    public String getFname() {
        return fname;
    }

    public String getLname() {
        return lname;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("CreateBusinessUserRequest{");
        sb.append(", accountId='").append(accountId).append('\'');
        sb.append(", fname='").append(fname).append('\'');
        sb.append(", lname='").append(lname).append('\'');
        sb.append(", email='").append(email).append('\'');
        sb.append(", password='").append(password).append('\'');
        sb.append('}');
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CreateBusinessUserRequest that = (CreateBusinessUserRequest) o;
        return Objects.equals(accountId, that.accountId) &&
                Objects.equals(fname, that.fname) &&
                Objects.equals(lname, that.lname) &&
                Objects.equals(email, that.email) &&
                Objects.equals(password, that.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(accountId, fname, lname, email, password);
    }
}
