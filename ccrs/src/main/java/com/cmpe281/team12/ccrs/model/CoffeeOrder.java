package com.cmpe281.team12.ccrs.model;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import javax.persistence.Id;
import java.sql.Date;
import java.util.Objects;

import io.swagger.annotations.ApiModel;

@Entity
@Table(name = "coffee_order")
@ApiModel(description = "Represents a single Coffee Order object model")
public class CoffeeOrder {

    @Id
    @Column(name = "order_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(notes = "The order ID for this order")
    private Long orderId;

    @Column(name = "robot_id")
    private String robotId;

    @Column(name = "business_id")
    @ApiModelProperty(notes = "The account ID for this order")
    private String businessId;

    @Column(name = "customer_id")
    @ApiModelProperty(notes = "The customer ID for this order")
    private Long customerId;

    @Column(name = "coffee_type")
    private String coffeeType;

    @Column(name = "coffee_size")
    private String coffeeSize;

    @Column(name = "order_date")
    private Date orderDate;

    @Column(name = "order_state")
    private String orderState;

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public String getRobotId() {
        return robotId;
    }

    public void setRobotId(String robotId) {
        this.robotId = robotId;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public String getBusinessId() {
        return businessId;
    }

    public void setBusinessId(String businessId) {
        this.businessId = businessId;
    }

    public String getCoffeeType() {
        return coffeeType;
    }

    public void setCoffeeType(String coffeeType) {
        this.coffeeType = coffeeType;
    }

    public String getCoffeeSize() {
        return coffeeSize;
    }

    public void setCoffeeSize(String coffeeSize) {
        this.coffeeSize = coffeeSize;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public String getOrderState() {
        return orderState;
    }

    public void setOrderState(String orderState) {
        this.orderState = orderState;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("CoffeeOrder{");
        sb.append("orderId=").append(orderId);
        sb.append(", robotId='").append(robotId).append('\'');
        sb.append(", businessId='").append(businessId).append('\'');
        sb.append(", customerId=").append(customerId);
        sb.append(", coffeeType='").append(coffeeType).append('\'');
        sb.append(", coffeeSize='").append(coffeeSize).append('\'');
        sb.append(", orderDate=").append(orderDate);
        sb.append(", orderState='").append(orderState).append('\'');
        sb.append('}');
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CoffeeOrder that = (CoffeeOrder) o;
        return Objects.equals(orderId, that.orderId) &&
                Objects.equals(robotId, that.robotId) &&
                Objects.equals(businessId, that.businessId) &&
                Objects.equals(customerId, that.customerId) &&
                Objects.equals(coffeeType, that.coffeeType) &&
                Objects.equals(coffeeSize, that.coffeeSize) &&
                Objects.equals(orderDate, that.orderDate) &&
                Objects.equals(orderState, that.orderState);
    }

    @Override
    public int hashCode() {
        return Objects.hash(orderId, robotId, businessId, customerId, coffeeType, coffeeSize, orderDate, orderState);
    }
}
