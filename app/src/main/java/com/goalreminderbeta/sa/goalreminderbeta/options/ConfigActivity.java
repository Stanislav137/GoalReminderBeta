package com.goalreminderbeta.sa.goalreminderbeta.options;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.goalreminderbeta.sa.goalreminderbeta.R;
import com.goalreminderbeta.sa.goalreminderbeta.additional.notification.NotificationService;
import com.goalreminderbeta.sa.goalreminderbeta.all.RecordsActivity;
import com.goalreminderbeta.sa.goalreminderbeta.all.StartActivity;

import java.util.Calendar;


public class ConfigActivity extends Activity implements OnClickListener{
    private SharedPreferences sp;
    private static String frequency="1";
    public static boolean notifOn=true;
    private static boolean soundOn=true;
    private static boolean vibrOn=true;
    private static int[]selectedDays = new int[]{0,0,0,0,0,0,0};

    private Button save;
    private TextView config;

    public static String getFrequency() {
        return frequency;
    }

    public static boolean isNotifOn() {
        return notifOn;
    }

    public static boolean isSoundOn() {
        return soundOn;
    }

    public static boolean isVibrOn() {
        return vibrOn;
    }

    public static int[] getSelectedDays() {
        return selectedDays;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_config);
        getFragmentManager().beginTransaction().add(R.id.mainLL,new Config()).commit();
        save = (Button)findViewById(R.id.saveBTN);
        save.setOnClickListener(this);
        sp = PreferenceManager.getDefaultSharedPreferences(this);
        /*if(notifOn){
            stopService(new Intent(this,NotificationService.class));
            Intent intent = new Intent(this,NotificationService.class);
            intent.putExtra("size",StartActivity.sizeOfList);
            intent.putExtra("frequency",frequency);
            intent.putExtra("sound",soundOn);
            intent.putExtra("vibr",vibrOn);
            intent.putExtra("days",selectedDays);
            NotificationService.isService = true;
            startService(intent);
        }
        else
            NotificationService.isService = false;
            stopService(new Intent(this,NotificationService.class));*/

    }

    @Override
    protected void onResume() {
        super.onResume();
        frequency = sp.getString("interval","");
        notifOn = sp.getBoolean("notification",true);
        soundOn = sp.getBoolean("sound",true);
        vibrOn = sp.getBoolean("vibration",true);
        for(int i=0;i<7;i++){
            boolean a = sp.getBoolean("day"+(i+1),false);
            if(!a){
                selectedDays[i]=i+1;
            }else{
                selectedDays[i]=0;
            }
        }

    }

    public void openGoals(View view) {
        Intent intent = new Intent(ConfigActivity.this, StartActivity.class);
        startActivity(intent);
        this.finish();
    }
    public void openRecords(View view) {
        Intent intent = new Intent(ConfigActivity.this, RecordsActivity.class);
        startActivity(intent);
        this.finish();
    }


    @Override
    public void onClick(View view) {
        NotificationService.configSaved = true;
        //Calendar calendar = Calendar.getInstance();
       // int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
        sp = PreferenceManager.getDefaultSharedPreferences(this);
        frequency = sp.getString("interval","");
        int freq = Integer.parseInt(frequency);
        notifOn = sp.getBoolean("notification",false);
        NotificationService.notifOn=notifOn;
        soundOn = sp.getBoolean("sound",false);
        vibrOn = sp.getBoolean("vibration",false);
        for(int i=0;i<7;i++){
            boolean a = sp.getBoolean("day"+(i+1),false);
            if(!a){
                selectedDays[i]=i+1;
            }else{
                selectedDays[i]=0;
            }
        }
        SharedPreferences.Editor editor = sp.edit();
       // editor.putString("interval",frequency);
        //editor.putBoolean("notification",notifOn);
        //editor.putBoolean("sound",soundOn);
        //editor.putBoolean("vibration",vibrOn);
        editor.putBoolean("fromStart", true);
        NotificationService.fromStart = sp.getBoolean("fromStart", true);
        //editor.commit();
        /*StringBuilder sb = new StringBuilder("");
        sb.append("freq:"+frequency+" notifOn "+notifOn+" soundON "+soundOn+" vibrOn "+vibrOn+" days");
        for(int i:selectedDays){
            sb.append(" "+i);
        }
        config.setText(sb.toString());*/
        Intent intent = new Intent(this,NotificationService.class);
        //editor = sp.edit();
        if(StartActivity.sizeOfList>0){
            intent.putExtra("title", "You goals are ready!");
            intent.putExtra("content", "Keep it up!");
            editor.putString("title","You goals are ready!");
            editor.putString("content","Keep it up!");
            editor.putBoolean("notification",notifOn);
            editor.commit();
        }else{
            intent.putExtra("title", "You have no goals!");
            intent.putExtra("content", "Add some goal to start");
            editor.putString("title","You have no goals!");
            editor.putString("content","Add some goal to start");
            editor.putBoolean("notification",notifOn);
            editor.commit();
        }
        intent.putExtra("frequency",freq);
        intent.putExtra("soundOn",soundOn);
        intent.putExtra("vibrOn",vibrOn);
        intent.putExtra("notification",notifOn);

            NotificationService.isService = true;
            startService(intent);


    }


    /*@Override
    protected void onDestroy() {
        super.onDestroy();
        if(!sp.getBoolean("fromStart",false)) {
            Intent intent = new Intent(this, NotificationService.class);
            intent.putExtra("size",StartActivity.sizeOfList);
            SharedPreferences.Editor editor = sp.edit();
            if(StartActivity.sizeOfList>0){
                intent.putExtra("title", "DC You goals are ready!");
                intent.putExtra("content", "Keep it up!");
                editor.putString("title","DC You goals are ready!");
                editor.putString("content","Keep it up!");
                editor.commit();
            }else{
                intent.putExtra("title", "DC You have no goals!");
                intent.putExtra("content", "Add some goal to start");
                editor.putString("title","DC You have no goals!");
                editor.putString("content","Add some goal to start");
                editor.commit();
            }
            intent.putExtra("frequency", Integer.parseInt(frequency));
            intent.putExtra("soundOn", soundOn);
            intent.putExtra("vibrOn", vibrOn);
            intent.putExtra("notifOn",notifOn);
            if(ConfigActivity.isNotifOn()) {
                NotificationService.isService = true;
                editor = sp.edit();
                editor.putBoolean("notif",notifOn);
                startService(intent);
            }
            else{
                NotificationService.isService = false;
                stopService(intent);}
        }
    }*/
        /*Intent restartIntent = new Intent(this, NotificationService.class);
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        String title = sp.getString("title","");
        String content = sp.getString("content","");
        restartIntent.putExtra("title", "fromDestr conf "+title);
        restartIntent.putExtra("content", content);

        PendingIntent pi = PendingIntent.getService(this, 1, restartIntent, PendingIntent.FLAG_ONE_SHOT);
        AlarmManager am = (AlarmManager) getSystemService(ALARM_SERVICE);

        am.setRepeating(AlarmManager.RTC, System.currentTimeMillis() + 3000,5000, pi);*/


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
