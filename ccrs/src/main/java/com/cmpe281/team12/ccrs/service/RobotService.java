package com.cmpe281.team12.ccrs.service;

import com.cmpe281.team12.ccrs.model.Robot;
import com.cmpe281.team12.ccrs.model.payload.CreateRobotRequest;
import com.cmpe281.team12.ccrs.model.payload.RobotConfigRequest;
import com.cmpe281.team12.ccrs.model.payload.RobotPositionRequest;
import com.cmpe281.team12.ccrs.model.payload.UpdateRobotRequest;

import java.util.List;
import java.util.Map;

public interface RobotService {

    List<Robot> rentRobots(CreateRobotRequest createRobotRequest);
    List<Robot> getRobotsByAccount(String accountId);
    Robot updateRobotState(UpdateRobotRequest robot);
    String getRobotState(String robotId);
    List<Robot> getAllRobots();
    Map<String,String> pauseRobot(String robotId);

    Map<String,String> adjustBaseYaw(RobotPositionRequest req);
    Map<String, String> adjustBasePitch(RobotPositionRequest req);
    Map<String,String> adjustArmPitch(RobotPositionRequest req);
    Map<String,String> rotateArm(RobotPositionRequest req);
    Map<String,String> adjustGripperUpDown(RobotPositionRequest req);
    Map<String,String> rotateGripper(RobotPositionRequest req);
    Map<String,String> closeGripper(String robotId);
    Map<String,String> openGripper(String robotId);
    void resetPositions(String robotId);

    Map<String,String> getRobotConfig(String robotId);
    Boolean setRobotConfig(RobotConfigRequest request);
}