package com.goalreminderbeta.sa.goalreminderbeta.all.science;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.goalreminderbeta.sa.goalreminderbeta.R;
import com.goalreminderbeta.sa.goalreminderbeta.additional.BootStrap;
import com.goalreminderbeta.sa.goalreminderbeta.additional.CustomDatePicker;
import com.goalreminderbeta.sa.goalreminderbeta.all.StartActivity;
import com.goalreminderbeta.sa.goalreminderbeta.goals.LanguageCorrectionGoal;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class LanguageCorrectionActivity extends AppCompatActivity {

    private Date dateFrom, dateTo;
    private TextView languageDateFrom, languageDateTo;
    private Dialog dialog;
    private String goalDescription, goalName;
    private double currentLangLvl = 300;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.language_theme);

        findAllBtnsBootStrap(); // using for BootStrap!
        findAllButtons();
        initializeUX();
    }

    public void editDescription(View view) {
        dialog = new Dialog(LanguageCorrectionActivity.this);
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

    private void findAllButtons() {
        languageDateFrom = (TextView) findViewById(R.id.sportDateFrom);
        languageDateTo = (TextView) findViewById(R.id.repeatsDateTo);
    }

    public void pickDateFrom(View view) throws ParseException {
        CustomDatePicker.pickDate(LanguageCorrectionActivity.this, languageDateFrom);
    }
    public void pickDateTo(View view) {
        CustomDatePicker.pickDate(LanguageCorrectionActivity.this, languageDateTo);
    }

    public void saveGoal(View view) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        dateFrom = formatter.parse(String.valueOf(languageDateFrom.getText()));
        dateTo = formatter.parse(String.valueOf(languageDateTo.getText()));

        Date dateFrom = this.dateFrom;
        Date dateTo = this.dateTo;
        LanguageCorrectionGoal languageCorrectionGoal = new LanguageCorrectionGoal(currentLangLvl, dateFrom, dateTo, goalName, goalDescription);
        languageCorrectionGoal.save();
        Intent intent = new Intent(LanguageCorrectionActivity.this, StartActivity.class);
        startActivity(intent);
        this.finish();
    }

    public void backToHome(View view) {
        Intent intent = new Intent(LanguageCorrectionActivity.this, StartActivity.class);
        startActivity(intent);
        this.finish();
    }

    public void backToPrevActivity(View view) {
        Intent intent = new Intent(LanguageCorrectionActivity.this, AllSubThemesScience.class);
        startActivity(intent);
        this.finish();
    }

    private void findAllBtnsBootStrap() {
        ArrayList<Button> allBtnsRun = new ArrayList<>();
        Button lvlLanguageCurrent = (Button) findViewById(R.id.lvlLanguageCurrent);
        Button lvlLanguageGoal = (Button) findViewById(R.id.lvlLanguageGoal);
        allBtnsRun.add(lvlLanguageCurrent);
        allBtnsRun.add(lvlLanguageGoal);
        startBootStrap(allBtnsRun);
    }

    private void startBootStrap(ArrayList<Button> allBtnsRun) {
        BootStrap bootStrap = new BootStrap();
        bootStrap.bootStrapResultsBtns(LanguageCorrectionActivity.this, allBtnsRun);
    }

    public void showWarning(View view) {
        final Dialog dialog;
        dialog = new Dialog(LanguageCorrectionActivity.this);
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
        String reportDate = df.format(today);
        languageDateFrom.setText(reportDate);
    }
}
