package com.goalreminderbeta.sa.goalreminderbeta.additional.notification;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.support.v4.app.NotificationCompat;

import com.goalreminderbeta.sa.goalreminderbeta.R;

/**
 * Created by Yevgeniya on 02.12.2017.
 */
public class NotificationFactory {
    private static NotificationFactory factory;
    private String title;
    private String content;
    private PendingIntent pIntent;
    private Context context;

    private NotificationFactory(Context context, PendingIntent pIntent){

        this.context = context;
        this.pIntent = pIntent;
    }
    public static NotificationFactory getInstance(Context context,PendingIntent pIntent){
        if(factory==null) factory = new NotificationFactory(context,pIntent);
        return factory;
    }
    public Notification createNotification(){
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
        builder.setTicker("Notification");
        builder.setSmallIcon(R.mipmap.logo);
        builder.setContentTitle(title);
        builder.setContentText(content);
        builder.setAutoCancel(true);
        builder.setContentIntent(pIntent);
        return  builder.build();
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setpIntent(PendingIntent pIntent) {
        this.pIntent = pIntent;
    }

    public void setContext(Context context) {
        this.context = context;
    }
}

