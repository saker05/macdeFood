package com.grocery.hr.lalajikidukan.models;

/**
 * Created by vipul on 26/7/17.
 */

import java.util.List;

public class CartPageModel {
    List<CartModel> cartModelList;
    ShippingModel shippingModel;

    public List<CartModel> getCartModelList() {
        return cartModelList;
    }

    public void setCartModelList(List<CartModel> cartModelList) {
        this.cartModelList = cartModelList;
    }

    public ShippingModel getShippingModel() {
        return shippingModel;
    }

    public void setShippingModel(ShippingModel shippingModel) {
        this.shippingModel = shippingModel;
    }

    @Override
    public String toString() {
        return "AppCartPageModel [cartModelList=" + cartModelList + ", shippingModel=" + shippingModel + "]";
    }
}
