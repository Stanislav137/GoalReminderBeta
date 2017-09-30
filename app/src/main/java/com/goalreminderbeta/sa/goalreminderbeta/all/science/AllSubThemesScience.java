package com.goalreminderbeta.sa.goalreminderbeta.all.science;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import com.goalreminderbeta.sa.goalreminderbeta.R;
import com.goalreminderbeta.sa.goalreminderbeta.additional.BootStrap;
import java.util.ArrayList;

public class AllSubThemesScience extends AppCompatActivity {

    private LinearLayout subWeight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.all_subthemes_science);

        subWeight = (LinearLayout) findViewById(R.id.subWeight);
        findImagesBanners();
    }

    public void createBookCorrectionGoal(View view) {
        Intent intent = new Intent(AllSubThemesScience.this, BookCorrectionActivity.class);
        startActivity(intent);
        this.finish();
    }

    public void createLanguageCorrectionGoal(View view) {
        Intent intent = new Intent(AllSubThemesScience.this, LanguageCorrectionActivity.class);
        startActivity(intent);
        this.finish();
    }

    public void findImagesBanners() {
        ArrayList<ImageView> allImages = new ArrayList<>();
        ImageView imThemeBook = (ImageView) findViewById(R.id.imThemeBook);
        ImageView imThemeLanguage = (ImageView) findViewById(R.id.imThemeLanguage);
        allImages.add(imThemeBook);
        allImages.add(imThemeLanguage);
        startBootStrap(allImages);
    }

    public void startBootStrap(ArrayList<ImageView> allImages) {
        BootStrap bootStrap = new BootStrap();
        bootStrap.bootStrapAllSection(AllSubThemesScience.this, allImages);
    }
}
