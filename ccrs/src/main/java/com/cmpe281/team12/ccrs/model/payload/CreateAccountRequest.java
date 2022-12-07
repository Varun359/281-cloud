package com.cmpe281.team12.ccrs.model.payload;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

public class CreateAccountRequest {

    @JsonProperty
    private String address;

    @JsonProperty
    private String city;

    @JsonProperty
    private String state;

    @JsonProperty
    private String zip;

    @JsonProperty
    private String businessName;

    @JsonCreator
    public CreateAccountRequest(String address, String city, String state, String zip, String businessName) {
        this.address = address;
        this.city = city;
        this.state = state;
        this.zip = zip;
        this.businessName = businessName;
    }

    public String getAddress() {
        return address;
    }

    public String getCity() {
        return city;
    }

    public String getZip() {
        return zip;
    }

    public String getState() {
        return state;
    }

    public String getBusinessName() {
        return businessName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CreateAccountRequest that = (CreateAccountRequest) o;
        return Objects.equals(address, that.address) &&
                Objects.equals(city, that.city) &&
                Objects.equals(state, that.state) &&
                Objects.equals(zip, that.zip) &&
                Objects.equals(businessName, that.businessName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(address, city, state, zip, businessName);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("CreateAccountRequest{");
        sb.append("address='").append(address).append('\'');
        sb.append(", city='").append(city).append('\'');
        sb.append(", state='").append(state).append('\'');
        sb.append(", zip='").append(zip).append('\'');
        sb.append(", businessName='").append(businessName).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
