package com.cmpe281.team12.ccrs.model.payload;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CreateRobotRequest {

    @JsonProperty
    private int numRobots;

    @JsonProperty
    private String accountId;

    @JsonProperty
    private String type; //"RENT" or "LEASE"

    public int getNumRobots() {
        return numRobots;
    }

    public void setNumRobots(int numRobots) {
        this.numRobots = numRobots;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}


