package com.grocery.hr.lalajikidukan.models;

import java.sql.Timestamp;

/**
 * Created by vipul on 19/3/17.
 */

public class UserOrderModel {

    String uoc;
    String user;
    String orderDate;
    int orderTotalAmount;
    String status;

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

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


    public int getOrderTotalAmount() {
        return orderTotalAmount;
    }

    public void setOrderTotalAmount(int orderTotalAmount) {
        this.orderTotalAmount = orderTotalAmount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }


    @Override
    public String toString() {
        return "UserOrderModel{" +
                "uoc='" + uoc + '\'' +
                ", user='" + user + '\'' +
                ", orderDate='" + orderDate + '\'' +
                ", orderTotalAmount=" + orderTotalAmount +
                ", status='" + status + '\'' +
                '}';
    }
}
