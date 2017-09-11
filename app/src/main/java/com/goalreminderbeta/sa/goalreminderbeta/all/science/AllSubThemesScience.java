package com.goalreminderbeta.sa.goalreminderbeta.all.science;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.goalreminderbeta.sa.goalreminderbeta.R;
import com.goalreminderbeta.sa.goalreminderbeta.all.sport.WeightCorrectionActivity;

public class AllSubThemesScience extends AppCompatActivity {

    private LinearLayout subWeight;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.all_subthemes_science);

        subWeight = (LinearLayout) findViewById(R.id.subWeight);
    }

    public void createBookCorrectionGoal(View view) {
        Intent intent = new Intent(AllSubThemesScience.this, BookCorrectionActivity.class);
        startActivity(intent);
        this.finish();
    }
}
