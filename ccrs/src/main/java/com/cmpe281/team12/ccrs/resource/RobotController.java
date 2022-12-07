package com.cmpe281.team12.ccrs.resource;

import com.cmpe281.team12.ccrs.model.Robot;
import com.cmpe281.team12.ccrs.model.payload.CreateRobotRequest;
import com.cmpe281.team12.ccrs.model.payload.RobotConfigRequest;
import com.cmpe281.team12.ccrs.model.payload.RobotPositionRequest;
import com.cmpe281.team12.ccrs.model.payload.UpdateRobotRequest;
import com.cmpe281.team12.ccrs.service.RobotService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.logging.log4j.LogManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/robot")
public class RobotController {

    private static final Logger logger = LogManager.getLogger(AccountController.class);

    private RobotService robotService;

    @Autowired
    public void setRobotService(RobotService robotService) {
        this.robotService = robotService;
    }

    //@PreAuthorize("hasRole('ADMIN')")
    @ApiOperation(value = "Creates new robots",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @PostMapping(value = "/rent")
    public List<Robot> rentRobots(@ApiParam(value = "Create Robot Spec", required = true)
                                 @RequestBody CreateRobotRequest request) {
        logger.info("RobotController::createRobot()-- request: " + request);
        return robotService.rentRobots(request);
    }

    //@PreAuthorize("hasRole('ADMIN')")
    @GetMapping(value = "/account", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Fetches the robots for a supplied account ID",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Robot> getRobotsByAccount(@ApiParam(value = "Existing account ID to look up", required = true) @RequestParam String accountId) {
        logger.info("RobotController::getRobotsByAccount() -- accountId: " + accountId);
        return robotService.getRobotsByAccount(accountId);
    }


    @ApiOperation(value = "Update robot state",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @PostMapping(value = "/state")
    public Robot updateState(@RequestBody UpdateRobotRequest request) {
        return robotService.updateRobotState(request);
    }

    @ApiOperation(value = "Fetches the robot state of a specified robot",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @GetMapping(value = "/state")
    public String getState(@ApiParam(value = "Existing robot ID to look up", required = true) @RequestParam String robotId) {
        return robotService.getRobotState(robotId);
    }

    /**
     * Gets all the accounts known in CCRS.
     * Authorization: admin role
     * @return collection of Robot objects
     */
    //@PreAuthorize("hasRole('ADMIN')")
    @GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Fetches all known Robots",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Robot> getAllRobots() {
        return robotService.getAllRobots();
    }

    @ApiOperation(value = "Pause Robot Operation",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @PostMapping(value = "/pause")
    public Map<String,String> pauseRobot(@ApiParam(value = "Existing robot ID to look up", required = true) @RequestParam String robotId) {
        return robotService.pauseRobot(robotId);
    }

    @ApiOperation(value = "Adjust robot base yaw",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @PostMapping(value = "/base/yaw")
    public Map<String,String> adjustBaseYaw(@RequestBody RobotPositionRequest robotPositionRequest) {
        return robotService.adjustBaseYaw(robotPositionRequest);
    }

    @ApiOperation(value = "Adjust robot base pitch",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @PostMapping(value = "/base/pitch")
    public Map<String,String> adjustBasePitch(@RequestBody RobotPositionRequest robotPositionRequest) {
        return robotService.adjustBasePitch(robotPositionRequest);
    }

    @ApiOperation(value = "Adjust robot arm pitch",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @PostMapping(value = "/arm/pitch")
    public Map<String,String> adjustArmPitch(@RequestBody RobotPositionRequest robotPositionRequest) {
        return robotService.adjustArmPitch(robotPositionRequest);
    }

    @ApiOperation(value = "Rotate robot arm",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @PostMapping(value = "/arm/rotate")
    public Map<String,String> rotateArm(@RequestBody RobotPositionRequest robotPositionRequest) {
        return robotService.rotateArm(robotPositionRequest);
    }

    @ApiOperation(value = "Adjust robot gripper up or down",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @PostMapping(value = "/gripper/updown")
    public Map<String,String> adjustGripperUpDown(@RequestBody RobotPositionRequest robotPositionRequest) {
        return robotService.adjustGripperUpDown(robotPositionRequest);
    }

    @ApiOperation(value = "Rotate robot gripper",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @PostMapping(value = "/gripper/rotate")
    public Map<String,String> rotateGripper(@RequestBody RobotPositionRequest robotPositionRequest) {
        return robotService.rotateGripper(robotPositionRequest);
    }

    @ApiOperation(value = "Close robot gripper",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @PostMapping(value = "/gripper/close")
    public Map<String,String> closeGripper(@ApiParam(value = "Existing robot ID to look up", required = true) @RequestParam String robotId) {
        return robotService.closeGripper(robotId);
    }

    @ApiOperation(value = "Open robot gripper",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @PostMapping(value = "/gripper/open")
    public Map<String,String> openGripper(@ApiParam(value = "Existing robot ID to look up", required = true) @RequestParam String robotId) {
        return robotService.openGripper(robotId);
    }

    @ApiOperation(value = "Reset robot position",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @PostMapping(value = "/reset")
    public void resetPositions(@ApiParam(value = "Existing robot ID to look up", required = true) @RequestParam String robotId) {
        robotService.resetPositions(robotId);
    }

    @ApiOperation(value = "Fetches all config pairs for the specified robot ID",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @GetMapping(value = "/config", produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, String> getRobotConfig(@ApiParam(value = "Existing robot ID to look up", required = true) @RequestParam String robotId) {
        return robotService.getRobotConfig(robotId);
    }

    @ApiOperation(value = "Set the config pairs for a specified robot ID",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @PostMapping(value = "/config")
    public Boolean resetPositions(@ApiParam(value = "Set robot config spec", required = true)
                                      @RequestBody RobotConfigRequest request) {
        return robotService.setRobotConfig(request);
    }
}