package com.goalreminderbeta.sa.goalreminderbeta.all.other;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.goalreminderbeta.sa.goalreminderbeta.R;
import com.goalreminderbeta.sa.goalreminderbeta.additional.CustomDatePicker;
import com.goalreminderbeta.sa.goalreminderbeta.all.AllSectionTheme;
import com.goalreminderbeta.sa.goalreminderbeta.all.StartActivity;
import com.goalreminderbeta.sa.goalreminderbeta.goals.ElementCorrectionGoal;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class ElementCorrectionActivity extends AppCompatActivity implements View.OnClickListener {

    private Date dateFrom, dateTo;
    private TextView elementsDateFrom, elementsDateTo;
    private Dialog dialog;
    private String goalDescription, goalName;
    private TextView currentLvlGoal;
    private int levelCurrent = 0;
    Button lvlOne, lvlTwo, lvlThree, lvlFour;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.skills_theme);

        findAllButtons();
        setOnClick();
        initializeUX();
    }

    private void findAllButtons() {
        lvlOne = (Button) findViewById(R.id.lvlOne);
        lvlTwo = (Button) findViewById(R.id.lvlTwo);
        lvlThree = (Button) findViewById(R.id.lvlThree);
        lvlFour = (Button) findViewById(R.id.lvlFour);
        elementsDateFrom = (TextView) findViewById(R.id.elementsDateFrom);
        elementsDateTo = (TextView) findViewById(R.id.repeatsDateTo);
        currentLvlGoal = (TextView) findViewById(R.id.currentLvlGoal);
    }

    private void setOnClick() {
        lvlOne.setOnClickListener(this);
        lvlTwo.setOnClickListener(this);
        lvlThree.setOnClickListener(this);
        lvlFour.setOnClickListener(this);
    }

    public void editDescription(View view) {
        dialog = new Dialog(ElementCorrectionActivity.this);
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

    public void pickDateFrom(View view) throws ParseException {
        CustomDatePicker.pickDate(ElementCorrectionActivity.this, elementsDateFrom);
    }
    public void pickDateTo(View view) {
        CustomDatePicker.pickDate(ElementCorrectionActivity.this, elementsDateTo);
    }

    public void onClick(View v) {

        switch(v.getId()){

            case R.id.lvlOne:
                levelCurrent = 1;
                break;
            case R.id.lvlTwo:
                levelCurrent = 2;
                break;
            case R.id.lvlThree:
                levelCurrent = 3;
                break;
            case R.id.lvlFour:
                levelCurrent = 4;
                break;
            default:
                break;
        }
        currentLvlGoal.setText(levelCurrent + "");
    }

    public void saveGoal(View view) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        dateFrom = formatter.parse(String.valueOf(elementsDateFrom.getText()));
        dateTo = formatter.parse(String.valueOf(elementsDateTo.getText()));
        if (goalName != null && levelCurrent != 0 && !dateTo.equals(dateFrom)) {
        Date fromDate = this.dateFrom;
        Date toDate = this.dateTo;
        ElementCorrectionGoal elementCorrectionGoal = new ElementCorrectionGoal(levelCurrent, fromDate, toDate, goalName, goalDescription);
        elementCorrectionGoal.save();
        Intent intent = new Intent(ElementCorrectionActivity.this, StartActivity.class);
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

    public void backToHome(View view) {
        Intent intent = new Intent(ElementCorrectionActivity.this, StartActivity.class);
        startActivity(intent);
        this.finish();
    }

    public void backToPrevActivity(View view) {
        Intent intent = new Intent(ElementCorrectionActivity.this, AllSectionTheme.class);
        startActivity(intent);
        this.finish();
    }

    public void showWarning(View view) {
        final Dialog dialog;
        dialog = new Dialog(ElementCorrectionActivity.this);
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
    private void initializeUX(){
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        Date today = Calendar.getInstance().getTime();
        dateFrom = today;
        dateTo = today;
        String reportDate = df.format(today);
        elementsDateFrom.setText(reportDate);
        elementsDateTo.setText(reportDate);
    }
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, AllSectionTheme.class);
        startActivity(intent);
        this.finish();
    }
}
