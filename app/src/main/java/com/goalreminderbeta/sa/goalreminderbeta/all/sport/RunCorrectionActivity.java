package com.goalreminderbeta.sa.goalreminderbeta.all.sport;

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

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;

public class RunCorrectionActivity extends AppCompatActivity {

    private Date dateFrom, dateTo;
    private TextView sportDateFrom, sportDateTo;
    private Dialog dialog;
    private String goalDescription, goalName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.run_theme);

        findAllBtnsBootStrap(); // using for BootStrap!
        findAllButtons();
    }

    private void findAllButtons() {
        sportDateFrom = (TextView) findViewById(R.id.sportDateFrom);
        sportDateTo = (TextView) findViewById(R.id.sportDateTo);
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

    private void findAllBtnsBootStrap() {
        ArrayList<Button> allBtnsRun = new ArrayList<>();
        Button runDistance = (Button) findViewById(R.id.runDistance);
        Button runTime = (Button) findViewById(R.id.runTime);
        allBtnsRun.add(runDistance);
        allBtnsRun.add(runTime);
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
