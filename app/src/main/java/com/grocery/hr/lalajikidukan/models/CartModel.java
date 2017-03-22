package com.grocery.hr.lalajikidukan.models;

/**
 * Created by vipul on 19/3/17.
 */

public class CartModel {

    private String upc;
    private String name;
    private String status;
    private int noOfUnits;
    private Integer unitQuantityInGm; // if null, product is in piece
    private int unitAmount;

    public String getUpc() {
        return upc;
    }

    public void setUpc(String upc) {
        this.upc = upc;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getNoOfUnits() {
        return noOfUnits;
    }

    public void setNoOfUnits(int noOfUnits) {
        this.noOfUnits = noOfUnits;
    }

    public Integer getUnitQuantityInGm() {
        return unitQuantityInGm;
    }

    public void setUnitQuantityInGm(Integer unitQuantityInGm) {
        this.unitQuantityInGm = unitQuantityInGm;
    }

    public int getUnitAmount() {
        return unitAmount;
    }

    public void setUnitAmount(int unitAmount) {
        this.unitAmount = unitAmount;
    }

    @Override
    public String toString() {
        return "CartModel [upc=" + upc + ", name=" + name + ", status=" + status + ", noOfUnits=" + noOfUnits
                + ", unitQuantityInGm=" + unitQuantityInGm + ", unitAmount=" + unitAmount + "]";
    }

}
