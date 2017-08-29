package com.goalreminderbeta.sa.goalreminderbeta.all;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ExpandableListView;

import com.goalreminderbeta.sa.goalreminderbeta.R;
import com.goalreminderbeta.sa.goalreminderbeta.theme.InitialTheme;

import java.util.ArrayList;

/**
 * Created by stas0 on 16.07.2017.
 */

public class FrgmGoals extends Fragment {

    private Context context;
    private boolean isExpanded;

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

        ArrayList<ArrayList<InitialTheme>> groups = new ArrayList<>();

        ArrayList<InitialTheme> children1 = new ArrayList<>();
        ArrayList<InitialTheme> children2 = new ArrayList<>();

        InitialTheme theme1 = new InitialTheme();
        InitialTheme theme2 = new InitialTheme();

//        theme1.setCategoryName("Them1");
//        theme2.setCategoryName("Them2");

        children1.add(theme1);
        groups.add(children1);
        children2.add(theme2);
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
