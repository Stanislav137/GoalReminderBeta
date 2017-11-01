package com.goalreminderbeta.sa.goalreminderbeta.additional.notification;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.Button;

import com.goalreminderbeta.sa.goalreminderbeta.R;

import java.text.SimpleDateFormat;
import java.util.Date;

public class NotificationTest extends Activity implements View.OnClickListener {

    Button fivesek, tensek, thirtysek;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_layout);

        fivesek = (Button) findViewById(R.id.fivesek);
        tensek = (Button) findViewById(R.id.tensek);
        thirtysek = (Button) findViewById(R.id.thirtysek);

        fivesek.setOnClickListener(this);
        tensek.setOnClickListener(this);
        thirtysek.setOnClickListener(this);
    }

    public void scheduleNotification(Notification notification, int delay) {

        Intent notificationIntent = new Intent(this, NotificationPublisher.class);
        notificationIntent.putExtra(NotificationPublisher.NOTIFICATION_ID, 1);
        notificationIntent.putExtra(NotificationPublisher.NOTIFICATION, notification);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        long futureInMillis = SystemClock.elapsedRealtime() + delay;
        AlarmManager alarmManager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, futureInMillis, pendingIntent);
    }

    @SuppressLint("NewApi")
    public Notification getNotification(String content) {
        Notification.Builder builder = new Notification.Builder(this);
        builder.setContentTitle("Scheduled Notification");
        builder.setContentText(content);
        builder.setSmallIcon(R.drawable.arrow_goal);
        return builder.build();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fivesek:
                Date d=new Date();
                SimpleDateFormat sdf=new SimpleDateFormat("hh");
                int currentDateTimeString = Integer.parseInt(sdf.format(d));
                if(currentDateTimeString > 6 && currentDateTimeString < 24) {
                    scheduleNotification(getNotification("5 second delay"), 5000);
                } else {
                    break;
                }
                break;
            case R.id.tensek:
                scheduleNotification(getNotification("10 second delay"), 10000);
                break;
            case R.id.thirtysek:
                scheduleNotification(getNotification("30 second delay"), 30000);
                break;
        }
    }
}