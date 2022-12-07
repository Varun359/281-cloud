package com.cmpe281.team12.ccrs.model.payload;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

public class RobotPositionRequest {

    @JsonProperty
    private String robotId;

    @JsonProperty
    private double pos;

    @JsonCreator
    public RobotPositionRequest(String robotId, double pos) {
        this.robotId = robotId;
        this.pos = pos;
    }

    public String getRobotId() {
        return robotId;
    }

    public void setRobotId(String robotId) {
        this.robotId = robotId;
    }

    public double getPos() {
        return pos;
    }

    public void setPos(double pos) {
        this.pos = pos;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("RobotPosition{");
        sb.append("robotId='").append(robotId).append('\'');
        sb.append(", pos=").append(pos);
        sb.append('}');
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RobotPositionRequest that = (RobotPositionRequest) o;
        return pos == that.pos && Objects.equals(robotId, that.robotId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(robotId, pos);
    }
}