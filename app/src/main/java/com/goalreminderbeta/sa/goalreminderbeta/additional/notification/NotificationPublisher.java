package com.goalreminderbeta.sa.goalreminderbeta.additional.notification;

import android.app.ActivityManager;
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
    public void onReceive(Context context, Intent intent) {

        NotificationManager notificationManager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
        Notification notification = intent.getParcelableExtra("notification");
        boolean notOn = intent.getBooleanExtra("notOn",true);
        if(notOn)
        {
            notificationManager.notify(1, notification);
        }

    }

}