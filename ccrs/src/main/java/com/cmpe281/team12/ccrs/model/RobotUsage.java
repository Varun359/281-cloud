package com.cmpe281.team12.ccrs.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.Id;
import java.util.Date;
import java.util.Objects;

public class RobotUsage {

    @Id
    private String id;

    @JsonProperty
    private String robotId;

    @JsonProperty
    private String accountId;

    @JsonProperty
    private Date trackDate;

    @JsonProperty
    private Integer startActiveTime;

    @JsonProperty
    private Integer endActiveTime;

    @JsonProperty
    private Integer numOrders;

    public RobotUsage() {}

    @JsonCreator
    public RobotUsage(String robotId, String accountId, Date trackDate, Integer startActiveTime, Integer endActiveTime, Integer numOrders) {
        this.robotId = robotId;
        this.accountId = accountId;
        this.trackDate = trackDate;
        this.startActiveTime = startActiveTime;
        this.endActiveTime = endActiveTime;
        this.numOrders = numOrders;
    }

    public String getRobotId() {
        return robotId;
    }

    public void setRobotId(String robotId) {
        this.robotId = robotId;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public Date getTrackDate() {
        return trackDate;
    }

    public void setTrackDate(Date trackDate) {
        this.trackDate = trackDate;
    }

    public Integer getStartActiveTime() {
        return startActiveTime;
    }

    public void setStartActiveTime(Integer startActiveTime) {
        this.startActiveTime = startActiveTime;
    }

    public Integer getEndActiveTime() {
        return endActiveTime;
    }

    public void setEndActiveTime(Integer endActiveTime) {
        this.endActiveTime = endActiveTime;
    }

    public Integer getNumOrders() {
        return numOrders;
    }

    public void setNumOrders(Integer numOrders) {
        this.numOrders = numOrders;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("RobotUsage{");
        sb.append("id='").append(id).append('\'');
        sb.append(", robotId='").append(robotId).append('\'');
        sb.append(", accountId='").append(accountId).append('\'');
        sb.append(", trackDate=").append(trackDate);
        sb.append(", startActiveTime=").append(startActiveTime);
        sb.append(", endActiveTime=").append(endActiveTime);
        sb.append(", numOrders=").append(numOrders);
        sb.append('}');
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RobotUsage that = (RobotUsage) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(robotId, that.robotId) &&
                Objects.equals(accountId, that.accountId) &&
                Objects.equals(trackDate, that.trackDate) &&
                Objects.equals(startActiveTime, that.startActiveTime) &&
                Objects.equals(endActiveTime, that.endActiveTime) &&
                Objects.equals(numOrders, that.numOrders);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, robotId, accountId, trackDate, startActiveTime, endActiveTime, numOrders);
    }
}