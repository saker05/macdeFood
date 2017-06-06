package com.grocery.hr.lalajikidukan;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.grocery.hr.lalajikidukan.constants.AppConstants;
import com.grocery.hr.lalajikidukan.enums.AppUpdateStatusEnum;
import com.grocery.hr.lalajikidukan.models.BaseResponse;
import com.grocery.hr.lalajikidukan.utils.JsonParserUtils;
import com.grocery.hr.lalajikidukan.utils.Utils;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by vipul on 1/6/17.
 */

public class SplashActivity extends AppCompatActivity {

    public static final String TAG = SplashActivity.class.getSimpleName();

    private Utils mUtils;
    private Handler mHandler;
    private String appVersion;
    private String androidVersion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mUtils = Utils.getInstance();
        mHandler = new Handler();
        appVersion = BuildConfig.VERSION_CODE + "_"+BuildConfig.VERSION_NAME;
        androidVersion = Build.VERSION.SDK_INT + Build.VERSION.RELEASE;
    }

   /* @Override
    protected void onPostResume() {
        super.onPostResume();
        Intent intent = new Intent(SplashActivity.this, SplashActivity.class);
        startActivity(intent);
    }*/

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        if (!mUtils.isDeviceOnline(getApplicationContext())) {
            DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Intent intent = new Intent(SplashActivity.this, SplashActivity.class);
                    startActivity(intent);
                }
            };
            mUtils.showMessage(
                    SplashActivity.this,
                    getString(R.string.no_internet_connection),
                    "Turn your internet on.\nTry Again",
                    "RETRY", dialogClickListener
            );
        } else {
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    new GetAppUpdateStatus().execute();
                }
            });
        }
    }

    private void afterGettingStatus(String status) {
        if (status.equals(AppUpdateStatusEnum.AVAILAIBLE_NOT_MANDATORY.toString())) {
            DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    switch (which) {
                        case DialogInterface.BUTTON_POSITIVE:
                            mUtils.moveToPlayStore(SplashActivity.this);
                            break;

                        case DialogInterface.BUTTON_NEGATIVE:
                            moveToMainActivity();
                            break;
                    }
                }
            };
            AlertDialog alertDialog = mUtils.showMessage(getApplicationContext(), null, String.valueOf(R.string.app_upgrade), "OK", dialogClickListener,
                    "CANCEL", dialogClickListener);
        } else if (status.equals(AppUpdateStatusEnum.MANDATORY.toString())) {
            DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    mUtils.moveToPlayStore(SplashActivity.this);
                }
            };
            AlertDialog alertDialog = mUtils.showMessage(getApplicationContext(), null, String.valueOf(R.string.app_upgrade), "OK", dialogClickListener);
        } else {
            moveToMainActivity();
        }
    }

    private void moveToMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    class GetAppUpdateStatus extends AsyncTask<Void, Void, String> {
        @Override
        protected String doInBackground(Void... params) {
            try {
                String url = AppConstants.Url.BASE_URL + AppConstants.Url.APP_UPDATE_STATUS;
                url = url.replace("+", appVersion);
                url = url.replace("*", androidVersion);
                return mUtils.getFromServer(url, null);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            Log.e(TAG, "GetAppUpdateStatus::onPostExecute(): result is: " + result);
            if ((result != null && result.trim().length() != 0)) {
                try {
                    String status = new JSONObject(result).getString("data");
                    afterGettingStatus(status);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
                DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(SplashActivity.this, SplashActivity.class);
                        startActivity(intent);
                    }
                };
                mUtils.showMessage(
                        SplashActivity.this,
                        getString(R.string.cant_connect_to_server),
                        "Try again later.\nTry Again",
                        "RETRY", dialogClickListener            
                );
            }
        }
    }

}

