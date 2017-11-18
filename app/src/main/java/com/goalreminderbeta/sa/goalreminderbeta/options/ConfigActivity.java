package com.goalreminderbeta.sa.goalreminderbeta.options;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.TextView;

import com.goalreminderbeta.sa.goalreminderbeta.R;
import com.goalreminderbeta.sa.goalreminderbeta.all.RecordsActivity;
import com.goalreminderbeta.sa.goalreminderbeta.all.StartActivity;



public class ConfigActivity extends Activity {
    private SharedPreferences sp;
    private String frequency="1";
    private boolean notifOn=true;
    private boolean soundOn=true;
    private boolean vibrOn=true;
    private int[]selectedDays = new int[]{0,0,0,0,0,0,0};

    public String getFrequency() {
        return frequency;
    }

    public boolean isNotifOn() {
        return notifOn;
    }

    public boolean isSoundOn() {
        return soundOn;
    }

    public boolean isVibrOn() {
        return vibrOn;
    }

    public int[] getSelectedDays() {
        return selectedDays;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_config);
        getFragmentManager().beginTransaction().add(R.id.mainLL,new Config()).commit();

        sp = PreferenceManager.getDefaultSharedPreferences(this);
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


}
