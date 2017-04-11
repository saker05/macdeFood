package com.grocery.hr.lalajikidukan.service;

import android.content.Context;

import com.grocery.hr.lalajikidukan.constants.AppConstants;
import com.grocery.hr.lalajikidukan.entity.CartDO;
import com.grocery.hr.lalajikidukan.manager.CartManager;
import com.grocery.hr.lalajikidukan.models.CartModel;

import java.util.List;

/**
 * Created by vipul on 11/4/17.
 */

public class CartService {

    private CartService instance=null;
    private CartManager cartManager;

    private CartService(){

    }

    public CartService getInstance(Context context){
        if(instance==null){
            instance= new CartService();
            cartManager=CartManager.getInstance(context);
            return instance;
        }
        return instance;
    }

    public String getCartTotalPrice(List<CartModel> cartModels){
        int totalPrice=0;
        List<CartDO> cartDOs=cartManager.getCartItems();

        for(CartDO cartDO:cartDOs){
            for(CartModel cartModel:cartModels){
                if(cartDO.getUpc().equals(cartModel.getUpc()) && cartModel.getStatus().equals(AppConstants.ProductStatusEnum.AVAILABLE)){
                        totalPrice +=  (cartModel.getUnitAmount()*cartDO.getNoOfUnits());
                }
            }
        }
        return String.valueOf(totalPrice);
    }

}
