package com.goalreminderbeta.sa.goalreminderbeta.all;


 import android.app.DatePickerDialog;
 import android.content.Intent;
 import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

import com.goalreminderbeta.sa.goalreminderbeta.R;
import com.goalreminderbeta.sa.goalreminderbeta.db.SportGoal;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static com.orm.SugarRecord.findById;

public class SportThemeActivity extends AppCompatActivity {

    private DatePickerDialog.OnDateSetListener mDateSetListener;
    private Button sportMinusWeightCurrent, sportPlusWeightCurrent,
            sportCurrentWeight, sportMinusWeightGoal, sportPlusWeightGoal, sportGoalWeight,
            sportSaveGoal;
    private TextView sportDateFrom, sportDateTo;
    private int currentWeight = 0, goalWeight = 0;
    private Date dateFrom, dateTo;

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


    private void pickDate(final TextView view,final boolean from){
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        final Date[] finalDate = {new Date()};

        DatePickerDialog dialog = new DatePickerDialog(SportThemeActivity.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
                String stringDate = day+"/"+month+"/"+year;
                Date date = null;
                try {
                    date = formatter.parse(stringDate);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                view.setText(formatter.format(date));
                finalDate[0] = date;
                if (from){
                    dateFrom = finalDate[0];
                }
                else {
                    dateTo = finalDate[0];
                }
            }
        }, year, month, day);
        dialog.show();
    }

    public void pickDateFrom(View view) {
        pickDate(sportDateFrom, true);
    }

    public void pickDateTo(View view) {
        pickDate(sportDateTo, false);
    }

    public void saveGoal(View view) {

        int currentWeight = this.currentWeight;
        int goalWeight = this.goalWeight;
        Date dateFrom = this.dateFrom;
        Date dateTo = this.dateTo;
        SportGoal goal = new SportGoal(currentWeight, goalWeight, dateFrom, dateTo);
        goal.save();

        //List<SportGoal> goals = SportGoal.listAll(SportGoal.class);

        //SportGoal goalFromDb = goals.get(goals.size() -1);
        //sportSaveGoal.setText(goalFromDb.toString());

        Intent intent = new Intent(SportThemeActivity.this, StartActivity.class);
        startActivity(intent);
        this.finish();
    }
}
