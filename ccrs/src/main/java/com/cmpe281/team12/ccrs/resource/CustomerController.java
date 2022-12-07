package com.cmpe281.team12.ccrs.resource;


import com.cmpe281.team12.ccrs.service.CustomerService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/customer")
public class CustomerController {

    private CustomerService customerService;

    @Autowired
    public void setCustomerService(CustomerService customerService) {
        this.customerService = customerService;
    }

    @CrossOrigin
    @GetMapping(value = "/activelocations", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Get the active locations for the robots",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Map<String,String>> getActiveAccountLocations() {
        return customerService.getActiveAccountLocations();
    }
}