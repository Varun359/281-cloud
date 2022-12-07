package com.cmpe281.team12.ccrs.model.payload;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;

import java.util.Objects;

public class Response {

    public enum Status {

        OK("OK"),
        FAILED("FAILED");

        private String status;

        Status(String status) {
            this.status = status;
        }

        @Override
        public String toString() {
            return status;
        }
    }

    public enum ErrorCode {

        NONE("NONE"),
        COFFEE_REQUEST_FAILED("COFFEE_REQUEST_FAILED"),
        ROBOT_UNAVAILABLE("ROBOT_UNAVAILABLE");

        private String errorCode;

        ErrorCode(String errorCode) {
            this.errorCode = errorCode;
        }

        @Override
        public String toString() {
            return errorCode;
        }
    }

    @JsonProperty
    @ApiModelProperty(notes = "Response Status")
    protected Status status;

    @JsonProperty
    @ApiModelProperty(notes = "Response Error Code (if an error occurred)")
    protected ErrorCode errorCode;

    @JsonProperty
    @ApiModelProperty(notes = "Response Error Message (if an error occurred)")
    protected String errorMsg;

    @JsonCreator
    public Response(Status status, ErrorCode errorCode, String errorMsg) {
        this.status = status;
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Response response = (Response) o;
        return Objects.equals(status, response.status) &&
                Objects.equals(errorCode, response.errorCode) &&
                Objects.equals(errorMsg, response.errorMsg);
    }

    @Override
    public int hashCode() {
        return Objects.hash(status, errorCode, errorMsg);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Response{");
        sb.append("status=").append(status);
        sb.append(", errorCode=").append(errorCode);
        sb.append(", errorMsg='").append(errorMsg).append('\'');
        sb.append('}');
        return sb.toString();
    }
}