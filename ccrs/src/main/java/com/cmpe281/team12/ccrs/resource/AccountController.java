package com.cmpe281.team12.ccrs.resource;

import com.cmpe281.team12.ccrs.model.Account;
import com.cmpe281.team12.ccrs.model.BusinessUser;
import com.cmpe281.team12.ccrs.model.RobotUsage;
import com.cmpe281.team12.ccrs.model.payload.CreateAccountRequest;
import com.cmpe281.team12.ccrs.model.payload.CreateBusinessUserRequest;
import com.cmpe281.team12.ccrs.service.AccountService;
import com.cmpe281.team12.ccrs.service.RobotUsageService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * This the API entry point for Account related functionality.
 * The top-level API mapping is "/ccrs" therefore all APIs under
 * here will be "/ccrs/account/..".
 */
@RestController
@RequestMapping("/account")
public class AccountController {

    private static final Logger logger = LogManager.getLogger(AccountController.class);

    private AccountService accountService;
    private RobotUsageService robotUsageService;

    @Autowired
    public void setAccountService(AccountService accountService) {
        this.accountService = accountService;
    }

    @Autowired
    public void setRobotUsageService(RobotUsageService robotUsageService) {
        this.robotUsageService = robotUsageService;
    }

    /**
     * Gets all the accounts known in CCRS.
     * Authorization: admin role
     * @return collection of Account objects
     */
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Fetches all known accounts",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Account> getAllAccounts() {
        return accountService.getAllAccounts();
    }

    /**
     * Gets the data for a specific business account.
     * Authorization: business, admin roles
     * @param accountId The account ID
     * @return Account representing the business
     */
    @PreAuthorize("hasRole('BUSINESS') or hasRole('ADMIN')")
    @GetMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Fetches a specific account",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Account getAccount(@ApiParam(value = "Existing account ID to look up", required = true) @RequestParam String accountId) {
        logger.info("AccountController::getAccount() -- accountId: " + accountId);
        return accountService.getAccount(accountId);
    }

    /**
     * Creates a new account in CCRS.
     * Authorization: public (all roles)
     * @param request The required info to create a new account.
     * @return Account the new Account object
     */
    @PostMapping(value = "/")
    @ApiOperation(value = "Creates a new account",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Account createAccount(@ApiParam(value = "Create Account Spec", required = true)
                                 @RequestBody CreateAccountRequest request) {
        logger.info("AccountController::createAccount() -- request: " + request);
        return accountService.createAccount(request);
    }

    /**
     * Creates a new business user for an existing account.
     * Authorization: business role
     * @param request The required info to create a new business user.
     * @return BusinessUser the new BusinessUser object
     */
    @PostMapping(value = "/user")
    @ApiOperation(value = "Creates a new user for an existing account",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public BusinessUser createBusinessUser(@ApiParam(value = "Create Business User Spec", required = true)
                                           @RequestBody CreateBusinessUserRequest request) {
        logger.info("AccountController::createBusinessUser() -- request: " + request);
        return accountService.createBusinessUser(request);
    }

    /**
     * Gets all the users for a specific account.
     * Authorization: business, admin roles
     * @return List collection of BusinessUser objects
     */
    @PreAuthorize("hasRole('BUSINESS') or hasRole('ADMIN')")
    @GetMapping(value = "/user/all", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Fetches all known business users for a specified account",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public List<BusinessUser> getUsersForAccount(@ApiParam(value = "Existing account ID of users to look up", required = true) @RequestParam String accountId) {
        logger.info("AccountController::getUsersForAccount() -- accountId: " + accountId);
        return accountService.getUsersForAccount(accountId);
    }

    /**
     * Gets a business user for the supplied user ID.
     * Authorization: business, admin roles
     * @return BusinessUser the business user details or none
     */
    @PreAuthorize("hasRole('BUSINESS') or hasRole('ADMIN')")
    @GetMapping(value = "/user", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Fetches the business user for a supplied user ID",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public BusinessUser getBusinessUser(@ApiParam(value = "Existing business user ID to look up", required = true) @RequestParam Long userId) {
        logger.info("AccountController::getBusinessUser() -- userId: " + userId);
        return accountService.getBusinessUser(userId);
    }

//    @PreAuthorize("hasRole('BUSINESS') or hasRole('ADMIN')")
    @GetMapping(value = "/accountusage", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Fetches the robot usage records for the specified account",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, RobotUsage> getAccountRobotUsage(@ApiParam(value = "Existing account ID to look up", required = true) @RequestParam String accountId) {
        logger.info("AccountController::getAccountRobotUsage() -- accountId: " + accountId);
        return robotUsageService.getAccountRobotUsage(accountId);
    }

    //    @PreAuthorize("hasRole('BUSINESS') or hasRole('ADMIN')")
    @GetMapping(value = "/robotusage", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Fetches the robot usage record for a specific robot in an account",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public RobotUsage getRobotUsage(@ApiParam(value = "Existing account ID to look up", required = true) @RequestParam String accountId,
                                    @ApiParam(value = "Existing robot ID to look up", required = true) @RequestParam String robotId) {
        logger.info("AccountController::getRobotUsage() -- accountId: " + accountId + ", robotId: " + robotId);
        return robotUsageService.getRobotUsage(accountId, robotId);
    }

    @PostMapping(value = "/robotusage")
    @ApiOperation(value = "Persists a new robot usage record (into robot_usage MongoDB collection)",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public RobotUsage setRobotUsage(@ApiParam(value = "Create Business User Spec", required = true)
                                           @RequestBody RobotUsage robotUsage) {
        logger.info("AccountController::setRobotUsage() -- robotUsage: " + robotUsage);
        return robotUsageService.setRobotUsage(robotUsage);
    }
}