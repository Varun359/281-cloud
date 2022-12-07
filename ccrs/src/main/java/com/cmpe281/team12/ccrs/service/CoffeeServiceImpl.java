package com.cmpe281.team12.ccrs.service;

import com.cmpe281.team12.ccrs.model.CoffeeOrder;
import com.cmpe281.team12.ccrs.model.Robot;
import com.cmpe281.team12.ccrs.model.RobotState;
import com.cmpe281.team12.ccrs.model.RobotUsage;
import com.cmpe281.team12.ccrs.repository.CoffeeOrderRepository;
import com.cmpe281.team12.ccrs.model.payload.MakeCoffeeRequest;
import com.cmpe281.team12.ccrs.model.payload.MakeCoffeeResponse;
import com.cmpe281.team12.ccrs.model.payload.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.cmpe281.team12.ccrs.model.Coffee.MakeCoffeeStatus;

import java.sql.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CoffeeServiceImpl implements CoffeeService {

    private static final Logger logger = LogManager.getLogger(CoffeeServiceImpl.class);

    private static final int DEFAULT_START_TIME = 8;
    private static final int DEFAULT_END_TIME = 17;

    private RobotSupervisor robotSupervisor;
    private RobotService robotService;
    private CoffeeOrderRepository coffeeOrderRepository;
    private RobotUsageService robotUsageService;

    @Autowired
    public void setRobotSupervisor(RobotSupervisor robotSupervisor) {
        this.robotSupervisor = robotSupervisor;
    }

    @Autowired
    public void setRobotService(RobotService robotService) {
        this.robotService = robotService;
    }

    @Autowired
    public void setRobotUsageService(RobotUsageService robotUsageService) {
        this.robotUsageService = robotUsageService;
    }

    @Autowired
    public void setCoffeeOrderRepository(CoffeeOrderRepository coffeeOrderRepository) {
        this.coffeeOrderRepository = coffeeOrderRepository;
    }

    @Override
    public MakeCoffeeResponse makeCoffee(MakeCoffeeRequest request) {

        // Get the list of robots for a business.
        List<Robot> robots = robotService.getRobotsByAccount(request.getBusinessId());

        // If no robots available, abort.
        if (robots == null || robots.isEmpty()) {
            return respondWithFailure(request, "Zero robots available");
        }

        // Only robots that are ENABLED can be called upon.
        List<Robot> availRobots = robots.stream()
                .filter(x -> x.getState().equals(RobotState.ENABLED.name()))
                .collect(Collectors.toList());

        // If no robots enabled, abort.
        if (availRobots.isEmpty()) {
            return respondWithFailure(request, "Zero robots ENABLED");
        }
        logger.info("CoffeeServiceImpl::makeCoffee() -- # of robots available: " + availRobots.size());

        // Take the first of the available robots to make the coffee.
        Robot selectedRobot = availRobots.get(0);
        String robotId = selectedRobot.getRobotId();
        logger.info("CoffeeServiceImpl::makeCoffee() -- SELECTED robotId: " + robotId);

        // Create a new order record to track it.
        Long orderId = generateCoffeeOrder(request, robotId);
        logger.info("CoffeeServiceImpl::makeCoffee() -- coffee_order record generated, orderId: " + orderId);

        // Dispatch the simulator to make coffee.
        // In a future improvement, we should have a fleet of virtual robots and dispatch to one of them.
        RobotState robotState = robotSupervisor.run(orderId, robotId, robotService);
        logger.info("CoffeeServiceImpl::makeCoffee() -- dispatched robot to make coffee, robotId: "
                + robotId + ", robotState: " + robotState.name());
        if (robotState == RobotState.RUNNING) {

            // Prepare final success message that making coffee is now in progress.
            // (Note: when robotSupervisor.run() is finished, it will update the coffee_order record to DONE,
            // and a subsequent API call to get the status of the order will reflect being finished so it can
            // update the UI accordingly.)
            MakeCoffeeResponse response = new MakeCoffeeResponse(orderId, Response.Status.OK,
                    MakeCoffeeStatus.IN_PROGRESS, request);
            logger.info("CoffeeServiceImpl::makeCoffee() -- FINAL RESPONSE: " + response);

            // Store robot_usage record into MongoDB
            upsertRobotUsage(request.getBusinessId(), robotId);

            // TODO: Store order_history record into MongoDB (lower priority)

            return response;

        } else {
            return respondWithFailure(request, "robotId: " + robotId + " failed to dispatch..");
        }
    }

    @Override
    public CoffeeOrder getCoffeeOrderStatus(Long orderId) {
        return coffeeOrderRepository.findById(orderId).orElse(null);
    }

    private Long generateCoffeeOrder(MakeCoffeeRequest request, String robotId) {

        // Create a new coffee order record to persist into the db.
        CoffeeOrder coffeeOrder = new CoffeeOrder();
        coffeeOrder.setBusinessId(request.getBusinessId());
        coffeeOrder.setRobotId(robotId);
        coffeeOrder.setCustomerId(request.getCustomerId());
        coffeeOrder.setCoffeeSize(request.getCoffeeSize().name());
        coffeeOrder.setCoffeeType(request.getCoffeeType().name());
        coffeeOrder.setOrderDate(new Date(System.currentTimeMillis()));
        coffeeOrder.setOrderState(MakeCoffeeStatus.IN_PROGRESS.name());

        // Save into the db.
        CoffeeOrder dbResult = coffeeOrderRepository.save(coffeeOrder);

        // Return the order ID generated after saving it into the db.
        return dbResult.getOrderId();
    }

    private MakeCoffeeResponse respondWithFailure(MakeCoffeeRequest request, String errorMsg) {
        MakeCoffeeResponse response = new MakeCoffeeResponse(-1L, Response.Status.FAILED,
                MakeCoffeeStatus.FAILED, request);
        logger.error("CoffeeServiceImpl::makeCoffee() -- ERROR: " + errorMsg);
        return response;
    }

    private RobotUsage upsertRobotUsage(String accountId, String robotId) {

        RobotUsage robotUsage = robotUsageService.getRobotUsage(accountId, robotId);

        if (robotUsage != null) {

            // Found an existing MongoDB record, so update it with a new order.
            int numOrders = robotUsage.getNumOrders();
            robotUsage.setNumOrders(numOrders + 1);

        } else {

            // No record exists for this accountId + robotId, so create a new robot usage record.
            robotUsage = new RobotUsage();
            robotUsage.setAccountId(accountId);
            robotUsage.setRobotId(robotId);
            robotUsage.setStartActiveTime(DEFAULT_START_TIME);
            robotUsage.setEndActiveTime(DEFAULT_END_TIME);
            robotUsage.setTrackDate(new Date(System.currentTimeMillis()));
            robotUsage.setNumOrders(1);
        }

        return robotUsageService.setRobotUsage(robotUsage);
    }
}