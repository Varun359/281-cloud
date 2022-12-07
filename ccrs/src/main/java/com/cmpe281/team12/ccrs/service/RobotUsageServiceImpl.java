package com.cmpe281.team12.ccrs.service;

import com.cmpe281.team12.ccrs.model.RobotUsage;
import com.cmpe281.team12.ccrs.repository.RobotUsageRepository;
import com.google.common.collect.Maps;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class RobotUsageServiceImpl implements RobotUsageService {

    private static final Logger logger = LogManager.getLogger(RobotUsageServiceImpl.class);

    private RobotUsageRepository robotUsageRepository;

    @Autowired
    public void setRobotUsageRepository(RobotUsageRepository robotUsageRepository) {
        this.robotUsageRepository = robotUsageRepository;
    }

    @Override
    public Map<String, RobotUsage> getAccountRobotUsage(String accountId) {
        List<RobotUsage> usageData = robotUsageRepository.findByAccountId(accountId);
        Map<String,RobotUsage> results = usageData
                .stream()
                .collect(Collectors.toMap(RobotUsage::getRobotId, Function.identity()));
        logger.info("RobotUsageServiceImpl::getAccountRobotUsage() -- # of results found: " + usageData.size());
        return results;
    }

    @Override
    public RobotUsage setRobotUsage(RobotUsage robotUsage) {
        if (robotUsage.getTrackDate() == null) {
            robotUsage.setTrackDate(new Date(System.currentTimeMillis()));
        }
        return robotUsageRepository.save(robotUsage);
    }

    @Override
    public RobotUsage getRobotUsage(String accountId, String robotId) {
        RobotUsage robotUsage = robotUsageRepository.findByRobotIdAndAccountId(robotId, accountId);
        if (robotUsage == null) {
            logger.warn("RobotUsageServiceImpl::getRobotUsage() -- no robotUsage record found..");
        }
        return robotUsage;
    }
}