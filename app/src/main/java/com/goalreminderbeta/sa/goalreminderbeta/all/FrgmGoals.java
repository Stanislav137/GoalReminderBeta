package com.goalreminderbeta.sa.goalreminderbeta.all;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ExpandableListView;

import com.goalreminderbeta.sa.goalreminderbeta.R;
import com.goalreminderbeta.sa.goalreminderbeta.db.SportGoal;

import java.util.ArrayList;
import java.util.Date;


public class FrgmGoals extends Fragment {

    private Context context;

    public FrgmGoals(Context context) {
        this.context = context;
    }

    public FrgmGoals() {

    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.frgm_goals, null);
//        animCircleAddGoal(v);


        ExpandableListView listView = (ExpandableListView)v.findViewById(R.id.exListView);

        ArrayList<ArrayList<SportGoal>> groups = new ArrayList<>();

        ArrayList<SportGoal> children1 = new ArrayList<>();
        ArrayList<SportGoal> children2 = new ArrayList<>();

        SportGoal goal1 = new SportGoal(100,70, new Date(),new Date());
        SportGoal goal2 = new SportGoal(50,70,new Date(),new Date());

        children1.add(goal1);
        groups.add(children1);
        children2.add(goal2);
        groups.add(children2);

        ExpListAdapter adapter = new ExpListAdapter(context, groups);
        listView.setAdapter(adapter);

        return v;
    }
//    public void animCircleAddGoal(View v) {
//        Button animImage = (Button) v.findViewById(R.id.myGoal);
//        Animation hyperspaceJumpAnimation = AnimationUtils.loadAnimation(this, R.anim.btn_anim); //TODO не работает анимация
//        animImage.startAnimation(hyperspaceJumpAnimation);
//    }

//    public void showAddGoal(boolean isExpanded) {
//
//        if(isExpanded) {
//            Button myGoal = (Button) getView().findViewById(R.id.myGoal);
//            myGoal.setVisibility(View.INVISIBLE);
//        }
//    }
}
