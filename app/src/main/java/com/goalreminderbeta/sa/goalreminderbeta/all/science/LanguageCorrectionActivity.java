package com.goalreminderbeta.sa.goalreminderbeta.all.science;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.goalreminderbeta.sa.goalreminderbeta.R;
import com.goalreminderbeta.sa.goalreminderbeta.additional.BootStrap;
import com.goalreminderbeta.sa.goalreminderbeta.additional.CustomDatePicker;
import com.goalreminderbeta.sa.goalreminderbeta.all.StartActivity;
import com.goalreminderbeta.sa.goalreminderbeta.all.sport.ElementCorrectionActivity;
import com.goalreminderbeta.sa.goalreminderbeta.all.sport.RunCorrectionActivity;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;

public class LanguageCorrectionActivity extends AppCompatActivity {

    private Date dateFrom, dateTo;
    private TextView sportDateFrom, sportDateTo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.language_theme);

        findAllBtnsBootStrap(); // using for BootStrap!
        findAllButtons();
    }

    private void findAllButtons() {
        sportDateFrom = (TextView) findViewById(R.id.sportDateFrom);
        sportDateTo = (TextView) findViewById(R.id.sportDateTo);
    }

    public void pickDateFrom(View view) throws ParseException {
        CustomDatePicker.pickDate(LanguageCorrectionActivity.this, sportDateFrom);
    }
    public void pickDateTo(View view) {
        CustomDatePicker.pickDate(LanguageCorrectionActivity.this, sportDateTo);
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
