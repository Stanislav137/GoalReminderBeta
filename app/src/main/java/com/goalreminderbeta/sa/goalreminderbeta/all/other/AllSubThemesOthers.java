package com.goalreminderbeta.sa.goalreminderbeta.all.other;

import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import com.goalreminderbeta.sa.goalreminderbeta.R;
import com.goalreminderbeta.sa.goalreminderbeta.additional.BootStrap;
import com.goalreminderbeta.sa.goalreminderbeta.all.AllSectionTheme;
import com.goalreminderbeta.sa.goalreminderbeta.all.StartActivity;
import com.goalreminderbeta.sa.goalreminderbeta.all.science.AllSubThemesScience;
import com.goalreminderbeta.sa.goalreminderbeta.goals.ElementCorrectionGoal;

import java.util.ArrayList;

public class AllSubThemesOthers extends AppCompatActivity {

    private LinearLayout subWeight;
    private NotificationManager notificationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.all_subthemes_others);

//        notificationManager = (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
//        subWeight = (LinearLayout) findViewById(R.id.subWeight); // ToDo what is it?
        findImagesBanners();
    }

    public void createSkillsGoal(View view) {
        Intent intent = new Intent(AllSubThemesOthers.this, ElementCorrectionActivity.class);
        startActivity(intent);
        this.finish();
    }

    public void createMyGoal(View view) {
        Intent intent = new Intent(AllSubThemesOthers.this, MyGoalCorrectionActivity.class);
        startActivity(intent);
        this.finish();
    }

    public void findImagesBanners() {
        ArrayList<Button> allBtns = new ArrayList<>();
        Button btnCreateSkillsGoal = (Button) findViewById(R.id.btnCreateSkillsGoal);
        Button btnCreateMyGoal = (Button) findViewById(R.id.btnCreateMyGoal);
        allBtns.add(btnCreateSkillsGoal);
        allBtns.add(btnCreateMyGoal);
        startBootStrap(allBtns);
    }

    public void startBootStrap(ArrayList<Button> allImages) {
        BootStrap bootStrap = new BootStrap();
        bootStrap.bootStrapAllSection(AllSubThemesOthers.this, allImages);
    }

    public void backToPrevActivity(View view) {
        Intent intent = new Intent(AllSubThemesOthers.this, AllSectionTheme.class);
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
