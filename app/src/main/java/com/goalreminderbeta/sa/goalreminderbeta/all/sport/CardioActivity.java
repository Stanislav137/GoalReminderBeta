package com.goalreminderbeta.sa.goalreminderbeta.all.sport;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.goalreminderbeta.sa.goalreminderbeta.R;
import com.goalreminderbeta.sa.goalreminderbeta.additional.BootStrap;
import com.goalreminderbeta.sa.goalreminderbeta.additional.CustomDatePicker;
import com.goalreminderbeta.sa.goalreminderbeta.additional.DialogBuilder;
import com.goalreminderbeta.sa.goalreminderbeta.additional.DialogFactory;
import com.goalreminderbeta.sa.goalreminderbeta.all.AllSectionTheme;
import com.goalreminderbeta.sa.goalreminderbeta.all.StartActivity;
import com.goalreminderbeta.sa.goalreminderbeta.goals.CardioGoal;
import com.goalreminderbeta.sa.goalreminderbeta.goals.ElementCorrectionGoal;
import com.goalreminderbeta.sa.goalreminderbeta.goals.Goal;
import com.goalreminderbeta.sa.goalreminderbeta.goals.LanguageLearningGoal;
import com.goalreminderbeta.sa.goalreminderbeta.goals.ReadBookGoal;
import com.goalreminderbeta.sa.goalreminderbeta.goals.RepeatsCorrectionGoal;
import com.goalreminderbeta.sa.goalreminderbeta.goals.WeightCorrectionGoal;

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
    private boolean verifyNextTime = false;
    private Date dateFrom, dateTo;
    private TextView sportDateFrom, sportDateTo, changeTxtTime;
    private Dialog dialog;
    private String goalDescription, goalName;
    private int distance, currentRunTime = 0, goalRunTime = 0;
    private boolean verifyMode[] = {false};
    public CardioDialogBuilder cdb;

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
                        verifyNextTime = true;
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
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.description_goal);
        EditText descriptionGoal = (EditText) dialog.findViewById(R.id.descriptionGoal);
        EditText nameGoal = (EditText) dialog.findViewById(R.id.nameGoal);
        nameGoal.setText(goalName);
        descriptionGoal.setText(goalDescription);
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
        if (goalName != null  && currentRunTime != 0 && goalRunTime != 0 && !dateTo.equals(dateFrom) && dateFrom.getTime() < dateTo.getTime() && currentRunTime >= goalRunTime) {
            Date dateFrom = this.dateFrom;
            Date dateTo = this.dateTo;
            CardioGoal runCorrectionGoal = new CardioGoal(distance, currentRunTime, goalRunTime, dateFrom, dateTo, goalName, goalDescription);
            if(cdb==null) cdb = new CardioDialogBuilder();
            dialog = cdb.createDialog(CardioActivity.this,runCorrectionGoal);
            dialog.show();
            //runCorrectionGoal.save();
            /*Intent intent = new Intent(CardioActivity.this, StartActivity.class);
            startActivity(intent);
            this.finish();*/
        } else {
            Toast toast;
            if (dateTo.equals(dateFrom)) {
                toast = Toast.makeText(getApplicationContext(), "ВАША ДАТА ЦЕЛИ СОВПАДАЕТ С СЕГОДНЯШНЕЙ ДАТОЙ", Toast.LENGTH_SHORT);
            } else if (goalName == null) {
                toast = Toast.makeText(getApplicationContext(), "ВВЕДИТЕ ОПИСАНИЕ ЦЕЛИ", Toast.LENGTH_SHORT);
            } else {
                toast = Toast.makeText(getApplicationContext(), "ПОЖАЛУЙСТА, ПРОВЕРЬТЕ ДАННЫЕ", Toast.LENGTH_SHORT);
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
        if(verifyNextTime) {
            if (changeTxtTime.getText().equals("ТЕКУЩЕЕ ВРЕМЯ:")) {
                setTimerOnButton(sportMinusTime, "-", "false", 1);
                setTimerOnButton(sportAddTime, "+", "false", 1);
                changeTxtTime.setText("ЖЕЛАЕМАЯ ЦЕЛЬ:");
                runTimeResult.setText("" + goalRunTime);
            } else {
                setTimerOnButton(sportMinusTime, "-", "true", 1);
                setTimerOnButton(sportAddTime, "+", "true", 1);
                changeTxtTime.setText("ТЕКУЩЕЕ ВРЕМЯ:");
                runTimeResult.setText("" + currentRunTime);
            }
        } else return;
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
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
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
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.choose_value);
        Button apply = (Button) dialog.findViewById(R.id.apply);
        final EditText value = (EditText) dialog.findViewById(R.id.value);
        if(!verifyMode[0]){
            value.setText(currentRunTime + "");
            verifyMode[0] = true;
        } else {
            value.setText(goalRunTime + "");
            verifyMode[0] = false;
        }
        apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(verifyMode[0]){
                    currentRunTime = Integer.parseInt(value.getText().toString());
                    runTimeResult.setText(currentRunTime + "");
                    verifyNextTime = true;
                } else {
                    goalRunTime = Integer.parseInt(value.getText().toString());
                    runTimeResult.setText(goalRunTime + "");
                }
                dialog.dismiss();
            }
        });

        dialog.show();

    }
    @Override
    public void onBackPressed(){
        Intent intent = new Intent(this, AllSubThemesSport.class);
        startActivity(intent);
        this.finish();
    }

    private static class CardioDialogBuilder extends DialogBuilder {
        private static EditText distanceET, currentRunTimeET, goalRunTimeET;
        private static TextView distanceTV, currentRunTimeTV, goalRunTimeTV;

        private static Dialog cardioDialog;

        public CardioDialogBuilder() {

        }
        @Override
        public Dialog createDialog(final Activity activity, Goal goal){
            cardioDialog = super.createDialog(activity,goal);
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            String date_from = sdf.format(dialogBuilderGoal.getFromDate());
            dateFrom.setText(date_from);
            String date_to = sdf.format(dialogBuilderGoal.getToDate());
            dateTo.setText(date_to);

            distanceTV = new TextView(cardioDialog.getContext());
            distanceTV.setText("Your distance is:");
            dialogLV.addView(distanceTV,lp);
            distanceET = new EditText(cardioDialog.getContext());
            distanceET.setText(String.valueOf(dialogBuilderGoal.getDistance()));
            dialogLV.addView(distanceET,lp);

            currentRunTimeTV = new TextView(cardioDialog.getContext());
            currentRunTimeTV.setText("Your time now is:");
            dialogLV.addView(currentRunTimeTV,lp);
            currentRunTimeET = new EditText(cardioDialog.getContext());
            currentRunTimeET.setText(String.valueOf(dialogBuilderGoal.getCurrentResult()));
            dialogLV.addView(currentRunTimeET,lp);

            goalRunTimeTV = new TextView(cardioDialog.getContext());
            goalRunTimeTV.setText("Goal time is:");
            dialogLV.addView(goalRunTimeTV,lp);
            goalRunTimeET = new EditText(cardioDialog.getContext());
            goalRunTimeET.setText(String.valueOf(dialogBuilderGoal.getGoalResult()));
            dialogLV.addView(goalRunTimeET,lp);
            confirm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Class c = dialogBuilderGoal.getClass();
                    if(c == CardioGoal.class){
                        ((CardioGoal)dialogBuilderGoal).save();
                    }else if(c== ElementCorrectionGoal.class){
                        ((ElementCorrectionGoal)dialogBuilderGoal).save();
                    }else if(c== RepeatsCorrectionGoal.class){
                        ((RepeatsCorrectionGoal)dialogBuilderGoal).save();
                    }else if(c== WeightCorrectionGoal.class){
                        ((WeightCorrectionGoal)dialogBuilderGoal).save();
                    }else if(c== LanguageLearningGoal.class){
                        ((LanguageLearningGoal)dialogBuilderGoal).save();
                    }else if(c== ReadBookGoal.class){
                        ((ReadBookGoal)dialogBuilderGoal).save();
                    }
                    encourage();
                    Intent intent = new Intent(activity, StartActivity.class);
            activity.startActivity(intent);
            activity.finish();
                }
            });
            cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.hide();
                }
            });

            return cardioDialog;

        }

    }
}
