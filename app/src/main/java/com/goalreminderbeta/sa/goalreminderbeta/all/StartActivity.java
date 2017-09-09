package com.goalreminderbeta.sa.goalreminderbeta.all;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ExpandableListView;

import com.goalreminderbeta.sa.goalreminderbeta.R;
import com.goalreminderbeta.sa.goalreminderbeta.db.SportGoal;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class StartActivity extends AppCompatActivity {

    private Button startAddGoal;
    private ExpandableListView allGoalsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start_activity);

        startAddGoal = (Button) findViewById(R.id.startAddGoal);
        allGoalsList = (ExpandableListView) findViewById(R.id.allGoalsList);
        printAllGoals();
    }

    private void printAllGoals(){
        ArrayList<ArrayList<SportGoal>> groups = new ArrayList<>();

        List<SportGoal> allGoals = SportGoal.listAll(SportGoal.class);

        for (SportGoal goal : allGoals){
            ArrayList<SportGoal> children = new ArrayList<>();
            children.add(goal);
            groups.add(children);
        }

        ExpListAdapter adapter = new ExpListAdapter(getApplicationContext(), groups);
        allGoalsList.setAdapter(adapter);
    }

    public void openGoalChooser(View view) {
        Intent intent = new Intent(StartActivity.this, SportThemeActivity.class);
        startActivity(intent);
        this.finish();
    }
}
