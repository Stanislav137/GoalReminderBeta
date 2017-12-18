package com.goalreminderbeta.sa.goalreminderbeta.options;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;

import com.goalreminderbeta.sa.goalreminderbeta.R;
import com.goalreminderbeta.sa.goalreminderbeta.all.RecordsActivity;
import com.goalreminderbeta.sa.goalreminderbeta.all.StartActivity;

public class OptionsActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView notificationInterval;
    private Button firstDay;
    private Switch switchSound;
    private boolean correct;
    private SharedPreferences sharedPreferences;
    private  static final String SETTINGS = "settings";

    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options);

        sharedPreferences = getSharedPreferences(SETTINGS, Context.MODE_PRIVATE);
        notificationInterval = (TextView) findViewById(R.id.notificationInterval);
        firstDay = (Button) findViewById(R.id.firstDay);

        editor = sharedPreferences.edit();
        editor.putString("test", "t");
        editor.apply();
        if(sharedPreferences.contains(SETTINGS)) {
            notificationInterval.setText(sharedPreferences.getString(SETTINGS, "test"));
        }

        OptionsDTO optionsDTO = OptionsDTO.findById(OptionsDTO.class, 1);
        correct = optionsDTO.getSoundConfig();

        switchSound = (Switch) findViewById(R.id.switchSound);
        switchSound.setOnClickListener(this);
        verifySwitch();
    }

    private void verifySwitch() {
        if(correct) {
            switchSound.setChecked(true);
        } else {
            switchSound.setChecked(false);
        }
    }

    public void validationOptions() {
        if(correct) {
            correct = false;
        } else {
            correct = true;
        }
        saveOptions();
    }

    public void openGoals(View view) {
        Intent intent = new Intent(OptionsActivity.this, StartActivity.class);
        startActivity(intent);
        this.finish();
    }
    public void openRecords(View view) {
        Intent intent = new Intent(OptionsActivity.this, RecordsActivity.class);
        startActivity(intent);
        this.finish();
    }

    public void onBackPressed() {
        Intent intent = new Intent(this, StartActivity.class);
        startActivity(intent);
        this.finish();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.switchSound:
                validationOptions();
                break;
        }
    }

    private void saveOptions() {
        //OptionsDTO options = OptionsDTO.findById(OptionsDTO.class, 1);
        //options.setSoundConfig(correct);
        //options.save();
    }

    public void test(View view) {

        editor = sharedPreferences.edit();
        editor.putString("test", "t");
        editor.apply();
        if(sharedPreferences.contains(SETTINGS)) {
            notificationInterval.setText(sharedPreferences.getString(SETTINGS, "test"));
        }
    }
}
