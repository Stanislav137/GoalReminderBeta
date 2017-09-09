package com.goalreminderbeta.sa.goalreminderbeta.all;

import android.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
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
    private FrgmGoals frgmGoals;
    private FrgmRecords frgmRecords;
    private FrgmOptions frgmOptions;
    private FragmentTransaction fTrans;
    private RelativeLayout rlGoalsBtn, rlRecordsBtn, rlOptionsBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);

//        frgmGoals = new FrgmGoals(getApplicationContext());
//        frgmRecords = new FrgmRecords();
//        frgmOptions = new FrgmOptions();
//
//        rlGoalsBtn = (RelativeLayout) findViewById(R.id.rlGoalBtn);
//        rlRecordsBtn = (RelativeLayout) findViewById(R.id.rlRecordsBtn);
//        rlOptionsBtn = (RelativeLayout) findViewById(R.id.rlOptionsBtn);
//        rlGoalsBtn.setOnClickListener(this);
//        rlRecordsBtn.setOnClickListener(this);
//        rlOptionsBtn.setOnClickListener(this);
//
//        fTrans = getFragmentManager().beginTransaction();
//        fTrans.add(R.id.frgmCont, frgmGoals);
//        fTrans.commit();
//
//    }
//
//    public void onClick(View v) {
//        fTrans = getFragmentManager().beginTransaction();
//        switch (v.getId()) {
//            case R.id.rlGoalBtn:
//                fTrans.add(R.id.frgmCont, frgmGoals);
//                break;
//            case R.id.rlRecordsBtn:
//                fTrans.replace(R.id.frgmCont, frgmRecords);
//                break;
//            case R.id.rlOptionsBtn:
//                fTrans.replace(R.id.frgmCont, frgmOptions);
//            default:
//                break;
//        }
//        fTrans.commit();
    }
}
