package com.grocery.hr.lalajikidukan.constants;

/**
 * Created by vipul on 25/3/17.
 */

public interface AppConstants {

    public interface User {
        public static final String MOBILE_NO = "mobileNO";
        public static final String PASSWORD = "password";
    }

    public interface Notification {
        public static final String KEY_ACCESS_TOKEN = "token";
        public static final String TOKEN_BROADCAST = "tokenBroadCast";
        public static final String NOTIFICATION_ACTION_CART = "cart";
        public static final String NOTIFICATION_ACTION_ORDER = "order";
    }

    public interface Url {
        public static final String BASE_URL = "http://192.168.1.2:8080/groceryApp";
        public static final String ANONYMOUS_TOKEN_PATH = "/fcmtoken/anonymous";
        public static final String TOKEN_PATH = "/fcmtoken";
        public static final String ADD_CART   = "/cart";
        public static final String CART_PRODUCT_INFO="/cart/productInfoForNotLoggedInUser";
        public static final String GET_CATEGORY="/category";
        public static final String GET_PRODUCT ="/category/?/products";
        public static final String GET_SHIPPING_DETAIL="/shipping";
        public static final String GET_HIGHLIGHTED_PRODUCT="/product/highlighted";
    }

    public interface Cloudinary{
        public static final String CLOUD_NAME_VALUE="da34xgchr";
        public static final String API_KEY_VALUE = "733193466466665";
        public static final String API_SECRET_VALUE="Ie1FpU4q6XZaqn_k_1PyweFg8D8";
        public static final String CLOUD_NAME="cloud_name";
        public static final String API_KEY= "api_key";
        public static final String API_SECRET="api_secret";
    }

    public interface ProductStatusEnum{
        public static final String AVAILABLE="AVAILAIBLE";
    }


}
