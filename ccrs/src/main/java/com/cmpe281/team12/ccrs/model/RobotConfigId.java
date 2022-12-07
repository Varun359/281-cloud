package com.cmpe281.team12.ccrs.model;

import java.io.Serializable;
import java.util.Objects;

public class RobotConfigId implements Serializable {

    private String robotId;
    private String robotKey;

    public RobotConfigId() {}

    public RobotConfigId(String robotId, String robotKey) {
        this.robotId = robotId;
        this.robotKey = robotKey;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RobotConfigId that = (RobotConfigId) o;
        return Objects.equals(robotId, that.robotId) && Objects.equals(robotKey, that.robotKey);
    }

    @Override
    public int hashCode() {
        return Objects.hash(robotId, robotKey);
    }
}
