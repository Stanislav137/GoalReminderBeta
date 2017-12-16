package com.goalreminderbeta.sa.goalreminderbeta.additional.notification;

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
import android.support.v4.app.NotificationCompat;

import com.goalreminderbeta.sa.goalreminderbeta.R;
import com.goalreminderbeta.sa.goalreminderbeta.all.StartActivity;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class MyService extends Service {
    SharedPreferences sp;
    NotificationFactory factory;
    Context context;
    AlarmManager alarmManager;

   // NotificationManager notificationManager;

    public MyService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        NotificationCompat.Builder builder2 = new NotificationCompat.Builder(this).setSmallIcon(R.mipmap.logo);
        startForeground(27,builder2.build());
        Intent hideIntent = new Intent(this, ServiceHelper.class);
        startService(hideIntent);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        String title = intent.getStringExtra("title");
        String text = intent.getStringExtra("text");
        String freq = intent.getStringExtra("interval");

        int freqNum = Integer.parseInt(freq);
        boolean notOn = intent.getBooleanExtra("notification",true);
        boolean soundOn = intent.getBooleanExtra("sound",true);
        boolean vibrOn = intent.getBooleanExtra("vibration",true);
        long delay = intent.getLongExtra("delay",3600000);
        //StringBuffer resultText = new StringBuffer();
        //resultText.append(text+" f "+freqNum+" n "+notOn+" "+" s "+soundOn+" v "+vibrOn);
        context = getApplicationContext();

        Notification notification = createNotification(context,title,text,soundOn,vibrOn);

        send(context,notification,freqNum,delay,notOn);
        NotificationCompat.Builder builder2 = new NotificationCompat.Builder(this).setSmallIcon(R.mipmap.logo);
        startForeground(27,builder2.build());
        Intent hideIntent = new Intent(this, ServiceHelper.class);
        startService(hideIntent);


        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        //throw new UnsupportedOperationException("Not yet implemented");
        return null;
    }

    private void send(Context context, Notification notification, int freqNum, long delay,boolean notOn){
        Intent notifIntent = new Intent(context,NotificationPublisher.class);
        notifIntent.putExtra("notifID",1);
        notifIntent.putExtra("notification",notification);
        notifIntent.putExtra("notOn",notOn);

        PendingIntent pIntent = PendingIntent.getBroadcast(context,0,notifIntent,PendingIntent.FLAG_UPDATE_CURRENT);
        long futureTime = SystemClock.elapsedRealtime()+delay;
        AlarmManager alarmManager = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.setRepeating(AlarmManager.ELAPSED_REALTIME,futureTime,freqNum*1000*3600,pIntent);

    }

    private Notification createNotification(Context context,String title,String resultText,boolean soundOn, boolean vibrOn){
        Intent serviceIntent = new Intent(this,StartActivity.class);
        PendingIntent pIntent = PendingIntent.getActivity(context,0,serviceIntent,0);
        factory = NotificationFactory.getInstance(context,pIntent);
        factory.setTitle(title);
        factory.setContent(resultText);
        Notification notification = factory.createNotification();
        if(soundOn&&vibrOn)
        {notification.defaults = Notification.DEFAULT_SOUND|Notification.DEFAULT_VIBRATE;}
        else if(soundOn&&!vibrOn)
        {notification.defaults = Notification.DEFAULT_SOUND;}
        else if(!soundOn&&vibrOn)
        {notification.defaults = Notification.DEFAULT_VIBRATE;}
        else{}
        return  notification;
    }

    /*@Override
    public void onTaskRemoved(Intent rootIntent) {
        super.onTaskRemoved(rootIntent);
        sp = PreferenceManager.getDefaultSharedPreferences(this);
        String freq = sp.getString("interval","");
        boolean notOn = sp.getBoolean("notification",true);
        boolean soundOn = sp.getBoolean("sound",true);
        boolean vibrOn = sp.getBoolean("vibration",true);
        Intent intent = new Intent(this,MyService.class);
        intent.putExtra("text","From TR");
        intent.putExtra("title","From TR");
        intent.putExtra("interval",freq);
        intent.putExtra("notification",notOn);
        intent.putExtra("sound",soundOn);
        intent.putExtra("vibration",vibrOn);
        startService(intent);
    }*/
}

