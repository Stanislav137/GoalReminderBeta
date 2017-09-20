package com.goalreminderbeta.sa.goalreminderbeta.all;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.goalreminderbeta.sa.goalreminderbeta.R;
import com.goalreminderbeta.sa.goalreminderbeta.goals.Goal;
import com.goalreminderbeta.sa.goalreminderbeta.goals.ReadBookGoal;
import com.goalreminderbeta.sa.goalreminderbeta.goals.WeightCorrectionGoal;

import java.util.ArrayList;
import java.util.List;

public class StartActivity extends AppCompatActivity {

    private Button startAddGoal;
    private ExpandableListView allGoalsList;
    private List<Goal> allGoals;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start_activity);

        startAddGoal = (Button) findViewById(R.id.startAddGoal);
        allGoalsList = (ExpandableListView) findViewById(R.id.allGoalsList);

        allGoals = new ArrayList<>();

        setListeners();
        printAllGoals();
    }
    private void setListeners(){
        allGoalsList.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView expandableListView, View view, int i, int i1, long l) {

                Goal goal = (Goal) expandableListView.getExpandableListAdapter().getChild(i, i1);
                //Получаем объект по нажатию на него внутри групы (тепер можем его удалить либо модифицировать)

                if (goal!=null){ // Если объект нашелся, удаляем по нажатии на него внутри группы
                    goal.delete();
                }
                printAllGoals(); // обновляем наш лейаут после удаления
                return false;
            }
        });
    }

    private void printAllGoals(){

        ArrayList<ArrayList<Goal>> groups = new ArrayList<>();

        List<ReadBookGoal> allReadBookGoals = ReadBookGoal.listAll(ReadBookGoal.class);
        List<WeightCorrectionGoal> allWeightCorrectionGoals = WeightCorrectionGoal.listAll(WeightCorrectionGoal.class);
        //Находим все записи в базе

        for (ReadBookGoal goal : allReadBookGoals){ // Итерация по кажной записи в базе и добавления их в експандер
            ArrayList<Goal> children = new ArrayList<>();
            children.add(goal);
            groups.add(children);
            allGoals.add(goal);
        }
        for (WeightCorrectionGoal goal : allWeightCorrectionGoals  ){ // Итерация по кажной записи в базе и добавления их в експандер
            ArrayList<Goal> children = new ArrayList<>();
            children.add(goal);
            groups.add(children);
            allGoals.add(goal);
        }
        if (allGoals.size() == 0) {
            changeStartBtn();
        }

        ExpListAdapter adapter = new ExpListAdapter(getApplicationContext(), groups);
        allGoalsList.setAdapter(adapter);
    }

    public void openGoalChooser(View view) {
        Intent intent = new Intent(StartActivity.this, AllSectionTheme.class);
        startActivity(intent);
        this.finish();
    }

    public void openRecords(View view) {
        Intent intent = new Intent(StartActivity.this, RecordsActivity.class);
        startActivity(intent);
        this.finish();
    }

    public void openOptions(View view) {
        Intent intent = new Intent(StartActivity.this, OptionsActivity.class);
        startActivity(intent);
        this.finish();
    }

    public void changeStartBtn() {
        RelativeLayout.LayoutParams changeBtnParams = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        DisplayMetrics metrics = this.getResources().getDisplayMetrics();
        int width = metrics.widthPixels;
        initStartGoal(width,changeBtnParams);
    }

    public void initStartGoal(int width, RelativeLayout.LayoutParams changeBtnParams) {
        changeBtnParams.addRule(RelativeLayout.CENTER_HORIZONTAL);
        changeBtnParams.addRule(RelativeLayout.CENTER_VERTICAL);
        changeBtnParams.width = width / 2;
        changeBtnParams.height = width / 2;
        showStartGoal(changeBtnParams);
    }

    public void showStartGoal(RelativeLayout.LayoutParams changeBtnParams) {
        startAddGoal.setLayoutParams(changeBtnParams);
        startAddGoal.setTextSize(20);
    }
}
