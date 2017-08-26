package com.goalreminderbeta.sa.goalreminderbeta.all;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.goalreminderbeta.sa.goalreminderbeta.R;
import com.goalreminderbeta.sa.goalreminderbeta.theme.InitialTheme;
import com.goalreminderbeta.sa.goalreminderbeta.theme.childTheme.ChildTheme;

import java.util.ArrayList;

public class SectionTheme extends AppCompatActivity {

    private TextView goalName;
    private TextView categoryName;
    private ImageView image;
    private ProgressBar progressGoal;
    private ChildTheme child;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ExpandableListView listView = (ExpandableListView)findViewById(R.id.exListView);

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

        ExpListAdapter adapter = new ExpListAdapter(getApplicationContext(), groups);
        listView.setAdapter(adapter);

    }
}
