package com.goalreminderbeta.sa.goalreminderbeta.options;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Switch;

import com.goalreminderbeta.sa.goalreminderbeta.R;
import com.goalreminderbeta.sa.goalreminderbeta.all.RecordsActivity;
import com.goalreminderbeta.sa.goalreminderbeta.all.StartActivity;

public class OptionsActivity extends AppCompatActivity implements View.OnClickListener {

    private Switch switchSound;
    private static boolean correct;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options);

        OptionsDTO optionsDTO = new OptionsDTO();
        correct = optionsDTO.isCorrect();

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
        OptionsDTO optionsDTO = new OptionsDTO(correct);
        optionsDTO.save();
    }
}
