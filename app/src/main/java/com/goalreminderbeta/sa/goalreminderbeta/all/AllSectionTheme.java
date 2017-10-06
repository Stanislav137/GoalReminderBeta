package com.goalreminderbeta.sa.goalreminderbeta.all;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import com.goalreminderbeta.sa.goalreminderbeta.R;
import com.goalreminderbeta.sa.goalreminderbeta.additional.BootStrap;
import com.goalreminderbeta.sa.goalreminderbeta.all.other.OtherCorrectionActivity;
import com.goalreminderbeta.sa.goalreminderbeta.all.science.AllSubThemesScience;
import com.goalreminderbeta.sa.goalreminderbeta.all.sport.AllSubThemesSport;
import java.util.ArrayList;

public class AllSectionTheme extends AppCompatActivity {

    private LinearLayout llThemeSport, llThemeScience;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.all_themes);

        llThemeSport = (LinearLayout) findViewById(R.id.llThemeSport);
        llThemeScience = (LinearLayout) findViewById(R.id.llThemeScience);
        findImagesBanners();
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

    public void createOtherThemeGoal(View view) {
        Intent intent = new Intent(AllSectionTheme.this, OtherCorrectionActivity.class);
        startActivity(intent);
        this.finish();
    }

    public void findImagesBanners() {
        ArrayList<Button> allBtns = new ArrayList<>();
        Button imgSport = (Button) findViewById(R.id.imThemeSport);
        Button imThemeScience = (Button) findViewById(R.id.imThemeScience);
        Button imThemeOthers = (Button) findViewById(R.id.imThemeOthers);
        allBtns.add(imgSport);
        allBtns.add(imThemeScience);
        allBtns.add(imThemeOthers);
        startBootStrap(allBtns);
    }

    public void startBootStrap(ArrayList<Button> allBtns) {
        BootStrap bootStrap = new BootStrap();
        bootStrap.bootStrapAllSection(AllSectionTheme.this, allBtns);
    }

    public void backToPrevActivity(View view) {
        Intent intent = new Intent(AllSectionTheme.this, StartActivity.class);
        startActivity(intent);
        this.finish();
    }
}
