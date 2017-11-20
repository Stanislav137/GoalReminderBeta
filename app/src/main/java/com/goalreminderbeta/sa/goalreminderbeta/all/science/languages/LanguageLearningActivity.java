package com.goalreminderbeta.sa.goalreminderbeta.all.science.languages;

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
import com.goalreminderbeta.sa.goalreminderbeta.additional.BootStrap;
import com.goalreminderbeta.sa.goalreminderbeta.additional.CustomDatePicker;
import com.goalreminderbeta.sa.goalreminderbeta.additional.DialogBuilder;
import com.goalreminderbeta.sa.goalreminderbeta.all.StartActivity;
import com.goalreminderbeta.sa.goalreminderbeta.all.science.AllSubThemesScience;
import com.goalreminderbeta.sa.goalreminderbeta.goals.Goal;
import com.goalreminderbeta.sa.goalreminderbeta.goals.LanguageLearningGoal;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class LanguageLearningActivity extends AppCompatActivity {

    private Button lvlLanguageCurrent, lvlLanguageGoal;
    private Date dateFrom, dateTo;
    private TextView languageDateFrom, languageDateTo;
    private Dialog dialog;
    private String goalDescription, goalName;
    private LanguageDialogBuilder ldb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.language_theme);

        findAllBtnsBootStrap(); // using for BootStrap!
        findAllButtons();
        initializeUX();
    }

    public void editDescription(View view) {
        dialog = new Dialog(LanguageLearningActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
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

    private void findAllButtons() {
        lvlLanguageCurrent = (Button) findViewById(R.id.lvlLanguageCurrent);
        lvlLanguageGoal = (Button) findViewById(R.id.lvlLanguageGoal);
        languageDateFrom = (TextView) findViewById(R.id.languageDateFrom);
        languageDateTo = (TextView) findViewById(R.id.languageDateTo);
    }

    public void pickDateTo(View view) {
        CustomDatePicker.pickDate(LanguageLearningActivity.this, languageDateTo);
    }

    public void saveGoal(View view) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        dateFrom = formatter.parse(String.valueOf(languageDateFrom.getText()));
        dateTo = formatter.parse(String.valueOf(languageDateTo.getText()));
        if (goalName != null && !dateTo.equals(dateFrom)) {
        Date dateFrom = this.dateFrom;
        Date dateTo = this.dateTo;
        LanguageLearningGoal languageLearningGoal = new LanguageLearningGoal(dateFrom, dateTo, goalName, goalDescription,
                LanguageLevels.valueOf(String.valueOf(lvlLanguageCurrent.getText())),
                LanguageLevels.valueOf(String.valueOf(lvlLanguageGoal.getText())));
            if(ldb==null){
                ldb = new LanguageDialogBuilder();
            }
            ldb.createDialog(LanguageLearningActivity.this,languageLearningGoal).show();
        //languageLearningGoal.save();

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
        Intent intent = new Intent(LanguageLearningActivity.this, StartActivity.class);
        startActivity(intent);
        this.finish();
    }

    public void backToPrevActivity(View view) {
        Intent intent = new Intent(LanguageLearningActivity.this, AllSubThemesScience.class);
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
        bootStrap.bootStrapResultsBtns(LanguageLearningActivity.this, allBtnsRun);
    }

    public void showWarning(View view) {
        final Dialog dialog;
        dialog = new Dialog(LanguageLearningActivity.this);
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
        languageDateFrom.setText(reportDate);
        languageDateTo.setText(reportDate);
        lvlLanguageCurrent.setText(LanguageLevels.A1.toString());
        lvlLanguageGoal.setText(LanguageLevels.C2.toString());
    }

    public void currentLevelPrev(View view) {
        LanguageLevels currentlevel =
                getPrevLanguageLevel(LanguageLevels.valueOf(String.valueOf(lvlLanguageCurrent.getText())));
        lvlLanguageCurrent.setText(currentlevel.toString());
    }

    public void currentLevelNext(View view) {
        LanguageLevels currentlevel =
                getNextLanguageLevel(LanguageLevels.valueOf(String.valueOf(lvlLanguageCurrent.getText())));
        lvlLanguageCurrent.setText(currentlevel.toString());
    }
    public void goalLevelPrev(View view) {
        LanguageLevels currentlevel =
                getPrevLanguageLevel(LanguageLevels.valueOf(String.valueOf(lvlLanguageGoal.getText())));
        lvlLanguageGoal.setText(currentlevel.toString());
    }

    public void goalLevelNext(View view) {
        LanguageLevels currentlevel =
                getNextLanguageLevel(LanguageLevels.valueOf(String.valueOf(lvlLanguageGoal.getText())));
        lvlLanguageGoal.setText(currentlevel.toString());
    }
    private LanguageLevels getNextLanguageLevel(LanguageLevels e)
    {
        int index = e.ordinal();
        if (index < 5) {
            int nextIndex = index + 1;
            LanguageLevels[] levels = LanguageLevels.values();
            nextIndex %= levels.length;
            return levels[nextIndex];
        }
        return LanguageLevels.C2;
    }
    private LanguageLevels getPrevLanguageLevel(LanguageLevels e)
    {
        int index = e.ordinal();
        if (index > 0){
            int nextIndex = index - 1;
            LanguageLevels[] levels = LanguageLevels.values();
            nextIndex %= levels.length;
            return levels[nextIndex];
        }
        return LanguageLevels.Begin;
    }
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, AllSubThemesScience.class);
        startActivity(intent);
        this.finish();
    }

    private static class LanguageDialogBuilder extends DialogBuilder {
        private static EditText currentLanguageET, goalLanguageET;
        private static TextView currentLanguageTV, goalLanguageTV;

        private static Dialog languageDialog;

        public LanguageDialogBuilder() {


        }
        @Override
        public Dialog createDialog(final Activity activity, Goal goal){
            languageDialog = super.createDialog(activity,goal);
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            String date_from = sdf.format(dialogBuilderGoal.getFromDate());
            dateFrom.setText(date_from);
            String date_to = sdf.format(dialogBuilderGoal.getToDate());
            dateTo.setText(date_to);

            currentLanguageTV = new TextView(languageDialog.getContext());
            currentLanguageTV.setText("Your language level is:");
            dialogLV.addView(currentLanguageTV,lp);
            currentLanguageET = new EditText(languageDialog.getContext());
            currentLanguageET.setText(String.valueOf(dialogBuilderGoal.getCurrentResult()));
            dialogLV.addView(currentLanguageET,lp);

            goalLanguageTV = new TextView(languageDialog.getContext());
            goalLanguageTV.setText("Goal language level is:");
            dialogLV.addView(goalLanguageTV,lp);
            goalLanguageET = new EditText(languageDialog.getContext());
            goalLanguageET.setText(String.valueOf(dialogBuilderGoal.getGoalResult()));
            dialogLV.addView(goalLanguageET,lp);
            confirm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Class c = dialogBuilderGoal.getClass();
                    if(c== LanguageLearningGoal.class){
                        ((LanguageLearningGoal)dialogBuilderGoal).save();
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

            return languageDialog;
        }
    }
}
