package com.grocery.hr.lalajikidukan.utils;

import com.grocery.hr.lalajikidukan.models.CartModel;
import com.grocery.hr.lalajikidukan.models.CategoryModel;
import com.grocery.hr.lalajikidukan.models.ProductModel;
import com.grocery.hr.lalajikidukan.models.ShippingModel;

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

    public static List<CartModel> cartParser(String jsonString) {
        if(jsonString==null || jsonString.isEmpty())
            return null;
        try {
            List<CartModel> cartModelList = new ArrayList<CartModel>();
            JSONArray cartList = new JSONObject(jsonString)
                    .optJSONArray("data");

            for (int i = 0; i < cartList.length(); i++) {
                CartModel cartModel = new CartModel();
                JSONObject cart = cartList.getJSONObject(i);
                cartModel.setName(cart.getString("name"));
                cartModel.setNoOfUnits(cart.getInt("noOfUnits"));
                cartModel.setStatus(cart.getString("status"));
                cartModel.setUnitAmount(cart.getInt("unitAmount"));
                if (cart.isNull("unitQuantityInGm")) {
                    cartModel.setUnitQuantityInGm(null);
                } else {
                    cartModel.setUnitQuantityInGm(cart.getInt("unitQuantityInGm"));
                }
                cartModel.setUpc(cart.getString("upc"));
                cartModelList.add(cartModel);
            }
            return cartModelList;

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static List<CategoryModel> categoryParser(String jsonString) {
        if(jsonString==null || jsonString.isEmpty()){
            return  null;
        }
        try {
            List<CategoryModel> categoryModelList = new ArrayList<>();
            JSONArray categoryList = new JSONObject(jsonString).optJSONArray("data");
            for (int i = 0; i < categoryList.length(); i++) {
                CategoryModel categoryModel = new CategoryModel();
                JSONObject category = categoryList.getJSONObject(i);
                categoryModel.setStatus(category.getString("status"));
                categoryModel.setName(category.getString("name"));
                categoryModel.setId(category.getInt("id"));
                categoryModel.setImageUrl(category.getString("imageUrl"));
                categoryModel.setDescription(category.getString("description"));
                categoryModelList.add(categoryModel);
            }
            return categoryModelList;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }


    public static List<ProductModel> productParser(String jsonString) {
        if(jsonString==null || jsonString.isEmpty()){
            return  null;
        }
        try {
            List<ProductModel> productModelList = new ArrayList<>();
            JSONArray productList = new JSONObject(jsonString).optJSONArray("data");
            for (int i = 0; i < productList.length(); i ++) {
                ProductModel productModel = new ProductModel();
                JSONObject product = productList.getJSONObject(i);
                productModel.setImageUrl(product.getString("imageUrl"));
                productModel.setUpc(product.getString("upc"));
                productModel.setStatus(product.getString("status"));
                productModel.setUnitAmount(product.getInt("unitAmount"));
                productModel.setName(product.getString("name"));
                productModel.setCategoryId(product.getInt("categoryId"));
                productModel.setDescription(product.getString("description"));
                productModel.setUnitQuantityInGm(product.getInt("unitQuantityInGm"));
                productModelList.add(productModel);
            }
            return productModelList;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static ShippingModel shippingParser(String jsonString){
        if(jsonString==null || jsonString.isEmpty()){
            return  null;
        }
        try{
            JSONObject shippingDetail=new JSONObject(jsonString).optJSONObject("data");
            ShippingModel shippingModel=new ShippingModel();
            shippingModel.setDeliveryCharge(shippingDetail.getInt("deliveryCharge"));
            shippingModel.setMinOrderForFreeDelivery(shippingDetail.getInt("minOrderForFreeDelivery"));
            return shippingModel;
        }catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

}
