package com.goalreminderbeta.sa.goalreminderbeta.additional.notification;

import android.app.ActivityManager;
import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.os.SystemClock;
import android.preference.PreferenceManager;
import android.support.v4.app.NotificationCompat;
import com.goalreminderbeta.sa.goalreminderbeta.R;
import com.goalreminderbeta.sa.goalreminderbeta.all.StartActivity;
public class NotificationService extends Service {
    NotificationManager manager;
    public static boolean isService=true;
    public static boolean isDay=true;
    AlarmManager am;
    public static boolean fromStart = false;
    public static boolean configSaved = false;
    public static String title="";
    public static String content="";
    public static int frequency=1;
    public static boolean soundOn=true;
    public static boolean vibrOn=true;
    public static boolean notifOn=true;
    public static int size=0;
    public static int[]days = {1,2,3,4,5,6,7};
    private static SharedPreferences sp;
    public NotificationService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        sp = PreferenceManager.getDefaultSharedPreferences(this);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        title = intent.getStringExtra("title");
        content = intent.getStringExtra("content");
        frequency = intent.getIntExtra("frequency",1);
        soundOn = intent.getBooleanExtra("soundOn",true);
        vibrOn = intent.getBooleanExtra("vibrOn",true);
        //days = intent.getIntArrayExtra("days");
        size = intent.getIntExtra("size",0);
        NotificationService.notifOn = intent.getBooleanExtra("notification",true);
        Context context = getApplicationContext();
        startNotification(context,title,content,frequency,soundOn,vibrOn,notifOn);
        return super.onStartCommand(intent,flags,startId);
    }


    public void startNotification(Context context,String title,String content, int frequency, boolean soundOn, boolean vibrOn,boolean notifOn){
        Notification notification = createNotification(context,title,content);
        if(soundOn) {
            if(vibrOn)
                notification.defaults = Notification.DEFAULT_VIBRATE | Notification.DEFAULT_SOUND;
            else
                notification.defaults = Notification.DEFAULT_SOUND;
        }else {
            if(vibrOn)
                notification.defaults = Notification.DEFAULT_VIBRATE;
            else{
            }
        }
        scheduleNotification(context,notification,frequency,notifOn);
    }

    private static void scheduleNotification(Context context,Notification notification,int frequency,boolean notifOn){

        Intent notifIntent = new Intent(context,NotificationPublisher.class);
        notifIntent.putExtra("notifID",1);
        notifIntent.putExtra("notif",notification);
        notifIntent.putExtra("not",notifOn);
        PendingIntent pIntent = PendingIntent.getBroadcast(context,0,notifIntent,PendingIntent.FLAG_UPDATE_CURRENT);
        long futureTime = SystemClock.elapsedRealtime()+frequency*1000;
        AlarmManager alarmManager = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.setRepeating(AlarmManager.ELAPSED_REALTIME,futureTime,frequency*10000,pIntent);
    }

    private static Notification createNotification(Context context, String title, String content){
        Intent intentNotif = new Intent(context,StartActivity.class);
        PendingIntent pIntent = PendingIntent.getActivity(context,0,intentNotif,0);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
        builder.setContentTitle(title);
        if(InformationService.send)builder.setContentText("IS "+content);
        else builder.setContentText(content);
        builder.setContentIntent(pIntent);
        builder.setWhen(System.currentTimeMillis());
        builder.setSmallIcon(R.drawable.my_goal_image);
        builder.setTicker("Be better!");
        builder.setAutoCancel(true);
        return  builder.build();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    /*@Override public void onTaskRemoved(Intent rootIntent){
        Intent restartServiceIntent = new Intent(this, NotificationService.class);
        //restartServiceIntent.setPackage(getPackageName());
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        //String title = sp.getString("title","");
        //String content = sp.getString("content","");
        //boolean soundOn = sp.getBoolean("sound",true);
        //boolean vibrOn=sp.getBoolean("vibration",true);
        //String frequency = sp.getString("interval","1");

        //int freq = Integer.parseInt(frequency);
        restartServiceIntent.putExtra("title",title);
        restartServiceIntent.putExtra("content", content);
        restartServiceIntent.putExtra("soundOn",soundOn);
        restartServiceIntent.putExtra("vibrOn",vibrOn);
        restartServiceIntent.putExtra("frequency",frequency);
        restartServiceIntent.putExtra("notification",notifOn);
        //PendingIntent restartServicePendingIntent = PendingIntent.getService(getApplicationContext(), 1, restartServiceIntent, PendingIntent.FLAG_ONE_SHOT);
        //AlarmManager alarmService = (AlarmManager) getApplicationContext().getSystemService(Context.ALARM_SERVICE);
        //alarmService.set(AlarmManager.ELAPSED_REALTIME, SystemClock.elapsedRealtime() + 1000, restartServicePendingIntent);
        startService(restartServiceIntent);
        super.onTaskRemoved(rootIntent);
    }*/

    private boolean isMyServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }
}
