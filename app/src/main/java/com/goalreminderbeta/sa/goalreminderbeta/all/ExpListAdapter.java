package com.goalreminderbeta.sa.goalreminderbeta.all;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.preference.PreferenceManager;
import android.support.v4.content.ContextCompat;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.goalreminderbeta.sa.goalreminderbeta.R;
import com.goalreminderbeta.sa.goalreminderbeta.all.science.languages.LanguageLevels;
import com.goalreminderbeta.sa.goalreminderbeta.goals.CardioGoal;
import com.goalreminderbeta.sa.goalreminderbeta.goals.ElementCorrectionGoal;
import com.goalreminderbeta.sa.goalreminderbeta.goals.Goal;
import com.goalreminderbeta.sa.goalreminderbeta.goals.LanguageLearningGoal;
import com.goalreminderbeta.sa.goalreminderbeta.goals.ReadBookGoal;
import com.goalreminderbeta.sa.goalreminderbeta.goals.RepeatsCorrectionGoal;
import com.goalreminderbeta.sa.goalreminderbeta.goals.WeightCorrectionGoal;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class ExpListAdapter extends BaseExpandableListAdapter {

    private ArrayList<ArrayList<Goal>> mGroups;
    private Context mContext;
    private ImageView arrowDownUp, statusGoal;
    private Map<Long,Goal> allGoalsMap;
    //private Goal goal;
    private TextView fromGoal, toGoal, goalDescription, currentResultUnits, goalResultUnits, distanceRunUnits, leftDaysGoal;
    private TextView leftToGoalUnits, dataBook, taskOfDayUnits, taskOfWeekUnits;
    private RelativeLayout bookPresent;
    private LinearLayout runDistance;
    //private boolean checkComplete = false;
    private LinearLayout separator1, separator2;
    private Typeface faceBold = null;
    private double lvlLangHoursCurrent = 0, lvlLangHoursGoal = 0, pointsSkillsCurrent = 0, pointsSkillsGoal = 0;
    private double madeTodayResult = 0;
    private String units;
    Button completed;
    Dialog congrDialog;
    AlertDialog.Builder adb;
    SharedPreferences sp;
    ProgressBar progressBar;
    TextView textCircleProgress;

    /* FOR DAY GOAL */

    private TextView nameData, distanceDG, currentResultDG, goalResultDG, taskDG, madeToday, goalDescriptionDG;

    public ExpListAdapter(Context context,ArrayList<ArrayList<Goal>> groups, Map<Long,Goal> allGoalsMap){
        mContext = context;
        mGroups = groups;
        this.allGoalsMap = allGoalsMap;


    }

    @Override
    public int getGroupCount() {
        return mGroups.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return mGroups.get(groupPosition).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return mGroups.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return mGroups.get(groupPosition).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    private void checkProgress(double currentProgress, View convertView) {
        progressBar = (ProgressBar) convertView.findViewById(R.id.circleProgress);
        textCircleProgress = (TextView) convertView.findViewById(R.id.textCircleProgress);

        progressBar.setProgress((int) currentProgress);

        if(currentProgress <= 30) {
            progressBar.getProgressDrawable().setColorFilter(mContext.
                    getResources().getColor(R.color.colorRed), PorterDuff.Mode.SRC_IN);
            textCircleProgress.setTextColor(mContext.getResources().getColor(R.color.colorRed));
        }
        if(currentProgress > 30 && currentProgress <= 60) {
            progressBar.getProgressDrawable().setColorFilter(mContext.
                    getResources().getColor(R.color.colorYellow), PorterDuff.Mode.SRC_IN);
            textCircleProgress.setTextColor(mContext.getResources().getColor(R.color.colorYellow));
        }
        if(currentProgress > 60 && currentProgress < 100) {
            progressBar.getProgressDrawable().setColorFilter(mContext.
                    getResources().getColor(R.color.colorGreen), PorterDuff.Mode.SRC_IN);
            textCircleProgress.setTextColor(mContext.getResources().getColor(R.color.colorGreen));
        }
        if(currentProgress >= 100) {
            textCircleProgress.setText("100%");
            progressBar.getProgressDrawable().setColorFilter(mContext.
                    getResources().getColor(R.color.colorGreen), PorterDuff.Mode.SRC_IN);
            textCircleProgress.setTextColor(mContext.getResources().getColor(R.color.colorGreen));
        } else {
            textCircleProgress.setText((int)currentProgress + "%");
        }
    }

    private void findWidgetsChild(View view) {
        fromGoal = (TextView) view.findViewById(R.id.fromGoal);
        toGoal = (TextView) view.findViewById(R.id.toGoal);
        distanceRunUnits = (TextView) view.findViewById(R.id.distanceRunUnits);
        goalDescription = (TextView) view.findViewById(R.id.goalDescription);
        currentResultUnits = (TextView) view.findViewById(R.id.currentResultUnits);
        goalResultUnits = (TextView) view.findViewById(R.id.yourGoalUnits);
        bookPresent = (RelativeLayout) view.findViewById(R.id.bookPresent);
        runDistance = (LinearLayout) view.findViewById(R.id.runDistance);
        leftDaysGoal = (TextView) view.findViewById(R.id.leftDaysGoal);
        leftToGoalUnits = (TextView) view.findViewById(R.id.leftToGoalUnits);
        dataBook = (TextView) view.findViewById(R.id.dataBook);
        taskOfDayUnits = (TextView) view.findViewById(R.id.taskOfDayUnits);
        statusGoal = (ImageView) view.findViewById(R.id.statusGoal);
        separator1 = (LinearLayout) view.findViewById(R.id.separator1);
        separator2 = (LinearLayout) view.findViewById(R.id.separator2);
        taskOfWeekUnits = (TextView) view.findViewById(R.id.taskOfWeekUnits);
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView,
                             ViewGroup parent) {

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.section_theme, null);

            Long groupPos = Long.parseLong(String.valueOf(groupPosition));
            Goal goal = allGoalsMap.get(groupPos); //actual goal

            fillDataGroup(goal.getThemeCategory(), goal, convertView);

            if(goal.getProgress() >= 100) {
                RelativeLayout list1 = (RelativeLayout) convertView.findViewById(R.id.list1);
                list1.setBackgroundColor(mContext.getResources().getColor(R.color.colorGreenBS));
            }
        }
        expandGoal(isExpanded, convertView);

        return convertView;
    }

    private void fillDataGroup(String themeCategory, Goal goal, View convertView) {
        ImageView ivThemeGoal = (ImageView) convertView.findViewById(R.id.ivTheme);
        switch (themeCategory){
            case "МАССА":
                ivThemeGoal.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.weight_image));
                break;
            case "КАРДИО":
                ivThemeGoal.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.cardio_image));
                break;
            case "НАВЫКИ":
                ivThemeGoal.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.skills_image));
                break;
            case "ПОВТОРЕНИЯ":
                ivThemeGoal.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.repeats_image));
                break;
            case "КНИГА":
                ivThemeGoal.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.book_image));
                break;
            case "ЯЗЫКИ":
                ivThemeGoal.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.language_image));
                break;

        }
        showDataGroup(goal, convertView, goal.getProgress());
    }

    private void showDataGroup(Goal goal, View convertView,double progress) {
        String typeLang = mContext.getResources().getConfiguration().locale.getLanguage();
        TextView nameGoal = (TextView) convertView.findViewById(R.id.nameGoal);
        nameGoal.setText(goal.getNameGoal());
        TextView themeCategory = (TextView) convertView.findViewById(R.id.themeCategory);
        if(typeLang.equals("ru")) {
            themeCategory.setText(goal.getThemeCategory());
        } else {
            if(goal.getThemeCategory().equals("МАССА")) {
                themeCategory.setText("WEIGHT");
            } else if(goal.getThemeCategory().equals("КАРДИО")) {
                themeCategory.setText("CARDIO");
            } else if(goal.getThemeCategory().equals("ПОВТОРЕНИЯ")) {
                themeCategory.setText("REPEATS");
            } else if(goal.getThemeCategory().equals("КНИГА")) {
                themeCategory.setText("BOOK");
            } else if(goal.getThemeCategory().equals("ЯЗЫКИ")) {
                themeCategory.setText("LANGUAGE");
            } else if(goal.getThemeCategory().equals("НАВЫКИ")) {
                themeCategory.setText("SKILLS");
            }
        }

        //Double progress = 30.0;
        double currentProgress = goal.getProgress();
        checkProgress(currentProgress, convertView);
    }

    @Override
    public View getChildView(final int groupPosition, final int childPosition, final boolean isLastChild,
                             View convertView, final ViewGroup parent) {

        final LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        Long groupPos = Long.parseLong(String.valueOf(groupPosition));
        final Goal goal = allGoalsMap.get(groupPos); //actual goal
        GregorianCalendar calendar = new GregorianCalendar();
        int day = calendar.get(Calendar.DATE);
        int month = calendar.get(Calendar.MONTH);
        month += 1;
        int year = calendar.get(Calendar.YEAR);
        final String date = String.valueOf(day) + "." + String.valueOf(month) + "." + String.valueOf(year);
        sp = PreferenceManager.getDefaultSharedPreferences(mContext);
        final String date2 = sp.getString("date"+goal.getThemeCategory()+goal.getNameGoal()+goal.getDescriptionGoal(), "");
        if (!date.equals(date2)) {
            goal.setCompleted(false);
            goal.save();
        } else {
            goal.setBlink(false);
            goal.setMadeTodayResult(0);
            goal.setCompleted(true);
            goal.save();
        }

        if(goal.getCompleted() || goal.getProgress() >= 100) {
            convertView = inflater.inflate(R.layout.child_section_stat, null);
            //convertView.setMinimumHeight(1500);


            findWidgetsChild(convertView);
            fillDataChild(goal, convertView, goal.getThemeCategory());

        } else {
            convertView = inflater.inflate(R.layout.child_section_complete, null);
            //convertView.setMinimumHeight(1500);

            findUxDayGoal(convertView);
            if(!goal.getBlink()) {
                blink(convertView);
            }
            showDataDG(goal, convertView);

            final LinearLayout showPopupDayTask = (LinearLayout) convertView.findViewById(R.id.showPopupDayTask);
            showPopupDayTask.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String typeLang = mContext.getResources().getConfiguration().locale.getLanguage();
                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(mContext);
                    if(typeLang.equals("ru")) {
                        alertDialog.setTitle("НОВЫЙ РЕЗУЛЬТАТ");
                        alertDialog.setMessage("Введите новый прогресс");
                    } else {
                        alertDialog.setTitle("NEW RESULT");
                        alertDialog.setMessage("Enter new progress");
                    }

                    final EditText input = new EditText(mContext);
                    LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.MATCH_PARENT,
                            LinearLayout.LayoutParams.MATCH_PARENT);
                    input.setInputType(InputType.TYPE_CLASS_NUMBER);
                    input.setLayoutParams(lp);
                    alertDialog.setView(input);

                    String btnOk;
                    String btnCancel;
                    if(typeLang.equals("ru")) {
                        btnOk = "ОК";
                        btnCancel = "ОТМЕНА";
                    } else {
                        btnOk = "OK";
                        btnCancel = "CANCEL";
                    }

                    alertDialog.setPositiveButton(btnOk,
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    madeTodayResult = Double.parseDouble(input.getText().toString());
                                    madeToday.setText(madeTodayResult + "");
                                    goal.setMadeTodayResult(madeTodayResult);
                                    if(goal instanceof  WeightCorrectionGoal
                                            ||goal instanceof RepeatsCorrectionGoal
                                            ||goal instanceof CardioGoal
                                            ||goal instanceof LanguageLearningGoal){
                                        goal.setCurrentResult(madeTodayResult);
                                    }
                                    double iR;
                                    double cR;
                                    double gR;
                                    double percent = 0;
                                    if(goal instanceof ReadBookGoal){
                                        cR = ((ReadBookGoal)goal).getCurrentResult();
                                        gR = ((ReadBookGoal)goal).getGoalResult();
                                        percent = Math.abs((cR/gR)*100);
                                    }else if(goal instanceof LanguageLearningGoal){
                                        double mR = goal.getMadeTodayResult();
                                        iR = ((LanguageLearningGoal)goal).getInitialResult();
                                        gR = ((LanguageLearningGoal)goal).getGoalResult();
                                        percent = Math.abs((mR/(gR-iR))*100);
                                    }else if(goal instanceof CardioGoal
                                            ||goal instanceof RepeatsCorrectionGoal||goal instanceof WeightCorrectionGoal) {
                                        iR = goal.getInitialResult();
                                        cR = goal.getCurrentResult();
                                        gR = goal.getGoalResult();
                                        percent = Math.abs(((iR - cR) / (gR - iR)) * 100);
                                    }else if(goal instanceof ElementCorrectionGoal){
                                        iR = ((ElementCorrectionGoal)goal).getInitialResult();
                                        cR =((ElementCorrectionGoal)goal).getCurrentResult2();
                                        percent = Math.round(Math.abs((iR - cR)/
                                                (500-((ElementCorrectionGoal)goal).getInitialResult())*100));
                                    }else {
                                        percent = 0;
                                    }
                                    goal.setProgress(Math.round(percent));
                                    int currentProgress = (int)Math.round(goal.getProgress());
                                    progressBar.setProgress(currentProgress);

                                    if(currentProgress <= 30) {
                                        progressBar.getProgressDrawable().setColorFilter(mContext.
                                                getResources().getColor(R.color.colorRed), PorterDuff.Mode.SRC_IN);
                                        textCircleProgress.setTextColor(mContext.getResources().getColor(R.color.colorRed));
                                    }
                                    if(currentProgress > 30 && currentProgress <= 60) {
                                        progressBar.getProgressDrawable().setColorFilter(mContext.
                                                getResources().getColor(R.color.colorYellow), PorterDuff.Mode.SRC_IN);
                                        textCircleProgress.setTextColor(mContext.getResources().getColor(R.color.colorYellow));
                                    }
                                    if(currentProgress > 60 && currentProgress < 100) {
                                        progressBar.getProgressDrawable().setColorFilter(mContext.
                                                getResources().getColor(R.color.colorGreen), PorterDuff.Mode.SRC_IN);
                                        textCircleProgress.setTextColor(mContext.getResources().getColor(R.color.colorGreen));
                                    }
                                    if(currentProgress >= 100) {
                                        String typeLang = mContext.getResources().getConfiguration().locale.getLanguage();
                                        AlertDialog.Builder alertDialog = new AlertDialog.Builder(mContext);
                                        if(typeLang.equals("ru")) {
                                            alertDialog.setTitle("ЦЕЛЬ ВЫПОЛНЕНА! ТАК ДЕРЖАТЬ!");
                                            alertDialog.setMessage("ЗАПИШИТЕ СЛЕДУЮЩУЮ ЦЕЛЬ! ВПЕРЁД К ВЕЛИЧИЮ!");
                                        } else {
                                            alertDialog.setTitle("THE PURPOSE IS IMPLEMENTED! KEEP IT UP!");
                                            alertDialog.setMessage("WRITE THE FOLLOWING PURPOSE! FORWARD TO VALUE!");
                                        }
                                        alertDialog.setPositiveButton("OK", null);
                                        progressBar.getProgressDrawable().setColorFilter(mContext.
                                                getResources().getColor(R.color.colorGreen), PorterDuff.Mode.SRC_IN);
                                        textCircleProgress.setTextColor(mContext.getResources().getColor(R.color.colorGreen));
                                        textCircleProgress.setText("100%");
                                        alertDialog.show();
                                    } else {
                                        textCircleProgress.setText((int) currentProgress + "%");
                                    }
                                    goal.setBlink(true);
                                    goal.save();
                                    notifyDataSetChanged();
                                    dialog.cancel();
                                }
                            });

                    alertDialog.setNegativeButton(btnCancel,
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                }
                            });

                    alertDialog.show();
                    }
                });
            showDataDG(goal,convertView);

                completed = (Button) convertView.findViewById(R.id.completed);

                faceBold = Typeface.createFromAsset(mContext.getAssets(), "fonts/start_font.otf");
                completed.setTypeface(faceBold);
                completed.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        SharedPreferences.Editor editor = sp.edit();
                        editor.putString("date"+goal.getThemeCategory()+goal.getNameGoal()+goal.getDescriptionGoal(),date);
                        editor.commit();
                        goal.setCompleted(true);
                        double iR;
                        double cR;
                        double gR;
                        double percent = 0;
                        if(goal instanceof ReadBookGoal){
                            cR = ((ReadBookGoal)goal).getCurrentResult();
                            gR = ((ReadBookGoal)goal).getGoalResult();
                            percent = Math.abs((cR/gR)*100);
                        }else if(goal instanceof LanguageLearningGoal){
                            double mR = goal.getMadeTodayResult();
                            iR = ((LanguageLearningGoal)goal).getInitialResult();
                            gR = ((LanguageLearningGoal)goal).getGoalResult();
                            percent = Math.abs((mR/(gR-iR))*100);
                        }else if(goal instanceof CardioGoal
                                ||goal instanceof RepeatsCorrectionGoal||goal instanceof WeightCorrectionGoal) {
                            iR = goal.getInitialResult();
                            cR = goal.getCurrentResult();
                            gR = goal.getGoalResult();
                            percent = Math.abs(((iR - cR) / (gR - iR)) * 100);
                        }else if(goal instanceof ElementCorrectionGoal){
                            iR = ((ElementCorrectionGoal)goal).getInitialResult();
                            cR =((ElementCorrectionGoal)goal).getCurrentResult2();
                            percent = Math.round(Math.abs((iR - cR)/
                                    (500-((ElementCorrectionGoal)goal).getInitialResult())*100));
                        }else {
                            percent = 0;
                        }
                        goal.setProgress(Math.round(percent));
                        int currentProgress = (int)Math.round(goal.getProgress());
                        progressBar.setProgress(currentProgress);

                        if(currentProgress <= 30) {
                            progressBar.getProgressDrawable().setColorFilter(mContext.
                                    getResources().getColor(R.color.colorRed), PorterDuff.Mode.SRC_IN);
                            textCircleProgress.setTextColor(mContext.getResources().getColor(R.color.colorRed));
                        }
                        if(currentProgress > 30 && currentProgress <= 60) {
                            progressBar.getProgressDrawable().setColorFilter(mContext.
                                    getResources().getColor(R.color.colorYellow), PorterDuff.Mode.SRC_IN);
                            textCircleProgress.setTextColor(mContext.getResources().getColor(R.color.colorYellow));
                        }
                        if(currentProgress > 60 && currentProgress < 100) {
                            progressBar.getProgressDrawable().setColorFilter(mContext.
                                    getResources().getColor(R.color.colorGreen), PorterDuff.Mode.SRC_IN);
                            textCircleProgress.setTextColor(mContext.getResources().getColor(R.color.colorGreen));
                        }
                        if(currentProgress >= 100) {
                            textCircleProgress.setText("100%");
                            progressBar.getProgressDrawable().setColorFilter(mContext.
                                    getResources().getColor(R.color.colorGreen), PorterDuff.Mode.SRC_IN);
                            textCircleProgress.setTextColor(mContext.getResources().getColor(R.color.colorGreen));
                        } else {
                            textCircleProgress.setText((int)currentProgress + "%");
                        }
                        goal.save();
                        String typeLang = mContext.getResources().getConfiguration().locale.getLanguage();
                        adb = new AlertDialog.Builder(mContext);
                        if(typeLang.equals("ru")) {
                            adb.setTitle("Поздравляем!");
                            adb.setMessage("Молодец! Продолжай в том же духе!");
                            adb.setPositiveButton("ОКЕЙ", null);
                        } else {
                            adb.setTitle("Congratulations");
                            adb.setMessage("Go on! You are the best!");
                            adb.setPositiveButton("Thanks", null);
                        }
                        adb.setCancelable(true);
                        congrDialog = adb.create();
                        congrDialog.show();
                        notifyDataSetChanged();
                    }
                });
            }
        return convertView;
    }

    private void findUxDayGoal(View view) {
        nameData = (TextView) view.findViewById(R.id.nameData);
        distanceDG = (TextView) view.findViewById(R.id.distanceDG);
        currentResultDG = (TextView) view.findViewById(R.id.currentResultDG);
        goalResultDG = (TextView) view.findViewById(R.id.goalResultDG);
        taskDG = (TextView) view.findViewById(R.id.taskDG);
        madeToday = (TextView) view.findViewById(R.id.madeToday);
        goalDescriptionDG = (TextView) view.findViewById(R.id.goalDescriptionDG);
        taskOfWeekUnits = (TextView) view.findViewById(R.id.taskOfWeekUnits);
    }

    private void showDataDG(Goal goal, View view) {
        double dayTask = (goal.getGoalResult() - goal.getCurrentResult()) / getDifferenceInDays(new Date(), goal.getToDate());
        units = "";

        if(goal.getThemeCategory().equals("КНИГА")) {
            if (goal.getDataBook().equals("")) {
                nameData.setText(goal.getNameGoal() + "");
            } else {
                nameData.setText(goal.getNameGoal() + " | " + goal.getDataBook());
            }
        } else {
            nameData.setText(goal.getNameGoal() + "");
        }
        String typeLang = mContext.getResources().getConfiguration().locale.getLanguage();
        switch (goal.getThemeCategory()) {
            case "МАССА":
                TextView titleTxt = (TextView) view.findViewById(R.id.titleTxt);
                TextView titleDG = (TextView) view.findViewById(R.id.titleDG);
                if(typeLang.equals("ru")) {
                    titleTxt.setText("МОЙ ВЕС СОСТАВЛЯЕТ:");
                    units = "кг";
                } else {
                    titleTxt.setText("MY WEIGHT MAKES:");
                    units = "kg";
                }
                LinearLayout llTaskWeek = (LinearLayout) view.findViewById(R.id.llTaskWeek);
                LinearLayout separator3 = (LinearLayout) view.findViewById(R.id.separator3);
                TextView taskOfWeek = (TextView) view.findViewById(R.id.taskOfWeek);
                llTaskWeek.setVisibility(View.VISIBLE);
                separator3.setVisibility(View.VISIBLE);
                if(typeLang.equals("ru")) {
                    taskDG.setText("тренировка");
                    taskOfWeek.setText("ЗАДАЧА НА НЕДЕЛЮ:");
                } else {
                    taskDG.setText("training");
                    taskOfWeek.setText("TASK FOR A WEEK:");
                }
                taskDG.setTextColor(Color.parseColor("#d23134"));
                taskOfWeekUnits.setText(String.format("%.1f", dayTask * 7) + " " + units);
                titleDG.setTextColor(Color.parseColor("#d23134"));
                currentResultDG.setText(goal.getInitialResult() + " " + units);
                goalResultDG.setText(goal.getGoalResult() + " " + units);
                madeToday.setText(String.format("%.1f", goal.getMadeTodayResult()) + " " + units);
                break;
            case "КАРДИО":
                if(typeLang.equals("ru")) {
                    units = "сек";
                } else units = "seconds";
                if(goal.getCurrentResult()==0 && goal.getGoalResult() == 0) { // regular attack
                    TextView distanceTitle = (TextView) view.findViewById(R.id.distanceTitle);
                    LinearLayout llCurrentResult = (LinearLayout) view.findViewById(R.id.llCurrentResult);
                    LinearLayout showPopupDayTask = (LinearLayout) view.findViewById(R.id.showPopupDayTask);
                    LinearLayout separator4 = (LinearLayout) view.findViewById(R.id.separator4);
                    LinearLayout separator3RA = (LinearLayout) view.findViewById(R.id.separator3);
                    LinearLayout separator5 = (LinearLayout) view.findViewById(R.id.separator5);
                    llCurrentResult.setVisibility(View.GONE);
                    showPopupDayTask.setVisibility(View.GONE);
                    separator4.setVisibility(View.GONE);
                    separator3RA.setVisibility(View.GONE);
                    separator5.setVisibility(View.GONE);
                    if(typeLang.equals("ru")) {
                        distanceDG.setText(goal.getDistance() + " метров");
                        taskDG.setText("преодолеть дистанцию");
                        goalResultDG.setText(goal.getDistance() + " " + "метров");
                        distanceTitle.setText("ДИСТАНЦИЯ");
                    } else {
                        distanceDG.setText(goal.getDistance() + " meters");
                        taskDG.setText("bridge the distance");
                        goalResultDG.setText(goal.getDistance() + " " + "meters");
                        distanceTitle.setText("DISTANCE");
                    }
                    madeToday.setText("ТРЕНИРОВКА");
                } else {
                    LinearLayout llDistance = (LinearLayout) view.findViewById(R.id.llDistance);
                    LinearLayout separator = (LinearLayout) view.findViewById(R.id.separator);
                    llDistance.setVisibility(View.VISIBLE);
                    separator.setVisibility(View.VISIBLE);
                    TextView titleTxtCardio = (TextView) view.findViewById(R.id.titleTxt);
                    if(typeLang.equals("ru")) {
                        titleTxtCardio.setText("МОЁ НОВОЕ ВРЕМЯ:");
                        distanceDG.setText(goal.getDistance() + " метров");
                        taskDG.setText("тренировка");
                    } else {
                        titleTxtCardio.setText("MY NEW TIME:");
                        distanceDG.setText(goal.getDistance() + " meters");
                        taskDG.setText("training");
                    }
                    currentResultDG.setText(goal.getInitialResult() + " " + units);
                    goalResultDG.setText(goal.getGoalResult() + " " + units);
                    madeToday.setText(String.format("%.1f", goal.getMadeTodayResult()) + " " + units);
                }
                break;
            case "НАВЫКИ":
                TextView titleTxtSkill = (TextView) view.findViewById(R.id.titleTxt);
                if(typeLang.equals("ru")) {
                    titleTxtSkill.setText("ВВЕДИ НОВЫЙ ПРОГРЕСС:");
                    units = "очки";
                    taskDG.setText("тренировка");
                    currentResultDG.setText(((ElementCorrectionGoal)goal).getInitialLevel() + " уровень" + " / " + ((ElementCorrectionGoal)goal).getInitialResult() + " " + units);
                    goalResultDG.setText(goal.getGoalResult() + " уровень" + " / " + pointsSkillsGoal + " " + units);
                } else {
                    titleTxtSkill.setText("ENTER THE NEW PROGRESS:");
                    units = "points";
                    taskDG.setText("training");
                    currentResultDG.setText(((ElementCorrectionGoal)goal).getInitialLevel() + " level" + " / " + ((ElementCorrectionGoal)goal).getInitialResult() + " " + units);
                    goalResultDG.setText(goal.getGoalResult() + " level" + " / " + pointsSkillsGoal + " " + units);
                }
                skillsPoints(goal);
                madeToday.setText(String.format("%.1f", goal.getMadeTodayResult()) + " " + units);

                break;
            case "ПОВТОРЕНИЯ":
                if(typeLang.equals("ru")) {
                    units = "повторения";
                } else units = "repetition";
                if(goal.getCurrentResult() == 0) {
                    LinearLayout llCurrentResult = (LinearLayout) view.findViewById(R.id.llCurrentResult);
                    LinearLayout showPopupDayTask = (LinearLayout) view.findViewById(R.id.showPopupDayTask);
                    LinearLayout separator4 = (LinearLayout) view.findViewById(R.id.separator4);
                    LinearLayout separator5 = (LinearLayout) view.findViewById(R.id.separator5);
                    llCurrentResult.setVisibility(View.GONE);
                    separator4.setVisibility(View.GONE);
                    separator5.setVisibility(View.GONE);
                    showPopupDayTask.setVisibility(View.GONE);
                    if(typeLang.equals("ru")) {
                        taskDG.setText("выполнить повторения");
                    } else {
                        taskDG.setText("execute repeats");
                    }
                } else {
                    titleTxt = (TextView) view.findViewById(R.id.titleTxt);
                    if(typeLang.equals("ru")) {
                        taskDG.setText("макс. кол-во повторений");
                        titleTxt.setText("МОЙ НОВЫЙ ПРОГРЕСС:");
                    } else {
                        taskDG.setText("max. number of repetitions");
                        titleTxt.setText("MY NEW PROGRESS:");
                    }
                }
                currentResultDG.setText(goal.getInitialResult() + " " + units);
                goalResultDG.setText(goal.getGoalResult() + " " + units);
                madeToday.setText(String.format("%.1f", goal.getMadeTodayResult()) + " " + units);
                break;
            case "КНИГА":
                if(typeLang.equals("ru")) {
                    units = "страниц";
                } else units = "pages";
                dayTask = Math.ceil(dayTask);
                taskDG.setText(String.format("%.1f", dayTask) + " " + units);
                currentResultDG.setText(goal.getInitialResult() + " " + units);
                goalResultDG.setText(goal.getGoalResult() + " " + units);
                madeToday.setText(String.format("%.1f", goal.getMadeTodayResult()) + " " + units);
                break;
            case "ЯЗЫКИ":
                languageLevelInHours(goal);
                double lvlDayTask = (lvlLangHoursGoal - lvlLangHoursCurrent) / getDifferenceInDays(new Date(), goal.getToDate());
                if(typeLang.equals("ru")) {
                    units = "часы";
                } else {
                    units = "hours";
                }
                taskDG.setText(String.format("%.1f", lvlDayTask) + " " + units);
                currentResultDG.setText(((LanguageLearningGoal)goal).getCurrentLanguageLevel() + " / " + ((LanguageLearningGoal)goal).getCurrentResult() + " часов");
                goalResultDG.setText(goal.getGoalLanguageLevel() + " / " + goal.getGoalResult() + " часов");
                madeToday.setText(String.format("%.1f", goal.getMadeTodayResult()) + " " + units);
                break;
        }

        /* FOR ALL GOALS */
        if(goal.getDescriptionGoal().equals("")) {
            if(typeLang.equals("ru")) {
                goalDescriptionDG.setText("ТЫ НЕ ПРОИГРАЛ ПОКА НЕ СДАЛСЯ !"); // default string
            } else goalDescriptionDG.setText("YOU DID NOT LOSE UNTIL YOU CAME !"); // default string
        } else {
            goalDescriptionDG.setText(goal.getDescriptionGoal() + "");
        }
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    private void fillDataChild(Goal goal, View convertView, String themeCategory) {

        String units = "";
        double currentNumber = 0, goalNumber = 0;

        currentNumber = goal.getCurrentResult();
        goalNumber = goal.getGoalResult();

        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        String fromDate = String.valueOf(formatter.format(goal.getFromDate()));
        String toDate = String.valueOf(formatter.format(goal.getToDate()));
        double leftGoalUnits = goal.getGoalResult() - goal.getCurrentResult();
        double dayTask = (goal.getGoalResult() - goal.getCurrentResult()) / getDifferenceInDays(new Date(), goal.getToDate());
        double currentStatus = leftGoalUnits / 100;
        double goalStatus = currentStatus * 50;
        String typeLang = mContext.getResources().getConfiguration().locale.getLanguage();
        switch (themeCategory){
            case "МАССА":
                LinearLayout llTaskWeek = (LinearLayout) convertView.findViewById(R.id.llTaskWeek);
                LinearLayout separator3 = (LinearLayout) convertView.findViewById(R.id.separator3);
                TextView taskOfWeek = (TextView) convertView.findViewById(R.id.taskOfWeek);
                llTaskWeek.setVisibility(View.VISIBLE);
                separator3.setVisibility(View.VISIBLE);
                if(typeLang.equals("ru")) {
                    units = "кг";
                    taskOfDayUnits.setText("тренировка");
                    taskOfWeek.setText("ЗАДАЧА НА НЕДЕЛЮ:");
                } else {
                    units = "kg";
                    taskOfDayUnits.setText("training");
                    taskOfWeek.setText("TASK FOR A WEEK:");
                }
                DecimalFormat precision = new DecimalFormat("0.0");
                currentResultUnits.setText(precision.format(currentNumber) + " " + units);
                goalResultUnits.setText(precision.format(goalNumber) + " " + units);
                leftToGoalUnits.setText(String.format("%.1f", leftGoalUnits) + " " + units);
                taskOfWeekUnits.setText(String.format("%.1f", dayTask * 7) + "");
                //goalResultUnits.setText((int)goalNumber + " " + units);
                //taskOfDayUnits.setText(String.format("%.1f", dayTask) + " " + units);
                currentResultUnits.setText((int)currentNumber + " " + units);
                break;
            case "КАРДИО":
                if(typeLang.equals("ru")) {
                    units = "сек";
                } else units = "seconds";
                if(goal.getCurrentResult() == 0) {
                    LinearLayout runDistance = (LinearLayout) convertView.findViewById(R.id.runDistance);
                    LinearLayout llCurrentResult = (LinearLayout) convertView.findViewById(R.id.llCurrentResult);
                    LinearLayout llLeftToGoal = (LinearLayout) convertView.findViewById(R.id.llLeftToGoal);
                    LinearLayout separator1 = (LinearLayout) convertView.findViewById(R.id.separator1);
                    LinearLayout separator7 = (LinearLayout) convertView.findViewById(R.id.separator7);
                    LinearLayout separator8 = (LinearLayout) convertView.findViewById(R.id.separator8);
                    runDistance.setVisibility(View.GONE);
                    llCurrentResult.setVisibility(View.GONE);
                    llLeftToGoal.setVisibility(View.GONE);
                    separator1.setVisibility(View.GONE);
                    separator7.setVisibility(View.GONE);
                    separator8.setVisibility(View.GONE);
                } else {
                    runDistance.setVisibility(View.VISIBLE);
                    separator2.setVisibility(View.VISIBLE);
                    goalResultUnits.setText((int)goalNumber + " " + units);
                }
                if(typeLang.equals("ru")) {
                    goalResultUnits.setText(goal.getDistance() + " метров");
                    taskOfDayUnits.setText("преодолеть дистанцию");
                } else {
                    goalResultUnits.setText(goal.getDistance() + " meters");
                    taskOfDayUnits.setText("bridge the distance");
                }
                currentResultUnits.setText((int)currentNumber + " " + units);
                leftToGoalUnits.setText(String.format("%.1f", leftGoalUnits) + " " + units);
                break;
            case "НАВЫКИ":
                skillsPoints(goal);
                //double leftPointsSkill = (pointsSkillsGoal - pointsSkillsCurrent) / getDifferenceInDays(new Date(), goal.getToDate()
                double left = 5 - ((ElementCorrectionGoal)goal).getCurrentResult();
                double leftUnits = 500 - ((ElementCorrectionGoal)goal).getCurrentResult2();
                if(typeLang.equals("ru")) {
                    units = "уровень";
                    currentResultUnits.setText(((ElementCorrectionGoal) goal).getInitialLevel() + " " + units + " / " + ((ElementCorrectionGoal) goal).getInitialResult() + " очков");
                    goalResultUnits.setText(String.valueOf(5) + " " + units + " / " + "500 очков");
                    taskOfDayUnits.setText("тренировка");
                    leftToGoalUnits.setText(String.format("%.1f", left) + " " + "уровня" + " / " + leftUnits + " очков");
                } else {
                    units = "level";
                    currentResultUnits.setText(((ElementCorrectionGoal) goal).getInitialLevel() + " " + units + " / " + ((ElementCorrectionGoal) goal).getInitialResult() + " points");
                    goalResultUnits.setText(String.valueOf(5) + " " + units + " / " + "500 points");
                    taskOfDayUnits.setText("training");
                    leftToGoalUnits.setText(String.format("%.1f", left) + " " + "level" + " / " + leftUnits + " points");
                }
                currentResultUnits.setText(((ElementCorrectionGoal)goal).getCurrentResult()+ " " + units + " / " + ((ElementCorrectionGoal)goal).getCurrentResult2() + " очков");
                goalResultUnits.setText(String.valueOf(5) + " " + units + " / " + "500 очков");
                taskOfDayUnits.setText("тренировка");
                leftToGoalUnits.setText(String.format("%.1f", left) + " " + "уровня" + " / " + leftUnits + " очков");
                break;
            case "ПОВТОРЕНИЯ":
                if(typeLang.equals("ru")) {
                    units = "повторений";
                } else units = "repeats";
                if(goal.getCurrentResult() == 0) {
                    LinearLayout runDistance = (LinearLayout) convertView.findViewById(R.id.runDistance);
                    LinearLayout llCurrentResult = (LinearLayout) convertView.findViewById(R.id.llCurrentResult);
                    LinearLayout llLeftToGoal = (LinearLayout) convertView.findViewById(R.id.llLeftToGoal);
                    LinearLayout separator1 = (LinearLayout) convertView.findViewById(R.id.separator1);
                    LinearLayout separator7 = (LinearLayout) convertView.findViewById(R.id.separator7);
                    LinearLayout separator8 = (LinearLayout) convertView.findViewById(R.id.separator8);
                    runDistance.setVisibility(View.GONE);
                    llCurrentResult.setVisibility(View.GONE);
                    llLeftToGoal.setVisibility(View.GONE);
                    separator1.setVisibility(View.GONE);
                    separator7.setVisibility(View.GONE);
                    separator8.setVisibility(View.GONE);
                    if(typeLang.equals("ru")) {
                        goalResultUnits.setText(goal.getGoalResult() + " метров");
                        taskOfDayUnits.setText("преодолеть дистанцию");
                    } else {
                        goalResultUnits.setText(goal.getGoalResult() + " meters");
                        taskOfDayUnits.setText("bridge the distance");
                    }
                } else {
                    goalResultUnits.setText((int) goalNumber + " " + units);
                    taskOfDayUnits.setText(String.format("%.1f", dayTask) + " " + units);
                }
                currentResultUnits.setText((int)currentNumber + " " + units);
                leftToGoalUnits.setText(String.format("%.1f", leftGoalUnits) + " " + units);
                break;
            case "КНИГА":
                if(goal.getDataBook().equals(" ") || goal.getDataBook().equals("")) {
                    bookPresent.setVisibility(View.GONE);
                    separator1.setVisibility(View.GONE);
                } else {
                    bookPresent.setVisibility(View.VISIBLE);
                    separator1.setVisibility(View.VISIBLE);
                }
                if(typeLang.equals("ru")) {
                    units = "страниц";
                } else units = "pages";
                currentResultUnits.setText((int)currentNumber + " " + units);
                goalResultUnits.setText((int)goalNumber + " " + units);
                taskOfDayUnits.setText(String.format("%.1f", dayTask) + " " + units);
                leftToGoalUnits.setText(String.format("%.1f", leftGoalUnits) + " " + units);
                break;
            case "ЯЗЫКИ":
                languageLevelInHours(goal);
                double lvlLangLeftGoal = (lvlLangHoursGoal - lvlLangHoursCurrent) / getDifferenceInDays(new Date(), goal.getToDate());
                if(typeLang.equals("ru")) {
                    units = "часов";
                } else units = "hours";
                currentResultUnits.setText(((LanguageLearningGoal)goal).getCurrentLanguageLevel()+ " / " + ((LanguageLearningGoal)goal).getCurrentResult() + " " + units);
                goalResultUnits.setText(goal.getGoalLanguageLevel()+ " / " + lvlLangHoursGoal + " " + units);
                taskOfDayUnits.setText(String.format("%.1f", lvlLangLeftGoal) + " " + units);
                leftToGoalUnits.setText(String.format("%.1f", (goal.getGoalResult() - goal.getCurrentResult())) + " " + units);
                break;
        }

        /* FOR ALL GOALS */
        if(typeLang.equals("ru")) {
            distanceRunUnits.setText("" + goal.getDistance() + " метров");
        } else distanceRunUnits.setText("" + goal.getDistance() + " meters");
        dataBook.setText(goal.getDataBook());

        verifyStatus(currentStatus, goalStatus); // СТАТУС

        if(goal.getDescriptionGoal().equals("")) {
            if(typeLang.equals("ru")) {
                goalDescription.setText("Ты не проиграл пока не сдался!"); // в том случае если никто не ввел описание
            } else goalDescription.setText("You did not lose until you came!"); // в том случае если никто не ввел описание
        } else {
            goalDescription.setText(goal.getDescriptionGoal() + ""); // ОПИСАНИЕ ЦЕЛИ
        }
        leftDaysGoal.setText(getDifferenceInDays(new Date(), goal.getToDate()) + "");   // СКОЛЬКО ДНЕЙ ОСТАЛОСЬ
        if(typeLang.equals("ru")) {
            fromGoal.setText("ОТ " + fromDate); // ОТ ЧИСЛА
            toGoal.setText("ДО " + toDate); // ДО ЧИСЛА
        } else {
            fromGoal.setText("FROM " + fromDate); // ОТ ЧИСЛА
            toGoal.setText("TO " + toDate); // ДО ЧИСЛА
        }
    }

    private int getDifferenceInDays(Date from, Date to) {
        long milliseconds = to.getTime() - from.getTime();
        return 1 + (int) (milliseconds = Integer.parseInt(String.valueOf(TimeUnit.DAYS.convert(milliseconds, TimeUnit.MILLISECONDS))));
    }

    private void verifyStatus(double currentStatus, double goalStatus) {
        if(currentStatus > goalStatus) {
            statusGoal.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.circle_status_green));
        } else {
            statusGoal.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.circle_status_red));
        }
    }

    private void expandGoal(Boolean isExpanded, View convertView) {
        if (isExpanded){
            arrowDownUp = (ImageView) convertView.findViewById(R.id.arrowDownUp);
            arrowDownUp.setRotation(180);
            arrowDownUp.getResources().getDrawable(R.drawable.arrow_goal);
        }
        else{
            arrowDownUp = (ImageView) convertView.findViewById(R.id.arrowDownUp);
            arrowDownUp.setRotation(0);
            arrowDownUp.getResources().getDrawable(R.drawable.arrow_goal);
        }
    }

    private void blink(final View view){
        new Thread(new Runnable() {
            @Override
            public void run() {
                int timeToBlink = 1000;    //in milissegunds
                try{Thread.sleep(timeToBlink);}catch (Exception e) {}
                view.post(new Runnable() {
                    @Override
                    public void run() {
                        TextView txt = (TextView) view.findViewById(R.id.madeToday);
                        TextView titleTxt = (TextView) view.findViewById(R.id.titleTxt);
                        LinearLayout showPopupDayTask = (LinearLayout) view.findViewById(R.id.showPopupDayTask);
                        if(txt.getVisibility() == View.VISIBLE){
                            txt.setVisibility(View.INVISIBLE);
                            showPopupDayTask.setBackgroundColor(Color.parseColor("#44b648"));
                            titleTxt.setTextColor(Color.WHITE);
                        }else{
                            txt.setVisibility(View.VISIBLE);
                            showPopupDayTask.setBackgroundColor(Color.parseColor("#FFE4E4E4"));
                            titleTxt.setTextColor(Color.BLACK);
                        }
                        blink(view);
                    }
                });
            }
        }).start();
    }

    private void languageLevelInHours(Goal goal) {
        if (goal.getCurrentLanguageLevel().equals(LanguageLevels.A1)) {
            lvlLangHoursCurrent = 70;
        } else if (goal.getCurrentLanguageLevel().equals(LanguageLevels.A2)) {
            lvlLangHoursCurrent = 140;
        } else if (goal.getCurrentLanguageLevel().equals(LanguageLevels.B1)) {
            lvlLangHoursCurrent = 210;
        } else if (goal.getCurrentLanguageLevel().equals(LanguageLevels.B2)) {
            lvlLangHoursCurrent = 280;
        } else if (goal.getCurrentLanguageLevel().equals(LanguageLevels.C1)) {
            lvlLangHoursCurrent = 350;
        } else if (goal.getCurrentLanguageLevel().equals(LanguageLevels.C2)) {
            lvlLangHoursCurrent = 420;
        }

        if (goal.getGoalLanguageLevel().equals(LanguageLevels.A1)) {
            lvlLangHoursGoal = 70;
        } else if (goal.getGoalLanguageLevel().equals(LanguageLevels.A2)) {
            lvlLangHoursGoal = 140;
        } else if (goal.getGoalLanguageLevel().equals(LanguageLevels.B1)) {
            lvlLangHoursGoal = 210;
        } else if (goal.getGoalLanguageLevel().equals(LanguageLevels.B2)) {
            lvlLangHoursGoal = 280;
        } else if (goal.getGoalLanguageLevel().equals(LanguageLevels.C1)) {
            lvlLangHoursGoal = 350;
        } else if (goal.getGoalLanguageLevel().equals(LanguageLevels.C2)) {
            lvlLangHoursGoal = 420;
        }
    }
    private void skillsPoints(Goal goal) {
        if(goal.getCurrentResult() == 1) {
            pointsSkillsCurrent = 100;
        } else if (goal.getCurrentResult() == 2) {
            pointsSkillsCurrent = 200;
        } else if (goal.getCurrentResult() == 3) {
            pointsSkillsCurrent = 300;
        } else if (goal.getCurrentResult() == 4) {
            pointsSkillsCurrent = 400;
        } else if (goal.getCurrentResult() == 5) {
            pointsSkillsCurrent = 500;
        }

        if(goal.getGoalResult() == 1) {
            pointsSkillsGoal = 100;
        } else if (goal.getGoalResult() == 2) {
            pointsSkillsGoal = 200;
        } else if (goal.getGoalResult() == 3) {
            pointsSkillsGoal = 300;
        } else if (goal.getGoalResult() == 4) {
            pointsSkillsGoal = 400;
        } else if (goal.getGoalResult() == 5) {
            pointsSkillsGoal = 500;
        }
    }
}
