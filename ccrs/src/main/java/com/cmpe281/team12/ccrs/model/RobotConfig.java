package com.cmpe281.team12.ccrs.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import java.sql.Date;
import java.util.Objects;

@Entity
@Table(name = "robot_config")
@IdClass(RobotConfigId.class)
@ApiModel(description = "Represents a single Robot Config object model")
public class RobotConfig {

    @JsonProperty
    @Id
    @Column(name = "robot_id")
    @ApiModelProperty(notes = "The Robot ID")
    private String robotId;

    @JsonProperty
    @Id
    @Column(name = "robot_key")
    @ApiModelProperty(notes = "A Robot config key")
    private String robotKey;

    @JsonProperty
    @Column(name = "robot_val")
    @ApiModelProperty(notes = "A Robot config value")
    private String robotVal;

    @Column(name = "updated_at")
    @ApiModelProperty(notes = "Date when this robot config was updated")
    private Date updatedAt;

    public RobotConfig() {}

    public String getRobotId() {
        return robotId;
    }

    public void setRobotId(String robotId) {
        this.robotId = robotId;
    }

    public String getRobotKey() {
        return robotKey;
    }

    public void setRobotKey(String robotKey) {
        this.robotKey = robotKey;
    }

    public String getRobotVal() {
        return robotVal;
    }

    public void setRobotVal(String robotVal) {
        this.robotVal = robotVal;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("RobotConfig{");
        sb.append("robotId='").append(robotId).append('\'');
        sb.append(", robotKey='").append(robotKey).append('\'');
        sb.append(", robotVal='").append(robotVal).append('\'');
        sb.append(", updatedAt=").append(updatedAt);
        sb.append('}');
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RobotConfig that = (RobotConfig) o;
        return Objects.equals(robotId, that.robotId) &&
                Objects.equals(robotKey, that.robotKey) &&
                Objects.equals(robotVal, that.robotVal) &&
                Objects.equals(updatedAt, that.updatedAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(robotId, robotKey, robotVal, updatedAt);
    }
}