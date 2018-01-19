package com.goalreminderbeta.sa.goalreminderbeta.additional.notification;

import android.app.ActivityManager;
import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.preference.PreferenceManager;
import android.support.v4.app.NotificationCompat;

import com.goalreminderbeta.sa.goalreminderbeta.R;
import com.goalreminderbeta.sa.goalreminderbeta.additional.DayPicker;
import com.goalreminderbeta.sa.goalreminderbeta.all.StartActivity;
import com.goalreminderbeta.sa.goalreminderbeta.options.ConfigActivity;

public class NotificationPublisher extends BroadcastReceiver {
    NotificationManager nm;
    private int frequency=1;
    private int size=0;
    String freq="1";
    private String text="Default text";
    private String title="Default title";
    private boolean notOn=true;
    private boolean soundOn=true;
    private boolean vibrOn=true;
    private boolean delay=false;
    SharedPreferences sp;
    private String typeLang="en";
    private long t=5000;
    AlarmManager alarmManager;
    Intent i;
    PendingIntent pending;
    @Override
    public void onReceive(Context context, Intent intent) {
        nm = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
        if(alarmManager!=null){
            alarmManager.cancel(pending);
        }
        sp = PreferenceManager.getDefaultSharedPreferences(context);
        freq = sp.getString("interval","1");
        switch (freq){
            case "1":{frequency=1;}break;
            case "2":{frequency=2;}break;
            case "3":{frequency=3;}break;
            case "4":{frequency=4;}break;
            default:{frequency=1;}
        }
        notOn = sp.getBoolean("notification",true);
        soundOn = sp.getBoolean("sound",true);
        vibrOn = sp.getBoolean("vibration",true);
        delay = sp.getBoolean("delay",false);
        typeLang = sp.getString("lang","en");
        size=sp.getInt("goals",0);
        if(size>0) {
            if((typeLang.equals("en")||typeLang.equals("pl")))
            {text = "You goals are ready!";
            title = "Keep it up!";}
            else{
                title="Проверь свои цели на сегодня!";
                text="Твои цели записаны!";
            }
        }
        else{
            if((typeLang.equals("en")||typeLang.equals("pl")))
            {text = "You have no goals!";
            title="Add some goal to start";}
            else{
                text="Ты не записал цели!";
                title="Добавь целей, чтобы начать!";
            }
        }

        if(notOn){
            sendNotif(context,title,text,soundOn,vibrOn);
            alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
            i = new Intent(context, NotificationPublisher.class);
            pending = PendingIntent.getBroadcast(context, 0, i, PendingIntent.FLAG_CANCEL_CURRENT);
            t = System.currentTimeMillis() + frequency*3000;
            alarmManager.set(AlarmManager.RTC, t, pending);
        }else {
       //alarmManager.cancel(pending);
        }
    }

    void sendNotif(Context context,String title,String text,boolean soundOn,boolean vibrOn) {
        Intent intent = new Intent(context, StartActivity.class);
        PendingIntent pIntent = PendingIntent.getActivity(context, 0, intent, 0);
        Notification notification = createNotification(context,title,text,pIntent);
        if(soundOn&&vibrOn){
            notification.defaults = Notification.DEFAULT_SOUND|Notification.DEFAULT_VIBRATE;
        }
        if(soundOn&&!vibrOn){
            notification.defaults = Notification.DEFAULT_SOUND;
        }
        if(!soundOn&&vibrOn){
            notification.defaults = Notification.DEFAULT_VIBRATE;
        }
        nm.notify(1, notification);
    }

    public Notification createNotification(Context context, String title,String text,PendingIntent pIntent) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
        BitmapFactory.Options options = new BitmapFactory.Options();
        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(),R.mipmap.logo,options);
        builder.setTicker("Notification");
        builder.setSmallIcon(R.drawable.logo_notif);
        builder.setLargeIcon(bitmap);
        builder.setContentTitle(title);
        builder.setContentText(text);
        builder.setAutoCancel(true);
        builder.setContentIntent(pIntent);
        return builder.build();
    }
}