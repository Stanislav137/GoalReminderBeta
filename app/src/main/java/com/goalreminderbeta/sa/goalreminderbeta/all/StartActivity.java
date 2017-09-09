package com.goalreminderbeta.sa.goalreminderbeta.all;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ExpandableListView;

import com.goalreminderbeta.sa.goalreminderbeta.R;
import com.goalreminderbeta.sa.goalreminderbeta.all.sport.AllSubThemesSport;
import com.goalreminderbeta.sa.goalreminderbeta.all.sport.WeightCorrectionActivity;
import com.goalreminderbeta.sa.goalreminderbeta.goals.WeightCorrectionGoal;

import java.util.ArrayList;
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
        setListeners();
        printAllGoals();
    }
    private void setListeners(){
        allGoalsList.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView expandableListView, View view, int i, int i1, long l) {

                WeightCorrectionGoal goal = (WeightCorrectionGoal) expandableListView.getExpandableListAdapter().getChild(i, i1);
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

        ArrayList<ArrayList<WeightCorrectionGoal>> groups = new ArrayList<>();
        List<WeightCorrectionGoal> allGoals = WeightCorrectionGoal.listAll(WeightCorrectionGoal.class);
        //Находим все записи в базе

        for (WeightCorrectionGoal goal : allGoals){ // Итерация по кажной записи в базе и добавления их в експандер
            ArrayList<WeightCorrectionGoal> children = new ArrayList<>();
            children.add(goal);
            groups.add(children);
        }

        ExpListAdapter adapter = new ExpListAdapter(getApplicationContext(), groups);
        allGoalsList.setAdapter(adapter);
    }

    public void openGoalChooser(View view) {
        Intent intent = new Intent(StartActivity.this, AllSubThemesSport.class);
        startActivity(intent);
        this.finish();
    }
}
