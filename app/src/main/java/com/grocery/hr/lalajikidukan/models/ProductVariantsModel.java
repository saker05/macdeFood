package com.grocery.hr.lalajikidukan.models;

import java.util.Comparator;

/**
 * Created by vipul on 24/7/17.
 */

public class ProductVariantsModel implements Comparable<ProductVariantsModel> {

    private String type;
    private int price;
    private int discountPercent;
    private String sku;

    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
    public int getPrice() {
        return price;
    }
    public void setPrice(int price) {
        this.price = price;
    }
    public int getDiscountPercent() {
        return discountPercent;
    }
    public void setDiscountPercent(int discountPercent) {
        this.discountPercent = discountPercent;
    }
    public String getSku() {
        return sku;
    }
    public void setSku(String sku) {
        this.sku = sku;
    }

    @Override
    public String toString() {
        return "ProductVariantsModel [type=" + type + ", price=" + price + ", discountPercent=" + discountPercent
                + ", sku=" + sku + "]";
    }

    public int compareTo(ProductVariantsModel anotherInstance) {
        return this.price - anotherInstance.price;
    }
}
