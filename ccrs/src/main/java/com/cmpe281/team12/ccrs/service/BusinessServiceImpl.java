package com.cmpe281.team12.ccrs.service;

import com.cmpe281.team12.ccrs.model.CoffeeOrder;
import com.cmpe281.team12.ccrs.model.Robot;
import com.cmpe281.team12.ccrs.model.payload.StatsResponse;
import com.cmpe281.team12.ccrs.repository.AccountRepository;
import com.cmpe281.team12.ccrs.repository.RobotRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.cmpe281.team12.ccrs.repository.CoffeeOrderRepository;

import java.sql.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class BusinessServiceImpl implements BusinessService {
    private static final Logger logger = LogManager.getLogger(CoffeeServiceImpl.class);

    @Autowired
    private CoffeeOrderRepository coffeeOrderRepository;
    @Autowired
    private RobotRepository robotRepository;
    @Autowired
    private AccountRepository accountRepository;

    @Override
    public StatsResponse getSalesType(String businessId, Date startDate, Date endDate) {
        logger.info("BusinessServiceImpl::MakeSalesType() ");
        List<CoffeeOrder> list = coffeeOrderRepository.findAllByDateAccount(startDate,
                endDate, businessId);
        if (list.isEmpty()) return null;

        Map<String, Integer> totalSales = new HashMap<>();
        for	(CoffeeOrder order : list) {
            totalSales.merge(order.getCoffeeType(), 1, Integer::sum);
        }
        return new StatsResponse(totalSales.keySet(), totalSales.values());
    }

    @Override
    public StatsResponse getSalesRobots(String businessId, Date date) {
        logger.info("BusinessServiceImpl::MakeSalesRobots() ");

        List<CoffeeOrder> list = coffeeOrderRepository.findAllByDateAccount(date, businessId);
        if (list.isEmpty()) return null;

        Map<String, Integer> totalSales = new HashMap<>();
        for	(CoffeeOrder order : list) {
            if (totalSales.get(order.getRobotId())==null)
                totalSales.put(order.getRobotId(), 1);
            else
                totalSales.put(order.getRobotId(), totalSales.get(order.getRobotId())+1);
        }
        return new StatsResponse(totalSales.keySet(), totalSales.values());
    }

    @Override
    public List<CoffeeOrder> getAllOrdersForAccount(String accountId) {
        return coffeeOrderRepository.findAllByAccount(accountId);
    }

    @Override
    public StatsResponse getRobotsOfBusiness() {
        logger.info("BusinessServiceImpl::getRobotsOfBusiness()");
        // count number of robots by each business
        List<Robot> robots = robotRepository.findAll();
        Map<String, Integer> ret = new HashMap<>();
        for	(Robot r : robots) {
            if (ret.get(r.getAccountId()) == null)
                ret.put(r.getAccountId(), 1);
            else
                ret.put(r.getAccountId(), ret.get(r.getAccountId()) + 1);
        }
        logger.info("Replace account id with business name");
        // replace account id with business name

        return new StatsResponse(ret.keySet(), ret.values());
    }
}
