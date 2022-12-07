package com.cmpe281.team12.ccrs.model.payload;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

public class UpdateRobotRequest {
    @JsonProperty
    private String robotId;

    @JsonProperty
    private String state;

    @JsonCreator
    public void UpdateRobotRequest(String robotId, String state) {
        this.robotId = robotId;
        this.state = state;
    }

    public String getRobotId() {
        return robotId;
    }

    public void setRobotId(String robotId) {
        this.robotId = robotId;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("UpdateRobotRequest{");
        sb.append("robotId='").append(robotId).append('\'');
        sb.append(", state='").append(state).append('\'');
        sb.append('}');
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UpdateRobotRequest that = (UpdateRobotRequest) o;
        return Objects.equals(robotId, that.robotId) && Objects.equals(state, that.state);
    }

    @Override
    public int hashCode() {
        return Objects.hash(robotId, state);
    }
}
