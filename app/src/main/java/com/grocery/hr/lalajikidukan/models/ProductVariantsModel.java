package com.grocery.hr.lalajikidukan.models;

import java.util.Comparator;

/**
 * Created by vipul on 24/7/17.
 */

public class ProductVariantsModel implements Comparable<ProductVariantsModel>{

    public enum SizeType{
        SMALL,
        MEDIUM,
        LARGE
    }

    private String sizeType;
    private int price;

    public String getSizeType() {
        return sizeType;
    }
    public void setSizeType(String sizeType) {
        this.sizeType = sizeType;
    }

    public int getPrice() {
        return price;
    }
    public void setPrice(int price) {
        this.price = price;
    }

    public int compareTo(ProductVariantsModel anotherInstance) {
        return this.price - anotherInstance.price;
    }

    @Override
    public String toString() {
        return "ProductVariantsModel [sizeType=" + sizeType + ", price=" + price + "]";
    }

}
