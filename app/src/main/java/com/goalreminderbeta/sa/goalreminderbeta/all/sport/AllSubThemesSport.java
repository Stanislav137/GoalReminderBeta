package com.goalreminderbeta.sa.goalreminderbeta.all.sport;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import com.goalreminderbeta.sa.goalreminderbeta.R;
import com.goalreminderbeta.sa.goalreminderbeta.additional.BootStrap;

import java.util.ArrayList;

public class AllSubThemesSport extends AppCompatActivity {

    private LinearLayout weightCorrectionGoal;

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
        Intent intent = new Intent(AllSubThemesSport.this, RunCorrectionActivity.class);
        startActivity(intent);
        this.finish();
    }

    public void createElementCorrectionGoal(View view) {
        Intent intent = new Intent(AllSubThemesSport.this, ElementCorrectionActivity.class);
        startActivity(intent);
        this.finish();
    }

    public void createRepeatsCorrectionGoal(View view) {
        Intent intent = new Intent(AllSubThemesSport.this, RepeatsCorrectionActivity.class);
        startActivity(intent);
        this.finish();
    }

    public void findImagesBanners() {
        ArrayList<ImageView> allImages = new ArrayList<>();
        ImageView imgSport = (ImageView) findViewById(R.id.imThemeWeight);
        ImageView imThemeRun = (ImageView) findViewById(R.id.imThemeRun);
        ImageView imThemeElements = (ImageView) findViewById(R.id.imThemeElements);
        ImageView imThemeRepeats = (ImageView) findViewById(R.id.imThemeRepeats);
        allImages.add(imgSport);
        allImages.add(imThemeRun);
        allImages.add(imThemeElements);
        allImages.add(imThemeRepeats);
        startBootStrap(allImages);
    }

    public void startBootStrap(ArrayList<ImageView> allImages) {
        BootStrap bootStrap = new BootStrap();
        bootStrap.bootStrapAllSection(AllSubThemesSport.this, allImages);
    }
}
