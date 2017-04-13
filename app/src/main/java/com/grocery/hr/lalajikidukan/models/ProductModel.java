package com.grocery.hr.lalajikidukan.models;

/**
 * Created by vipul on 19/3/17.
 */

public class ProductModel {

    private String upc;
    private String name;
    private String description;
    private Integer categoryId;
    private String status;
    private Integer unitQuantityInGm; // if it is null mean product will be sold per piece
    private int unitAmount;
    private String imageUrl;
    private int noOfItemInCart;

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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


    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public int getNoOfItemInCart() {
        return noOfItemInCart;
    }

    public void setNoOfItemInCart(int noOfItemInCart) {
        this.noOfItemInCart = noOfItemInCart;
    }

    @Override
    public String toString() {
        return "ProductModel [upc=" + upc + ", name=" + name + ", description=" + description + ", categoryId="
                + categoryId + ", status=" + status + ", unitQuantityInGm=" + unitQuantityInGm + ", unitAmount="
                + unitAmount + ", imageUrl=" + imageUrl + "]";
    }

}
