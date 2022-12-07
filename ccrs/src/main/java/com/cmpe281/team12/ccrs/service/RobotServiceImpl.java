package com.cmpe281.team12.ccrs.service;

import com.cmpe281.team12.ccrs.model.Robot;
import com.cmpe281.team12.ccrs.model.RobotConfig;
import com.cmpe281.team12.ccrs.model.RobotState;
import com.cmpe281.team12.ccrs.model.payload.CreateRobotRequest;
import com.cmpe281.team12.ccrs.model.payload.RobotConfigRequest;
import com.cmpe281.team12.ccrs.model.payload.RobotPositionRequest;
import com.cmpe281.team12.ccrs.model.payload.UpdateRobotRequest;
import com.cmpe281.team12.ccrs.repository.RobotConfigRepository;
import com.cmpe281.team12.ccrs.repository.RobotRepository;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class RobotServiceImpl implements RobotService {

    private static final Logger logger = LogManager.getLogger(RobotServiceImpl.class);

    private RobotSupervisor robotSupervisor;
    private RobotRepository robotRepository;
    private RobotConfigRepository robotConfigRepository;

    @Autowired
    public void setRobotSupervisor(RobotSupervisor robotSupervisor) {
        this.robotSupervisor = robotSupervisor;
    }

    @Autowired
    public void setRobotRepository(RobotRepository robotRepository) {
        this.robotRepository = robotRepository;
    }

    @Autowired
    public void setRobotConfigRepository(RobotConfigRepository robotConfigRepository) {
        this.robotConfigRepository = robotConfigRepository;
    }

    public List<Robot> rentRobots(CreateRobotRequest createRobotRequest) {

        // Create as many robots as requested
        List<Robot> robots = Lists.newArrayList();
        for (int i=0; i<createRobotRequest.getNumRobots(); i++) {
            robots.add(new Robot(createRobotRequest.getAccountId(), RobotState.ENABLED.name()));
        }
        // Save all robot entities as new DB records
        robotRepository.saveAll(robots);

        logger.info("RobotServiceImpl::rentRobots() -- # of robots created/rented: " + robots.size());
        return robots;
    }

    public List<Robot> getRobotsByAccount(String accountId) {
        return robotRepository.findAllByAccount(accountId);
    }

    public Robot updateRobotState(UpdateRobotRequest request) {

        logger.info("RobotServiceImpl::updateState() -- request: " + request);
        Robot robot = robotRepository.findById(request.getRobotId()).orElse(null);

        if (robot != null) {
            robotRepository.updateRobotState(request.getRobotId(), request.getState(), new Date(System.currentTimeMillis()));
            logger.info("RobotServiceImpl::updateState() -- robot update SUCCESS");
            robot.setState(request.getState());
            robot.setUpdatedAt(new Date(System.currentTimeMillis()));
        } else {
            logger.error("ERROR: Robot ID does not exist");
        }

        return robot;
    }

    public String getRobotState(String robotId) {
        return robotRepository.getById(robotId).getState();
    }

    public List<Robot> getAllRobots() {
        return robotRepository.findAll();
    }

    public Map<String,String> pauseRobot(String robotId) {

        logger.info("RobotServiceImpl::pauseRobot() -- robotId: \'" + robotId + "\'");

        Map<String,String> results = Maps.newHashMap();

        if (getRobotState(robotId).equals(RobotState.RUNNING.name())) {
            UpdateRobotRequest req = new UpdateRobotRequest();
            req.setRobotId(robotId);
            req.setState(RobotState.PAUSED.name());
            updateRobotState(req);
            logger.info("RobotServiceImpl::pauseRobot() -- robotId: " + robotId + " PAUSED.");
        }

        robotSupervisor.pause();
        results.put("robotId", robotId);
        results.put("status", RobotState.PAUSED.name());

        return results;
    }

    public Map<String,String> adjustBaseYaw(RobotPositionRequest req) {
        Map<String, String> results = Maps.newHashMap();
        results.put("robotId", req.getRobotId());
        results.put("baseYaw", Double.toString(req.getPos()));
        // TODO: robotId lookup of the actual robot to control, for now, using single simulated robot
        robotSupervisor.adjustBaseYaw(req.getPos());
        return results;
    }

    public Map<String,String> adjustBasePitch(RobotPositionRequest req) {
        Map<String,String> results = Maps.newHashMap();
        results.put("robotId", req.getRobotId());
        results.put("basePitch", Double.toString(req.getPos()));
        // TODO: robotId lookup of the actual robot to control, for now, using single simulated robot
        robotSupervisor.adjustBasePitch(req.getPos());
        return results;
    }

    public Map<String,String> adjustArmPitch(RobotPositionRequest req) {
        Map<String,String> results = Maps.newHashMap();
        results.put("robotId", req.getRobotId());
        results.put("armPitch", Double.toString(req.getPos()));
        // TODO: robotId lookup of the actual robot to control, for now, using single simulated robot
        robotSupervisor.adjustArmPitch(req.getPos());
        return results;
    }

    public Map<String,String> rotateArm(RobotPositionRequest req) {
        Map<String,String> results = Maps.newHashMap();
        results.put("robotId", req.getRobotId());
        results.put("armRotation", Double.toString(req.getPos()));
        // TODO: robotId lookup of the actual robot to control, for now, using single simulated robot
        robotSupervisor.rotateArm(req.getPos());
        return results;
    }

    public Map<String,String> adjustGripperUpDown(RobotPositionRequest req) {
        Map<String,String> results = Maps.newHashMap();
        results.put("robotId", req.getRobotId());
        results.put("gripperUpDown", Double.toString(req.getPos()));
        // TODO: robotId lookup of the actual robot to control, for now, using single simulated robot
        robotSupervisor.adjustGripperUpDown(req.getPos());
        return results;
    }

    public Map<String,String> rotateGripper(RobotPositionRequest req) {
        Map<String,String> results = Maps.newHashMap();
        results.put("robotId", req.getRobotId());
        results.put("gripperRotate", Double.toString(req.getPos()));
        // TODO: robotId lookup of the actual robot to control, for now, using single simulated robot
        robotSupervisor.rotateGripper(req.getPos());
        return results;
    }

    public Map<String,String> closeGripper(String robotId) {
        Map<String,String> results = Maps.newHashMap();
        results.put("robotId", robotId);
        results.put("closeGripper", "true");
        // TODO: robotId lookup of the actual robot to control, for now, using single simulated robot
        robotSupervisor.closeGripper();
        return results;
    }

    public Map<String,String> openGripper(String robotId) {
        Map<String,String> results = Maps.newHashMap();
        results.put("robotId", robotId);
        results.put("openGripper", "true");
        // TODO: robotId lookup of the actual robot to control, for now, using single simulated robot
        robotSupervisor.openGripper();
        return results;
    }
    public void resetPositions(String robotId) {
        robotSupervisor.resetPositions();
    }

    /**
     * Obtains all the key/val config pairs for a specified robot.
     * @param robotId The robot ID to lookup the config settings for.
     * @return Map all key/val config pairs associated with the robot ID
     */
    public Map<String,String> getRobotConfig(String robotId) {
        return robotConfigRepository.findByRobotId(robotId)
                .stream()
                .collect(Collectors.toMap(RobotConfig::getRobotKey, RobotConfig::getRobotVal));
    }

    /**
     * Persists the collection of configuration pairs for the specified robot.
     * @param request the robot ID and collection of config pairs to set in the robot_config table
     * @return Boolean true=success, false=failed
     */
    public Boolean setRobotConfig(RobotConfigRequest request) {

        String robotId = request.getRobotId();
        Map<String,String> configMap = request.getConfigMap();

        if (configMap == null || configMap.isEmpty())
            return false;

        List<RobotConfig> entries = configMap.entrySet().stream().map(x -> {
            RobotConfig robotConfig = new RobotConfig();
            robotConfig.setRobotId(robotId);
            robotConfig.setRobotKey(x.getKey());
            robotConfig.setRobotVal(x.getValue());
            robotConfig.setUpdatedAt(new Date(System.currentTimeMillis()));
            logger.info("RobotServiceImpl::setRobotConfig() -- creating robotConfig entity: " + robotConfig);
            return robotConfig;
        }).collect(Collectors.toList());

        return robotConfigRepository.saveAll(entries).size() > 0;
    }
}