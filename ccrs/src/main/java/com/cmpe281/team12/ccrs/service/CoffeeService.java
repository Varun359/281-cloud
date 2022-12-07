package com.cmpe281.team12.ccrs.service;

import com.cmpe281.team12.ccrs.model.CoffeeOrder;
import com.cmpe281.team12.ccrs.model.payload.MakeCoffeeRequest;
import com.cmpe281.team12.ccrs.model.payload.MakeCoffeeResponse;

import java.util.List;

public interface CoffeeService {
    MakeCoffeeResponse makeCoffee(MakeCoffeeRequest dto);
    CoffeeOrder getCoffeeOrderStatus(Long orderId);
}
