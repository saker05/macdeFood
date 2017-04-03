package com.grocery.hr.lalajikidukan.constants;

/**
 * Created by vipul on 25/3/17.
 */

public interface AppConstants {

    public interface User {
        public static final String MOBILE_NO = "mobileNO";
        public static final String PASSWORD = "password";
    }

    public interface Notification{
        public static final String KEY_ACCESS_TOKEN= "token";
        public static final String TOKEN_BROADCAST =  "tokenBroadCast";
        public static final String NOTIFICATION_ACTION_CART="cart";
        public static final String NOTIFICATION_ACTION_ORDER="order";
    }

    public  interface Url{
        public static final String BASE_URL="http://192.168.1.7:8080/groceryApp";
    }



}
