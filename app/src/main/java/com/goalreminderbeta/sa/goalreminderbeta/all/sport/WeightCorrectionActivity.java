package com.goalreminderbeta.sa.goalreminderbeta.all.sport;


 import android.app.DatePickerDialog;
 import android.app.Dialog;
 import android.content.Intent;
 import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
 import android.widget.EditText;
 import android.widget.ImageView;
 import android.widget.TextView;

import com.goalreminderbeta.sa.goalreminderbeta.R;
 import com.goalreminderbeta.sa.goalreminderbeta.additional.CustomDatePicker;
 import com.goalreminderbeta.sa.goalreminderbeta.all.StartActivity;
 import com.goalreminderbeta.sa.goalreminderbeta.all.science.ReadBookActivity;
 import com.goalreminderbeta.sa.goalreminderbeta.goals.WeightCorrectionGoal;

 import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class WeightCorrectionActivity extends AppCompatActivity {

    private Button sportMinusWeightCurrent, sportPlusWeightCurrent,
            sportCurrentWeight, sportMinusWeightGoal, sportPlusWeightGoal, sportGoalWeight,
            sportSaveGoal;
    private TextView sportDateFrom, sportDateTo;
    private int currentWeight = 0, goalWeight = 0;
    private Date dateFrom, dateTo;
    private Dialog dialog;
    private String goalDescription;

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
    public void pickDateFrom(View view) throws ParseException {
        CustomDatePicker.pickDate(WeightCorrectionActivity.this, sportDateFrom);
    }
    public void pickDateTo(View view) {
        CustomDatePicker.pickDate(WeightCorrectionActivity.this, sportDateTo);
    }

    public void editDescription(View view) {
        dialog = new Dialog(WeightCorrectionActivity.this);
        dialog.setContentView(R.layout.description_goal);
        dialog.show();
    }

    public void saveDescription(View view) {
        EditText descriptionGoal = (EditText) dialog.findViewById(R.id.descriptionGoal);
        goalDescription = descriptionGoal.getText().toString();
        ImageView imgReadyDescription = (ImageView) findViewById(R.id.imgReadyDescription);
        imgReadyDescription.setBackground(getResources().getDrawable(R.drawable.ready));
        dialog.dismiss();
    }

    public void saveGoal(View view) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        dateFrom = formatter.parse(String.valueOf(sportDateFrom.getText()));
        dateTo = formatter.parse(String.valueOf(sportDateTo.getText()));

        int currentWeight = this.currentWeight; // $HELP зачем так делать?
        int goalWeight = this.goalWeight;
        Date dateFrom = this.dateFrom;
        Date dateTo = this.dateTo;
        WeightCorrectionGoal goal = new WeightCorrectionGoal(currentWeight, goalWeight, dateFrom, dateTo);
        goal.save();

        Intent intent = new Intent(WeightCorrectionActivity.this, StartActivity.class);
        startActivity(intent);
        this.finish();
    }

    public void showWarning(View view) {
        final Dialog dialog;
        dialog = new Dialog(WeightCorrectionActivity.this);
        dialog.setContentView(R.layout.warning);

        Button closeWarning = (Button) dialog.findViewById(R.id.closeWarning);
        closeWarning.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImageView showWarningId = (ImageView) findViewById(R.id.showWarningId);
                showWarningId.setVisibility(View.INVISIBLE);
                dialog.dismiss();
            }
        });
        dialog.show();
    }
}
