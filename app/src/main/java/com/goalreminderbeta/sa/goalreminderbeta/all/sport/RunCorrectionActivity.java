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
import com.goalreminderbeta.sa.goalreminderbeta.goals.RunCorrectionGoal;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class RunCorrectionActivity extends AppCompatActivity {

    private Button sportMinusDistance;
    private Button sportAddDistance;
    private Button sportAddDistanceX100;
    private Button runDistanceResult, runTimeResult;
    private Button sportMinusTime, sportAddTime, nextTime;
    private Date dateFrom, dateTo;
    private TextView sportDateFrom, sportDateTo;
    private Dialog dialog;
    private String goalDescription, goalName;
    private int distanceRunResult, currentRunTime, goalRunTime;
    private boolean currentOrGoalTime = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.run_theme);

        findAllWidgets();
        addToList(); // using for BootStrap!
        setListenersOnButtons();
    }

    private void findAllWidgets() {
        /* Buttons for Distance */
        sportMinusDistance = (Button) findViewById(R.id.sportMinusDistance);
        sportAddDistance = (Button) findViewById(R.id.sportAddDistance);
        sportAddDistanceX100 = (Button) findViewById(R.id.sportAddDistanceX100);
        runDistanceResult = (Button) findViewById(R.id.runDistanceResult);
        /* Buttons for Time */
        runTimeResult = (Button) findViewById(R.id.runTimeResult);
        sportMinusTime = (Button) findViewById(R.id.sportMinusTime);
        sportAddTime = (Button) findViewById(R.id.sportAddTime);
        nextTime = (Button) findViewById(R.id.nextTime);
        /* TextView */
        sportDateFrom = (TextView) findViewById(R.id.sportDateFrom);
        sportDateTo = (TextView) findViewById(R.id.sportDateTo);
    }

    private void setListenersOnButtons(){
        /* Added data for Distance */
        setTimeOnButton(sportMinusDistance, "-", true); // если будет дистанция
        setTimeOnButton(sportAddDistance, "+", true);
        setTimeOnButton(sportAddDistanceX100, "x100", true);
        /* Added data for Time */
        setTimeOnButton(sportMinusTime, "-", false); // если будет время
        setTimeOnButton(sportAddTime, "+", false);
        setTimeOnButton(nextTime, "ok", false);
    }

    private void setTimeOnButton(Button button, final String direction, final boolean current){
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

        CountDownTimer timer = new CountDownTimer(30000, 100) { // скорость и интервал добавления страниц
            @Override
            public void onTick(long l) {
                if (direction.equals("+") || direction.equals("x100")){
                    if(current) {
                        if(direction.equals("x100")) {
                            distanceRunResult+=100;
                        } else distanceRunResult++;
                    } else currentRunTime++;
                }
                if (direction.equals("-")){
                    if(current){
                        if (distanceRunResult > 0)
                            distanceRunResult--;
                    }
                    else {
                        if(currentRunTime > 0)
                            currentRunTime--;
                    }
                }
                if(current){
                    runDistanceResult.setText("" + distanceRunResult);
                }else  runTimeResult.setText("" + currentRunTime);
            }
            @Override
            public void onFinish() {
            }
        };
        return timer;
    }

    public void pickDateFrom(View view) throws ParseException {
        CustomDatePicker.pickDate(RunCorrectionActivity.this, sportDateFrom);
    }
    public void pickDateTo(View view) {
        CustomDatePicker.pickDate(RunCorrectionActivity.this, sportDateTo);
    }

    public void backToHome(View view) {
        Intent intent = new Intent(RunCorrectionActivity.this, StartActivity.class);
        startActivity(intent);
        this.finish();
    }

    private void addToList() {
        ArrayList<Button> allBtnsRun = new ArrayList<>();
        allBtnsRun.add(runDistanceResult);
        allBtnsRun.add(runTimeResult);
        startBootStrap(allBtnsRun);
    }

    private void startBootStrap(ArrayList<Button> allBtnsRun) {
        BootStrap bootStrap = new BootStrap();
        bootStrap.bootStrapResultsBtns(RunCorrectionActivity.this, allBtnsRun);
    }

    public void editDescription(View view) {
        dialog = new Dialog(RunCorrectionActivity.this);
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

    public void saveGoal(View view) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        dateFrom = formatter.parse(String.valueOf(sportDateFrom.getText()));
        dateTo = formatter.parse(String.valueOf(sportDateTo.getText()));

        String themeCategory = "БЕГ";
        Date dateFrom = this.dateFrom;
        Date dateTo = this.dateTo;
        RunCorrectionGoal runCorrectionGoal = new RunCorrectionGoal(dateFrom, dateTo, goalName, themeCategory);
        runCorrectionGoal.save();
        Intent intent = new Intent(RunCorrectionActivity.this, StartActivity.class);
        startActivity(intent);
        this.finish();
    }

    public void showWarning(View view) {
        final Dialog dialog;
        dialog = new Dialog(RunCorrectionActivity.this);
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
