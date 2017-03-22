package com.grocery.hr.lalajikidukan.utils;

import android.content.Context;

public class AppPrefs extends BasePrefs {

    public static final String TAG = AppPrefs.class.getSimpleName();
    private final String FirstLaunch = "FirstLaunch";
    private final String LoggedIn = "LoggedIn";
    private final String Password = "Password";
    private final String MobileNo = "MobileNo";
    private final String CartTotal = "CartTotal";
    private final String CartItem = "CartItem";
    private final String LastLocation = "LastLocation";
    private final String Address = "Address";
    private final String Running = "Running";

    public AppPrefs(Context context) {
        super(TAG, context);
    }

    public boolean isFirstLaunch() {
        return getBoolean(FirstLaunch, true);
    }

    public void setFirstLaunch(boolean firstLaunch) {
        putBoolean(FirstLaunch, firstLaunch);
    }

    public boolean isLoggedIn() {
        return getBoolean(LoggedIn);
    }

    public void setLoggedIn(boolean loggedIn) {
        putBoolean(LoggedIn, loggedIn);
    }

    public String getPassword() {
        return "vipul";
    }

    public void setPassword(String password) {
        putString(Password, password);
    }

    public String getMobileNo() {
        return "9729012780";
    }

    public void setMobileNo(String mobileNo) {
        putString(MobileNo, mobileNo);
    }

    public int getCartTotal() {
        return getInt(CartTotal);
    }

    public void setCartTotal(int cartTotal) {
        putInt(CartTotal, cartTotal);
    }

    public int getCartItem() {
        return getInt(CartItem);
    }

    public void setCartItem(int cartItem) {
        putInt(CartItem, cartItem);
    }

    public String getLastLocation() {
        return getString(LastLocation);
    }

    public void setLastLocation(String lastLocation) {
        putString(LastLocation, lastLocation);
    }

    public String getAddress() {
        return getString(Address, getLastLocation());
    }

    public void setAddress(String address) {
        putString(Address, address);
    }

    public boolean isRunning() {
        return getBoolean(Running);
    }

    public void setRunning(boolean running) {
        putBoolean(Running, running);
    }
}
