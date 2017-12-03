package com.goalreminderbeta.sa.goalreminderbeta.additional.notification;

import android.app.Notification;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;

import com.goalreminderbeta.sa.goalreminderbeta.R;

public class ServiceHelper extends Service {
    public ServiceHelper() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
//comment
    @Override
    public void onCreate() {
        Notification.Builder builder = new Notification.Builder(this)
                .setSmallIcon(R.mipmap.logo);
        Notification notification=builder.build();
        startForeground(27, notification);
        stopForeground(true);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        NotificationCompat.Builder builder2 = new NotificationCompat.Builder(this).setSmallIcon(R.mipmap.logo);
        startForeground(27,builder2.build());
        stopForeground(true);
        return super.onStartCommand(intent, flags, startId);
    }
}
