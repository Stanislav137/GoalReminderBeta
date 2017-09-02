package com.goalreminderbeta.sa.goalreminderbeta.all;


import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

import com.goalreminderbeta.sa.goalreminderbeta.R;

public class SportThemeActivity extends AppCompatActivity {

    private Button sportMinusWeightCurrent, sportPlusWeightCurrent,
            sportCurrentWeight, sportMinusWeightGoal, sportPlusWeightGoal, sportGoalWeight;
    private int currentWeight = 0, goalWeight = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sport_theme);

        findAllButtons();
        setListenersOnButtons();
    }
    private void findAllButtons(){
        sportMinusWeightCurrent = (Button) findViewById(R.id.sportMinusWeightCurrent);
        sportPlusWeightCurrent = (Button) findViewById(R.id.sportPlusWeightCurrent);
        sportCurrentWeight = (Button) findViewById(R.id.sportCurrentWeight);
        sportMinusWeightGoal = (Button) findViewById(R.id.sportMinusWeightGoal);
        sportPlusWeightGoal = (Button) findViewById(R.id.sportPlusWeightGoal);
        sportGoalWeight = (Button) findViewById(R.id.sportGoalWeight);
    }
    private void setListenersOnButtons(){
        setTimemOnButton(sportMinusWeightCurrent, "-", true);
        setTimemOnButton(sportPlusWeightCurrent, "+", true);
        setTimemOnButton(sportMinusWeightGoal, "-", false);
        setTimemOnButton(sportPlusWeightGoal, "+", false);
    }

    private CountDownTimer getTimer(final String direction, final boolean current){

        CountDownTimer timer = new CountDownTimer(30000, 100) {
            @Override
            public void onTick(long l) {
                if (direction.equals("+")){
                    if (current){
                        currentWeight++;
                    }
                    else goalWeight++;
                }
                if (direction.equals("-")){
                    if (current){
                        if (currentWeight > 0)
                        currentWeight--;
                    }
                    else {
                        if (goalWeight > 0)
                        goalWeight--;
                    }
                }
                if (current){
                    sportCurrentWeight.setText("" + currentWeight);
                }else {
                    sportGoalWeight.setText("" + goalWeight);
                }
            }
            @Override
            public void onFinish() {
            }
        };
        return timer;
    }
    private void setTimemOnButton(Button button, final String direction, final boolean current){
        button.setOnTouchListener(new View.OnTouchListener() {
            CountDownTimer timer = getTimer(direction, current);
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch(event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        timer.start();
                        return true;
                    case MotionEvent.ACTION_UP:
                        timer.cancel();
                        return true;
                }
                return false;
            }
        });
    }
}
