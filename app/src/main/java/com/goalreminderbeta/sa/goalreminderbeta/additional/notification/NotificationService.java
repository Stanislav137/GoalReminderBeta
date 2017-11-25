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
import android.os.SystemClock;
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
    public static boolean isDay=true;
    AlarmManager am;
    public NotificationService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {


        Context context = getApplicationContext();

        if(isService) {
            Calendar calendar = Calendar.getInstance();
            int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
            int frequency = Integer.parseInt(intent.getStringExtra("frequency"));
            int[]days = intent.getIntArrayExtra("days");
            if(isDay(dayOfWeek,days)){
            try{
                TimeUnit.SECONDS.sleep(frequency*10);
            }catch (InterruptedException e){e.printStackTrace();}
            sendNotification(context,intent);
            }/*else{
                while (!isDay){
                    isDay(dayOfWeek,intent.getIntArrayExtra("days"));
                }
                try{
                    TimeUnit.SECONDS.sleep(frequency*10);
                }catch (InterruptedException e){e.printStackTrace();}
                sendNotification(context,intent);
            }*/
        }

        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private void sendNotification(Context context,Intent intent){
        int frequency = intent.getIntExtra("frequency",1);
        int[]days = intent.getIntArrayExtra("days");
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

       // am = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
        //am.setRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP, SystemClock.elapsedRealtime()+5000,10000,pIntent);
        manager.notify(NOTIFICATION_ID,notification);

    }

    public static Notification createNotification(String content, String title, Context context, PendingIntent pIntent) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
        builder.setContentIntent(pIntent);
        builder.setWhen(System.currentTimeMillis());

        builder.setSmallIcon(R.mipmap.notification);
        builder.setTicker("Be better");
        builder.setLargeIcon(BitmapFactory.decodeResource(context.getResources(),R.mipmap.logo));

        builder.setSmallIcon(R.drawable.my_goal_image);
        builder.setTicker("Be better");

        builder.setAutoCancel(true);
        builder.setContentTitle(title);
        builder.setContentText(content);

        return builder.build();
    }

    private boolean isDay(int day, int[]days){
        for(int d:days){
            if(days[day-2]!=0){
                isDay=true;
                return isDay;
            }
        }
        isDay=false;
        return isDay;
    }




}
