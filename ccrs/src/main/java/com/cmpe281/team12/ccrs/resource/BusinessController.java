package com.cmpe281.team12.ccrs.resource;


import com.cmpe281.team12.ccrs.model.CoffeeOrder;
import com.cmpe281.team12.ccrs.model.payload.StatsResponse;
import com.cmpe281.team12.ccrs.service.BusinessService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;

import java.sql.Date;
import java.util.List;

@RestController
@RequestMapping("/business")
public class BusinessController {
    private static final Logger logger = LogManager.getLogger(BusinessController.class);

    private BusinessService businessService;

    @Autowired
    public void setBusinessService(BusinessService businessService) {
        this.businessService = businessService;
    }

    @PreAuthorize("hasRole('BUSINESS') or hasRole('ADMIN')")
    @GetMapping(value = "/salesbytype",  produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "total sales by type",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE,
            response = StatsResponse.class)
    public StatsResponse getSalesType(@ApiParam(value = "get Total Sales by Type as given date, account(business id)", required = true)
                                           @RequestParam String accountId, @RequestParam Date startDate, @RequestParam Date endDate) {
        logger.info("BusinessController::getSalesType() " + accountId + startDate + endDate);
        StatsResponse response = businessService.getSalesType(accountId, startDate , endDate);
        logger.info("BusinessController::getSalesType() -- response: " + response);
        return response;
    }

    @PreAuthorize("hasRole('BUSINESS') or hasRole('ADMIN')")
    @GetMapping(value = "/salesbyrobot",  produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "total sales by robots",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE,
            response = StatsResponse.class)
    public StatsResponse getSalesRobots(@ApiParam(value = "get Total Sales by Robots as given date, account(business id)", required = true)
                                             @RequestParam String accountId, @RequestParam Date date) {
        logger.info("BusinessController::getSalesRobots() -- request: " + accountId + " date: "+ date);
        StatsResponse response = businessService.getSalesRobots(accountId, date);
        logger.info("BusinessController::getSalesRobots() -- response: " + response);
        return response;
    }

    @PreAuthorize("hasRole('BUSINESS') or hasRole('ADMIN')")
    @GetMapping(value = "/allorders", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Fetches all known all orders history for a specified account",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public List<CoffeeOrder> getAllOrdersForAccount(@ApiParam(value = "Existing account ID of users to look up", required = true) @RequestParam String accountId) {
        logger.info("BusinessController::getAllOrdersForAccount() -- accountId: " + accountId);
        return businessService.getAllOrdersForAccount(accountId);
    }

    @PreAuthorize("hasRole('BUSINESS') or hasRole('ADMIN')")
    @GetMapping(value = "/robots", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Fetches all known business users for a specified account",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public StatsResponse getRobotsOfBusiness() {
        logger.info("BusinessController::getRobotsOfBusiness()");
        return businessService.getRobotsOfBusiness();
    }
}
