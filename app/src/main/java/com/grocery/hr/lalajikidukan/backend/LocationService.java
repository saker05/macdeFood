package com.grocery.hr.lalajikidukan.backend;

/**
 * Created by vipul on 25/3/17.
 */

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;
import com.grocery.hr.lalajikidukan.preferences.AppPrefs;
import com.grocery.hr.lalajikidukan.utils.Utils;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class LocationService extends Service
        implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    public static final String TAG = "LocationService";
    /**
     * Restart Service Interval
     */
    private static final int REPEAT_TIME_IN_SECONDS = 60 * 1000; // 1 Minute
    private static boolean started = false;
    private static LocationListener locationListener;
    // private static LatLng latLng;
    private static boolean isAlarmSet = false;
    /**
     * Provides the entry point to Google Play services.
     */
    protected GoogleApiClient mGoogleApiClient;
    private Utils mUtils = Utils.getInstance();
    private AppPrefs mAppPrefs;
    /**
     * Variable for setting Alarm
     */
    private AlarmManager manager;
    private PendingIntent pendingIntent;

    public static boolean isStarted() {
        return started;
    }

    public static void start(Context context) {
        if (!LocationService.isStarted()) {
            context.startService(new Intent(context, LocationService.class));
        }
    }

    public static void start(Context context, LocationListener locationListener) {
        LocationService.start(context);
        LocationService.locationListener = locationListener;
    }

    // public static LatLng getLatLng() {
    //     return latLng;
    // }

    public static boolean isAlarmScheduled() {
        return isAlarmSet;
    }

    /**
     * Builds a GoogleApiClient. Uses the addApi() method to request the
     * LocationServices API.
     */
    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this).addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this).addApi(LocationServices.API).build();
    }

    /**
     * Set the Alarm
     */
    private void setAlarm() {
        pendingIntent = PendingIntent.getBroadcast(this, 0, new Intent(this, LocationReceiver.class), 0);
        manager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        manager.setInexactRepeating(AlarmManager.RTC, System.currentTimeMillis(), REPEAT_TIME_IN_SECONDS,
                pendingIntent);
        isAlarmSet = true;
    }

    /**
     * Cancels the Alarm
     */
    private void cancelAlarm() {
        if (pendingIntent != null && manager != null) {
            manager.cancel(pendingIntent);
            pendingIntent.cancel();
            manager = null;
            pendingIntent = null;
        }
        isAlarmSet = false;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        // Log.i(TAG, "onConnected: ");
        Location mLastLocation = null;
        try {
            mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        } catch (SecurityException e) {
            Log.e(TAG, "onConnected::SecurityException: " + e.getMessage());
        }
        if (mLastLocation != null) {
            // Log.e(TAG,
            // String.valueOf(mLastLocation.getLatitude()) + ":" +
            // String.valueOf(mLastLocation.getLongitude()));
            // LocationService.latLng = new LatLng(
            //         mLastLocation.getLatitude(), mLastLocation.getLongitude()
            // );
            new GetAddressFromLatLng().execute(
                    new LatLng(mLastLocation.getLatitude(), mLastLocation.getLongitude())
            );
        } else {
            Log.e(TAG, "onConnected: " + "NULL location");
            if (LocationService.locationListener != null) {
                LocationService.locationListener.onLocation(null);
            }
        }
        if (mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
        }

        // Stopping Service
        stopSelf();
    }

    @Override
    public void onConnectionSuspended(int i) {
        // Log.e(TAG, "Connection suspended");
        mGoogleApiClient.connect();
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        // Log.e(TAG, "Connection failed: ConnectionResult.getErrorCode() = " +
        // result.getErrorCode());
        stopSelf();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);
        // Log.i(TAG, "onStartCommand: ");
        if (mAppPrefs == null) {
            mAppPrefs = AppPrefs.getInstance();
        }
        started = true;
        if (!mAppPrefs.getRunning()
                && !mUtils.isGPSLocationEnabled(getApplicationContext())
                && !mUtils.isNetworkLocationEnabled(getApplicationContext())) {
            cancelAlarm();
            stopSelf();
        } else {
            if (!isAlarmScheduled()) {
                setAlarm();
            }

            if (mGoogleApiClient == null) {
                buildGoogleApiClient();
            }

            mGoogleApiClient.connect();
        }

        // started = false;
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        // Log.i(TAG, "onDestroy");
        started = false;
    }

    public interface LocationListener {
        void onLocation(String address);
    }

    public class GetAddressFromLatLng extends AsyncTask<LatLng, Void, String> {

        final String TAG = GetAddressFromLatLng.class.getSimpleName();
        String formattedAddress;

        public GetAddressFromLatLng() {
            super();
            formattedAddress = "";
        }

        @Override
        protected String doInBackground(LatLng... latLng) {
            try {
                return mUtils.postToServer("http://maps.googleapis.com/maps/api/geocode/json?latlng="
                        + latLng[0].latitude + "," + latLng[0].longitude + "&sensor=true", null);
            } catch (Exception e) {
                Log.e(TAG, "GetAddressFromLatLng::doInBackground::Exception" + e.getMessage());
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            if (result != null && result.length() != 0) {
                // Log.i(TAG, "GetAddressFromLatLng::onPostExecute result " + result);
                try {
                    JSONObject object = new JSONObject(result);
                    JSONArray results = object.optJSONArray("results");
                    JSONObject address;
                    if (results != null && results.length() > 1) {
                        address = results.optJSONObject(1);
                    } else if (results != null && results.length() > 0) {
                        address = results.optJSONObject(0);
                    } else {
                        Log.w(TAG, "GetAddressFromLatLng: onPostExecute: results is null or empty");
                        return;
                    }
                    if (address != null) {
                        String formattedAddress = address.optString("formatted_address");
                        if (null != formattedAddress) {
                            // Log.i(TAG, "GetAddressFromLatLng: onPostExecute: formattedAddress is: "
                            // + formattedAddress);
                            AppPrefs prefs = AppPrefs.getInstance();
                            prefs.setLastLocation(formattedAddress);
                            if (LocationService.locationListener != null) {
                                try {
                                    LocationService.locationListener.onLocation(formattedAddress);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                    Log.e(TAG, "onConnected::Exception: " + e.getMessage());
                                }
                            }
                        } else {
                            Log.w(TAG, "GetAddressFromLatLng: onPostExecute: formattedAddress is null");
                        }
                    } else {
                        Log.w(TAG, "GetAddressFromLatLng: onPostExecute: address is null");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.e(TAG, "GetAddressFromLatLng: onPostExecute: JSONException: "
                            + e.getMessage());
                }
            }
        }
    }
}

