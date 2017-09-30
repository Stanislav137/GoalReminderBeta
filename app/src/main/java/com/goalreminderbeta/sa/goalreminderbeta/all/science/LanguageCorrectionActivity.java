package com.goalreminderbeta.sa.goalreminderbeta.all.science;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.goalreminderbeta.sa.goalreminderbeta.R;
import com.goalreminderbeta.sa.goalreminderbeta.additional.BootStrap;
import com.goalreminderbeta.sa.goalreminderbeta.all.StartActivity;
import com.goalreminderbeta.sa.goalreminderbeta.all.sport.RunCorrectionActivity;

import java.util.ArrayList;

public class LanguageCorrectionActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.language_theme);

        findAllBtnsBootStrap(); // using for BootStrap!
    }

    public void backToHome(View view) {
        Intent intent = new Intent(LanguageCorrectionActivity.this, StartActivity.class);
        startActivity(intent);
        this.finish();
    }

    private void findAllBtnsBootStrap() {
        ArrayList<Button> allBtnsRun = new ArrayList<>();
        Button lvlLanguageCurrent = (Button) findViewById(R.id.lvlLanguageCurrent);
        Button lvlLanguageGoal = (Button) findViewById(R.id.lvlLanguageGoal);
        allBtnsRun.add(lvlLanguageCurrent);
        allBtnsRun.add(lvlLanguageGoal);
        startBootStrap(allBtnsRun);
    }

    private void startBootStrap(ArrayList<Button> allBtnsRun) {
        BootStrap bootStrap = new BootStrap();
        bootStrap.bootStrapResultsBtns(LanguageCorrectionActivity.this, allBtnsRun);
    }
}
