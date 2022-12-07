package com.cmpe281.team12.ccrs.model.payload;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;
import java.util.Objects;

public class RobotConfigRequest {

    @JsonProperty
    private String robotId;

    @JsonProperty
    private Map<String,String> configMap;

    @JsonCreator
    public RobotConfigRequest(String robotId, Map<String,String> configMap) {
        this.robotId = robotId;
        this.configMap = configMap;
    }

    public String getRobotId() {
        return robotId;
    }

    public Map<String, String> getConfigMap() {
        return configMap;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("RobotConfigRequest{");
        sb.append("robotId='").append(robotId).append('\'');
        sb.append(", configMap=").append(configMap);
        sb.append('}');
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RobotConfigRequest that = (RobotConfigRequest) o;
        return Objects.equals(robotId, that.robotId) && Objects.equals(configMap, that.configMap);
    }

    @Override
    public int hashCode() {
        return Objects.hash(robotId, configMap);
    }
}