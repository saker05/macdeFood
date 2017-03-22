package com.grocery.hr.lalajikidukan.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;
import android.support.v4.util.Pair;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.TypedValue;

import com.grocery.hr.lalajikidukan.R;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

/**
 * Created by vipul on 19/3/17.
 */

public class Utils {

    public static final String TAG = Utils.class.getSimpleName();
    public static final String baseURL = "http://192.168.1.7:8080/GroceryApp";
    private static Utils ourInstance = new Utils();

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

    public String getToServer(String url, Map<String,String> pairs) throws Exception {
        okhttp3.OkHttpClient client = new okhttp3.OkHttpClient();
        okhttp3.Request.Builder builder = new okhttp3.Request.Builder();

        if(pairs!=null){
            String encoding = Base64.encodeToString((pairs.get("user")+":"+pairs.get("passwd")).getBytes("UTF-8"),Base64.NO_WRAP);
            builder.addHeader("Authorization", "Basic " + encoding);
        }
        // Log.i(TAG, "getToServer: url is: " + url);
        builder.url(url);
        okhttp3.Request request = builder.build();
        okhttp3.Response response = client.newCall(request).execute();
        if (!response.isSuccessful()) {
            throw new IOException(response.message() + " " + response.toString());
        }
        String responses=response.body().string();
        return responses;
    }

    public int getActionBarSize(AppCompatActivity activity) {
        TypedValue value = new TypedValue();
        if (activity.getTheme().resolveAttribute(R.attr.actionBarSize, value, true)) {
            return TypedValue.complexToDimensionPixelSize(value.data,
                    activity.getResources().getDisplayMetrics());
        }
        return (int) 56d;
    }

}
