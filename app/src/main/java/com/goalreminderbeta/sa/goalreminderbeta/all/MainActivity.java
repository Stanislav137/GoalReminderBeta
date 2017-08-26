package com.goalreminderbeta.sa.goalreminderbeta.all;

import android.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.RelativeLayout;

import com.goalreminderbeta.sa.goalreminderbeta.R;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    FrgmGoals frgmGoals;
    FrgmRecords frgmRecords;
    FrgmOptions frgmOptions;
    FragmentTransaction fTrans;
    RelativeLayout rlGoalsBtn, rlRecordsBtn, rlOptionsBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
       // animCircleAddGoal();

//        frgmGoals = new FrgmGoals(getApplicationContext());
//        frgmRecords = new FrgmRecords();
//        frgmOptions = new FrgmOptions();

        rlGoalsBtn = (RelativeLayout) findViewById(R.id.rlGoalBtn);
        rlRecordsBtn = (RelativeLayout) findViewById(R.id.rlRecordsBtn);
        rlOptionsBtn = (RelativeLayout) findViewById(R.id.rlOptionsBtn);
//        rlGoalsBtn.setOnClickListener(this);
//        rlRecordsBtn.setOnClickListener(this);
//        rlOptionsBtn.setOnClickListener(this);

//        fTrans = getFragmentManager().beginTransaction();
//        fTrans.add(R.id.frgmCont, frgmGoals);
//        fTrans.commit();
    }

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
//    }

    public void animCircleAddGoal() {
        Button animImage = (Button) findViewById(R.id.editCalendarCircle);
        Animation hyperspaceJumpAnimation = AnimationUtils.loadAnimation(this, R.anim.btn_anim);
        animImage.startAnimation(hyperspaceJumpAnimation);
    }
}