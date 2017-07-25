package com.grocery.hr.lalajikidukan.models;

/**
 * Created by vipul on 19/3/17.
 */
import java.util.List;

public class ProductModel {
    private String upc;
    private String name;
    private String description;
    private Integer categoryId;
    private String status;
    private String imageUrl;
    private List<ProductVariantsModel> productVariants;
    private int noOfItemInCart; // this is using in android app

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

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public List<ProductVariantsModel> getProductVariants() {
        return productVariants;
    }

    public void setProductVariants(List<ProductVariantsModel> productVariants) {
        this.productVariants = productVariants;
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
                + categoryId + ", status=" + status
                + ", imageUrl=" + imageUrl + ", productVariants=" + productVariants + "]";
    }

}
