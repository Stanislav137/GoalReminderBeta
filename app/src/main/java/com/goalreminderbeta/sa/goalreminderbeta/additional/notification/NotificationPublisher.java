package com.goalreminderbeta.sa.goalreminderbeta.additional.notification;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v4.app.NotificationCompat;

import com.goalreminderbeta.sa.goalreminderbeta.options.ConfigActivity;

public class NotificationPublisher extends BroadcastReceiver {

    public static String NOTIFICATION_ID = "notifID";
    public static String NOTIFICATION = "notif";

    public void onReceive(Context context, Intent intent) {


        NotificationManager notificationManager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);

        Notification notification = intent.getParcelableExtra(NOTIFICATION);

        int id = intent.getIntExtra(NOTIFICATION_ID, 0);
        boolean not = intent.getBooleanExtra("not",true);

        if(not)
        notificationManager.notify(id, notification);
        else{

        }

    }
}