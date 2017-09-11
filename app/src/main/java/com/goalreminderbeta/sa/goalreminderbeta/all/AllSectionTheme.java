package com.goalreminderbeta.sa.goalreminderbeta.all;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.goalreminderbeta.sa.goalreminderbeta.R;
import com.goalreminderbeta.sa.goalreminderbeta.all.science.AllSubThemesScience;
import com.goalreminderbeta.sa.goalreminderbeta.all.sport.AllSubThemesSport;

public class AllSectionTheme extends AppCompatActivity {

    LinearLayout llThemeSport, llThemeScience;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.all_themes);

        llThemeSport = (LinearLayout) findViewById(R.id.llThemeSport);
        llThemeScience = (LinearLayout) findViewById(R.id.llThemeScience);
    }

    public void startSportTheme(View view) {
        Intent intent = new Intent(AllSectionTheme.this, AllSubThemesSport.class);
        startActivity(intent);
        this.finish();
    }

    public void startScienceTheme(View view) {
        Intent intent = new Intent(AllSectionTheme.this, AllSubThemesScience.class);
        startActivity(intent);
        this.finish();
    }
}
