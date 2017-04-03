package com.grocery.hr.lalajikidukan.service;

import android.content.Intent;
import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;
import com.grocery.hr.lalajikidukan.constants.AppConstants;
import com.grocery.hr.lalajikidukan.preferences.AppSharedPreference;

/**
 * Created by vipul on 26/3/17.
 */

public class MyFirebaseInstanceIdService extends FirebaseInstanceIdService {

    private static final String TAG = "MyFirebaseIIdService";

    @Override
    public void onTokenRefresh() {

        //Getting registration token
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();

        //Displaying token on logcat
        Log.d(TAG, "Refreshed token: " + refreshedToken);

      //  getApplicationContext().sendBroadcast(new Intent(AppConstants.Notification.TOKEN_BROADCAST));
        storeToken(refreshedToken);

    }

    private void storeToken(String token){
        AppSharedPreference.putString(this,AppConstants.Notification.KEY_ACCESS_TOKEN,token);
    }
}
