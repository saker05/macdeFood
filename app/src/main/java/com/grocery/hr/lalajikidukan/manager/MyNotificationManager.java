package com.grocery.hr.lalajikidukan.manager;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.support.v7.app.NotificationCompat;

import com.grocery.hr.lalajikidukan.R;
import com.grocery.hr.lalajikidukan.constants.AppConstants;
import com.grocery.hr.lalajikidukan.utils.CloudinaryUtility;

/**
 * Created by vipul on 26/3/17.
 */

public class MyNotificationManager {

    private Context context;

    public static final int NOTIFICATION_ID=234;  // any random value
    public PicassoManager picassoManager;

    public MyNotificationManager(Context context){
        this.context=context;
        picassoManager=PicassoManager.getInstance();
    }

    public void showNotification(String from, String notification, Intent intent,String image){

        PendingIntent pendingIntent=PendingIntent.getActivity(context,NOTIFICATION_ID,intent,PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder= new NotificationCompat.Builder(context);

        Notification mnotification=builder.setSmallIcon(R.mipmap.ic_local_grocery_store_black_24dp).setAutoCancel(true)
                .setContentIntent(pendingIntent)
                .setContentTitle(from)
                .setContentText(notification)
                .setLargeIcon(BitmapFactory.decodeResource(context.getResources(),R.mipmap.ic_local_grocery_store_black_24dp))
                .setStyle(new NotificationCompat.BigPictureStyle().bigPicture(picassoManager.getbitmapOfImage(context,CloudinaryUtility.getImageUrl(image)) ))
                .build();

        mnotification.flags=Notification.FLAG_AUTO_CANCEL;

        NotificationManager notificationManager=(NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(NOTIFICATION_ID,mnotification);

    }

}
