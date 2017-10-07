package com.goalreminderbeta.sa.goalreminderbeta.all.sport;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.goalreminderbeta.sa.goalreminderbeta.R;
import com.goalreminderbeta.sa.goalreminderbeta.additional.BootStrap;
import com.goalreminderbeta.sa.goalreminderbeta.additional.CustomDatePicker;
import com.goalreminderbeta.sa.goalreminderbeta.all.StartActivity;
import com.goalreminderbeta.sa.goalreminderbeta.goals.RepeatsCorrectionGoal;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class RepeatsCorrectionActivity extends AppCompatActivity {

    private Button currentRepeatsResult, goalRepeatsResult;
    private Button sportMinusRepeatCurrent, sportPlusRepeatCurrent, sportMinusRepeatGoal, sportPlusRepeatGoal;
    private Button addX10CurrentRepeat, addX10GoalRepeat;
    private Date dateFrom, dateTo;
    private TextView sportDateFrom, sportDateTo;
    private Dialog dialog;
    private String goalDescription, goalName;
    private double repeatsCurrent, repeatsGoal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.repeats_theme);

        findAllButtons();
        addToList(); // using for BootStrap!
        setListenersOnButtons();
    }

    private void findAllButtons() {
        sportMinusRepeatCurrent = (Button) findViewById(R.id.sportMinusRepeatCurrent);
        sportPlusRepeatCurrent = (Button) findViewById(R.id.sportPlusRepeatCurrent);
        sportMinusRepeatGoal = (Button) findViewById(R.id.sportMinusRepeatGoal);
        sportPlusRepeatGoal = (Button) findViewById(R.id.sportPlusRepeatGoal);
        addX10CurrentRepeat = (Button) findViewById(R.id.addX10CurrentRepeat);
        addX10GoalRepeat = (Button) findViewById(R.id.addX10GoalRepeat);
        currentRepeatsResult = (Button) findViewById(R.id.currentRepeatsResult);
        goalRepeatsResult = (Button) findViewById(R.id.goalRepeatsResult);
        sportDateFrom = (TextView) findViewById(R.id.sportDateFrom);
        sportDateTo = (TextView) findViewById(R.id.sportDateTo);
    }

    public void editDescription(View view) {
        dialog = new Dialog(RepeatsCorrectionActivity.this);
        dialog.setContentView(R.layout.description_goal);
        dialog.show();
    }

    public void saveDescription(View view) {
        EditText descriptionGoal = (EditText) dialog.findViewById(R.id.descriptionGoal);
        EditText nameGoal = (EditText) dialog.findViewById(R.id.nameGoal);
        goalDescription = descriptionGoal.getText().toString();
        goalName = nameGoal.getText().toString();
        ImageView imgReadyDescription = (ImageView) findViewById(R.id.imgReadyDescription);
        imgReadyDescription.setBackground(getResources().getDrawable(R.drawable.ready));
        dialog.dismiss();
    }

    public void pickDateFrom(View view) throws ParseException {
        CustomDatePicker.pickDate(RepeatsCorrectionActivity.this, sportDateFrom);
    }
    public void pickDateTo(View view) {
        CustomDatePicker.pickDate(RepeatsCorrectionActivity.this, sportDateTo);
    }

    private void setListenersOnButtons(){
        setTimerOnButton(sportMinusRepeatCurrent, "-", true); // true это текущее кол-во повторений
        setTimerOnButton(sportPlusRepeatCurrent, "+", true);
        setTimerOnButton(addX10CurrentRepeat, "x10", true);
        setTimerOnButton(sportMinusRepeatGoal, "-", false); // false это конечное кол-во повторений
        setTimerOnButton(sportPlusRepeatGoal, "+", false);
        setTimerOnButton(addX10GoalRepeat, "x10", false);
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

    private CountDownTimer getTimer(final String direction, final boolean current){

        CountDownTimer timer = new CountDownTimer(30000, 100) { // скорость и интервал добавления веса
            @Override
            public void onTick(long l) {
                if (direction.equals("+") || direction.equals("x10")){
                    if(current) {
                        if(direction.equals("x10")) {
                            repeatsCurrent+=10;
                        } else repeatsCurrent++;
                    } else if(direction.equals("x10")){
                        repeatsGoal+=10;
                    } else repeatsGoal++;
                }
                if (direction.equals("-")){
                    if (current){
                        if (repeatsCurrent > 0)
                            repeatsCurrent--;
                    }
                    else {
                        if (repeatsGoal > 0)
                            repeatsGoal--;
                    }
                }
                if (current){
                    currentRepeatsResult.setText("" + repeatsCurrent);
                }else {
                    goalRepeatsResult.setText("" + repeatsGoal);
                }
            }
            @Override
            public void onFinish() {
            }
        };
        return timer;
    }

    public void saveGoal(View view) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        dateFrom = formatter.parse(String.valueOf(sportDateFrom.getText()));
        dateTo = formatter.parse(String.valueOf(sportDateTo.getText()));

        Date dateFrom = this.dateFrom;
        Date dateTo = this.dateTo;
        RepeatsCorrectionGoal runCorrectionGoal = new RepeatsCorrectionGoal(repeatsCurrent, repeatsGoal, dateFrom, dateTo, goalName, goalDescription);
        runCorrectionGoal.save();
        Intent intent = new Intent(RepeatsCorrectionActivity.this, StartActivity.class);
        startActivity(intent);
        this.finish();
    }

    public void backToHome(View view) {
        Intent intent = new Intent(RepeatsCorrectionActivity.this, StartActivity.class);
        startActivity(intent);
        this.finish();
    }

    public void backToPrevActivity(View view) {
        Intent intent = new Intent(RepeatsCorrectionActivity.this, AllSubThemesSport.class);
        startActivity(intent);
        this.finish();
    }

    private void addToList() {
        ArrayList<Button> allBtnsRun = new ArrayList<>();
        allBtnsRun.add(currentRepeatsResult);
        allBtnsRun.add(goalRepeatsResult);
        startBootStrap(allBtnsRun);
    }

    private void startBootStrap(ArrayList<Button> allBtnsRun) {
        BootStrap bootStrap = new BootStrap();
        bootStrap.bootStrapResultsBtns(RepeatsCorrectionActivity.this, allBtnsRun);
    }

    public void showWarning(View view) {
        final Dialog dialog;
        dialog = new Dialog(RepeatsCorrectionActivity.this);
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
