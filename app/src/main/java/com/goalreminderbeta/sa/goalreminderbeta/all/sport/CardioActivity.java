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
import android.widget.Toast;

import com.goalreminderbeta.sa.goalreminderbeta.R;
import com.goalreminderbeta.sa.goalreminderbeta.additional.BootStrap;
import com.goalreminderbeta.sa.goalreminderbeta.additional.CustomDatePicker;
import com.goalreminderbeta.sa.goalreminderbeta.all.StartActivity;
import com.goalreminderbeta.sa.goalreminderbeta.goals.CardioGoal;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class CardioActivity extends AppCompatActivity {

    private Button sportMinusDistance;
    private Button sportAddDistance;
    private Button sportAddDistanceX100;
    private Button runDistanceResult, runTimeResult;
    private Button sportMinusTime, sportAddTime, nextTime;
    private Date dateFrom, dateTo;
    private TextView sportDateFrom, sportDateTo, changeTxtTime;
    private Dialog dialog;
    private String goalDescription, goalName;
    private int distance, currentRunTime, goalRunTime;
    private boolean currentOrGoalTime = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cardio_theme);

        findAllWidgets();
        addToList(); // using for BootStrap!
        setListenersOnButtons();
        initializeUX();
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
        sportDateTo = (TextView) findViewById(R.id.repeatsDateTo);
        changeTxtTime = (TextView) findViewById(R.id.changeTxtTime);
    }

    private void setListenersOnButtons(){
        /* Added data for Distance */
        setTimerOnButton(sportMinusDistance, "-", "none", 1); // если будет дистанция
        setTimerOnButton(sportAddDistance, "+", "none", 1);
        setTimerOnButton(sportAddDistanceX100, "x100", "none", 1);
        /* Added data for Time */
        setTimerOnButton(sportMinusTime, "-", "true", 1); // если будет время
        setTimerOnButton(sportAddTime, "+", "true", 1);
        nextTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchWeight();
            }
        });
    }

    private void setTimerOnButton(Button button, final String direction, final String current, final double increasing){
        button.setOnTouchListener(new View.OnTouchListener() {
            CountDownTimer timer = getTimer(direction, current, increasing);
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

    private CountDownTimer getTimer(final String direction, final String current, final double increasing){

        CountDownTimer timer = new CountDownTimer(30000, 100) { // скорость и интервал добавления страниц
            @Override
            public void onTick(long l) {
                if (current.equals("none")) {
                    if (direction.equals("+") || direction.equals("x100")) {
                        if (direction.equals("x100")) {
                            distance += 100;
                        } else distance += increasing;
                    }
                    if (direction.equals("-")) {
                        if (distance > 0)
                            distance -= increasing;
                    }
                }
                if (current.equals("true")) {
                    if (direction.equals("+")) {
                        currentRunTime += increasing;
                    } else if (currentRunTime > 0) {
                        currentRunTime -= increasing;
                    }
                } else if (current.equals("false")) {
                    if (direction.equals("+")) {
                        goalRunTime += increasing;
                    } else if (goalRunTime > 0) {
                        goalRunTime -= increasing;
                    }
                }
                if (current.equals("none")) {
                    runDistanceResult.setText("" + distance);
                } else if (current.equals("true")) {
                    runTimeResult.setText("" + currentRunTime);
                } else if (current.equals("false")) {
                    runTimeResult.setText("" + goalRunTime);
                }
            }
            @Override
            public void onFinish() {
            }
        };
        return timer;
    }

    public void pickDateFrom(View view) throws ParseException {
        CustomDatePicker.pickDate(CardioActivity.this, sportDateFrom);
    }
    public void pickDateTo(View view) {
        CustomDatePicker.pickDate(CardioActivity.this, sportDateTo);
    }

    public void backToHome(View view) {
        Intent intent = new Intent(CardioActivity.this, StartActivity.class);
        startActivity(intent);
        this.finish();
    }

    public void backToPrevActivity(View view) {
        Intent intent = new Intent(CardioActivity.this, AllSubThemesSport.class);
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
        bootStrap.bootStrapResultsBtns(CardioActivity.this, allBtnsRun);
    }

    public void editDescription(View view) {
        dialog = new Dialog(CardioActivity.this);
        dialog.setContentView(R.layout.description_goal);
        dialog.show();
    }

    public void saveDescription(View view) {
        EditText descriptionGoal = (EditText) dialog.findViewById(R.id.descriptionGoal);
        EditText nameGoal = (EditText) dialog.findViewById(R.id.nameGoal);
        goalDescription = descriptionGoal.getText().toString();
        goalName = nameGoal.getText().toString();
        if (!goalName.equals("") || !goalDescription.equals("")) {
            ImageView imgReadyDescription = (ImageView) findViewById(R.id.imgReadyDescription);
            imgReadyDescription.setBackground(getResources().getDrawable(R.drawable.ready));
            dialog.dismiss();
        } else {
            goalName = null;
            dialog.dismiss();
        }
    }

    public void saveGoal(View view) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        dateFrom = formatter.parse(String.valueOf(sportDateFrom.getText()));
        dateTo = formatter.parse(String.valueOf(sportDateTo.getText()));
        if (goalName != null  && currentRunTime != 0 && goalRunTime != 0 && !dateTo.equals(dateFrom)) {
            Date dateFrom = this.dateFrom;
            Date dateTo = this.dateTo;
            CardioGoal runCorrectionGoal = new CardioGoal(distance, currentRunTime, goalRunTime, dateFrom, dateTo, goalName, goalDescription);
            runCorrectionGoal.save();
            Intent intent = new Intent(CardioActivity.this, StartActivity.class);
            startActivity(intent);
            this.finish();
        } else {
            Toast toast;
            if (dateTo.equals(dateFrom)) {
                toast = Toast.makeText(getApplicationContext(), "ВАША ДАТА ЦЕЛИ СОВПАДАЕТ С СЕГОДНЯШНЕЙ ДАТОЙ", Toast.LENGTH_SHORT);
            } else if (goalName == null) {
                toast = Toast.makeText(getApplicationContext(), "ВВЕДИТЕ ОПИСАНИЕ ЦЕЛИ", Toast.LENGTH_SHORT);
            } else {
                toast = Toast.makeText(getApplicationContext(), "ПОЖАЛУЙСТА, ЗАПОЛНИТЕ ВСЕ ДАННЫЕ", Toast.LENGTH_SHORT);
            }
            toast.show();
        }
    }

    public void showWarning(View view) {
        final Dialog dialog;
        dialog = new Dialog(CardioActivity.this);
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

    private void switchWeight(){
        sportAddTime.setOnClickListener(null);
        sportMinusTime.setOnClickListener(null);
        if (changeTxtTime.getText().equals("ТЕКУЩЕЕ ВРЕМЯ:")){
            setTimerOnButton(sportMinusTime, "-", "false", 1);
            setTimerOnButton(sportAddTime, "+", "false", 1);
            changeTxtTime.setText("ЖЕЛАЕМАЯ ЦЕЛЬ:");
            runTimeResult.setText("" + goalRunTime);
        }else {
            setTimerOnButton(sportMinusTime, "-", "true", 1);
            setTimerOnButton(sportAddTime, "+", "true", 1);
            changeTxtTime.setText("ТЕКУЩЕЕ ВРЕМЯ:");
            runTimeResult.setText("" + currentRunTime);
        }
    }
    private void initializeUX(){
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        Date today = Calendar.getInstance().getTime();
        dateFrom = today;
        dateTo = today;
        String reportDate = df.format(today);
        sportDateFrom.setText(reportDate);
        sportDateTo.setText(reportDate);
    }

    public void setDistance(View view) {
        final Dialog dialog;
        dialog = new Dialog(CardioActivity.this);
        dialog.setContentView(R.layout.choose_value);
        Button apply = (Button) dialog.findViewById(R.id.apply);
        final EditText value = (EditText) dialog.findViewById(R.id.value);

        apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                runDistanceResult.setText(value.getText());
                distance = Integer.parseInt(value.getText().toString());
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    public void setTime(View view) {
        final Dialog dialog;
        dialog = new Dialog(CardioActivity.this);
        dialog.setContentView(R.layout.choose_value);
        Button apply = (Button) dialog.findViewById(R.id.apply);
        final EditText value = (EditText) dialog.findViewById(R.id.value);

        apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                runTimeResult.setText(value.getText());
                currentRunTime = Integer.parseInt(value.getText().toString());
                dialog.dismiss();
            }
        });

        dialog.show();
    }
}
