package com.goalreminderbeta.sa.goalreminderbeta.all.sport;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import com.goalreminderbeta.sa.goalreminderbeta.R;
import com.goalreminderbeta.sa.goalreminderbeta.additional.BootStrap;
import com.goalreminderbeta.sa.goalreminderbeta.all.AllSectionTheme;

import java.util.ArrayList;

public class AllSubThemesSport extends AppCompatActivity {

    private LinearLayout weightCorrectionGoal;
    private Typeface faceBold = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.all_subthemes_sport);

        weightCorrectionGoal = (LinearLayout) findViewById(R.id.weightCorrectionGoal);
        findImagesBanners();
    }

    public void createWeightCorrectionGoal(View view) {
        Intent intent = new Intent(AllSubThemesSport.this, WeightCorrectionActivity.class);
        startActivity(intent);
        this.finish();
    }

    public void createRunCorrectionGoal(View view) {
        Intent intent = new Intent(AllSubThemesSport.this, CardioActivity.class);
        startActivity(intent);
        this.finish();
    }

    public void createRepeatsCorrectionGoal(View view) {
        Intent intent = new Intent(AllSubThemesSport.this, RepeatsCorrectionActivity.class);
        startActivity(intent);
        this.finish();
    }

    public void findImagesBanners() {
        ArrayList<Button> allBtns = new ArrayList<>();
        Button imgSport = (Button) findViewById(R.id.imThemeWeight);
        Button imThemeRun = (Button) findViewById(R.id.imThemeRun);
        Button imThemeRepeats = (Button) findViewById(R.id.imThemeRepeats);
        allBtns.add(imgSport);
        allBtns.add(imThemeRun);
        allBtns.add(imThemeRepeats);
        startBootStrap(allBtns);
    }

    public void startBootStrap(ArrayList<Button> allBtns) {
        BootStrap bootStrap = new BootStrap();
        bootStrap.bootStrapAllSection(AllSubThemesSport.this, allBtns);
    }

    public void backToPrevActivity(View view) {
        Intent intent = new Intent(AllSubThemesSport.this, AllSectionTheme.class);
        startActivity(intent);
        this.finish();
    }
    @Override
    public void onBackPressed(){
        Intent intent = new Intent(this, AllSectionTheme.class);
        startActivity(intent);
        this.finish();
    }
}
