package com.cmpe281.team12.ccrs.model.payload;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import com.cmpe281.team12.ccrs.model.Coffee.CoffeeType;
import com.cmpe281.team12.ccrs.model.Coffee.CoffeeSize;

import java.util.Objects;

public class MakeCoffeeRequest {

    @JsonProperty
    @ApiModelProperty(value = "Coffee Type")
    private CoffeeType coffeeType;

    @JsonProperty
    @ApiModelProperty(value = "Coffee Size")
    private CoffeeSize coffeeSize;

    @JsonProperty
    @ApiModelProperty(value = "Business ID (aka Account ID)")
    private String businessId;

    @JsonProperty
    @ApiModelProperty(value = "Customer ID")
    private Long customerId;

    @JsonCreator
    public MakeCoffeeRequest(CoffeeType coffeeType, CoffeeSize coffeeSize, String businessId, Long customerId) {
        this.coffeeType = coffeeType;
        this.coffeeSize = coffeeSize;
        this.businessId = businessId;
        this.customerId = customerId;
    }

    public CoffeeType getCoffeeType() {
        return coffeeType;
    }

    public void setCoffeeType(CoffeeType coffeeType) {
        this.coffeeType = coffeeType;
    }

    public CoffeeSize getCoffeeSize() {
        return coffeeSize;
    }

    public void setCoffeeSize(CoffeeSize coffeeSize) {
        this.coffeeSize = coffeeSize;
    }

    public String getBusinessId() {
        return businessId;
    }

    public void setBusinessId(String businessId) {
        this.businessId = businessId;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("MakeCoffeeRequest{");
        sb.append("coffeeType=").append(coffeeType);
        sb.append(", coffeeSize=").append(coffeeSize);
        sb.append(", businessId='").append(businessId).append('\'');
        sb.append(", customerId=").append(customerId);
        sb.append('}');
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MakeCoffeeRequest that = (MakeCoffeeRequest) o;
        return coffeeType == that.coffeeType &&
                coffeeSize == that.coffeeSize &&
                Objects.equals(businessId, that.businessId) &&
                Objects.equals(customerId, that.customerId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(coffeeType, coffeeSize, businessId, customerId);
    }
}