package com.goalreminderbeta.sa.goalreminderbeta.all;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import com.goalreminderbeta.sa.goalreminderbeta.R;
import com.goalreminderbeta.sa.goalreminderbeta.additional.BootStrap;
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

    public void findImagesBanners() {
        ArrayList<ImageView> allImages = new ArrayList<>();
        ImageView imgSport = (ImageView) findViewById(R.id.imThemeSport);
        ImageView imThemeScience = (ImageView) findViewById(R.id.imThemeScience);
        ImageView imThemeOthers = (ImageView) findViewById(R.id.imThemeOthers);
        allImages.add(imgSport);
        allImages.add(imThemeScience);
        allImages.add(imThemeOthers);
        startBootStrap(allImages);
    }

    public void startBootStrap(ArrayList<ImageView> allImages) {
        BootStrap bootStrap = new BootStrap();
        bootStrap.bootStrapAllSection(AllSectionTheme.this, allImages);
    }
}
