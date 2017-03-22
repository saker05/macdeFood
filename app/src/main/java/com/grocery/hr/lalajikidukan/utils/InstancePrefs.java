package com.grocery.hr.lalajikidukan.utils;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

public class InstancePrefs<T> extends BasePrefs {

    private boolean savedInstanceState=false;
    private T serverCallResult;

    /**
     * @param TAG     name for SharedPreferences
     * @param context context
     */
    public InstancePrefs(@NonNull String TAG, @NonNull Context context) {
        super(TAG, context);
    }

    public boolean isSavedInstanceState() {
        return savedInstanceState;
    }

    public void setSavedInstanceState(boolean savedInstanceState) {
        this.savedInstanceState = savedInstanceState;
    }

    public T getServerCallResult() {
        return serverCallResult;
    }

    public void setServerCallResult(T serverCallResult) {
        this.serverCallResult = serverCallResult;
    }


}
