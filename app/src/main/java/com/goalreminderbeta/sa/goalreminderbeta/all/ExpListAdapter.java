package com.goalreminderbeta.sa.goalreminderbeta.all;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.goalreminderbeta.sa.goalreminderbeta.R;
import com.goalreminderbeta.sa.goalreminderbeta.goals.Goal;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

public class ExpListAdapter extends BaseExpandableListAdapter {

    private ArrayList<ArrayList<Goal>> mGroups;
    private Context mContext;
    private ImageView arrowDownUp;
    private Map<Long,Goal> allGoalsMap;

    private TextView fromGoal, toGoal, goalDescription, currentResultUnits, goalResultUnits;
    private RelativeLayout bookPresent, runDistance;

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

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView,
                             ViewGroup parent) {

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.section_theme, null);

            Long groupPos = Long.parseLong(String.valueOf(groupPosition));
            Goal goal = allGoalsMap.get(groupPos); //Fckng actual goal
            showDataSection(goal, convertView);
        }

        if (isExpanded){
            //Изменяем что-нибудь, если текущая Group раскрыта
            arrowDownUp = (ImageView) convertView.findViewById(R.id.arrowDownUp);
            arrowDownUp.setRotation(180);
            arrowDownUp.getResources().getDrawable(R.drawable.arrow_goal);
        }
        else{
            //Изменяем что-нибудь, если текущая Group скрыта
            arrowDownUp = (ImageView) convertView.findViewById(R.id.arrowDownUp);
            arrowDownUp.setRotation(0);
            arrowDownUp.getResources().getDrawable(R.drawable.arrow_goal);
        }
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild,
                             View convertView, ViewGroup parent) {

        //If all is fucked up check here !!!!111адын!11!
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.child_section, null);
            convertView.setMinimumHeight(1500);

            Long groupPos = Long.parseLong(String.valueOf(groupPosition));
            Goal goal = allGoalsMap.get(groupPos); //Fckng actual goal
            showDataChild(goal, convertView, goal.getThemeCategory());

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    private void showDataSection(Goal goal, View convertView) {
        TextView nameGoal = (TextView) convertView.findViewById(R.id.nameGoal);
        nameGoal.setText(goal.getNameGoal());
        TextView themeCategory = (TextView) convertView.findViewById(R.id.themeCategory);
        themeCategory.setText(goal.getThemeCategory());

        Double progress = new Double(String.valueOf(goal.getDifferenceInDays()));
        double currentProgress = progress; //sample progress
        checkProgress(currentProgress, convertView);
    }

    private void showDataChild(Goal goal, View convertView, String themeCategory) {

        String currentUnits = "", goalUnits = "";
        double currentNumber = 0, goalNumber = 0;

        findWidgetsChild(convertView);

        switch (themeCategory){
            case "МАССА":
                currentUnits = "кг";
                goalUnits = "кг";
                break;
            case "КАРДИО":
                currentUnits = "сек";
                goalUnits = "сек";
                runDistance.setVisibility(View.VISIBLE);
                break;
            case "НАВЫКИ":
                currentUnits = "уровень";
                goalUnits = "уровень";
                break;
            case "ПОВТОРЕНИЯ":
                currentUnits = "повторений";
                goalUnits = "повторений";
                break;
            case "КНИГА":
                currentUnits = "страниц";
                goalUnits = "страниц";
                bookPresent.setVisibility(View.VISIBLE);
                break;
            case "ЯЗЫКИ":
                currentUnits = "уровень";
                goalUnits = "уровень";
                break;

        }
        currentNumber = goal.getCurrentResult();
        goalNumber = goal.getGoalResult();

        showResultChild(themeCategory, goal, currentUnits, goalUnits, currentNumber, goalNumber);

    }

    private void showResultChild(String themeCategory, Goal goal, String currentUnits, String goalUnits, double currentNumber, double goalNumber) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

        if(themeCategory.equals("МАССА")) {
            DecimalFormat precision = new DecimalFormat("0.0");
            currentResultUnits.setText(precision.format(currentNumber) + " " + currentUnits);
            goalResultUnits.setText(precision.format(goalNumber) + " " + goalUnits);
        } else {
            currentResultUnits.setText((int)currentNumber + " " + currentUnits);
            goalResultUnits.setText((int)goalNumber + " " + goalUnits);
        }

        goalDescription.setText(goal.getDescriptionGoal() + "");

        String fromDate = String.valueOf(formatter.format(goal.getFromDate()));
        String toDate = String.valueOf(formatter.format(goal.getToDate()));

        fromGoal.setText("ОТ " + fromDate);
        toGoal.setText("ДО " + toDate);
    }

    private void findWidgetsChild(View view) {
        fromGoal = (TextView) view.findViewById(R.id.fromGoal);
        toGoal = (TextView) view.findViewById(R.id.toGoal);
        goalDescription = (TextView) view.findViewById(R.id.goalDescription);
        currentResultUnits = (TextView) view.findViewById(R.id.currentResultUnits);
        goalResultUnits = (TextView) view.findViewById(R.id.yourGoalUnits);
        bookPresent = (RelativeLayout) view.findViewById(R.id.bookPresent);
        runDistance = (RelativeLayout) view.findViewById(R.id.runDistance);
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
}
