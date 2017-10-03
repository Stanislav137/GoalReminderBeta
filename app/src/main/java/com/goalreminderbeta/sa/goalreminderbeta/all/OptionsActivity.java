package com.goalreminderbeta.sa.goalreminderbeta.all;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.v4.app.TaskStackBuilder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.goalreminderbeta.sa.goalreminderbeta.additional.notifications.NotificationConfig;
import com.goalreminderbeta.sa.goalreminderbeta.R;

public class OptionsActivity extends AppCompatActivity {

    private static final int NOTIFY_ID = 101;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options);
    }

    public void openGoals(View view) {
        Intent intent = new Intent(OptionsActivity.this, StartActivity.class);
        startActivity(intent);
        this.finish();
    }

    public void openRecords(View view) {
        Intent intent = new Intent(OptionsActivity.this, RecordsActivity.class);
        startActivity(intent);
        this.finish();
    }
    public void testNotification(View view) {

        Context context = getApplicationContext();

        Intent intent = new Intent(context, StartActivity.class);
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
        stackBuilder.addParentStack(StartActivity.class);
        stackBuilder.addNextIntent(intent);
        PendingIntent pendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);

        Resources res = context.getResources();
        Notification.Builder builder = new Notification.Builder(context);

        builder.setContentIntent(pendingIntent)
                .setSmallIcon(R.drawable.arrow_goal)
                .setLargeIcon(BitmapFactory.decodeResource(res, R.drawable.btn_orange))
                .setTicker("Ticker")
                .setWhen(System.currentTimeMillis())
                .setAutoCancel(true)
                .setContentTitle("Title")
                .setContentText("Text");

        Notification notification = builder.build();
        long[] vibrate = NotificationConfig.vibrationArray;
        Uri ring = NotificationConfig.ringURI;
        notification.sound = ring;
        notification.vibrate = vibrate;

        NotificationManager notificationManager = (NotificationManager) context
                .getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(NOTIFY_ID, notification);


    }
}
