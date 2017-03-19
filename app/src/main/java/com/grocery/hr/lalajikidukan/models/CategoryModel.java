package com.grocery.hr.lalajikidukan.models;

/**
 * Created by vipul on 19/3/17.
 */

public class CategoryModel {
    private int id;
    private String name;
    private String status;
    private String imageUrl;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    @Override
    public String toString() {
        return "CategoryModel [id=" + id + ", name=" + name + ", status=" + status + ", imageUrl=" + imageUrl + "]";
    }



}
