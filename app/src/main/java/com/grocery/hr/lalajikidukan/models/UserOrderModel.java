package com.grocery.hr.lalajikidukan.models;

import java.sql.Timestamp;

/**
 * Created by vipul on 19/3/17.
 */

public class UserOrderModel {

    String uoc;
    String user;
    String orderDate;
    String updatedDate;
    int orderTotalAmount;
    int shippingCharge;
    OrderStatusEnum status;



    public String getUoc() {
        return uoc;
    }

    public void setUoc(String uoc) {
        this.uoc = uoc;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public String getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(String updatedDate) {
        this.updatedDate = updatedDate;
    }

    public int getOrderTotalAmount() {
        return orderTotalAmount;
    }

    public void setOrderTotalAmount(int orderTotalAmount) {
        this.orderTotalAmount = orderTotalAmount;
    }

    public OrderStatusEnum getStatus() {
        return status;
    }

    public void setStatus(OrderStatusEnum status) {
        this.status = status;
    }

    public int getShippingCharge() {
        return shippingCharge;
    }

    public void setShippingCharge(int shippingCharge) {
        this.shippingCharge = shippingCharge;
    }

    @Override
    public String toString() {
        return "UserOrderModel [uoc=" + uoc + ", user=" + user + ", orderDate=" + orderDate + ", updatedDate="
                + updatedDate + ", orderTotalAmount=" + orderTotalAmount + ", shippingCharge=" + shippingCharge
                + ", status=" + status + "]";
    }

}