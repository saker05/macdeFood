package com.grocery.hr.lalajikidukan.models;

/**
 * Created by vipul on 19/3/17.
 */

public class UserSubOrderModel {

    String uoc;
    String upc;
    int noOfUnits;
    int unitAmount;
    Integer unitQuantityInGm;

    public String getUoc() {
        return uoc;
    }

    public void setUoc(String uoc) {
        this.uoc = uoc;
    }

    public String getUpc() {
        return upc;
    }

    public void setUpc(String upc) {
        this.upc = upc;
    }

    public int getNoOfUnits() {
        return noOfUnits;
    }

    public void setNoOfUnits(int noOfUnits) {
        this.noOfUnits = noOfUnits;
    }

    public int getUnitAmount() {
        return unitAmount;
    }

    public void setUnitAmount(int unitAmount) {
        this.unitAmount = unitAmount;
    }

    public Integer getUnitQuantityInGm() {
        return unitQuantityInGm;
    }

    public void setUnitQuantityInGm(Integer unitQuantityInGm) {
        this.unitQuantityInGm = unitQuantityInGm;
    }

    @Override
    public String toString() {
        return "UserSubOrderModel [uoc=" + uoc + ", upc=" + upc + ", noOfUnits=" + noOfUnits + ", unit_amount="
                + unitAmount + ", unitQuantityInGm=" + unitQuantityInGm + "]";
    }
}
