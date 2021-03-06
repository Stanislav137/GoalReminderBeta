package com.goalreminderbeta.sa.goalreminderbeta.all;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.ContextMenu;
import android.view.Display;
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
import android.widget.Toast;

import com.goalreminderbeta.sa.goalreminderbeta.R;
import com.goalreminderbeta.sa.goalreminderbeta.additional.DialogBuilder;
import com.goalreminderbeta.sa.goalreminderbeta.all.science.languages.LanguageLevels;
import com.goalreminderbeta.sa.goalreminderbeta.goals.Goal;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.IllegalFormatException;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.logging.Handler;
import java.util.logging.LogRecord;

import static android.R.attr.cacheColorHint;
import static android.R.attr.password;

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
    private String units="";
    Button completed;
    Dialog congrDialog;
    AlertDialog.Builder adb;

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
        ProgressBar progressBar = (ProgressBar) convertView.findViewById(R.id.circleProgress);
        TextView textCircleProgress = (TextView) convertView.findViewById(R.id.textCircleProgress);

        progressBar.setProgress((int) currentProgress);

        if(currentProgress <= 30) {
            progressBar.getProgressDrawable().setColorFilter(mContext.
                    getResources().getColor(R.color.colorRed), PorterDuff.Mode.SRC_IN);
            textCircleProgress.setTextColor(Color.argb(255,255,0,0));
        }
        if(currentProgress > 30 && currentProgress <= 60) {
            progressBar.getProgressDrawable().setColorFilter(mContext.
                    getResources().getColor(R.color.colorYellow), PorterDuff.Mode.SRC_IN);
            textCircleProgress.setTextColor(Color.argb(255,255,208,0));
        }
        if(currentProgress > 60 && currentProgress <= 100) {
            progressBar.getProgressDrawable().setColorFilter(mContext.
                    getResources().getColor(R.color.colorGreen), PorterDuff.Mode.SRC_IN);
            textCircleProgress.setTextColor(Color.argb(255,66,255,63));
        }
        textCircleProgress.setText((int)currentProgress + "%");
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
        showDataGroup(goal, convertView);
    }

    private void showDataGroup(Goal goal, View convertView) {
        TextView nameGoal = (TextView) convertView.findViewById(R.id.nameGoal);
        nameGoal.setText(goal.getNameGoal());
        TextView themeCategory = (TextView) convertView.findViewById(R.id.themeCategory);
        themeCategory.setText(goal.getThemeCategory());

        Double progress = 0.0;
        double currentProgress = progress;
        checkProgress(currentProgress, convertView);
    }

    @Override
    public View getChildView(final int groupPosition, final int childPosition, final boolean isLastChild,
                             View convertView, final ViewGroup parent) {

        final LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        Long groupPos = Long.parseLong(String.valueOf(groupPosition));
        final Goal goal = allGoalsMap.get(groupPos); //actual goal

        if(goal.getCompleted()) {
            convertView = inflater.inflate(R.layout.child_section_stat, null);
            //convertView.setMinimumHeight(1500);

            findWidgetsChild(convertView);
            fillDataChild(goal, convertView, goal.getThemeCategory());
        } else {
            convertView = inflater.inflate(R.layout.child_section_complete, null);
            //convertView.setMinimumHeight(1500);

            findUxDayGoal(convertView);
            blink(convertView);
            showDataDG(goal, convertView);

            final LinearLayout showPopupDayTask = (LinearLayout) convertView.findViewById(R.id.showPopupDayTask);
            showPopupDayTask.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(mContext);
                    alertDialog.setTitle("НОВЫЙ РЕЗУЛЬТАТ");
                    alertDialog.setMessage("Введите новый прогресс");

                    final EditText input = new EditText(mContext);
                    LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.MATCH_PARENT,
                            LinearLayout.LayoutParams.MATCH_PARENT);
                    input.setLayoutParams(lp);
                    alertDialog.setView(input);

                    alertDialog.setPositiveButton("YES",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    try {
                                        String res = input.getText().toString();
                                        double resInt = Double.parseDouble(res);
                                        String curRes = currentResultDG.getText().toString().substring(0,
                                                currentResultDG.getText().toString().indexOf(' '));
                                        double curResInt = Double.parseDouble(curRes);
                                        if(goal.getThemeCategory().equals("МАССА")||
                                                goal.getThemeCategory().equals("КАРДИО"))
                                        {
                                            double finRes = curResInt - Math.abs(resInt);
                                        goal.setCurrentResult(finRes);
                                        }else if(goal.getThemeCategory().equals("КНИГА")||
                                                goal.getThemeCategory().equals("ПОВТОРЕНИЯ")
                                                ||goal.getThemeCategory().equals("ЯЗЫКИ")||
                                                goal.getThemeCategory().equals("НАВЫКИ")){
                                            double finRes = curResInt + Math.abs(resInt);
                                            goal.setCurrentResult(finRes);
                                        }

                                    }catch (IllegalFormatException e){
                                        e.printStackTrace();
                                    }
                                    notifyDataSetChanged();
                                    dialog.cancel();
                                }
                            });

                    alertDialog.setNegativeButton("NO",
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
                        goal.setCompleted(true);
                        goal.save();
                        adb = new AlertDialog.Builder(mContext);
                        adb.setTitle("Congratulations");
                        adb.setMessage("Go on! You are super");
                        adb.setPositiveButton("Thanks",null);
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
        double madeTodayResult = 0;
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

        switch (goal.getThemeCategory()) {
            case "МАССА":
                TextView titleTxt = (TextView) view.findViewById(R.id.titleTxt);
                TextView titleDG = (TextView) view.findViewById(R.id.titleDG);
                titleTxt.setText("МОЙ ВЕС СОСТАВЛЯЕТ:");
                units = "кг";
                LinearLayout llTaskWeek = (LinearLayout) view.findViewById(R.id.llTaskWeek);
                LinearLayout separator3 = (LinearLayout) view.findViewById(R.id.separator3);
                llTaskWeek.setVisibility(View.VISIBLE);
                separator3.setVisibility(View.VISIBLE);
                taskDG.setText("тренировка");
                taskDG.setTextColor(Color.parseColor("#d23134"));
                taskOfWeekUnits.setText(String.format("%.1f", dayTask * 7) + " " + units);
                titleDG.setTextColor(Color.parseColor("#d23134"));
                currentResultDG.setText(goal.getCurrentResult() + " " + units);
                goalResultDG.setText(goal.getGoalResult() + " " + units);
                break;
            case "КАРДИО":
                units = "сек";
                if(goal.getCurrentResult()==0 && goal.getGoalResult() == 0) { // regular attack
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
                    distanceDG.setText(goal.getDistance() + " метров");
                    taskDG.setText("преодолеть дистанцию");
                    goalResultDG.setText(goal.getDistance() + " " + "метров");
                } else {
                    LinearLayout llDistance = (LinearLayout) view.findViewById(R.id.llDistance);
                    LinearLayout separator = (LinearLayout) view.findViewById(R.id.separator);
                    llDistance.setVisibility(View.VISIBLE);
                    separator.setVisibility(View.VISIBLE);
                    TextView titleTxtCardio = (TextView) view.findViewById(R.id.titleTxt);
                    titleTxtCardio.setText("МОЁ НОВОЕ ВРЕМЯ:");
                    distanceDG.setText(goal.getDistance() + " метров");
                    taskDG.setText("тренировка");
                    currentResultDG.setText(goal.getCurrentResult() + " " + units);
                    goalResultDG.setText(goal.getGoalResult() + " " + units);
                }
                break;
            case "НАВЫКИ":
                TextView titleTxtSkill = (TextView) view.findViewById(R.id.titleTxt);
                titleTxtSkill.setText("ВВЕДИ НОВЫЙ ПРОГРЕСС:");
                skillsPoints(goal);
                units = "очки";
                taskDG.setText("тренировка");
                currentResultDG.setText(goal.getCurrentResult() + " уровень" + " / " + pointsSkillsCurrent + " " + units);
                goalResultDG.setText(goal.getGoalResult() + " уровень" + " / " + pointsSkillsGoal + " " + units);
                break;
            case "ПОВТОРЕНИЯ":
                units = "повторения";
                if(goal.getCurrentResult() == 0) {
                    LinearLayout llCurrentResult = (LinearLayout) view.findViewById(R.id.llCurrentResult);
                    LinearLayout showPopupDayTask = (LinearLayout) view.findViewById(R.id.showPopupDayTask);
                    LinearLayout separator4 = (LinearLayout) view.findViewById(R.id.separator4);
                    LinearLayout separator5 = (LinearLayout) view.findViewById(R.id.separator5);
                    llCurrentResult.setVisibility(View.GONE);
                    separator4.setVisibility(View.GONE);
                    separator5.setVisibility(View.GONE);
                    showPopupDayTask.setVisibility(View.GONE);
                    taskDG.setText("выполнить повторения");
                } else {
                    taskDG.setText(String.format("%.1f", dayTask) + " " + units);
                }
                currentResultDG.setText(goal.getCurrentResult() + " " + units);
                goalResultDG.setText(goal.getGoalResult() + " " + units);
                break;
            case "КНИГА":
                units = "страниц";
                dayTask = Math.ceil(dayTask);
                taskDG.setText(String.format("%.1f", dayTask) + " " + units);
                currentResultDG.setText(goal.getCurrentResult() + " " + units);
                goalResultDG.setText(goal.getGoalResult() + " " + units);
                break;
            case "ЯЗЫКИ":
                languageLevelInHours(goal);
                double lvlDayTask = (lvlLangHoursGoal - lvlLangHoursCurrent) / getDifferenceInDays(new Date(), goal.getToDate());
                units = "часы";
                taskDG.setText(String.format("%.1f", lvlDayTask) + " " + units);
                currentResultDG.setText(goal.getCurrentLanguageLevel() + " / " + lvlLangHoursCurrent + " часов");
                goalResultDG.setText(goal.getGoalLanguageLevel() + " / " + lvlLangHoursGoal + " часов");
                break;
        }

        /* FOR ALL GOALS */
        madeToday.setText(String.format("%.1f", madeTodayResult) + " " + units);
        if(goal.getDescriptionGoal().equals("")) {
            goalDescriptionDG.setText("ТЫ НЕ ПРОИГРАЛ ПОКА НЕ СДАЛСЯ !"); // default string
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

        switch (themeCategory){
            case "МАССА":
                LinearLayout llTaskWeek = (LinearLayout) convertView.findViewById(R.id.llTaskWeek);
                LinearLayout separator3 = (LinearLayout) convertView.findViewById(R.id.separator3);
                llTaskWeek.setVisibility(View.VISIBLE);
                separator3.setVisibility(View.VISIBLE);
                units = "кг";
                DecimalFormat precision = new DecimalFormat("0.0");
                currentResultUnits.setText(precision.format(currentNumber) + " " + units);
                goalResultUnits.setText(precision.format(goalNumber) + " " + units);
                leftToGoalUnits.setText(String.format("%.1f", leftGoalUnits) + " " + units);
                taskOfWeekUnits.setText(String.format("%.1f", dayTask * 7) + "");
                taskOfDayUnits.setText("тренировка");
                goalResultUnits.setText((int)goalNumber + " " + units);
                taskOfDayUnits.setText(String.format("%.1f", dayTask) + " " + units);
                currentResultUnits.setText((int)currentNumber + " " + units);
                break;
            case "КАРДИО":
                units = "сек";
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
                    goalResultUnits.setText(goal.getDistance() + " метров");
                    taskOfDayUnits.setText("преодолеть дистанцию");
                } else {
                    runDistance.setVisibility(View.VISIBLE);
                    separator2.setVisibility(View.VISIBLE);
                    goalResultUnits.setText((int)goalNumber + " " + units);
                    taskOfDayUnits.setText(String.format("%.1f", dayTask) + " " + units);
                }
                currentResultUnits.setText((int)currentNumber + " " + units);
                leftToGoalUnits.setText(String.format("%.1f", leftGoalUnits) + " " + units);
                break;
            case "НАВЫКИ":
                skillsPoints(goal);
                units = "уровень";
                double leftPointsSkill = (pointsSkillsGoal - pointsSkillsCurrent) / getDifferenceInDays(new Date(), goal.getToDate());
                currentResultUnits.setText(goal.getCurrentResult() + " " + units + " / " + pointsSkillsCurrent + " очков");
                goalResultUnits.setText((int)goalNumber + " " + units + " / " + pointsSkillsGoal + " очков");
                taskOfDayUnits.setText("тренировка");
                leftToGoalUnits.setText(String.format("%.1f", leftGoalUnits) + " " + "уровня" + " / " + (pointsSkillsGoal - pointsSkillsCurrent) + " очков");
                break;
            case "ПОВТОРЕНИЯ":
                units = "повторений";
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
                    goalResultUnits.setText(goal.getGoalResult() + " метров");
                    taskOfDayUnits.setText("преодолеть дистанцию");
                } else {
                    goalResultUnits.setText((int) goalNumber + " " + units);
                    taskOfDayUnits.setText(String.format("%.1f", dayTask) + " " + units);
                }
                currentResultUnits.setText((int)currentNumber + " " + units);
                leftToGoalUnits.setText(String.format("%.1f", leftGoalUnits) + " " + units);
                break;
            case "КНИГА":
                if(goal.getDataBook().equals(" ")) {
                    bookPresent.setVisibility(View.GONE);
                    separator1.setVisibility(View.GONE);
                } else {
                    bookPresent.setVisibility(View.VISIBLE);
                    separator1.setVisibility(View.VISIBLE);
                }
                units = "страниц";
                currentResultUnits.setText((int)currentNumber + " " + units);
                goalResultUnits.setText((int)goalNumber + " " + units);
                taskOfDayUnits.setText(String.format("%.1f", dayTask) + " " + units);
                leftToGoalUnits.setText(String.format("%.1f", leftGoalUnits) + " " + units);
                break;
            case "ЯЗЫКИ":
                languageLevelInHours(goal);
                double lvlLangLeftGoal = (lvlLangHoursGoal - lvlLangHoursCurrent) / getDifferenceInDays(new Date(), goal.getToDate());
                units = "часов";
                currentResultUnits.setText(goal.getCurrentLanguageLevel()+ " / " + lvlLangHoursCurrent + " " + units);
                goalResultUnits.setText(goal.getGoalLanguageLevel()+ " / " + lvlLangHoursGoal + " " + units);
                taskOfDayUnits.setText(String.format("%.1f", lvlLangLeftGoal) + " " + units);
                leftToGoalUnits.setText(String.format("%.1f", lvlLangHoursGoal - lvlLangHoursCurrent) + " " + units);
                break;
        }

        /* FOR ALL GOALS */
        distanceRunUnits.setText("" + goal.getDistance() + " метров");
        dataBook.setText(goal.getDataBook());

        verifyStatus(currentStatus, goalStatus); // СТАТУС

        if(goal.getDescriptionGoal().equals("")) {
            goalDescription.setText("Ты не проиграл пока не сдался!"); // в том случае если никто не ввел описание
        } else {
            goalDescription.setText(goal.getDescriptionGoal() + ""); // ОПИСАНИЕ ЦЕЛИ
        }
        leftDaysGoal.setText(getDifferenceInDays(new Date(), goal.getToDate()) + "");   // СКОЛЬКО ДНЕЙ ОСТАЛОСЬ
        fromGoal.setText("ОТ " + fromDate); // ОТ ЧИСЛА
        toGoal.setText("ДО " + toDate); // ДО ЧИСЛА
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
