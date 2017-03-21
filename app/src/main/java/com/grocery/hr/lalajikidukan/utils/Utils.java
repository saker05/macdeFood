package com.grocery.hr.lalajikidukan.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;
import android.support.v4.util.Pair;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;

/**
 * Created by vipul on 19/3/17.
 */

public class Utils {

    public static final String baseURL = "http://localhost:8080/GroceryApp";
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

    public String getToServer(String url, List<Pair<String, String>> pairs) throws Exception {
        okhttp3.OkHttpClient client = new okhttp3.OkHttpClient();

        if (pairs != null) {
            int index = -1;
            for (Pair<String, String> pair : pairs) {
                index++;
                if (index == 0) {
                    url += "?";
                }
                url += pair.first + "=" + URLEncoder.encode(pair.second, "UTF-8");
                if (index != pairs.size() - 1) {
                    url += "&";
                }
            }
        }
        // Log.i(TAG, "getToServer: url is: " + url);
        okhttp3.Request.Builder builder = new okhttp3.Request.Builder().url(url);
        okhttp3.Request request = builder.build();
        okhttp3.Response response = client.newCall(request).execute();
        if (!response.isSuccessful()) {
            throw new IOException(response.message() + " " + response.toString());
        }
        return response.body().string();
    }

}
