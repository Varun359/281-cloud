package com.cmpe281.team12.ccrs.service;

import com.cmpe281.team12.ccrs.model.Coffee;
import com.cmpe281.team12.ccrs.model.CoffeeOrder;
import com.cmpe281.team12.ccrs.model.RobotState;
import com.cmpe281.team12.ccrs.model.payload.UpdateRobotRequest;
import com.cmpe281.team12.ccrs.repository.CoffeeOrderRepository;
import com.cyberbotics.webots.controller.Device;
import com.cyberbotics.webots.controller.Motor;
import com.cyberbotics.webots.controller.Robot;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Wrapper class for a Webots Robot.
 */
public class CoffeeMakerRobot {

    private static final Logger logger = LogManager.getLogger(CoffeeMakerRobot.class);

    private final Robot self;
    private final Motor motor1;  // yaw axis base movement
    private final Motor motor2;  // pitch axis base-arm movement
    private final Motor motor3;  // pitch axis immediate-arm movement
    private final Motor motor4;  // twist rotation immediate-arm movement
    private final Motor motor5;  // gripper up/down movement
    private final Motor motor6;  // gripper twist movement (similar effect as motor4)
    private final Motor gripper1;
    private final Motor gripper2;
    private CoffeeOrderRepository coffeeOrderRepository;

    private static RobotState robotState = RobotState.CREATED;

    public CoffeeMakerRobot(RobotBuilder builder) {
        this.motor1 = builder.motor1;
        this.motor2 = builder.motor2;
        this.motor3 = builder.motor3;
        this.motor4 = builder.motor4;
        this.motor5 = builder.motor5;
        this.motor6 = builder.motor6;
        this.gripper1 = builder.gripper1;
        this.gripper2 = builder.gripper2;
        this.self = builder.self;
        init();
    }

    public Motor getMotor1() {
        return motor1;
    }

    public Motor getMotor2() {
        return motor2;
    }

    public Motor getMotor3() {
        return motor3;
    }

    public Motor getMotor4() {
        return motor4;
    }

    public Motor getMotor5() {
        return motor5;
    }

    public Motor getMotor6() {
        return motor6;
    }

    public Motor getGripper1() {
        return gripper1;
    }

    public Motor getGripper2() {
        return gripper2;
    }

    public Robot getSelf() {
        return self;
    }

    public static RobotState getRobotState() {
        return robotState;
    }

    private void init() {

        String robotName = self.getName();
        int robotMode = self.getMode();
        logger.info("*************** CoffeeMakerRobot::init() - START: " + robotName + "-" + robotMode + " *******************");

        self.setMode(Robot.MODE_SIMULATION, null);

        motor1.setPosition(0.0);
        motor2.setPosition(0.0);
        motor3.setPosition(0.0);
        motor4.setPosition(0.0);
        motor5.setPosition(0.0);
        motor6.setPosition(0.0);

        motor1.setVelocity(1.0);
        motor2.setVelocity(1.0);
        motor3.setVelocity(1.0);
        motor4.setVelocity(1.0);
        motor5.setVelocity(1.0);
        motor6.setVelocity(1.0);

        robotState = RobotState.ENABLED;
        logger.info("*************** CoffeeMakerRobot::init() - DONE *******************");
    }

    public RobotState run(Long orderId, String robotId, RobotService robotService, CoffeeOrderRepository coffeeOrderRepository) {

        logger.info("*************** CoffeeMakerRobot:run(orderId=" + orderId + ", robotId=" + robotId + ") *******************");
        this.coffeeOrderRepository = coffeeOrderRepository;
        robotState = RobotState.RUNNING;

        new Thread(() -> {

            // Update robot table for robot_id record to change from ENABLED to RUNNING
            updateRobotState(robotService, robotId, robotState);

            int numDevices = self.getNumberOfDevices();
            System.out.println("# of devices: " + numDevices);
            for (int i=0; i<numDevices; i++) {
                Device device = self.getDeviceByIndex(i);
                System.out.println("device #" + i + " : " + device.getName());
            }

            // Let robot execute the make-coffee job.
            makeCoffee();

            // Coffee is now done, so update the order to reflect it's new status.
            if (!updateCoffeeOrderDone(orderId)) {
                logger.error("CoffeeMakerRobot:run() -- ERROR: failed to update coffee_order to DONE");
            } else {
                logger.info("CoffeeMakerRobot:run() -- *** FINISHED MAKING COFFEE ***");
            }

            // Update robot table for robot_id record to change from RUNNING to ENABLED
            updateRobotState(robotService, robotId, RobotState.ENABLED);

            /*
            while (step(timeStep) != -1 && robotState != RobotState.PAUSED) {
                System.out.println("ned robot running...");
                try {
                    demo();
                } catch (InterruptedException e) {}
            }
            logger.info(">>>>>> CoffeeMakerRobot::run() -- *** PAUSING ***");
            */
        }).start();
        logger.info(">>>>>> CoffeeMakerRobot::run() THREAD SPAWNED..");

        return robotState;
    }

    public void resetPositions() {
        motor1.setPosition(0.0);
        motor2.setPosition(0.0);
        motor3.setPosition(0.0);
        motor4.setPosition(0.0);
        motor5.setPosition(0.0);
        motor6.setPosition(0.0);
        closeGripper();
        self.step(1500);
    }

    public void makeCoffee() {

        // move arm above the coffee carafe
        adjustBaseYaw(1.56);

        // lower it slightly above the carafe and open the gripper
        adjustBasePitch(0.4);
        openGripper();

        // point the gripper down towards the handle
        adjustGripperUpDown(-0.9);

        // lower the arm even further so it is next to the handle
        adjustBasePitch(0.5);

        // grab the handle
        closeGripper();

        // bring the arm up with the carafe
        adjustBasePitch(0.3);

        // move the carafe into position near the cup
        adjustBaseYaw(0.0);

        // tilt the carafe above the cup
        rotateArm(-0.7);

        // final tilt of the carafe to pour the coffee into the cup
        rotateGripper(-0.8);

        // reverse to put the carafe back to its original place
        rotateGripper(0.0);
        rotateArm(0.0);
        adjustBaseYaw(1.56);
        adjustBasePitch(0.5);
        openGripper();
        adjustGripperUpDown(0.0);
        adjustBasePitch(0.0);
        adjustBaseYaw(0.0);
    }

    private boolean canProceed() {
        boolean canProceed = robotState == RobotState.ENABLED || robotState == RobotState.RUNNING;
        logger.info(">>>>>>>>>>>>>>>>>>>>>>> CoffeeMakerRobot::canProceed() -- " + canProceed + " <<<<<<<<<<<<<<<<<<<<<<<<");
        return canProceed;
    }

    public void adjustBaseYaw(double pos) {
        logger.info(">>>>>>>>>>>>>>>>>>>>>>> CoffeeMakerRobot::adjustBaseYaw() called <<<<<<<<<<<<<<<<<<<<<<<<");
        if (!canProceed()) return;
        motor1.setPosition(pos);
        self.step(1500);
    }

    public void adjustBasePitch(double pos) {
        logger.info(">>>>>>>>>>>>>>>>>>>>>>> CoffeeMakerRobot::adjustBasePitch() called <<<<<<<<<<<<<<<<<<<<<<<<");
        if (!canProceed()) return;
        motor2.setPosition(pos);
        self.step(1500);
    }

    public void adjustArmPitch(double pos) {
        logger.info(">>>>>>>>>>>>>>>>>>>>>>> CoffeeMakerRobot::adjustArmPitch() called <<<<<<<<<<<<<<<<<<<<<<<<");
        if (!canProceed()) return;
        motor3.setPosition(pos);
        self.step(1500);
    }

    public void rotateArm(double pos) {
        logger.info(">>>>>>>>>>>>>>>>>>>>>>> CoffeeMakerRobot::rotateArm() called <<<<<<<<<<<<<<<<<<<<<<<<");
        if (!canProceed()) return;
        motor4.setPosition(pos);
        self.step(1500);
    }

    public void adjustGripperUpDown(double pos) {
        logger.info(">>>>>>>>>>>>>>>>>>>>>>> CoffeeMakerRobot::adjustGripperUpDown() called <<<<<<<<<<<<<<<<<<<<<<<<");
        if (!canProceed()) return;
        motor5.setPosition(pos);
        self.step(1500);
    }

    public void rotateGripper(double pos) {
        logger.info(">>>>>>>>>>>>>>>>>>>>>>> CoffeeMakerRobot::rotateGripper() called <<<<<<<<<<<<<<<<<<<<<<<<");
        if (!canProceed()) return;
        motor6.setPosition(pos);
        self.step(1500);
    }

    public void closeGripper() {
        logger.info(">>>>>>>>>>>>>>>>>>>>>>> CoffeeMakerRobot::closeGripper() called <<<<<<<<<<<<<<<<<<<<<<<<");
        if (!canProceed()) return;
        gripper1.setPosition(-0.01);
        gripper2.setPosition(-0.01);
        self.step(1500);
    }

    public void openGripper() {
        logger.info(">>>>>>>>>>>>>>>>>>>>>>> CoffeeMakerRobot::openGripper() called <<<<<<<<<<<<<<<<<<<<<<<<");
        if (!canProceed()) return;
        gripper1.setPosition(0.01);
        gripper2.setPosition(0.01);
        self.step(1500);
    }

    private Boolean updateCoffeeOrderDone(Long orderId) {
        CoffeeOrder coffeeOrder = coffeeOrderRepository.findById(orderId).orElse(null);
        if (coffeeOrder == null) return false;
        coffeeOrder.setOrderState(Coffee.MakeCoffeeStatus.DONE.name());
        coffeeOrderRepository.save(coffeeOrder);
        return true;
    }

    private void updateRobotState(RobotService robotService, String robotId, RobotState robotState) {
        UpdateRobotRequest request = new UpdateRobotRequest();
        request.setRobotId(robotId);
        request.setState(robotState.name());
        robotService.updateRobotState(request);
        logger.info("CoffeeMakerRobot::updateRobotState() -- updated robotId: " + robotId + ", to state: " + robotState.name());
    }

    public Boolean pause() {
        if (robotState == RobotState.RUNNING) {
            logger.info("*************** CoffeeMakerRobot:pause() *******************");
            robotState = RobotState.PAUSED;
            return true;
        } else {
            logger.warn("CoffeeMakerRobot::pause() -- not running, so pause unnecessary");
        }
        return false;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("CoffeeMakerRobot{");
        sb.append("motor1=").append(motor1);
        sb.append(", motor2=").append(motor2);
        sb.append(", motor3=").append(motor3);
        sb.append(", motor4=").append(motor4);
        sb.append(", motor5=").append(motor5);
        sb.append(", motor6=").append(motor6);
        sb.append(", gripper1=").append(gripper1);
        sb.append(", gripper2=").append(gripper2);
        sb.append('}');
        return sb.toString();
    }

    public static class RobotBuilder {

        private Motor motor1;
        private Motor motor2;
        private Motor motor3;
        private Motor motor4;
        private Motor motor5;
        private Motor motor6;
        private Motor gripper1;
        private Motor gripper2;
        private Robot self;

        public RobotBuilder(Robot r) {
            this.self = r;
        }

        public RobotBuilder motor1(Motor m1) {
            this.motor1 = m1;
            return this;
        }

        public RobotBuilder motor2(Motor m2) {
            this.motor2 = m2;
            return this;
        }

        public RobotBuilder motor3(Motor m3) {
            this.motor3 = m3;
            return this;
        }

        public RobotBuilder motor4(Motor m4) {
            this.motor4 = m4;
            return this;
        }

        public RobotBuilder motor5(Motor m5) {
            this.motor5 = m5;
            return this;
        }

        public RobotBuilder motor6(Motor m6) {
            this.motor6 = m6;
            return this;
        }

        public RobotBuilder gripper1(Motor g1) {
            this.gripper1 = g1;
            return this;
        }

        public RobotBuilder gripper2(Motor g2) {
            this.gripper2 = g2;
            return this;
        }

        public CoffeeMakerRobot build() {
            CoffeeMakerRobot robot = new CoffeeMakerRobot(this);
            return robot;
        }
    }
}