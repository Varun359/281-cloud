package com.cmpe281.team12.ccrs.resource;

import com.cmpe281.team12.ccrs.model.CoffeeOrder;
import com.cmpe281.team12.ccrs.model.payload.MakeCoffeeRequest;
import com.cmpe281.team12.ccrs.model.payload.MakeCoffeeResponse;
import com.cmpe281.team12.ccrs.service.CoffeeService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/coffee")
public class CoffeeController {

    private static final Logger logger = LogManager.getLogger(CoffeeController.class);

    private CoffeeService coffeeService;

    @Autowired
    public void setCoffeeService(CoffeeService coffeeService) {
        this.coffeeService = coffeeService;
    }

    /**
     * Initiates an order to make coffee by directing the request to a robot within a specific account (business).
     * Authorization: customer, business roles
     * @param request The details of the coffee order
     * @return MakeCoffeeResponse details indicating success or failure to start making coffee
     */
    //@PreAuthorize("hasRole('CUSTOMER') or hasRole('BUSINESS')")
    @PostMapping(value = "/order")
    @ApiOperation(value = "Initiates a request a robot to make coffee",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE,
            response = MakeCoffeeResponse.class)
    public MakeCoffeeResponse makeCoffee(@ApiParam(value = "Make Coffee Spec", required = true) @RequestBody MakeCoffeeRequest request) {
        logger.info("CoffeeController::makeCoffee() -- request: " + request);
        MakeCoffeeResponse response = coffeeService.makeCoffee(request);
        logger.info("CoffeeController::makeCoffee() -- response: " + response);
        return response;
    }

    @GetMapping(value = "/order/status", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Get the status of an existing coffee order",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public CoffeeOrder getCoffeeOrderStatus(@ApiParam(value = "Existing order ID to look up", required = true) @RequestParam Long orderId) {
        logger.info("CoffeeController::makeCoffee() -- orderId: " + orderId);
        return coffeeService.getCoffeeOrderStatus(orderId);
    }
}