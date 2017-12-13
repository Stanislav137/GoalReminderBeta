package com.goalreminderbeta.sa.goalreminderbeta.options;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.goalreminderbeta.sa.goalreminderbeta.R;

import com.goalreminderbeta.sa.goalreminderbeta.additional.notification.MyService;

import com.goalreminderbeta.sa.goalreminderbeta.all.RecordsActivity;
import com.goalreminderbeta.sa.goalreminderbeta.all.StartActivity;

public class ConfigActivity extends Activity implements OnClickListener{
    private BroadcastReceiver br;
    private IntentFilter filter;
    private SharedPreferences sp;
    SharedPreferences.Editor editor;
    Intent serviceIntent,serviceIntent2;

    private String freq;
    private boolean notOn;
    private boolean soundOn;
    private boolean vibrOn;
    private boolean isServ;
    private boolean fromMain;
    private boolean days[];
    private int size;

    private Button save;
    private TextView config,dayText;

    public String getFrequency() {
        return freq;
    }
    public boolean isNotifOn() {
        return notOn;
    }
    public boolean isSoundOn() {
        return soundOn;
    }
    public boolean isVibrOn() {
        return vibrOn;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_config);
        getFragmentManager().beginTransaction().add(R.id.mainLL,new Config()).commit();
        config = (TextView)findViewById(R.id.configTV);
        dayText = (TextView)findViewById(R.id.dayTV);
        sp = PreferenceManager.getDefaultSharedPreferences(this);
        days = new boolean[7];
        save = (Button)findViewById(R.id.saveBTN);
        save.setOnClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        freq = sp.getString("interval","");
        notOn = sp.getBoolean("notification",true);
        soundOn = sp.getBoolean("sound",true);
        vibrOn = sp.getBoolean("vibration",true);
        fromMain=sp.getBoolean("main",true);
        size = sp.getInt("goals",0);
        isServ  = isMyServiceRunning(MyService.class);
        for(int i=0;i<7;i++){
            days[i]=sp.getBoolean(""+i,false);
        }
        StringBuilder sb = new StringBuilder();
        for(int i=0;i<7;i++){
            sb.append(String.valueOf(days[i])).append(" ");
        }

        config.setText("F "+freq+" nOn "+notOn+" sOn "+soundOn+" vOn "+vibrOn+" fM "+fromMain+" size "+size+
                " Alive1 "+isMyServiceRunning(MyService.class)+
                " days "+sb.toString());

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
        editor = sp.edit();
        editor.putBoolean("main",false);
        editor.commit();
        freq = sp.getString("interval","");
        notOn = sp.getBoolean("notification",true);
        soundOn = sp.getBoolean("sound",true);
        vibrOn = sp.getBoolean("vibration",true);
        fromMain=sp.getBoolean("main",true);
        isServ  = isMyServiceRunning(MyService.class);
        for(int i=0;i<7;i++){
            days[i]=sp.getBoolean(""+i,false);
        }
        StringBuilder sb = new StringBuilder();
        for(int i=0;i<7;i++){
            sb.append(String.valueOf(days[i])).append(" ");
        }
        size = sp.getInt("goals",0);


        serviceIntent = new Intent(this,MyService.class);
        if(sp.getInt("goals",0)>0) {
            serviceIntent.putExtra("title", "You goals are ready!");
            serviceIntent.putExtra("text", "Keep it up!");
        }
        else {
            serviceIntent.putExtra("title", "You have no goals!");
            serviceIntent.putExtra("text", "Add some goal to start");
        }
        serviceIntent.putExtra("interval",freq);
        serviceIntent.putExtra("notification",notOn);
        serviceIntent.putExtra("sound",soundOn);
        serviceIntent.putExtra("vibration",vibrOn);
        startService(serviceIntent);

        config.setText("F "+freq+" nOn "+notOn+" sOn "+soundOn+" vOn "+vibrOn+" fM "+fromMain
                +" size "+size
                +" Alive1 "+isMyServiceRunning(MyService.class)
               +" days "+sb.toString());
    }



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
