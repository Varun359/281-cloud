package com.cmpe281.team12.ccrs.service;

import com.cmpe281.team12.ccrs.model.CoffeeOrder;
import com.cmpe281.team12.ccrs.model.payload.StatsResponse;

import java.util.List;
import java.sql.Date;

public interface BusinessService {
    StatsResponse getSalesType(String businessId, Date startDate, Date endDate);
    StatsResponse getSalesRobots(String businessId, Date date);
    List<CoffeeOrder> getAllOrdersForAccount(String accountId);
    StatsResponse getRobotsOfBusiness();
}
