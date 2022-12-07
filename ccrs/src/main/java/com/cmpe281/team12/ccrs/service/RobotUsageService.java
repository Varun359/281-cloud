package com.cmpe281.team12.ccrs.service;

import com.cmpe281.team12.ccrs.model.RobotUsage;

import java.util.Map;

public interface RobotUsageService {

    // collection of all robot usage for a given account ID
    // key: robotId, val: robot_usage
    Map<String,RobotUsage> getAccountRobotUsage(String accountId);

    RobotUsage setRobotUsage(RobotUsage robotUsage);
    RobotUsage getRobotUsage(String accountId, String robotId);
}