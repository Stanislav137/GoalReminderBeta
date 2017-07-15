package com.goalreminderbeta.sa.goalreminderbeta;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        animAddGoal();

    }

    public void animAddGoal() {
        Button spaceshipImage = (Button) findViewById(R.id.myGoal);
        Animation hyperspaceJumpAnimation = AnimationUtils.loadAnimation(this, R.anim.btn_green);
        spaceshipImage.startAnimation(hyperspaceJumpAnimation);
    }

}
