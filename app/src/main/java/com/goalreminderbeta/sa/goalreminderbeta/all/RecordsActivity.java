package com.goalreminderbeta.sa.goalreminderbeta.all;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.goalreminderbeta.sa.goalreminderbeta.R;
import com.goalreminderbeta.sa.goalreminderbeta.additional.BootStrap;

import java.util.ArrayList;

public class RecordsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_records);

        findImagesBanners();
    }

    public void openGoals(View view) {
        Intent intent = new Intent(RecordsActivity.this, StartActivity.class);
        startActivity(intent);
        this.finish();
    }

    public void openOptions(View view) {
        Intent intent = new Intent(RecordsActivity.this, OptionsActivity.class);
        startActivity(intent);
        this.finish();
    }

    public void findImagesBanners() {
        ArrayList<ImageView> allImages = new ArrayList<>();
        ImageView imgSport = (ImageView) findViewById(R.id.imgChampSport);
        ImageView imgChampScience = (ImageView) findViewById(R.id.imgChampScience);
        ImageView imgChampOthers = (ImageView) findViewById(R.id.imgChampOthers);
        allImages.add(imgSport);
        allImages.add(imgChampScience);
        allImages.add(imgChampOthers);
        startBootStrap(allImages);
    }

    public void startBootStrap(ArrayList<ImageView> allImages) {
        BootStrap bootStrap = new BootStrap();
        bootStrap.bootStrapAllSection(RecordsActivity.this, allImages);
    }
}
