package com.goalreminderbeta.sa.goalreminderbeta.all.sport;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.goalreminderbeta.sa.goalreminderbeta.R;

public class AllSubThemesSport extends AppCompatActivity {

    private LinearLayout weightCorrectionGoal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.all_subthemes_sport);

        weightCorrectionGoal = (LinearLayout) findViewById(R.id.weightCorrectionGoal);
    }

    public void createWeightCorrectionGoal(View view) {
        Intent intent = new Intent(AllSubThemesSport.this, WeightCorrectionActivity.class);
        startActivity(intent);
        this.finish();
    }
}