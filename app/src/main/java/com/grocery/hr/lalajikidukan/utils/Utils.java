package com.grocery.hr.lalajikidukan.utils;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.util.Pair;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.util.TypedValue;

import com.grocery.hr.lalajikidukan.R;
import com.grocery.hr.lalajikidukan.backend.LocationService;
import com.grocery.hr.lalajikidukan.constants.AppConstants;
import com.grocery.hr.lalajikidukan.preferences.AppSharedPreference;

import org.json.JSONArray;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * Created by vipul on 19/3/17.
 */

public class Utils {

    public static final String TAG = Utils.class.getSimpleName();
    private static Utils ourInstance = new Utils();
    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    private Utils() {
    }

    public static Utils getInstance() {
        return ourInstance;
    }

    public static boolean isDeviceOnline(@NonNull Context context) {
        ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        return (networkInfo != null && networkInfo.isConnected());
    }


    public int getActionBarSize(AppCompatActivity activity) {
        TypedValue value = new TypedValue();
        if (activity.getTheme().resolveAttribute(R.attr.actionBarSize, value, true)) {
            return TypedValue.complexToDimensionPixelSize(value.data,
                    activity.getResources().getDisplayMetrics());
        }
        return (int) 56d;
    }

    public AlertDialog showMessage(Context context,
                                   String title,
                                   String message,
                                   String positiveButton,
                                   DialogInterface.OnClickListener positiveButtonListener) {
        return new AlertDialog.Builder(context, R.style.AppAlertDialogTheme)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton(positiveButton, positiveButtonListener)
                .setCancelable(false)
                .show();
    }

    public AlertDialog showMessage(Context context,
                                   String title,
                                   String message,
                                   String positiveButton,
                                   DialogInterface.OnClickListener positiveButtonListener,
                                   String negativeButton,
                                   DialogInterface.OnClickListener negativeButtonListener) {
        return new AlertDialog.Builder(context, R.style.AppAlertDialogTheme)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton(positiveButton, positiveButtonListener)
                .setNegativeButton(negativeButton, negativeButtonListener)
                .setCancelable(false)
                .show();
    }


    public AlertDialog checkLocationSettings(final Context context) {
        if (isGPSLocationEnabled(context)) {
            LocationService.start(context);
        } else {
            return showLocationMessage(context);
        }
        return null;
    }

    public boolean isGPSLocationEnabled(final Context context) {
        LocationManager lm = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        try {
            return lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(TAG, "isGPSLocationEnabled(): " + e.getMessage());
        }
        return false;
    }

    private AlertDialog showLocationMessage(final Context context) {
        return showMessage(context, context.getString(R.string.location_alert_title),
                context.getString(R.string.location_alert_message),
                "Yes",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                        context.startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                    }
                }, "No", null);
    }

    public boolean isNetworkLocationEnabled(final Context context) {
        LocationManager lm = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        try {
            return lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(TAG, "isNetworkLocationEnabled(): " + e.getMessage());
        }
        return false;
    }

    public String postToServer(String url, Map<String, String> pairs, String data) throws Exception {
        okhttp3.OkHttpClient client = new okhttp3.OkHttpClient();
        okhttp3.Request.Builder builder = new okhttp3.Request.Builder().url(url);

        if (pairs != null) {
            String encoding = Base64.encodeToString((pairs.get("user") + ":" + pairs.get("passwd")).getBytes("UTF-8"), Base64.NO_WRAP);
            builder.addHeader("Authorization", "Basic " + encoding);
        }
        RequestBody body = RequestBody.create(JSON, data);
        builder.put(body);
        okhttp3.Request request = builder.build();
        okhttp3.Response response = client.newCall(request).execute();
        if (!response.isSuccessful()) {
            throw new IOException(response.message() + " " + response.toString());
        }
        return response.body().string();
    }


    public  String getFromServer(String url, Map<String, String> pairs) throws Exception {
        okhttp3.OkHttpClient client = new okhttp3.OkHttpClient();
        okhttp3.Request.Builder builder = new okhttp3.Request.Builder();

        if (pairs != null) {
            String encoding = Base64.encodeToString((pairs.get("user") + ":" + pairs.get("passwd")).getBytes("UTF-8"), Base64.NO_WRAP);
            builder.addHeader("Authorization", "Basic " + encoding);
        }
        // Log.i(TAG, "getToServer: url is: " + url);
        builder.url(url);
        okhttp3.Request request = builder.build();
        okhttp3.Response response = client.newCall(request).execute();
        if (!response.isSuccessful()) {
            throw new IOException(response.message() + " " + response.toString());
        }
        return response.body().string();
    }

    public Map<String,String> getUerPasswordMap(Context context){
        if(AppSharedPreference.getString(context, AppConstants.User.MOBILE_NO, "abc").equals("abc") ){ // no use name password store in shared preference
            return null;
        }
        Map<String, String> pairs = new HashMap<>();
        pairs.put("user",AppSharedPreference.getString(context,AppConstants.User.MOBILE_NO));
        pairs.put("passwd",AppSharedPreference.getString(context,AppConstants.User.PASSWORD));

        return pairs;
    }


}
