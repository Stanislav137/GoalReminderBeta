package com.goalreminderbeta.sa.goalreminderbeta.all;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutCompat;
import android.view.LayoutInflater;
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
import com.goalreminderbeta.sa.goalreminderbeta.additional.DialogFactory;
import com.goalreminderbeta.sa.goalreminderbeta.goals.Goal;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class ExpListAdapter extends BaseExpandableListAdapter {

    private ArrayList<ArrayList<Goal>> mGroups;
    private Context mContext;
    private ImageView arrowDownUp, statusGoal;
    private Map<Long,Goal> allGoalsMap;
    private TextView fromGoal, toGoal, goalDescription, currentResultUnits, goalResultUnits, distanceRunUnits, leftDaysGoal;
    private TextView leftToGoalUnits, dataBook, taskOfDayUnits;
    private RelativeLayout bookPresent, runDistance;
    private boolean checkComplete = false;

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
        runDistance = (RelativeLayout) view.findViewById(R.id.runDistance);
        leftDaysGoal = (TextView) view.findViewById(R.id.leftDaysGoal);
        leftToGoalUnits = (TextView) view.findViewById(R.id.leftToGoalUnits);
        dataBook = (TextView) view.findViewById(R.id.dataBook);
        taskOfDayUnits = (TextView) view.findViewById(R.id.taskOfDayUnits);
        statusGoal = (ImageView) view.findViewById(R.id.statusGoal);
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

        Double progress = new Double(getDifferenceInDays(goal.getFromDate(), goal.getToDate()));
        double currentProgress = progress;
        checkProgress(currentProgress, convertView);
    }

    @Override
    public View getChildView(final int groupPosition, final int childPosition, final boolean isLastChild,
                             View convertView, final ViewGroup parent) {

        final LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if(checkComplete) {
            convertView = inflater.inflate(R.layout.child_section, null);
            convertView.setMinimumHeight(1500);

            Long groupPos = Long.parseLong(String.valueOf(groupPosition));
            Goal goal = allGoalsMap.get(groupPos); //actual goal
            fillDataChild(goal, convertView, goal.getThemeCategory());
        } else {
            convertView = inflater.inflate(R.layout.child_section2, null);
            convertView.setMinimumHeight(1500);

            findUxDayGoal(convertView);
            showDataDG(groupPosition, convertView);

            final LinearLayout showPopupDayTask = (LinearLayout) convertView.findViewById(R.id.showPopupDayTask);
            showPopupDayTask.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    final LinearLayoutCompat.LayoutParams lparams = new LinearLayoutCompat.LayoutParams(LinearLayoutCompat.LayoutParams.FILL_PARENT, LinearLayoutCompat.LayoutParams.FILL_PARENT);
//                    final EditText edittext = new EditText(mContext.getApplicationContext());
//                    edittext.setLayoutParams(lparams);
//                    edittext.setWidth(32);
//                    edittext.setEms(50);
                }
            });

            final Button completed = (Button) convertView.findViewById(R.id.completed);
            completed.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    checkComplete = true;
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
    }

    private void showDataDG(int groupPosition, View view) {
        Long groupPos = Long.parseLong(String.valueOf(groupPosition));
        Goal goal = allGoalsMap.get(groupPos); //actual goal
        String dataBookDG;
        double dayTask = (goal.getGoalResult() - goal.getCurrentResult()) / getDifferenceInDays(new Date(), goal.getToDate());
        double madeTodayResult = 100;
        if(goal.getDataBook() == null) {
            dataBookDG = "";
        } else {
            dataBookDG = " | " + goal.getDataBook();
        }
        if(!goal.getThemeCategory().equals("КАРДИО")) {
            LinearLayout llDistance = (LinearLayout) view.findViewById(R.id.llDistance);
            LinearLayout separator = (LinearLayout) view.findViewById(R.id.separator);
            llDistance.setVisibility(View.GONE);
            separator.setVisibility(View.GONE);
        } else {
            distanceDG.setText(goal.getDistance() + " метров");
        }
        nameData.setText(goal.getNameGoal() +  dataBookDG);
        currentResultDG.setText(goal.getCurrentResult() + "");
        goalResultDG.setText(goal.getGoalResult() + "");
        taskDG.setText(String.format("%.1f", dayTask) + "");
        madeToday.setText(String.format("%.1f", madeTodayResult) + "");
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

        findWidgetsChild(convertView);

        switch (themeCategory){
            case "МАССА":
                units = "кг";
                break;
            case "КАРДИО":
                units = "сек";
                runDistance.setVisibility(View.VISIBLE);
                break;
            case "НАВЫКИ":
                units = "уровень";
                break;
            case "ПОВТОРЕНИЯ":
                units = "повторений";
                break;
            case "КНИГА":
                if(goal.getDataBook().equals("")) {
                    bookPresent.setVisibility(View.GONE);
                } else {
                    bookPresent.setVisibility(View.VISIBLE);
                }
                units = "страниц";
                break;
            case "ЯЗЫКИ":
                units = "часов";
                break;

        }
        currentNumber = goal.getCurrentResult();
        goalNumber = goal.getGoalResult();

        showResultChild(themeCategory, goal, units, currentNumber, goalNumber);
    }

    private void showResultChild(String themeCategory, Goal goal, String units, double currentNumber, double goalNumber) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        String fromDate = String.valueOf(formatter.format(goal.getFromDate()));
        String toDate = String.valueOf(formatter.format(goal.getToDate()));
        double leftGoalUnits = goal.getGoalResult() - goal.getCurrentResult();
        double dayTask = (goal.getGoalResult() - goal.getCurrentResult()) / getDifferenceInDays(new Date(), goal.getToDate());
        double currentStatus = leftGoalUnits / 100;
        double goalStatus = currentStatus * 50;

        /* ПОКАЗЫВАЕМ ТЕКУЩЕЕ КОЛ-ВО И ВАШУ ЦЕЛЬ */

        if(themeCategory.equals("МАССА")) {
            DecimalFormat precision = new DecimalFormat("0.0");
            currentResultUnits.setText(precision.format(currentNumber) + " " + units);
            goalResultUnits.setText(precision.format(goalNumber) + " " + units);
        } else if(themeCategory.equals("ЯЗЫКИ")) {
            currentResultUnits.setText(goal.getCurrentLanguageLevel() + "");
            goalResultUnits.setText(goal.getGoalLanguageLevel() + "");
        } else {
            currentResultUnits.setText((int)currentNumber + " " + units);
            goalResultUnits.setText((int)goalNumber + " " + units);
            distanceRunUnits.setText("" + goal.getDistance() + " метров");
            dataBook.setText(goal.getDataBook());
        }

        /* FOR ALL GOALS */

        verifyStatus(currentStatus, goalStatus); // СТАТУС

        if(goal.getDescriptionGoal().equals("")) {
            goalDescription.setText("Ты не проиграл пока не сдался!"); // в том случае если никто не ввел описание
        } else {
            goalDescription.setText(goal.getDescriptionGoal() + ""); // ОПИСАНИЕ ЦЕЛИ
        }
        if(themeCategory.equals("НАВЫКИ")) {
            leftToGoalUnits.setText(String.format("%.1f", leftGoalUnits) + " очков");   // СКОЛЬКО UNITS ОСТАЛОСЬ
            taskOfDayUnits.setText(String.format("%.1f", dayTask) + " очков");            // ЗАДАЧА В ДЕНЬ
        } else {
            leftToGoalUnits.setText(String.format("%.1f", leftGoalUnits) + " " + units);
            taskOfDayUnits.setText(String.format("%.1f", dayTask) + " " + units);
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


}
