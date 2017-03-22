package com.grocery.hr.lalajikidukan.utils;

import com.grocery.hr.lalajikidukan.models.CartModel;

import org.apache.commons.lang3.math.NumberUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vipul on 22/3/17.
 */

public class JsonParserUtils {

    public static List<CartModel> cartParser(String jsonString){
        try {
            List<CartModel> cartModelList=new ArrayList<CartModel>() ;

            JSONArray cartList = new JSONObject(jsonString)
                    .optJSONArray("data");

            for (int i = 0; i < cartList.length(); i++) {
                CartModel cartModel=new CartModel();
                JSONObject cart = cartList.getJSONObject(i);
                cartModel.setName(cart.getString("name"));
                cartModel.setNoOfUnits(cart.getInt("noOfUnits"));
                cartModel.setStatus(cart.getString("status"));
                cartModel.setUnitAmount(cart.getInt("unitAmount"));
                if(cart.isNull("unitQuantityInGm")){
                    cartModel.setUnitQuantityInGm(null);
                }else{
                    cartModel.setUnitQuantityInGm(cart.getInt("unitQuantityInGm"));
                }
                cartModel.setUpc(cart.getString("upc"));
                cartModelList.add(cartModel);
            }
            return  cartModelList;

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;

    }
}
