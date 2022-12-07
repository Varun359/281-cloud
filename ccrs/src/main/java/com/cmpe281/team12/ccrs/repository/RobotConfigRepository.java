package com.cmpe281.team12.ccrs.repository;

import com.cmpe281.team12.ccrs.model.RobotConfig;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RobotConfigRepository extends JpaRepository<RobotConfig, String> {

    @Query(value="SELECT * FROM robot_config as rc WHERE rc.robot_id = ?1", nativeQuery = true)
    List<RobotConfig> findByRobotId(String robotId);
}
