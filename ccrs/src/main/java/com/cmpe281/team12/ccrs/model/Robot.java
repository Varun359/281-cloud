package com.cmpe281.team12.ccrs.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import java.sql.Date;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "robot")
@ApiModel(description = "Represents a single Robot object model")
public class Robot {

    @Id
    @Column(name = "robot_id")
    //@GeneratedValue(strategy = GenerationType.AUTO)
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(notes = "The Robot ID")
    private String robotId;

    @Column(name = "account_id")
    @ApiModelProperty(notes = "The Account Id is associated with Robot")
    private String accountId;

    @Column(name = "state")
    @ApiModelProperty(notes = "Robot state")
    private String state;

    @Column(name = "created_at")
    @ApiModelProperty(notes = "Date when this robot was created")
    private Date createdAt;

    @Column(name = "updated_at")
    @ApiModelProperty(notes = "Date when this robot was updated")
    private Date updatedAt;

    public Robot() {
        super();
    }

    public Robot(String accountId, String state) {
        this.robotId = UUID.randomUUID().toString();
        this.accountId = accountId;
        this.state = state;

        Date now = new Date(System.currentTimeMillis());
        this.createdAt = now;
        this.updatedAt = now;
    }

    public String getRobotId() {
        return robotId;
    }

    public void setRobotId(String robotId) {
        this.robotId=robotId;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Robot that = (Robot) o;
        return Objects.equals(robotId, that.robotId) &&
                Objects.equals(accountId, that.accountId) &&
                Objects.equals(createdAt, that.createdAt) &&
                Objects.equals(updatedAt, that.updatedAt) &&
                Objects.equals(state, that.state);
    }

    @Override
    public int hashCode() {
        return Objects.hash(robotId, accountId, state, createdAt, updatedAt);
    }

}