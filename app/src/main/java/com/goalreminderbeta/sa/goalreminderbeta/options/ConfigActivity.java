package com.goalreminderbeta.sa.goalreminderbeta.options;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.goalreminderbeta.sa.goalreminderbeta.R;
import com.goalreminderbeta.sa.goalreminderbeta.additional.notification.NotificationService;
import com.goalreminderbeta.sa.goalreminderbeta.all.RecordsActivity;
import com.goalreminderbeta.sa.goalreminderbeta.all.StartActivity;



public class ConfigActivity extends Activity implements OnClickListener{
    private SharedPreferences sp;
    private static String frequency="1";
    private static boolean notifOn=true;
    private static boolean soundOn=true;
    private static boolean vibrOn=true;
    private static int[]selectedDays = new int[]{0,0,0,0,0,0,0};

    private Button save;

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
        if(notifOn){
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
            stopService(new Intent(this,NotificationService.class));

    }

    @Override
    protected void onResume() {
        super.onResume();
        frequency = sp.getString("interval","");
        notifOn = sp.getBoolean("notification",true);
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
        sp = PreferenceManager.getDefaultSharedPreferences(this);
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

        if(notifOn){
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
            stopService(new Intent(this,NotificationService.class));
    }
}
