package com.goalreminderbeta.sa.goalreminderbeta.all;


import android.app.DatePickerDialog;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

import com.goalreminderbeta.sa.goalreminderbeta.R;

import java.util.Calendar;
import java.util.Date;

import static com.orm.SugarRecord.findById;

public class SportThemeActivity extends AppCompatActivity {

    private DatePickerDialog.OnDateSetListener mDateSetListener;
    private Button sportMinusWeightCurrent, sportPlusWeightCurrent,
            sportCurrentWeight, sportMinusWeightGoal, sportPlusWeightGoal, sportGoalWeight,
            sportSaveGoal;
    private TextView sportDateFrom, sportDateTo;
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
        sportSaveGoal = (Button) findViewById(R.id.sportSaveGoal);
        sportDateFrom = (TextView) findViewById(R.id.sportDateFrom);
        sportDateTo = (TextView) findViewById(R.id.sportDateTo);
    }
    private void setListenersOnButtons(){
        setTimerOnButton(sportMinusWeightCurrent, "-", true); // true это текуший вес
        setTimerOnButton(sportPlusWeightCurrent, "+", true);
        setTimerOnButton(sportMinusWeightGoal, "-", false); // false это конечный вес
        setTimerOnButton(sportPlusWeightGoal, "+", false);
    }

    private CountDownTimer getTimer(final String direction, final boolean current){

        CountDownTimer timer = new CountDownTimer(30000, 100) { // скорость и интервал добавления веса
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
    private void setTimerOnButton(Button button, final String direction, final boolean current){
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

    private void pickDate(final TextView view){
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog dialog = new DatePickerDialog(SportThemeActivity.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                view.setText(day+"/"+month+"/"+year); //todo save date to db in Date format
            }
        }, year, month, day);
        dialog.show();
    }

    public void pickDateFrom(View view) {
        pickDate(sportDateFrom);
    }

    public void pickDateTo(View view) {
        pickDate(sportDateTo);
    }
}
