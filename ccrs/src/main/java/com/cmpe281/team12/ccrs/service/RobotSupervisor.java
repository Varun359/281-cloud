package com.cmpe281.team12.ccrs.service;

import com.cmpe281.team12.ccrs.model.RobotState;
import com.cmpe281.team12.ccrs.repository.CoffeeOrderRepository;
import com.cyberbotics.webots.controller.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * This represents a simulated robot env that can be called to make coffee based on a customer request.
 * It is used as one or more virtual robots for all allocated robots across every CCRS account, which means
 * that although the real situation will have multiple coffee maker robots, a single env is
 * used to simulate multiple robots across all accounts.
 */
@Service
public class RobotSupervisor extends Supervisor {

    private static final Logger logger = LogManager.getLogger(RobotSupervisor.class);

    private CoffeeMakerRobot coffeeMakerRobot;
    private CoffeeOrderRepository coffeeOrderRepository;

    @Autowired
    public void setCoffeeOrderRepository(CoffeeOrderRepository coffeeOrderRepository) {
        this.coffeeOrderRepository = coffeeOrderRepository;
    }

    private Node r1;
    private Node r2;
    private static final String ROBOT1 = "robot1";
    private static final String ROBOT2 = "robot2";

    public RobotSupervisor() {
        super();
        logger.info(">>>>>>>>>>>>>>>>>>>>>>> RobotSupervisor::CTOR() -- calling init() <<<<<<<<<<<<<<<<<<<<<<<<");
        init();
    }

    public void init() {

        coffeeMakerRobot = new CoffeeMakerRobot.RobotBuilder(this)
                .motor1(getMotor("joint_1"))
                .motor2(getMotor("joint_2"))
                .motor3(getMotor("joint_3"))
                .motor4(getMotor("joint_4"))
                .motor5(getMotor("joint_5"))
                .motor6(getMotor("joint_6"))
                .gripper1(getMotor("joint_base_to_jaw_1"))
                .gripper2(getMotor("joint_base_to_jaw_2"))
                .build();

        r1 = getFromDef(ROBOT1);
        r2 = getFromDef(ROBOT2);
        if (r1 != null && r2 != null) {
            logger.info("*************** RobotSupervisor::init() - DEBUG >>> ROBOT1: " + r1.getBaseTypeName() + ", " + r1.getTypeName());
            logger.info("*************** RobotSupervisor::init() - DEBUG >>> ROBOT2: " + r2.getBaseTypeName() + ", " + r2.getTypeName());
        } else {
            logger.info("*************** RobotSupervisor::init() - DEBUG >>> root is NULL..");
        }

        logger.info("*************** RobotSupervisor::init() - DONE *******************");
    }

    public RobotState run(Long orderId, String robotId, RobotService robotService) {
        return coffeeMakerRobot.run(orderId, robotId, robotService, coffeeOrderRepository);
//        examineNode(r2);
//        return RobotState.RUNNING;
    }

    private void fieldOutput(Field f, int i, int level) {

        String fieldName = f.getName();
        String typeName = f.getTypeName();
        String sfString = "n/a";
        int count = -1;
        boolean bool=false;
        double val = -1;

        if (typeName.startsWith("SFString")) {
            // single-valued field
            sfString = f.getSFString();
        } else if (typeName.startsWith("SFBool")){
            bool = f.getSFBool();
        } else if (typeName.startsWith("SFFloat")){
            val = f.getSFFloat();
        } else if (typeName.startsWith("MF")){
            // multi-valued field
            count = f.getCount();
        }
        if (fieldName.equals("position")) {
            logger.info("@@@@@@@@ ATTEMPTING TO MODIFY POSITION @@@@@@@@");
            f.setSFFloat(1.1);
        }

        String padding = "";
        for (int x=0; x<level; x++) {
            padding += ">>>>>> ";
        }
        logger.info(padding + "EXAMINE_NODE: DEBUG: FIELD #" + (i+1) + " : " + fieldName + ", " + typeName + ", " + count + ", " + sfString + ", bool: " + bool + ", val: " + val);
//        if (nodeName.equals("supervisor")) {
//            f.setSFBool(true);
//        }
    }

    private void expandField(Field f, int pos, int level) {

        fieldOutput(f, pos, level);

        String padding = "";
        for (int x=0; x<level; x++) {
            padding += "===== ";
        }

        if (f.getName().equals("children") || f.getName().equals("device") || f.getName().equals("muscles")) {
            logger.info(padding + "===================== START: " + f.getName() + " (level=" + level + ") ======================");
            int numNodes = f.getCount();
            for (int i = 0; i < numNodes; i++) {
                Node subNode = f.getMFNode(i);
                logger.info("***** NODE #" + (i+1) + " *****");
                for (int j = 0; j < subNode.getNumberOfFields(); j++) {
                    Field subField = subNode.getFieldByIndex(j);
                    expandField(subField, j, level + 1);
                }
            }
            logger.info(padding + "===================== END: " + f.getName() + " (level=" + level + ") ======================");
        } else if (f.getName().equals("jointParameters") || f.getName().equals("endPoint")) {
            logger.info(padding + "===================== START: " + f.getName() + " (level=" + level + ") ======================");
            Node sfNode = f.getSFNode();
            for (int j = 0; j < sfNode.getNumberOfFields(); j++) {
                Field sfField = sfNode.getFieldByIndex(j);
                expandField(sfField, j, level + 1);
            }
            logger.info(padding + "===================== END: " + f.getName() + " (level=" + level + ") ======================");
        }

    }

    private void examineNode(Node n2) {

        int numContacts = n2.getNumberOfContactPoints();
        int numFields = n2.getNumberOfFields();
        int numProtoFields = n2.getProtoNumberOfFields();
        int numCP1 = n2.getNumberOfContactPoints(true);
        int numCP2 = n2.getNumberOfContactPoints(false);
        logger.info("**** EXAMINE_NODE: DEBUG: " + numContacts + ", " + numFields + ", " + numCP1 + ", " + numCP2);

        logger.info("============================== PROTO FIELD ===============================");
        for (int i=0; i<numProtoFields; i++) {
            Field f = n2.getProtoFieldByIndex(i);
            expandField(f, i, 0);
/*
            fieldOutput(f, i);
            if (f.getName().equals("children")) {
                logger.info("=============== PROTO-FIELD CHILDREN *** START *** ================");
                int numMFNodes = f.getCount();
                for (int j=0; j<numMFNodes; j++) {
                    Node mfNode = f.getMFNode(j);
                    logger.info("=============== PROTO-MF-NODE #" + (j+1) + " FIELD ================");
                    for (int k=0; k<mfNode.getNumberOfFields(); k++) {
                        Field kField = mfNode.getFieldByIndex(k);
                        fieldOutput(kField, k);

                        /***** jointParameters *****/
                        /*
                        if (kField.getName().equals("jointParameters")) {
                            Node subNode = kField.getSFNode();
                            logger.info(">>>>>>>>>>>> PROTO-MF-NODE #" + (j+1) + " JOINT FIELDS *** START *** <<<<<<<<<<");
                            for (int x=0; x<subNode.getNumberOfFields(); x++) {
                                Field jointField = subNode.getFieldByIndex(x);
                                fieldOutput(jointField, x);
                                if (jointField.getName().equals("position")) {
                                    logger.info(">>>>>>>>>>>> PROTO-MF-NODE #" + (j+1) + " JOIN POSITION FIELD: " + jointField.getSFFloat());
                                }
                            }
                            logger.info(">>>>>>>>>>>> PROTO-MF-NODE #" + (j+1) + " JOINT FIELDS *** END *** <<<<<<<<<<");
                        }
                        */

                        /***** DEVICE *****/
                        /*
                        if (kField.getName().equals("device")) {
                            int numSubNodes = kField.getCount();
                            for (int m=0; m<numSubNodes; m++) {
                                Node mfSubNode = kField.getMFNode(m);
                                logger.info(">>>>>>>>>>>> PROTO-MF-SUB-NODE #" + (m+1) + " FIELDS *** START *** <<<<<<<<<<");
                                for (int n=0; n<mfSubNode.getNumberOfFields(); n++) {
                                    Field subField = mfSubNode.getFieldByIndex(n);
                                    fieldOutput(subField, n);
                                    if (subField.getName().equals("name") && subField.getSFString().equals("joint_1")) {
                                        //logger.info(">>>>>>> setting joint_1 position to 2.2");
                                        //mfSubNode.setJointPosition(2.2);
                                    }
                                }
                                logger.info(">>>>>>>>>>>> PROTO-MF-SUB-NODE #" + (m+1) + " FIELDS *** END *** <<<<<<<<<<");
                            }
                        }
                        */
/*
                        if (kField.getName().equals("endPoint")) {
                            Node endPointNode = kField.getSFNode();
                            logger.info(">>>>>>>>>>>> PROTO-MF-NODE #" + (j+1) + " ENDPOINT FIELDS *** START *** <<<<<<<<<<");
                            for (int x=0; x<endPointNode.getNumberOfFields(); x++) {
                                Field epField = endPointNode.getFieldByIndex(x);
                                fieldOutput(epField, x);
                                if (epField.getName().equals("children")) {
                                }
                            }
                            logger.info(">>>>>>>>>>>> PROTO-MF-NODE #" + (j+1) + " ENDPOINT FIELDS *** END *** <<<<<<<<<<");
                        }
                        if (kField.getName().equals("position")) {
                            double position = kField.getSFFloat();
                            logger.info(">>>>>>>>>>>> PROTO-MF-NODE #" + (j+1) + " FIELD #" + k + " POSITION ===> " + position + " <<<<<<<<<<");
                        }
                    }
                }
                logger.info("=============== PROTO-FIELD CHILDREN *** END *** ================");
            }
 */
        }

        // properties of the Ned robot
        logger.info("============================== NON-PROTO FIELD ===============================");
        for (int i=0; i<numFields; i++) {
            Field f = n2.getFieldByIndex(i);
            fieldOutput(f, i, 0);
        }

//        Field n1SupField = n1.getField("supervisor");
//        n1SupField.setSFBool(false);
    }

    public void resetPositions() {
        coffeeMakerRobot.resetPositions();
    }

    public void adjustBaseYaw(double pos) {
        logger.info(">>>>>>>>>>>>>>>>>>>>>>> RobotSupervisor::adjustBaseYaw() called <<<<<<<<<<<<<<<<<<<<<<<<");
        coffeeMakerRobot.adjustBaseYaw(pos);
    }

    public void adjustBasePitch(double pos) {
        logger.info(">>>>>>>>>>>>>>>>>>>>>>> RobotSupervisor::adjustBasePitch() called <<<<<<<<<<<<<<<<<<<<<<<<");
        coffeeMakerRobot.adjustBasePitch(pos);
    }

    public void adjustArmPitch(double pos) {
        logger.info(">>>>>>>>>>>>>>>>>>>>>>> RobotSupervisor::adjustArmPitch() called <<<<<<<<<<<<<<<<<<<<<<<<");
        coffeeMakerRobot.adjustArmPitch(pos);
    }

    public void rotateArm(double pos) {
        logger.info(">>>>>>>>>>>>>>>>>>>>>>> RobotSupervisor::rotateArm() called <<<<<<<<<<<<<<<<<<<<<<<<");
        coffeeMakerRobot.rotateArm(pos);
    }

    public void adjustGripperUpDown(double pos) {
        logger.info(">>>>>>>>>>>>>>>>>>>>>>> RobotSupervisor::adjustGripperUpDown() called <<<<<<<<<<<<<<<<<<<<<<<<");
        coffeeMakerRobot.adjustGripperUpDown(pos);
    }

    public void rotateGripper(double pos) {
        logger.info(">>>>>>>>>>>>>>>>>>>>>>> RobotSupervisor::rotateGripper() called <<<<<<<<<<<<<<<<<<<<<<<<");
        coffeeMakerRobot.rotateGripper(pos);
    }

    public void closeGripper() {
        logger.info(">>>>>>>>>>>>>>>>>>>>>>> RobotSupervisor::closeGripper() called <<<<<<<<<<<<<<<<<<<<<<<<");
        coffeeMakerRobot.closeGripper();
    }

    public void openGripper() {
        logger.info(">>>>>>>>>>>>>>>>>>>>>>> RobotSupervisor::openGripper() called <<<<<<<<<<<<<<<<<<<<<<<<");
        coffeeMakerRobot.openGripper();
    }

    public Boolean pause() {
        return coffeeMakerRobot.pause();
    }
}