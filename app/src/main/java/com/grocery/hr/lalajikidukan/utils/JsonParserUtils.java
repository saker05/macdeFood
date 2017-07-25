package com.grocery.hr.lalajikidukan.utils;

import com.grocery.hr.lalajikidukan.models.AddressModel;
import com.grocery.hr.lalajikidukan.models.BaseResponse;
import com.grocery.hr.lalajikidukan.models.CartModel;
import com.grocery.hr.lalajikidukan.models.CategoryModel;
import com.grocery.hr.lalajikidukan.models.OpsOrderDetailModel;
import com.grocery.hr.lalajikidukan.models.OpsOrderModel;
import com.grocery.hr.lalajikidukan.enums.OrderStatusEnum;
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
        if (jsonString == null || jsonString.isEmpty()) {
            return null;
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
        if (jsonString == null || jsonString.isEmpty()) {
            return null;
        }
        try {
            List<ProductModel> productModelList = new ArrayList<>();
            JSONArray productList = new JSONObject(jsonString).optJSONArray("data");
            for (int i = 0; i < productList.length(); i++) {
                ProductModel productModel = new ProductModel();
                List<ProductVariantsModel> productVariantsModelList=new ArrayList<>();
                JSONObject product = productList.getJSONObject(i);
                productModel.setImageUrl(product.getString("imageUrl"));
                productModel.setUpc(product.getString("upc"));
                productModel.setStatus(product.getString("status"));
                productModel.setName(product.getString("name"));
                productModel.setCategoryId(product.getInt("categoryId"));
                productModel.setDescription(product.getString("description"));
                productModel.setProductVariants(productVariantsModelList);

                JSONArray productVariantsList = product.getJSONArray("productVariants");
                for (int j = 0; j < productVariantsList.length(); j++) {
                    JSONObject productVariants=productVariantsList.getJSONObject(j);
                    ProductVariantsModel productVariantsModel=new ProductVariantsModel();
                    productVariantsModel.setPrice(productVariants.getInt("price"));
                    productVariantsModel.setSizeType(productVariants.getString("sizeType"));
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

    public static ShippingModel shippingParser(String jsonString) {
        if (jsonString == null || jsonString.isEmpty()) {
            return null;
        }
        try {
            JSONObject shippingDetail = new JSONObject(jsonString).optJSONObject("data");
            ShippingModel shippingModel = new ShippingModel();
            shippingModel.setDeliveryCharge(shippingDetail.getInt("deliveryCharge"));
            shippingModel.setMinOrderForFreeDelivery(shippingDetail.getInt("minOrderForFreeDelivery"));
            return shippingModel;
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

    public static BaseResponse getBaseResponse(String jsonString){
        if (jsonString == null || jsonString.isEmpty()) {
            return null;
        }
        BaseResponse baseResponse=new BaseResponse();
        try {
            JSONObject jsonObject=new JSONObject(jsonString);
            String responseMessage=jsonObject.getString("responseMessage");
            int responseCode=jsonObject.getInt("responseCode");
            baseResponse.setResponseCode(responseCode);
            baseResponse.setResponseMessage(responseMessage);
            return baseResponse;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}