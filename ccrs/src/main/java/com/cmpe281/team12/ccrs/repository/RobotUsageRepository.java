package com.cmpe281.team12.ccrs.repository;

import com.cmpe281.team12.ccrs.model.RobotUsage;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface RobotUsageRepository extends MongoRepository<RobotUsage,String> {
    RobotUsage findByRobotIdAndAccountId(String robotId, String accountId);
    List<RobotUsage> findByAccountId(String accountId);
}
