package com.cmpe281.team12.ccrs.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import com.cmpe281.team12.ccrs.model.Robot;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Repository;
import java.sql.Date;
import java.util.List;


@Repository
public interface RobotRepository extends JpaRepository<Robot, String> {

    @Query(value="SELECT * FROM robot as r WHERE r.account_id IN (?1) AND r.state = \"ENABLED\"", nativeQuery = true)
    List<Robot> findByEnabledAccountIds(List<String> accountIds);

    @Query(value="SELECT * FROM robot as r WHERE r.account_id = ?1", nativeQuery = true)
    List<Robot> findAllByAccount(String account);

    @Transactional
    @Modifying
    @Query(value="UPDATE robot as r SET r.state=?2, r.updated_at=?3 WHERE r.robot_id = ?1", nativeQuery = true)
    void updateRobotState(String robotId, String state, Date updatedAt);
}