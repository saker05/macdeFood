package com.grocery.hr.lalajikidukan.models;

/**
 * Created by vipul on 25/7/17.
 */
import java.util.List;

public class HomePageModel {

    private List<CategoryModel> categoryModelList;
    private List<ProductModel>  productModelList;

    public List<CategoryModel> getCategoryModelList() {
        return categoryModelList;
    }
    public void setCategoryModelList(List<CategoryModel> categoryModelList) {
        this.categoryModelList = categoryModelList;
    }
    public List<ProductModel> getProductModelList() {
        return productModelList;
    }
    public void setProductModelList(List<ProductModel> productModelList) {
        this.productModelList = productModelList;
    }

    @Override
    public String toString() {
        return "AppHomePageModel [categoryModelList=" + categoryModelList + ", productModelList=" + productModelList
                + "]";
    }

}
