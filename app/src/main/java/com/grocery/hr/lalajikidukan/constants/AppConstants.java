package com.grocery.hr.lalajikidukan.constants;

import java.security.PublicKey;

/**
 * Created by vipul on 25/3/17.
 */

public interface AppConstants {

    public interface User {
        public static final String MOBILE_NO = "mobileNO";
        public static final String PASSWORD = "password";
        public static final String TYPE = "type";
        public static final String SHIPPING_DETAIL = "shippingDetail";
    }

    public interface OrderStatus {
        public static final String PLACED = "PLACED";
        public static final String PREPARING = "PREPARING";
        public static final String DISPATCHED = "DISPATCHED";
        public static final String DELIVERED = "DELIVERED";
        public static final String REJECTED = "REJECTED";

    }

    public interface USER_TYPE {
        public static final String NORMAL = "NORMAL";
    }

    public interface Notification {
        public static final String KEY_ACCESS_TOKEN = "token";
        public static final String TOKEN_BROADCAST = "tokenBroadCast";
        public static final String NOTIFICATION_ACTION_CART = "cart";
        public static final String NOTIFICATION_ACTION_ORDER = "order";
    }

    public interface Url {
        public static final String BASE_URL = "http://35.167.220.58:8080/groceryApp";
        //public static final String BASE_URL = "http://192.168.1.13:8080/groceryApp";
        public static final String ANONYMOUS_TOKEN_PATH = "/fcmtoken/anonymous";
        public static final String TOKEN_PATH = "/fcmtoken";
        public static final String ADD_CART = "/cart";
        public static final String CART_PRODUCT_INFO = "/cart/productInfoForNotLoggedInUser";
        public static final String GET_CATEGORY = "/category";
        public static final String GET_PRODUCT = "/category/?/products";
        public static final String GET_SHIPPING_DETAIL = "/shipping";
        public static final String GET_HIGHLIGHTED_PRODUCT = "/product/highlighted";
        public static final String GET_ADDRESS = "/user/address";
        public static final String ADD_NEW_ADDRESS = "/user/address";
        public static final String EDIT_ADDRESS = "/user/address";
        public static final String UPDATE_DEFAULT = "/user/address/makeDefault";
        public static final String GET_ORDERS = "/order";
        public static final String PLACE_ORDERS = "/order";
        public static final String GET_SUB_ORDERS = "/order/?/suborder";
        public static final String REGISTER_USER = "/user/registerme";
        public static final String LOGIN_USER = "/user/login";
        public static final String GET_OPS_ORDER_DETAIL = "/order/ops?start=+&end=*&status=#";
        public static final String UPDATE_ORDER_STATUS="/order/+?status=#";
        public static final String APP_UPDATE_STATUS= "/app/updatestatus?appVersion=+&osVersion=*";
    }

    public interface Cloudinary {
        public static final String CLOUD_NAME_VALUE = "da34xgchr";
        public static final String API_KEY_VALUE = "733193466466665";
        public static final String API_SECRET_VALUE = "Ie1FpU4q6XZaqn_k_1PyweFg8D8";
        public static final String CLOUD_NAME = "cloud_name";
        public static final String API_KEY = "api_key";
        public static final String API_SECRET = "api_secret";
    }

    public interface ProductStatusEnum {
        public static final String AVAILABLE = "AVAILAIBLE";
    }

    public interface Address_Fragment {
        public static final String CHECKOUT = "checkoutAddresses";
        public static final String MY_ADDRESSES = "myAddresses";
    }

}
