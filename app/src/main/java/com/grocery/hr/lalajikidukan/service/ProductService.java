package com.grocery.hr.lalajikidukan.service;

import android.content.Context;

import com.grocery.hr.lalajikidukan.entity.CartDO;
import com.grocery.hr.lalajikidukan.manager.CartManager;
import com.grocery.hr.lalajikidukan.models.CartModel;
import com.grocery.hr.lalajikidukan.models.ProductModel;

import java.util.List;

/**
 * Created by vipul on 16/4/17.
 */

public class ProductService {

    private static ProductService instance = null;
    private static CartManager cartManager;

    private ProductService() {

    }

    public static ProductService getInstance(Context context) {
        if (instance == null) {
            instance = new ProductService();
            cartManager = CartManager.getInstance(context);
            return instance;
        }
        return instance;
    }

    public  void syncProductCountWithCartCount(List<ProductModel> productItems){
        if(productItems!=null){
            for(ProductModel product:productItems){
                CartDO cart=cartManager.getCartItem(product.getUpc());
                if(cart!=null){
                    product.setNoOfItemInCart(cart.getNoOfUnits());
                }
            }
        }
    }
}
