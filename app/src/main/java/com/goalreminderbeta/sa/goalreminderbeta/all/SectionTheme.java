package com.goalreminderbeta.sa.goalreminderbeta.all;

import android.app.FragmentTransaction;
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
    FragmentTransaction fTrans;
    FrgmGoals frgmGoals;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fTrans = getFragmentManager().beginTransaction();
        fTrans.add(R.id.frgmCont, frgmGoals);
        fTrans.commit();
        frgmGoals = new FrgmGoals(getApplicationContext());
    }
}
