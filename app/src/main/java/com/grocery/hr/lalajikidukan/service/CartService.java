package com.grocery.hr.lalajikidukan.service;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;
import com.grocery.hr.lalajikidukan.MainActivity;
import com.grocery.hr.lalajikidukan.R;
import com.grocery.hr.lalajikidukan.constants.AppConstants;
import com.grocery.hr.lalajikidukan.entity.CartDO;
import com.grocery.hr.lalajikidukan.fragments.LoginFragment;
import com.grocery.hr.lalajikidukan.manager.CartManager;
import com.grocery.hr.lalajikidukan.models.BaseResponse;
import com.grocery.hr.lalajikidukan.models.CartModel;
import com.grocery.hr.lalajikidukan.models.ShippingModel;
import com.grocery.hr.lalajikidukan.utils.JsonParserUtils;
import com.grocery.hr.lalajikidukan.utils.Utils;

import java.util.List;

/**
 * Created by vipul on 11/4/17.
 */

public class CartService {

    private static CartService instance = null;
    private static CartManager cartManager;
    private Utils mUtils;
    private Gson gson;
    private String cartModelJson;

    private CartService() {
        mUtils=Utils.getInstance();
        gson = new Gson();
    }

    public static CartService getInstance(Context context) {
        if (instance == null) {
            instance = new CartService();
            cartManager = CartManager.getInstance(context);
            return instance;
        }
        return instance;
    }

    public int getCartTotalPrice(List<CartModel> cartModels) {
        int totalPrice = 0;
        for (CartModel cartModel : cartModels) {
            if (cartModel.getStatus().equals(AppConstants.ProductStatusEnum.AVAILABLE)) {
                totalPrice += (cartModel.getPrice() * cartModel.getNoOfUnits());
            }

        }
        return totalPrice;
    }

    public void syncCartModelAndCartDo(List<CartModel> cartModels) {
        List<CartDO> cartDOs = cartManager.getCartItems();
        for (int i = cartModels.size() - 1; i >= 0; i--) {
            CartModel cartModel = cartModels.get(i);
            Boolean isInCartDo = false;
            for (CartDO cartDO : cartDOs) {
                if (cartDO.getSku().equals(cartModel.getSku())) {
                    cartModel.setNoOfUnits(cartDO.getNoOfUnits());
                    isInCartDo = true;
                    break;
                }
            }
            if (!isInCartDo) {
                cartModels.remove(cartModel);
            }
        }
    }

    public int getShippingCharge(ShippingModel deliveryChargeModel, int cartTotalPrice) {
        if (deliveryChargeModel == null) return 0;
        return (cartTotalPrice < deliveryChargeModel.getMinOrderForFreeDelivery())
                ?
                Math.min(deliveryChargeModel.getMinOrderForFreeDelivery(),
                        (deliveryChargeModel.getDeliveryCharge() + cartTotalPrice)) - cartTotalPrice
                : 0;
    }

}
