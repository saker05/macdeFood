package com.grocery.hr.lalajikidukan.service;

import android.app.NotificationManager;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.grocery.hr.lalajikidukan.MainActivity;
import com.grocery.hr.lalajikidukan.NotificationHandlerActivity;
import com.grocery.hr.lalajikidukan.manager.MyNotificationManager;

/**
 * Created by vipul on 26/3/17.
 */

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private static final String TAG = "MyFirebaseMService";


    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        Intent notificationIntent=new Intent(getApplicationContext(),MainActivity.class);

        // ...

        // TODO(developer): Handle FCM messages here.
        // Not getting messages here? See why this may be: https://goo.gl/39bRNJ
        Log.d(TAG, "From: " + remoteMessage.getFrom());

        // Check if message contains a data payload.
        if (remoteMessage.getData().size() > 0) {
            Log.d(TAG, "Message data payload: " + remoteMessage.getData());
            Bundle bundle=new Bundle();
            bundle.putString("action",remoteMessage.getData().get("action"));
            notificationIntent.putExtras(bundle);
        }else{
            return;
        }

        // Check if message contains a notification payload.


        notifyUser(remoteMessage.getData().get("notification_title"), remoteMessage.getData().get("notification_body") , notificationIntent,remoteMessage.getData().get("image"));

        // Also if you intend on generating your own notifications as a result of a received FCM
        // message, here is where that should be initiated. See sendNotification method below.
    }

    public void notifyUser(String notificationTitle, String notificationBody,Intent notificationIntent,String image) {
        MyNotificationManager myNotificationManager = new MyNotificationManager(getApplicationContext());
        myNotificationManager.showNotification(notificationTitle, notificationBody, notificationIntent,image);
    }

}
