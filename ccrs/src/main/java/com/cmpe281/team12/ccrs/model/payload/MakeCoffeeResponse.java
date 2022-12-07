package com.cmpe281.team12.ccrs.model.payload;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import com.cmpe281.team12.ccrs.model.Coffee.MakeCoffeeStatus;

import java.util.Objects;

public class MakeCoffeeResponse extends Response {

    @JsonProperty
    @ApiModelProperty(value = "Coffee Type")
    private Long orderId;

    @JsonProperty
    @ApiModelProperty(value = "Make Coffee Status: IN_PROGRESS, FAILED, DONE")
    private MakeCoffeeStatus makeCoffeeStatus;

    @JsonProperty
    @ApiModelProperty(value = "The original make coffee request for this response")
    private MakeCoffeeRequest makeCoffeeRequest;

    @JsonCreator
    public MakeCoffeeResponse(Long orderId, Status status,
                              MakeCoffeeStatus makeCoffeeStatus,
                              MakeCoffeeRequest makeCoffeeRequest,
                              ErrorCode errorCode, String errorMsg) {
        super(status, errorCode, errorMsg);
        this.orderId = orderId;
        this.makeCoffeeStatus = makeCoffeeStatus;
        this.makeCoffeeRequest = makeCoffeeRequest;
    }

    public MakeCoffeeResponse(Long orderId, Status status,
                              MakeCoffeeStatus makeCoffeeStatus,
                              MakeCoffeeRequest makeCoffeeRequest) {
        this(orderId, status, makeCoffeeStatus, makeCoffeeRequest, ErrorCode.NONE, "");
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("MakeCoffeeResponse{");
        sb.append("orderId=").append(orderId);
        sb.append(", makeCoffeeStatus=").append(makeCoffeeStatus);
        sb.append(", status=").append(status);
        sb.append(", errorCode=").append(errorCode);
        sb.append(", errorMsg='").append(errorMsg).append('\'');
        sb.append('}');
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        MakeCoffeeResponse that = (MakeCoffeeResponse) o;
        return Objects.equals(orderId, that.orderId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), orderId);
    }
}