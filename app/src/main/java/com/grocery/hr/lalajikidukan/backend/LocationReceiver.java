package com.grocery.hr.lalajikidukan.backend;

/**
 * Created by vipul on 26/3/17.
 */


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class LocationReceiver extends BroadcastReceiver {

    public static final String TAG = "LocationReceiver";

    public LocationReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        // Log.i(TAG, "onReceive: " + intent.getAction());
        LocationService.start(context);
    }
}
