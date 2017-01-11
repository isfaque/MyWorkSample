package com.example.admin.hicabsdesign.GCMManager;

import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;

import com.example.admin.hicabsdesign.R;
import com.example.admin.hicabsdesign.SplashActivity;
import com.example.admin.hicabsdesign.UserMainActivity;
import com.google.android.gms.gcm.GcmListenerService;

/**
 * Created by admin on 16/05/2016.
 */
public class GCMPushReceiverService extends GcmListenerService {

    AlertDialog alertbox;

    @Override
    public void onMessageReceived(String from, Bundle data) {
        String message = data.getString("message");
        sendNotification(message);

    }

    private void sendNotification(String message){
        Intent intent = new Intent(this, SplashActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        int requestCode = 0; //Your request
        PendingIntent pendingIntent = PendingIntent.getActivity(this, requestCode, intent, PendingIntent.FLAG_ONE_SHOT);
        //setup notification
        //sound
        Uri sound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        //Build notification

        if(android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){

            NotificationCompat.Builder noBuilder = new NotificationCompat.Builder(this)
                    .setSmallIcon(R.drawable.ic_notifications)
                    .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.hicabs_icon))
                    .setContentTitle("hicabs Notification")
                    .setContentText(message)
                    .setDefaults(Notification.DEFAULT_SOUND)
                    .setAutoCancel(true)
                    .setPriority(Notification.PRIORITY_HIGH)
                    .setContentIntent(pendingIntent);

            NotificationManager notificationManager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.notify(0, noBuilder.build()); //0 = ID of notification

        }else{

            NotificationCompat.Builder noBuilder = new NotificationCompat.Builder(this)
                    .setSmallIcon(R.drawable.ic_notifications)
                    .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.hicabs_icon))
                    .setContentTitle("hicabs Notification")
                    .setContentText(message)
                    .setDefaults(Notification.DEFAULT_SOUND)
                    .setAutoCancel(true)
                    .setContentIntent(pendingIntent);

            NotificationManager notificationManager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.notify(0, noBuilder.build()); //0 = ID of notification
        }




    }
}
