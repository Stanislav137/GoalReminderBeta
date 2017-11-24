package com.goalreminderbeta.sa.goalreminderbeta.all.other;

import android.app.Activity;
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
import com.goalreminderbeta.sa.goalreminderbeta.additional.DialogBuilder;
import com.goalreminderbeta.sa.goalreminderbeta.all.AllSectionTheme;
import com.goalreminderbeta.sa.goalreminderbeta.all.StartActivity;
import com.goalreminderbeta.sa.goalreminderbeta.goals.ElementCorrectionGoal;
import com.goalreminderbeta.sa.goalreminderbeta.goals.Goal;

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
    private ElementDialogBuilder edb;

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
        if(!goalName.equals("") || !goalDescription.equals("")) {
            ImageView imgReadyDescription = (ImageView) findViewById(R.id.imgReadyDescription);
            imgReadyDescription.setBackground(getResources().getDrawable(R.drawable.ready));
            dialog.dismiss();
        } else {
            goalName = null;
            dialog.dismiss();
        }
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
        if (goalName != null && levelCurrent != 0 && !dateTo.equals(dateFrom) && dateFrom.getTime() < dateTo.getTime()) {
        Date fromDate = this.dateFrom;
        Date toDate = this.dateTo;
        ElementCorrectionGoal elementCorrectionGoal = new ElementCorrectionGoal(levelCurrent, fromDate, toDate, goalName, goalDescription);

            if(edb==null){
                edb = new ElementDialogBuilder();
            }
            edb.createDialog(ElementCorrectionActivity.this,elementCorrectionGoal).show();
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

    private static class ElementDialogBuilder extends DialogBuilder {
        private static EditText currentElementET, goalElementET;
        private static TextView currentElementTV, goalElementTV;

        private static Dialog elementDialog;

        public ElementDialogBuilder() {
        }
        @Override
        public Dialog createDialog(final Activity activity, Goal goal){
            elementDialog = super.createDialog(activity,goal);
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            String date_from = sdf.format(dialogBuilderGoal.getFromDate());
            dateFrom.setText(date_from);
            String date_to = sdf.format(dialogBuilderGoal.getToDate());
            dateTo.setText(date_to);

            currentElementTV = new TextView(elementDialog.getContext());
            currentElementTV.setText("Your current level is:");
            dialogLV.addView(currentElementTV,lp);
            currentElementET = new EditText(elementDialog.getContext());
            currentElementET.setText(String.valueOf(dialogBuilderGoal.getCurrentResult()));
            dialogLV.addView(currentElementET,lp);

            goalElementTV = new TextView(elementDialog.getContext());
            goalElementTV.setText("Goal level is:");
            dialogLV.addView(goalElementTV,lp);
            goalElementET = new EditText(elementDialog.getContext());
            goalElementET.setText(String.valueOf(dialogBuilderGoal.getGoalResult()));
            dialogLV.addView(goalElementET,lp);
            confirm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Class c = dialogBuilderGoal.getClass();
                    if(c== ElementCorrectionGoal.class){
                        ((ElementCorrectionGoal)dialogBuilderGoal).save();
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

            return elementDialog;
        }
    }
}
