package com.grocery.hr.lalajikidukan.Test;

import com.grocery.hr.lalajikidukan.models.CartModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vipul on 12/4/17.
 */

public class CartTest {

    public static List<CartModel> getCartItems(){
        CartModel cartModel=new CartModel();
        cartModel.setUpc("abc");
        cartModel.setStatus("AVAILABLE");
        cartModel.setUnitQuantityInGm(23);
        cartModel.setName("Cauliflower");
        cartModel.setUnitAmount(56);

        CartModel cartModel2=new CartModel();
        cartModel2.setUpc("ac");
        cartModel2.setStatus("AVAILABLE");
        cartModel2.setUnitQuantityInGm(23);
        cartModel2.setName("Cauliflower");
        cartModel2.setUnitAmount(56);

        List<CartModel> cartModelList=new ArrayList<>();
        cartModelList.add(cartModel);
        cartModelList.add(cartModel2);
        return cartModelList;
    }

}
