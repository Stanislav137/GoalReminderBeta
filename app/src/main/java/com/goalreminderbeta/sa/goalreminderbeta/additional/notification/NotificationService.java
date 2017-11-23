package com.goalreminderbeta.sa.goalreminderbeta.additional.notification;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;

import com.goalreminderbeta.sa.goalreminderbeta.R;
import com.goalreminderbeta.sa.goalreminderbeta.all.StartActivity;
import com.goalreminderbeta.sa.goalreminderbeta.goals.Goal;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class NotificationService extends Service {
    NotificationManager manager;
    Notification notification;
    private static final int NOTIFICATION_ID=1;
    public PendingIntent servicePendingIntent;
    public static boolean isService=true;
    public NotificationService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        int frequency = intent.getIntExtra("frequency",1);
        int[]days = intent.getIntArrayExtra("days");
        Calendar calendar = Calendar.getInstance();
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
        Context context = getApplicationContext();
                try{
                   TimeUnit.SECONDS.sleep(frequency*10);
        }catch (InterruptedException e){e.printStackTrace();}
                sendNotification(context,intent);
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private void sendNotification(Context context,Intent intent){
        int size = intent.getIntExtra("size",0);
        boolean sound = intent.getBooleanExtra("sound",true);
        boolean vibr = intent.getBooleanExtra("vibr",true);

        Intent intentNotif = new Intent(context,StartActivity.class);
        PendingIntent pIntent = PendingIntent.getActivity(context,0,intentNotif,0);
        if(size==0){
        notification = createNotification(
                "You have no goals!",
                "Add some goal to start!",context,pIntent);
        }
        else{
            notification = createNotification(
                    "You goals are ready!",
                    "Keep it up!",context,pIntent);
        }
        if(sound) {
            if(vibr)
            notification.defaults = Notification.DEFAULT_VIBRATE | Notification.DEFAULT_SOUND;
            else
                notification.defaults = Notification.DEFAULT_SOUND;
        }else {
            if(vibr)
                notification.defaults = Notification.DEFAULT_VIBRATE;
            else{
            }
        }
        manager.notify(NOTIFICATION_ID,notification);

    }

    public static Notification createNotification(String content, String title, Context context, PendingIntent pIntent) {
        //builder.setDefaults(Notification.DEFAULT_ALL);
        // setPriority(NotificationCompat.PRIORITY_LOW)*/
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
        builder.setContentIntent(pIntent);
        builder.setWhen(System.currentTimeMillis());
        builder.setSmallIcon(R.drawable.logo);
        builder.setTicker("Be better");
        builder.setLargeIcon(BitmapFactory.decodeResource(context.getResources(),R.mipmap.logo));
        builder.setAutoCancel(true);
        builder.setContentTitle(title);
        builder.setContentText(content); // Текст уведомления

        return builder.build();
    }

    private boolean isDay(int day, int[]days){
        for(int d:days){
            if(days[day-2]!=0){
                return true;
            }
        }
        return false;
    }


}
