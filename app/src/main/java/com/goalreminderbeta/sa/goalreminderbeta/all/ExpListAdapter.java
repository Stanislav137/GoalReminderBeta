package com.goalreminderbeta.sa.goalreminderbeta.all;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.support.v4.content.ContextCompat;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.goalreminderbeta.sa.goalreminderbeta.R;
import com.goalreminderbeta.sa.goalreminderbeta.all.science.ReadBookActivity;
import com.goalreminderbeta.sa.goalreminderbeta.goals.Goal;
import com.goalreminderbeta.sa.goalreminderbeta.goals.ReadBookGoal;
import com.goalreminderbeta.sa.goalreminderbeta.goals.WeightCorrectionGoal;

import java.util.ArrayList;
import java.util.Map;

import static java.security.AccessController.getContext;

public class ExpListAdapter extends BaseExpandableListAdapter {

    private ArrayList<ArrayList<Goal>> mGroups;
    private Context mContext;
    private ImageView arrowDownUp;
    private Map<Long,Goal> allGoalsMap;

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
            Integer progress = new Integer(String.valueOf(goal.getId()));
            int currentProgress = progress; //sample progress
            checkProgress(currentProgress, convertView);
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

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.child_section_sport, null);
            convertView.setMinimumHeight(1500);
        }

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    private void checkProgress(int currentProgress, View convertView) {
        ProgressBar progressBar = (ProgressBar) convertView.findViewById(R.id.circleProgress);
        TextView textCircleProgress = (TextView) convertView.findViewById(R.id.textCircleProgress);

        progressBar.setProgress(currentProgress);

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
        textCircleProgress.setText(currentProgress + "%");
    }
}
