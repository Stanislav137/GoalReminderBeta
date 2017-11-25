package com.goalreminderbeta.sa.goalreminderbeta.additional.notification;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

import com.goalreminderbeta.sa.goalreminderbeta.R;

/**
 * Created by Yevgeniya on 24.11.2017.
 */

public class NotificationFactory {

    private void scheduleNotification(Context context,Notification notification){
        Intent notifIntent = new Intent(context,NotificationService.class);
        notifIntent.putExtra("notifID",1);
        notifIntent.putExtra("notif",notification);
        PendingIntent pIntent = PendingIntent.getBroadcast(context,0,notifIntent,PendingIntent.FLAG_UPDATE_CURRENT);


    }

    private Notification createNotification(Context context, String title, String content){
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
        builder.setContentTitle(title);
        builder.setContentText(content);
        builder.setWhen(System.currentTimeMillis());
        builder.setSmallIcon(R.drawable.my_goal_image);
        builder.setTicker("Be better!");
        builder.setAutoCancel(true);
        return  builder.build();
    }
}
