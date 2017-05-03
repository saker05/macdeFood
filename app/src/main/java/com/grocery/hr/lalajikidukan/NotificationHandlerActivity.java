package com.grocery.hr.lalajikidukan;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.grocery.hr.lalajikidukan.manager.CartManager;
import com.grocery.hr.lalajikidukan.preferences.AppPrefs;
import com.grocery.hr.lalajikidukan.utils.Utils;

import java.util.Set;

import butterknife.ButterKnife;

/**
 * Created by vipul on 27/3/17.
 */

public class NotificationHandlerActivity  extends AppCompatActivity {

    Log log;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_empty);
        ButterKnife.bind(this);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        Bundle extras = getIntent().getExtras();
        String data1=extras.getString("action");



        // Log.e("abc",b.toString());
    }


}
