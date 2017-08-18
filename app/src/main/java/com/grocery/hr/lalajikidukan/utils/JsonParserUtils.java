package com.grocery.hr.lalajikidukan.utils;

import com.grocery.hr.lalajikidukan.enums.OrderStatusEnum;
import com.grocery.hr.lalajikidukan.models.AddressModel;
import com.grocery.hr.lalajikidukan.models.BaseResponse;
import com.grocery.hr.lalajikidukan.models.CartModel;
import com.grocery.hr.lalajikidukan.models.CartPageModel;
import com.grocery.hr.lalajikidukan.models.CategoryModel;
import com.grocery.hr.lalajikidukan.models.HomePageModel;
import com.grocery.hr.lalajikidukan.models.OpsOrderDetailModel;
import com.grocery.hr.lalajikidukan.models.OpsOrderModel;
import com.grocery.hr.lalajikidukan.models.ProductModel;
import com.grocery.hr.lalajikidukan.models.ProductVariantsModel;
import com.grocery.hr.lalajikidukan.models.ShippingModel;
import com.grocery.hr.lalajikidukan.models.UserOrderModel;
import com.grocery.hr.lalajikidukan.models.UserSubOrderModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by vipul on 22/3/17.
 */

public class JsonParserUtils {

    public static List<CartModel> cartParser(String jsonString) {
        if (jsonString == null || jsonString.isEmpty())
            return null;
        try {
            JSONArray cartJsonArray = new JSONObject(jsonString).optJSONArray("data");
            return cartJsonArrayParser(cartJsonArray);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static List<CategoryModel> categoryParser(String jsonString) {
        if (jsonString == null || jsonString.isEmpty()) {
            return null;
        }
        try {
            JSONArray categoryJsonArray = new JSONObject(jsonString).optJSONArray("data");
            return categoryJsonArrayParser(categoryJsonArray);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }


    public static List<ProductModel> productParser(String jsonString) {
        if (jsonString == null || jsonString.isEmpty()) {
            return null;
        }
        try {
            JSONArray productJsonArray = new JSONObject(jsonString).optJSONArray("data");
            return productJsonArrayParser(productJsonArray);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static ShippingModel shippingParser(String jsonString) {
        if (jsonString == null || jsonString.isEmpty()) {
            return null;
        }
        try {
            JSONObject shippingJsonObject = new JSONObject(jsonString).optJSONObject("data");
            return shippingJsonObjectParser(shippingJsonObject);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static List<AddressModel> addressParser(String jsonString) {
        if (jsonString == null || jsonString.isEmpty()) {
            return null;
        }
        try {
            List<AddressModel> addressModelList = new ArrayList<>();
            JSONArray addresses = new JSONObject(jsonString).optJSONArray("data");
            for (int i = 0; i < addresses.length(); i++) {
                AddressModel addressModel = new AddressModel();
                JSONObject address = addresses.getJSONObject(i);
                addressModel.setId(address.getInt("id"));
                addressModel.setName(address.getString("name"));
                addressModel.setFlat(address.getString("flat"));
                addressModel.setPincode(address.getInt("pincode"));
                addressModel.setLocality(address.getString("locality"));
                addressModel.setLandmark(address.getString("landmark"));
                addressModel.setAddressType(address.getString("addressType"));
                addressModel.setUser(address.getString("user"));
                addressModel.setPhoneNumber(address.getString("phoneNumber"));
                addressModelList.add(addressModel);
            }
            return addressModelList;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static List<UserOrderModel> orderParser(String jsonString) {
        if (jsonString == null || jsonString.isEmpty()) {
            return null;
        }
        try {
            List<UserOrderModel> orderModelList = new ArrayList<>();
            JSONArray orders = new JSONObject(jsonString).optJSONArray("data");
            for (int i = 0; i < orders.length(); i++) {
                UserOrderModel orderModel = new UserOrderModel();
                JSONObject order = orders.getJSONObject(i);
                orderModel.setUser(order.getString("user"));
                orderModel.setOrderDate(order.getString("orderDate"));
                orderModel.setOrderTotalAmount(order.getInt("orderTotalAmount"));
                orderModel.setStatus(OrderStatusEnum.getEnumFromString(order.getString("status")));
                orderModel.setUoc(order.getString("uoc"));
                orderModelList.add(orderModel);
            }
            return orderModelList;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static List<UserSubOrderModel> subOrderParser(String jsonString) {
        if (jsonString == null || jsonString.isEmpty()) {
            return null;
        }
        try {
            List<UserSubOrderModel> subOrderModelList = new ArrayList<>();
            JSONArray subOrders = new JSONObject(jsonString).optJSONArray("data");
            for (int i = 0; i < subOrders.length(); i++) {
                UserSubOrderModel subOrderModel = new UserSubOrderModel();
                JSONObject subOrder = subOrders.getJSONObject(i);
                subOrderModel.setUoc(subOrder.getString("uoc"));
                subOrderModel.setUpc(subOrder.getString("upc"));
                subOrderModel.setNoOfUnits(subOrder.getInt("noOfUnits"));
                subOrderModel.setUnitAmount(subOrder.getInt("unitAmount"));
                subOrderModel.setUnitQuantityInGm(subOrder.getInt("unitQuantityInGm"));
                subOrderModelList.add(subOrderModel);
            }
            return subOrderModelList;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }


    public static OpsOrderModel opsOrdeParser(String jsonString) {
        if (jsonString == null || jsonString.isEmpty()) {
            return null;
        }
        try {
            List<OpsOrderDetailModel> opsOrderDetailModelList = new ArrayList<>();
            JSONObject opsData = new JSONObject(jsonString).getJSONObject("data");
            int count = opsData.getInt("totalOrderCount");
            JSONArray opsOrderJsonArray = opsData.optJSONArray("opsOrderDetailModel");

            for (int i = 0; i < opsOrderJsonArray.length(); i++) {
                JSONObject addressJsonObject = (JSONObject) opsOrderJsonArray.getJSONObject(i).
                        get("userAddressDetailModel");
                JSONObject orderJsonObject = (JSONObject) opsOrderJsonArray.getJSONObject(i).get("userOrderModel");

                OpsOrderDetailModel opsOrderDetailModel = new OpsOrderDetailModel();
                UserOrderModel userOrderModel = new UserOrderModel();
                AddressModel addressModel = new AddressModel();

                userOrderModel.setShippingCharge(orderJsonObject.getInt("shippingCharge"));
                userOrderModel.setOrderDate(orderJsonObject.getString("orderDate"));
                userOrderModel.setStatus(OrderStatusEnum.getEnumFromString(orderJsonObject.getString("status")));
                userOrderModel.setUoc(orderJsonObject.getString("uoc"));
                userOrderModel.setUser(orderJsonObject.getString("user"));
                userOrderModel.setOrderTotalAmount(orderJsonObject.getInt("orderTotalAmount"));

                addressModel.setAddressType(addressJsonObject.getString("addressType"));
                addressModel.setFlat(addressJsonObject.getString("flat"));
                addressModel.setName(addressJsonObject.getString("name"));
                addressModel.setPhoneNumber(addressJsonObject.getString("phoneNumber"));
                addressModel.setPincode(addressJsonObject.getInt("pincode"));
                addressModel.setLandmark(addressJsonObject.getString("landmark"));
                addressModel.setAddressType(addressJsonObject.getString("addressType"));

                opsOrderDetailModel.setAddressModel(addressModel);
                opsOrderDetailModel.setUserOrderModel(userOrderModel);

                opsOrderDetailModelList.add(opsOrderDetailModel);

            }

            OpsOrderModel opsOrderModel = new OpsOrderModel();
            opsOrderModel.setOpsOrderDetailModel(opsOrderDetailModelList);
            opsOrderModel.setTotalOrderCount(count);

            return opsOrderModel;

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static HomePageModel homePageParser(String jsonString) {
        if (jsonString == null || jsonString.isEmpty()) {
            return null;
        }
        try {
            JSONObject homePageData = new JSONObject(jsonString).getJSONObject("data");
            JSONArray categoryJsonArray = homePageData.optJSONArray("categoryModelList");
            JSONArray productJsonArray = homePageData.optJSONArray("productModelList");

            List<CategoryModel> categoryModelList = categoryJsonArrayParser(categoryJsonArray);
            List<ProductModel> productModelList = productJsonArrayParser(productJsonArray);

            HomePageModel homePageModel = new HomePageModel();
            homePageModel.setCategoryModelList(categoryModelList);
            homePageModel.setProductModelList(productModelList);

            return homePageModel;

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static CartPageModel cartPageParser(String jsonString){
        if (jsonString == null || jsonString.isEmpty()) {
            return null;
        }
        try {
            JSONObject cartPageData = new JSONObject(jsonString).getJSONObject("data");
            JSONArray cartJsonArray = cartPageData.optJSONArray("cartModelList");
            JSONObject shippingJsonObject = cartPageData.optJSONObject("shippingModel");

            List<CartModel> cartModelList=cartJsonArrayParser(cartJsonArray);
            ShippingModel shippingModel=shippingJsonObjectParser(shippingJsonObject);

            CartPageModel cartPageModel=new CartPageModel();
            cartPageModel.setCartModelList(cartModelList);
            cartPageModel.setShippingModel(shippingModel);

            return cartPageModel;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static BaseResponse getBaseResponse(String jsonString) {
        if (jsonString == null || jsonString.isEmpty()) {
            return null;
        }
        BaseResponse baseResponse = new BaseResponse();
        try {
            JSONObject jsonObject = new JSONObject(jsonString);
            String responseMessage = jsonObject.getString("responseMessage");
            int responseCode = jsonObject.getInt("responseCode");
            baseResponse.setResponseCode(responseCode);
            baseResponse.setResponseMessage(responseMessage);
            return baseResponse;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }









    // JsonArrayParser


    public static List<CategoryModel> categoryJsonArrayParser(JSONArray categoryJsonArray) {
        try {
            List<CategoryModel> categoryModelList = new ArrayList<>();
            for (int i = 0; i < categoryJsonArray.length(); i++) {
                CategoryModel categoryModel = new CategoryModel();
                JSONObject category = categoryJsonArray.getJSONObject(i);
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

    public static List<ProductModel> productJsonArrayParser(JSONArray productJsonArray) {
        try {
            List<ProductModel> productModelList = new ArrayList<>();
            for (int i = 0; i < productJsonArray.length(); i++) {
                ProductModel productModel = new ProductModel();
                List<ProductVariantsModel> productVariantsModelList = new ArrayList<>();
                JSONObject product = productJsonArray.getJSONObject(i);
                productModel.setImageUrl(product.getString("imageUrl"));
                productModel.setUpc(product.getString("upc"));
                productModel.setStatus(product.getString("status"));
                productModel.setName(product.getString("name"));
                productModel.setCategoryId(product.getInt("categoryId"));
                productModel.setDescription(product.getString("description"));
                productModel.setProductVariants(productVariantsModelList);

                JSONArray productVariantsList = product.getJSONArray("productVariants");
                for (int j = 0; j < productVariantsList.length(); j++) {
                    JSONObject productVariants = productVariantsList.getJSONObject(j);
                    ProductVariantsModel productVariantsModel = new ProductVariantsModel();
                    productVariantsModel.setPrice(productVariants.getInt("price"));
                    productVariantsModel.setType(productVariants.getString("type"));
                    productVariantsModel.setSku(productVariants.getString("sku"));
                    productVariantsModel.setDiscountPercent(productVariants.getInt("discountPercent"));
                    productVariantsModelList.add(productVariantsModel);
                }
                Collections.sort(productVariantsModelList);
                productModelList.add(productModel);
            }
            return productModelList;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static List<CartModel> cartJsonArrayParser(JSONArray cartJsonArray) {
        try {
            List<CartModel> cartModelList = new ArrayList<CartModel>();
            for (int i = 0; i < cartJsonArray.length(); i++) {
                CartModel cartModel = new CartModel();
                JSONObject cart = cartJsonArray.getJSONObject(i);
                cartModel.setName(cart.getString("name"));
                cartModel.setNoOfUnits(cart.getInt("noOfUnits"));
                cartModel.setStatus(cart.getString("status"));
                cartModel.setPrice(cart.getInt("price"));
                cartModel.setSku(cart.getString("sku"));
                cartModel.setDiscountPercent(cart.getInt("discountPercent"));
                cartModel.setCategoryId(cart.getInt("categoryId"));
                cartModel.setDescription(cart.getString("description"));
                cartModel.setType(cart.getString("type"));
                cartModel.setImageUrl(cart.getString("imageUrl"));
                cartModel.setUpc(cart.getString("upc"));
                cartModelList.add(cartModel);
            }
            return cartModelList;

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static ShippingModel shippingJsonObjectParser(JSONObject shippingJsonObject){
        try {
            ShippingModel shippingModel = new ShippingModel();
            shippingModel.setDeliveryCharge(shippingJsonObject.getInt("deliveryCharge"));
            shippingModel.setMinOrderForFreeDelivery(shippingJsonObject.getInt("minOrderForFreeDelivery"));
            return shippingModel;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}