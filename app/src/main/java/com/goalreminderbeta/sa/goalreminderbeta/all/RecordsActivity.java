package com.goalreminderbeta.sa.goalreminderbeta.all;

import android.content.Intent;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import com.goalreminderbeta.sa.goalreminderbeta.R;
import com.goalreminderbeta.sa.goalreminderbeta.goals.CardioGoal;
import com.goalreminderbeta.sa.goalreminderbeta.goals.ElementCorrectionGoal;

import com.goalreminderbeta.sa.goalreminderbeta.goals.LanguageLearningGoal;
import com.goalreminderbeta.sa.goalreminderbeta.goals.ReadBookGoal;
import com.goalreminderbeta.sa.goalreminderbeta.goals.RepeatsCorrectionGoal;
import com.goalreminderbeta.sa.goalreminderbeta.goals.WeightCorrectionGoal;

import com.goalreminderbeta.sa.goalreminderbeta.options.ConfigActivity;
import com.goalreminderbeta.sa.goalreminderbeta.options.OptionsActivity;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class RecordsActivity extends AppCompatActivity {
    private List<ReadBookGoal> allReadBookGoals;
    private ReadBookGoal rbGoal1;
    private int rbGoal2;

    private List<WeightCorrectionGoal> allWeightCorrectionGoals;
    private WeightCorrectionGoal wcG1,wcG2;

    private List<CardioGoal> allCardioGoal;
    private CardioGoal cGoal1,cGoal2;

    private List<RepeatsCorrectionGoal> allRepeatsCorrectionGoal;
    private RepeatsCorrectionGoal rcGoal;

    private List<ElementCorrectionGoal> allElementsCorrectionGoal;
    private ElementCorrectionGoal aecGoal;

    private List<LanguageLearningGoal> allLanguageLearningGoal;
    private LanguageLearningGoal llGoal1,llGoal2;

    private TextView maxWeight,minWeight,distance,speed,repeats,pages,sumOfHours,maxHours,duration,count,exercise,skill;

    private GestureDetectorCompat gestureObject;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_records);
        gestureObject = new GestureDetectorCompat(this, new GestureRecords());
        defineLists();
        defineGoals();
        defineElements();
    }

    private void defineLists(){
        allReadBookGoals = ReadBookGoal.listAll(ReadBookGoal.class);
        allWeightCorrectionGoals = WeightCorrectionGoal.listAll(WeightCorrectionGoal.class);
        allCardioGoal = CardioGoal.listAll(CardioGoal.class);
        allRepeatsCorrectionGoal = RepeatsCorrectionGoal.listAll(RepeatsCorrectionGoal.class);
        allElementsCorrectionGoal = ElementCorrectionGoal.listAll(ElementCorrectionGoal.class);
        allLanguageLearningGoal = LanguageLearningGoal.listAll(LanguageLearningGoal.class);
    }

    private void defineGoals(){
        if(allWeightCorrectionGoals!=null&&allWeightCorrectionGoals.size()>0){
            wcG1 = defineWeightGoal1(allWeightCorrectionGoals);
            wcG2 = defineWeightGoal2(allWeightCorrectionGoals);
        }

        if(allReadBookGoals!=null&&allReadBookGoals.size()>0){
            rbGoal1 = defineBookGoals(allReadBookGoals);
            rbGoal2 = defineBookGoals2(allReadBookGoals);
        }
        if(allCardioGoal!=null&&allCardioGoal.size()>0){
            cGoal1 = defineCardioGoal1(allCardioGoal);
            cGoal2 = defineCardioGoal2(allCardioGoal);
        }
        if(allRepeatsCorrectionGoal!=null&&allRepeatsCorrectionGoal.size()>0){
            rcGoal = defineRepeatGoals(allRepeatsCorrectionGoal);
        }
        if (allElementsCorrectionGoal!=null&&allElementsCorrectionGoal.size()>0){
            aecGoal = defineElementGoals(allElementsCorrectionGoal);
        }
        if(allLanguageLearningGoal!=null&&allLanguageLearningGoal.size()>0){
            llGoal1 = defineLanguageGoal1(allLanguageLearningGoal);
            llGoal2 = defineLanguageGoal2(allLanguageLearningGoal);
        }
    }

    private void defineElements(){
        maxWeight = (TextView)findViewById(R.id.maxWeightTV);
        if(wcG1!=null){
            maxWeight.setText(String.valueOf(wcG1.getGoalResult())+" kg");
        }else {maxWeight.setText("kg");}
        minWeight = (TextView)findViewById(R.id.minWeightTV);
        if(wcG2!=null){
            minWeight.setText(String.valueOf(wcG2.getGoalResult())+" kg");
        }else {
            minWeight.setText("kg");
        }

        distance = (TextView)findViewById(R.id.distanceTV);
        if(cGoal1!=null){
            distance.setText(String.valueOf(cGoal1.getDistance())+" m");
        }else {distance.setText("m");}
        speed = (TextView)findViewById(R.id.speedTV);
        if(cGoal2!=null){
            double s = new BigDecimal(cGoal2.getDistance()/cGoal2.getGoalResult()).setScale(2).doubleValue();
            speed.setText(String.valueOf(s)+" m/sec");
        }else {speed.setText(" m/sec");}

        repeats = (TextView)findViewById(R.id.repeatsTV);
        if(rcGoal!=null){
            repeats.setText(String.valueOf(rcGoal.getGoalResult())+" times");
        }else {repeats.setText("times");}
        exercise = (TextView)findViewById(R.id.exerciseTV);
        if(rcGoal!=null){
            exercise.setText(rcGoal.getNameGoal());
        }else {repeats.setText("your repeatGoalName");}

        pages = (TextView)findViewById(R.id.pagesTV);
        if(rbGoal1!=null){
            pages.setText(String.valueOf(rbGoal1.getGoalResult())+" pages");
        }else {pages.setText("pages");}
        count = (TextView)findViewById(R.id.countBookTV);
        {
            count.setText(String.valueOf(rbGoal2)+" books");
        }


        sumOfHours = (TextView)findViewById(R.id.sumOfHoursTV);
        if(llGoal1!=null){
            sumOfHours.setText(String.valueOf(llGoal1.getGoalResult()-llGoal1.getInitialResult())+" hours");
        }else {sumOfHours.setText("hours");}
        maxHours = (TextView)findViewById(R.id.maxHoursTV);
        if(llGoal2!=null){
            double d1 = llGoal2.getGoalResult()-llGoal2.getInitialResult();
            Date today = new Date();
            long d2 = today.getTime() - llGoal2.getFromDate().getTime();
            d2 = Integer.parseInt(String.valueOf(TimeUnit.DAYS.convert(d2,TimeUnit.MILLISECONDS)));
            d2++;
            int d3=(int)(Math.round(d1/d2));
            maxHours.setText(String.valueOf(d3)+" h/day");
        }else {maxHours.setText("h/day");}

        duration = (TextView)findViewById(R.id.durationTV);
        if(aecGoal!=null){
            Date today = new Date();
            long d2 = today.getTime() - aecGoal.getFromDate().getTime();
            d2 = Integer.parseInt(String.valueOf(TimeUnit.DAYS.convert(d2,TimeUnit.MILLISECONDS)));
            d2++;
            duration.setText(String.valueOf(d2)+" days");
        }else { duration.setText("days");}
        skill = (TextView)findViewById(R.id.skillTV);
        if(aecGoal!=null){
            skill.setText(aecGoal.getNameGoal());
        }else {skill.setText("Your skillName");}


    }

    private WeightCorrectionGoal defineWeightGoal1(List<WeightCorrectionGoal> goals){
        WeightCorrectionGoal tempGoal = goals.get(0);
        double result = 0;
        for(WeightCorrectionGoal goal:goals){
            tempGoal = goal;
            if(tempGoal.getProgress()>=100){
                result = tempGoal.getGoalResult();
                break;
            }
        }

        for(WeightCorrectionGoal goal:goals){
            if(goal.getProgress()>=100){
                if(goal.getGoalResult()>result){
                    result=goal.getGoalResult();
                    tempGoal = goal;
                }
            }
        }
        if(tempGoal!=null){
            return tempGoal;
        }
        return null;
    }

    private WeightCorrectionGoal defineWeightGoal2(List<WeightCorrectionGoal> goals){
        WeightCorrectionGoal tempGoal = goals.get(0);
        double result = 0;
        for(WeightCorrectionGoal goal:goals){
            tempGoal = goal;
            if(tempGoal.getProgress()>=100){
                result = tempGoal.getGoalResult();
                break;
            }
        }

        for(WeightCorrectionGoal goal:goals){
            if(goal.getProgress()>=100){
                if(goal.getGoalResult()<result){
                    result=goal.getGoalResult();
                    tempGoal = goal;
                }
            }
        }
        if(tempGoal!=null){
            return tempGoal;
        }
        return null;
    }

    private CardioGoal defineCardioGoal1(List<CardioGoal> goals){
        CardioGoal tempGoal = goals.get(0);
        double result = 0;
        for(CardioGoal goal:goals){
            tempGoal = goal;
            if(tempGoal.getProgress()>=100){
                result = tempGoal.getDistance();
                break;
            }
        }

        for(CardioGoal goal:goals){
            if(goal.getProgress()>=100){
                if(goal.getDistance()>result){
                    result=goal.getDistance();
                    tempGoal = goal;
                }
            }
        }
        if(tempGoal!=null){
            return tempGoal;
        }
        return null;
    }

    private CardioGoal defineCardioGoal2(List<CardioGoal> goals){
        CardioGoal tempGoal = goals.get(0);
        double result = 0;
        for(CardioGoal goal:goals){
            tempGoal = goal;
            if(tempGoal.getProgress()>=100){
                result = new BigDecimal(tempGoal.getDistance()/tempGoal.getGoalResult()).setScale(2).doubleValue();
                break;
            }
        }

        for(CardioGoal goal:goals){
            if(goal.getProgress()>=100){
                double d = new BigDecimal(goal.getDistance()/goal.getGoalResult()).setScale(2).doubleValue();
                if(d>result){
                    result=d;
                    tempGoal = goal;
                }
            }
        }
        if(tempGoal!=null){
            return tempGoal;
        }
        return null;
    }


    private RepeatsCorrectionGoal defineRepeatGoals(List<RepeatsCorrectionGoal> goals){
        RepeatsCorrectionGoal tempGoal = goals.get(0);
        double result = 0;
        for(RepeatsCorrectionGoal goal:goals){
            tempGoal = goal;
            if(tempGoal.getProgress()>=100){
                result = tempGoal.getGoalResult();
                break;
            }
        }

        for(RepeatsCorrectionGoal goal:goals){
            if(goal.getProgress()>=100){
                double d = goal.getGoalResult();
                if(d>result){
                    result=d;
                    tempGoal = goal;
                }
            }
        }
        if(tempGoal!=null){
            return tempGoal;
        }
        return null;
    }
    private ReadBookGoal defineBookGoals(List<ReadBookGoal> goals){
        ReadBookGoal tempGoal=goals.get(0);
        double result = 0;
        for(ReadBookGoal goal:goals){
            tempGoal = goal;
            if(tempGoal.getProgress()>=100){
                result = tempGoal.getGoalResult();
                break;
            }
        }

        for(ReadBookGoal goal:goals){
            if(goal.getProgress()>=100){
                double d = goal.getGoalResult();
                if(d>result){
                    result=d;
                    tempGoal = goal;
                }
            }
        }
        if(tempGoal!=null){
            return tempGoal;
        }
        return null;
    }
    private int defineBookGoals2(List<ReadBookGoal> goals){

        int result = 0;
        for(ReadBookGoal goal:goals){
            if(goal.getProgress()>=100){
                result++;
            }
        }
        return result;
    }

    private LanguageLearningGoal defineLanguageGoal1(List<LanguageLearningGoal> goals){
        LanguageLearningGoal tempGoal = goals.get(0);
        double result = 0;
        for(LanguageLearningGoal goal:goals){
            tempGoal = goal;
            if(tempGoal.getProgress()>=100){
                result = tempGoal.getGoalResult()-tempGoal.getInitialResult();
                break;
            }
        }

        for(LanguageLearningGoal goal:goals){
            if(goal.getProgress()>=100){
                double d = goal.getGoalResult()-goal.getInitialResult();
                if(d>result){
                    result=d;
                    tempGoal = goal;
                }
            }
        }
        if(tempGoal!=null){
            return tempGoal;
        }
        return null;
    }
    private LanguageLearningGoal defineLanguageGoal2(List<LanguageLearningGoal> goals){
        LanguageLearningGoal tempGoal = goals.get(0);
        double result = 0;
        long l=1;
        int r=0;
        for(LanguageLearningGoal goal:goals){
            tempGoal = goal;
            if(tempGoal.getProgress()>=100){
                result = tempGoal.getGoalResult()-tempGoal.getInitialResult();
                l = tempGoal.getFromDate().getTime()-tempGoal.getToDate().getTime();
                l = Integer.parseInt(String.valueOf(TimeUnit.DAYS.convert(l,TimeUnit.MILLISECONDS)));
                l++;
                r=(int)(Math.round(result/l));
                break;
            }
        }

        for(LanguageLearningGoal goal:goals){
            if(goal.getProgress()>=100){
                double result2 = goal.getGoalResult()-goal.getInitialResult();
                long l2 =goal.getFromDate().getTime()-goal.getToDate().getTime();
                l2 = Integer.parseInt(String.valueOf(TimeUnit.DAYS.convert(l,TimeUnit.MILLISECONDS)));
                l2++;
                int r2=(int)(Math.round(result/l));
                if(r2>r){
                    r=r2;
                    tempGoal = goal;
                }
            }
        }
        if(tempGoal!=null){
            return tempGoal;
        }
        return null;
    }
    private ElementCorrectionGoal defineElementGoals(List<ElementCorrectionGoal> goals){
        ElementCorrectionGoal tempGoal = goals.get(0);
        long result=0;

        for(ElementCorrectionGoal goal:goals){
            tempGoal = goal;
            if(tempGoal.getProgress()>=100){
                result = tempGoal.getToDate().getTime()-tempGoal.getFromDate().getTime();
                result = Integer.parseInt(String.valueOf(TimeUnit.DAYS.convert(result,TimeUnit.MILLISECONDS)));
                result++;
                break;
            }
        }

        for(ElementCorrectionGoal goal:goals){
            if(goal.getProgress()>=100){
                long result2 = goal.getToDate().getTime()-goal.getFromDate().getTime();
                result2 = Integer.parseInt(String.valueOf(TimeUnit.DAYS.convert(result2,TimeUnit.MILLISECONDS)));
                result2++;
                if(result2>result){
                    result=result2;
                    tempGoal = goal;
                }
            }
        }
        if(tempGoal!=null){
            return tempGoal;
        }
        return null;
    }

    public void openGoals(View view) {
        Intent intent = new Intent(RecordsActivity.this, StartActivity.class);
        startActivity(intent);
        this.finish();
    }

    public void openOptions(View view) {
        Intent intent = new Intent(RecordsActivity.this, ConfigActivity.class);
        startActivity(intent);
        this.finish();
    }
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        this.gestureObject.onTouchEvent(event);
        return super.onTouchEvent(event);
    }
    class GestureRecords extends GestureDetector.SimpleOnGestureListener{
        @Override
        public boolean onFling(MotionEvent event1, MotionEvent event2, float velocityX, float velocityY) {

            if (event2.getX() > event1.getX()){
                Intent intent = new Intent(RecordsActivity.this, StartActivity.class);
                finish();
                startActivity(intent);
            }
            else
            if (event2.getX() < event1.getX()){

                Intent intent = new Intent(RecordsActivity.this, OptionsActivity.class);
                finish();
                startActivity(intent);
            }

            return true;
        }
    }
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, StartActivity.class);
        startActivity(intent);
        this.finish();
    }
}
