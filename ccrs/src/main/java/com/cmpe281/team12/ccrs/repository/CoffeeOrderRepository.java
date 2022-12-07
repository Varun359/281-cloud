package com.cmpe281.team12.ccrs.repository;

import com.cmpe281.team12.ccrs.model.CoffeeOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.sql.Date;
import java.util.List;

@Repository
public interface CoffeeOrderRepository extends JpaRepository<CoffeeOrder, Long> {

    @Query(value="SELECT * FROM coffee_order as c WHERE c.order_date >= ?1 AND c.order_date <= ?2 AND business_id=?3 ", nativeQuery = true)
    List<CoffeeOrder> findAllByDateAccount(Date start, Date end, String account);

    @Query(value="SELECT * FROM coffee_order as c WHERE c.order_date=?1 AND business_id=?2 ", nativeQuery = true)
    List<CoffeeOrder> findAllByDateAccount(Date start, String account);

    @Query(value="SELECT * FROM coffee_order as c WHERE business_id=?1 ", nativeQuery = true)
    List<CoffeeOrder> findAllByAccount(String accountId);
}