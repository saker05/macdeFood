package com.grocery.hr.lalajikidukan.service;

import android.content.Context;

import com.grocery.hr.lalajikidukan.entity.CartDO;
import com.grocery.hr.lalajikidukan.manager.CartManager;
import com.grocery.hr.lalajikidukan.models.CartModel;
import com.grocery.hr.lalajikidukan.models.ProductModel;
import com.grocery.hr.lalajikidukan.models.ProductVariantsModel;

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
                int count=0;
                for(ProductVariantsModel productVariantsModel:product.getProductVariants()){
                    CartDO cart=cartManager.getCartItem(productVariantsModel.getSku());
                    if(cart!=null){
                        count += cart.getNoOfUnits();
                    }
                }
                product.setNoOfItemInCart(count);
            }
        }
    }
}
