package com.goalreminderbeta.sa.goalreminderbeta.all.science;

import android.app.NotificationManager;
import android.content.Context;
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
import com.goalreminderbeta.sa.goalreminderbeta.all.StartActivity;
import com.goalreminderbeta.sa.goalreminderbeta.all.science.languages.LanguageLearningActivity;

import java.util.ArrayList;

public class AllSubThemesScience extends AppCompatActivity {

    private LinearLayout subWeight;
    private NotificationManager notificationManager;
    private Typeface faceBold = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.all_subthemes_science);

        notificationManager = (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
        subWeight = (LinearLayout) findViewById(R.id.subWeight);
        findImagesBanners();
    }

    public void createBookCorrectionGoal(View view) {
        Intent intent = new Intent(AllSubThemesScience.this, BookCorrectionActivity.class);
        startActivity(intent);
        this.finish();
    }

    public void createLanguageCorrectionGoal(View view) {
        Intent intent = new Intent(AllSubThemesScience.this, LanguageLearningActivity.class);
        startActivity(intent);
        this.finish();
    }

    public void findImagesBanners() {
        ArrayList<Button> allBtns = new ArrayList<>();
        faceBold = Typeface.createFromAsset(getAssets(), "fonts/start_font.otf");
        Button imThemeBook = (Button) findViewById(R.id.imThemeBook);
        imThemeBook.setTypeface(faceBold);
        Button imThemeLanguage = (Button) findViewById(R.id.imThemeLanguage);
        imThemeLanguage.setTypeface(faceBold);
        allBtns.add(imThemeBook);
        allBtns.add(imThemeLanguage);
        startBootStrap(allBtns);
    }

    public void startBootStrap(ArrayList<Button> allImages) {
        BootStrap bootStrap = new BootStrap();
        bootStrap.bootStrapAllSection(AllSubThemesScience.this, allImages);
    }

    public void backToPrevActivity(View view) {
        Intent intent = new Intent(AllSubThemesScience.this, AllSectionTheme.class);
        startActivity(intent);
        this.finish();
    }
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, AllSectionTheme.class);
        startActivity(intent);
        this.finish();
    }
}
