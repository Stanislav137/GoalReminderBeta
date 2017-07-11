package com.goalreminderbeta.sa.goalreminderbeta;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    Button myGoal, myRecords, myProgress, myOptions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myGoal = (Button) findViewById(R.id.myGoal);        // ToDO через коллекцию вроде как не получится (если я еще не совсем даун)
        myRecords = (Button) findViewById(R.id.myRecords);
        myProgress = (Button) findViewById(R.id.myProgress);
        myOptions = (Button) findViewById(R.id.myOptions);

        Animation animCircleGreen = AnimationUtils.loadAnimation(this, R.anim.btn_green); // ToDo может как-то можно в листу общую добавить, хотя нету смысла. Или метод отдельный сделать
        Animation animCircleRed = AnimationUtils.loadAnimation(this, R.anim.btn_red);
        Animation animCircleBlue = AnimationUtils.loadAnimation(this, R.anim.btn_blue);
        Animation animCircleOrange = AnimationUtils.loadAnimation(this, R.anim.btn_orange);
        myGoal.startAnimation(animCircleGreen);
        myRecords.startAnimation(animCircleRed);
        myProgress.startAnimation(animCircleOrange);
        myOptions.startAnimation(animCircleBlue);
    }
}
