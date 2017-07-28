package com.grocery.hr.lalajikidukan.entity;

/**
 * Created by vipul on 24/3/17.
 */

public class CartDO {

    private String sku;
    private Integer noOfUnits;

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
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
                "sku='" + sku + '\'' +
                ", noOfUnits=" + noOfUnits +
                '}';
    }
}
