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
import com.goalreminderbeta.sa.goalreminderbeta.additional.CustomDatePicker;
import com.goalreminderbeta.sa.goalreminderbeta.all.StartActivity;
import com.goalreminderbeta.sa.goalreminderbeta.all.science.BookCorrectionActivity;
import com.goalreminderbeta.sa.goalreminderbeta.goals.ElementCorrectionGoal;

import org.w3c.dom.Text;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ElementCorrectionActivity extends AppCompatActivity implements View.OnClickListener {

    private Date dateFrom, dateTo;
    private TextView sportDateFrom, sportDateTo;
    private Dialog dialog;
    private String goalDescription, goalName;
    private TextView currentLvlGoal;
    private int levelCurrent = 0;
    Button lvlOne, lvlTwo, lvlThree, lvlFour;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.elements_theme);

        findAllButtons();
        setOnClick();
    }

    private void findAllButtons() {
        lvlOne = (Button) findViewById(R.id.lvlOne);
        lvlTwo = (Button) findViewById(R.id.lvlTwo);
        lvlThree = (Button) findViewById(R.id.lvlThree);
        lvlFour = (Button) findViewById(R.id.lvlFour);
        sportDateFrom = (TextView) findViewById(R.id.sportDateFrom);
        sportDateTo = (TextView) findViewById(R.id.sportDateTo);
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
        CustomDatePicker.pickDate(ElementCorrectionActivity.this, sportDateFrom);
    }
    public void pickDateTo(View view) {
        CustomDatePicker.pickDate(ElementCorrectionActivity.this, sportDateTo);
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
        dateFrom = formatter.parse(String.valueOf(sportDateFrom.getText()));
        dateTo = formatter.parse(String.valueOf(sportDateTo.getText()));

        String themeCategory = "ЭЛЕМЕНТЫ";
        Date dateFrom = this.dateFrom;
        Date dateTo = this.dateTo;
        ElementCorrectionGoal elementCorrectionGoal = new ElementCorrectionGoal(levelCurrent, dateFrom, dateTo, goalName, goalDescription, themeCategory);
        elementCorrectionGoal.save();
        Intent intent = new Intent(ElementCorrectionActivity.this, StartActivity.class);
        startActivity(intent);
        this.finish();
    }

    public void backToHome(View view) {
        Intent intent = new Intent(ElementCorrectionActivity.this, StartActivity.class);
        startActivity(intent);
        this.finish();
    }

    public void backToPrevActivity(View view) {
        Intent intent = new Intent(ElementCorrectionActivity.this, AllSubThemesSport.class);
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
}
