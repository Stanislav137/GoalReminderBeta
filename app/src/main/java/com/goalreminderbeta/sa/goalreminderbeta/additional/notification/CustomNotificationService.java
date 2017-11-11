package com.goalreminderbeta.sa.goalreminderbeta.additional.notification;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.SystemClock;

import com.goalreminderbeta.sa.goalreminderbeta.R;

public class CustomNotificationService {

    public static Notification createNotification(String content, String title, Context context, boolean correct) {

        Notification.Builder builder = new Notification.Builder(context);
        correctNotification(correct, builder);
        builder.setContentTitle(title);
        builder.setContentText(content);
        builder.setTicker("This is ticker");
        builder.setSmallIcon(R.drawable.arrow_goal);
        //builder.setDefaults(Notification.DEFAULT_ALL);
        // setPriority(NotificationCompat.PRIORITY_LOW)
        return builder.build();
    }

    public static void correctNotification(boolean correct, Notification.Builder builder) {
        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        if(correct) {
            builder.setVibrate(new long[] { 300, 1000, 1000});
            builder.setSound(defaultSoundUri);
        }
    }

    public static void scheduleNotification(Notification notification, int delay, Context context) {

        Intent notificationIntent = new Intent(context, NotificationPublisher.class);
        notificationIntent.putExtra(NotificationPublisher.NOTIFICATION_ID, 1);
        notificationIntent.putExtra(NotificationPublisher.NOTIFICATION, notification);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        long futureInMillis = SystemClock.elapsedRealtime() + delay;
        AlarmManager alarmManager = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, futureInMillis, pendingIntent);
    }
}