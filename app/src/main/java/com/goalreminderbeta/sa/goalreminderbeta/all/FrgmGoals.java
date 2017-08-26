package com.goalreminderbeta.sa.goalreminderbeta.all;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import com.goalreminderbeta.sa.goalreminderbeta.R;
import com.goalreminderbeta.sa.goalreminderbeta.theme.InitialTheme;

import java.util.ArrayList;

/**
 * Created by stas0 on 16.07.2017.
 */

public class FrgmGoals extends Fragment {

    private Context context;

    public FrgmGoals(Context context) {
        this.context = context;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.frgm_goals, null);

        ExpandableListView listView = (ExpandableListView)v.findViewById(R.id.exListView);

        ArrayList<ArrayList<InitialTheme>> groups = new ArrayList<>();


        ArrayList<InitialTheme> children1 = new ArrayList<>();
        ArrayList<InitialTheme> children2 = new ArrayList<>();

        InitialTheme theme1 = new InitialTheme();
        InitialTheme theme2 = new InitialTheme();

        theme1.setCategoryName("Them1");
        theme2.setCategoryName("Them2");

        children1.add(theme1);
        groups.add(children1);
        children2.add(theme2);
        groups.add(children2);

        ExpListAdapter adapter = new ExpListAdapter(context, groups);
        listView.setAdapter(adapter);

        return v;
    }
}