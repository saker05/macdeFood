package com.grocery.hr.lalajikidukan.models;

/**
 * Created by vipul on 14/4/17.
 */

public class ShippingChargeModel {

    public int minOrderForFreeDelivery;
    public int deliveryCharge;

    public int getMinOrderForFreeDelivery() {
        return minOrderForFreeDelivery;
    }

    public void setMinOrderForFreeDelivery(int minOrderForFreeDelivery) {
        this.minOrderForFreeDelivery = minOrderForFreeDelivery;
    }

    public int getDeliveryCharge() {
        return deliveryCharge;
    }

    public void setDeliveryCharge(int deliveryCharge) {
        this.deliveryCharge = deliveryCharge;
    }

    @Override
    public String toString() {
        return "ShippingChargeModel [minFreeDeliveryOrder=" + minOrderForFreeDelivery + ", deliveryCharge="
                + deliveryCharge + "]";
    }

}
