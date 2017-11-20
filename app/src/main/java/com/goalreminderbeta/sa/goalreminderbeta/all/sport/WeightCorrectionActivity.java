package com.goalreminderbeta.sa.goalreminderbeta.all.sport;


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
 import android.widget.TextView;
 import android.widget.Toast;

 import com.goalreminderbeta.sa.goalreminderbeta.R;
 import com.goalreminderbeta.sa.goalreminderbeta.additional.BootStrap;
 import com.goalreminderbeta.sa.goalreminderbeta.additional.CustomDatePicker;
 import com.goalreminderbeta.sa.goalreminderbeta.all.StartActivity;
 import com.goalreminderbeta.sa.goalreminderbeta.goals.WeightCorrectionGoal;

 import java.text.DateFormat;
 import java.text.DecimalFormat;
 import java.text.ParseException;
import java.text.SimpleDateFormat;
 import java.util.ArrayList;
 import java.util.Calendar;
 import java.util.Date;

public class WeightCorrectionActivity extends AppCompatActivity {

    private Button sportMinusWeightCurrent, sportPlusWeightCurrent,
            sportCurrentWeight, sportMinusWeightGoal, sportPlusWeightGoal, sportGoalWeight,
            sportSaveGoal, changeCurrentWeight, changeGoalWeight;
    private TextView sportDateFrom, sportDateTo;
    private double currentWeight = 0, goalWeight = 0;
    private Date dateFrom, dateTo;
    private Dialog dialog;
    private String goalDescription, goalName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.weight_theme);

        findAllBtnsBootStrap(); // using for BootStrap!
        findAllButtons();
        setListenersOnButtons();
        initializeUX();
    }
    private void findAllButtons(){
        sportMinusWeightCurrent = (Button) findViewById(R.id.sportMinusWeightCurrent);
        sportPlusWeightCurrent = (Button) findViewById(R.id.sportPlusWeightCurrent);
        sportCurrentWeight = (Button) findViewById(R.id.sportCurrentWeight);
        sportMinusWeightGoal = (Button) findViewById(R.id.sportMinusWeightGoal);
        sportPlusWeightGoal = (Button) findViewById(R.id.sportPlusWeightGoal);
        sportGoalWeight = (Button) findViewById(R.id.sportGoalWeight);
        sportSaveGoal = (Button) findViewById(R.id.sportSaveGoal);
        changeCurrentWeight = (Button) findViewById(R.id.changeCurrentWeight);
        changeGoalWeight = (Button) findViewById(R.id.changeGoalWeight);
        sportDateFrom = (TextView) findViewById(R.id.sportDateFrom);
        sportDateTo = (TextView) findViewById(R.id.repeatsDateTo);
    }
    private void setListenersOnButtons(){
        setTimerOnButton(sportMinusWeightCurrent, "-", true, 1); // true это текуший вес
        setTimerOnButton(sportPlusWeightCurrent, "+", true, 1);
        setTimerOnButton(sportMinusWeightGoal, "-", false, 1); // false это конечный вес
        setTimerOnButton(sportPlusWeightGoal, "+", false, 1);
        changeCurrentWeight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchWeight(changeCurrentWeight);
            }
        });
        changeGoalWeight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchWeight(changeGoalWeight);
            }
        });

    }
    private CountDownTimer getTimer(final String direction, final boolean current, final double increasing){

        final DecimalFormat df = new DecimalFormat("#.##");
        CountDownTimer timer = new CountDownTimer(30000, 100) { // скорость и интервал добавления веса
            @Override
            public void onTick(long l) {
                if (direction.equals("+")){
                    if (current){
                        currentWeight+=increasing;
                    }
                    else goalWeight+=increasing;
                }
                if (direction.equals("-")){
                    if(currentWeight < 1) {
                        currentWeight = 0;
                    }
                    if(goalWeight < 1) {
                        goalWeight = 0;
                    }
                    if (current){
                        if (currentWeight > 1)
                        currentWeight-=increasing;
                    }
                    else {
                        if (goalWeight > 1)
                        goalWeight-=increasing;
                    }
                }
                if (current){
                    sportCurrentWeight.setText("" + df.format(currentWeight));
                }else {
                    sportGoalWeight.setText("" + df.format(goalWeight));
                }
            }
            @Override
            public void onFinish() {
            }
        };
        return timer;
    }
    private void setTimerOnButton(Button button, final String direction, final boolean current, final double increasing){
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
    public void pickDateTo(View view) {
        CustomDatePicker.pickDate(WeightCorrectionActivity.this, sportDateTo);
    }
    public void editDescription(View view) {
        dialog = new Dialog(WeightCorrectionActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.description_goal);
        dialog.show();
    }
    public void saveDescription(View view) {
        EditText descriptionGoal = (EditText) dialog.findViewById(R.id.descriptionGoal);
        EditText nameGoal = (EditText) dialog.findViewById(R.id.nameGoal);
        goalDescription = descriptionGoal.getText().toString();
        goalName = nameGoal.getText().toString();
        if(!goalName.equals("") || !goalDescription.equals("")) {
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


        if(goalName != null && currentWeight != 0 && goalWeight != 0 && !dateTo.equals(dateFrom)) {
            double currentWeight = this.currentWeight;
            double goalWeight = this.goalWeight;
            Date dateFrom = this.dateFrom;
            Date dateTo = this.dateTo;

            WeightCorrectionGoal goal = new WeightCorrectionGoal(currentWeight, goalWeight, dateFrom, dateTo, goalName, goalDescription);
            goal.save();

            Intent intent = new Intent(WeightCorrectionActivity.this, StartActivity.class);
            startActivity(intent);
            this.finish();
        } else {
            Toast toast;
            if(dateTo.equals(dateFrom)) {
                toast = Toast.makeText(getApplicationContext(), "ВАША ДАТА ЦЕЛИ СОВПАДАЕТ С СЕГОДНЯШНЕЙ ДАТОЙ", Toast.LENGTH_SHORT);
            } else if(goalName == null) {
                toast = Toast.makeText(getApplicationContext(), "ВВЕДИТЕ ОПИСАНИЕ ЦЕЛИ", Toast.LENGTH_SHORT);
            } else {
                toast = Toast.makeText(getApplicationContext(), "ПОЖАЛУЙСТА, ЗАПОЛНИТЕ ВСЕ ДАННЫЕ", Toast.LENGTH_SHORT);
            }
            toast.show();
        }
    }
    public void backToHome(View view) {
        Intent intent = new Intent(WeightCorrectionActivity.this, StartActivity.class);
        startActivity(intent);
        this.finish();
    }
    public void backToPrevActivity(View view) {
        Intent intent = new Intent(WeightCorrectionActivity.this, AllSubThemesSport.class);
        startActivity(intent);
        this.finish();
    }
    private void findAllBtnsBootStrap() {
        ArrayList<Button> allBtnsRun = new ArrayList<>();
        Button sportCurrentWeight = (Button) findViewById(R.id.sportCurrentWeight);
        Button sportGoalWeight = (Button) findViewById(R.id.sportGoalWeight);
        allBtnsRun.add(sportCurrentWeight);
        allBtnsRun.add(sportGoalWeight);
        startBootStrap(allBtnsRun);
    }
    private void startBootStrap(ArrayList<Button> allBtnsRun) {
        BootStrap bootStrap = new BootStrap();
        bootStrap.bootStrapResultsBtns(WeightCorrectionActivity.this, allBtnsRun);
    }
    public void showWarning(View view) {
        final Dialog dialog;
        dialog = new Dialog(WeightCorrectionActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
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
    private void switchWeight(Button button){
        if (button.getId()==R.id.changeCurrentWeight){
            sportMinusWeightCurrent.setOnClickListener(null);
            sportPlusWeightCurrent.setOnClickListener(null);
            if (button.getText().equals("ГР")){
                setTimerOnButton(sportMinusWeightCurrent, "-", true, 1);
                setTimerOnButton(sportPlusWeightCurrent, "+", true, 1);
                button.setText("КГ");
            }else {
                setTimerOnButton(sportMinusWeightCurrent, "-", true, 0.1);
                setTimerOnButton(sportPlusWeightCurrent, "+", true, 0.1);
                button.setText("ГР");
            }
        }
        if(button.getId()==R.id.changeGoalWeight) {
            sportMinusWeightGoal.setOnClickListener(null);
            sportPlusWeightGoal.setOnClickListener(null);
            if (button.getText().equals("ГР")){
                setTimerOnButton(sportMinusWeightGoal, "-", false, 1);
                setTimerOnButton(sportPlusWeightGoal, "+", false, 1);
                button.setText("КГ");
            }else {
                setTimerOnButton(sportMinusWeightGoal, "-", false, 0.1);
                setTimerOnButton(sportPlusWeightGoal, "+", false, 0.1);
                button.setText("ГР");
            }
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
        currentWeight = 50.0;
        goalWeight = 50.0;
        sportCurrentWeight.setText(String.valueOf(currentWeight));
        sportGoalWeight.setText(String.valueOf(currentWeight));
    }
    public void setCurrentWeight(View view) {
        final Dialog dialog;
        dialog = new Dialog(WeightCorrectionActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.choose_value);
        Button apply = (Button) dialog.findViewById(R.id.apply);
        final EditText value = (EditText) dialog.findViewById(R.id.value);
        value.setText(currentWeight + "");
        value.isShown();
        apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sportCurrentWeight.setText(value.getText());

                dialog.dismiss();
            }
        });

        dialog.show();
    }

    public void setGoalWeight(View view) {
        final Dialog dialog;
        dialog = new Dialog(WeightCorrectionActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.choose_value);
        Button apply = (Button) dialog.findViewById(R.id.apply);
        final EditText value = (EditText) dialog.findViewById(R.id.value);
        value.setText(goalWeight + "");
        apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sportGoalWeight.setText(value.getText());
                goalWeight = Double.parseDouble(value.getText().toString());
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
}
