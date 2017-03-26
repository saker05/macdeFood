package com.grocery.hr.lalajikidukan.entity;

/**
 * Created by vipul on 24/3/17.
 */

public class CartDO {

    private String upc;
    private Integer noOfUnits;

    public String getUpc() {
        return upc;
    }

    public void setUpc(String upc) {
        this.upc = upc;
    }

    public Integer getNoOfUnits() {
        return noOfUnits;
    }

    public void setNoOfUnits(Integer noOfUnits) {
        this.noOfUnits = noOfUnits;
    }

    @Override
    public String toString() {
        return "CartDO{" +
                "upc='" + upc + '\'' +
                ", noOfUnits=" + noOfUnits +
                '}';
    }
}
