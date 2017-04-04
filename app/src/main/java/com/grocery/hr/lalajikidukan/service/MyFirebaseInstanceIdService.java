package com.grocery.hr.lalajikidukan.service;

import android.content.Intent;
import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;
import com.grocery.hr.lalajikidukan.constants.AppConstants;
import com.grocery.hr.lalajikidukan.preferences.AppSharedPreference;
import com.grocery.hr.lalajikidukan.utils.Utils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by vipul on 26/3/17.
 */

public class MyFirebaseInstanceIdService extends FirebaseInstanceIdService {

    private static final String TAG = "MyFirebaseIIdService";
    private Utils mUtils;

    @Override
    public void onTokenRefresh() {
        mUtils=Utils.getInstance();
        //Getting registration token
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        //Displaying token on logcat
        Log.d(TAG, "Refreshed token: " + refreshedToken);
      //  getApplicationContext().sendBroadcast(new Intent(AppConstants.Notification.TOKEN_BROADCAST));
        storeToken(refreshedToken);
        sendTokenToServer(refreshedToken);
    }

    private void storeToken(String token){
        AppSharedPreference.putString(this,AppConstants.Notification.KEY_ACCESS_TOKEN,token);
    }

    private void sendTokenToServer(String token){
        if(AppSharedPreference.getString(this, AppConstants.User.MOBILE_NO, "abc").equals("abc")){
            String url=AppConstants.Url.BASE_URL+AppConstants.Url.ANONYMOUS_TOKEN_PATH;
            try {
                mUtils.postToServer(url,null,token);
            } catch (Exception e) {
                Log.d(TAG,"Error while sending token to server");
            }
        }else{
            String url=AppConstants.Url.BASE_URL+AppConstants.Url.TOKEN_PATH;
            try{
                mUtils.postToServer(url,mUtils.getUerPasswordMap(this),token);
            } catch (Exception e) {
                Log.d(TAG,"Error while sending token to server");
            }

        }
    }
}
