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
    private int rbGoal2,rbGoal3;

    private List<WeightCorrectionGoal> allWeightCorrectionGoals;
    private WeightCorrectionGoal wcG1,wcG2;

    private List<CardioGoal> allCardioGoal;
    private CardioGoal cGoal1,cGoal2;

    private List<RepeatsCorrectionGoal> allRepeatsCorrectionGoal;
    private RepeatsCorrectionGoal rcGoal;

    private List<ElementCorrectionGoal> allElementsCorrectionGoal;
    private ElementCorrectionGoal aecGoal;

    private List<LanguageLearningGoal> allLanguageLearningGoal;
    private LanguageLearningGoal llGoal2;
    private double llGoal3;

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
            rbGoal3 = defineBookGoals3(allReadBookGoals);
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
            llGoal2 = defineLanguageGoal2(allLanguageLearningGoal);
            llGoal3 = defineLanguageGoal3(allLanguageLearningGoal);
        }
    }

    private void defineElements(){
        String typeLang = getResources().getConfiguration().locale.getLanguage();
        maxWeight = (TextView)findViewById(R.id.maxWeightTV);
        if(wcG1!=null){
            if(typeLang.equals("en") || typeLang.equals("pl")) {
                maxWeight.setText(String.valueOf(String.format("%.1f", wcG1.getGoalResult())) + " kg");
            } else {
                maxWeight.setText(String.valueOf(String.format("%.1f", wcG1.getGoalResult())) + " кг");
            }
        }else {
            if(typeLang.equals("en") || typeLang.equals("pl")) {
                maxWeight.setText("kg");
            } else {
                maxWeight.setText("кг");
            }
        }
        minWeight = (TextView)findViewById(R.id.minWeightTV);
        if(wcG2!=null){
            if(typeLang.equals("en") || typeLang.equals("pl")) {
                minWeight.setText(String.valueOf(String.format("%.1f", wcG2.getGoalResult())) + " kg");
            } else {
                minWeight.setText(String.valueOf(String.format("%.1f", wcG2.getGoalResult())) + " кг");
            }
        }else {
            if(typeLang.equals("en") || typeLang.equals("pl")) {
                minWeight.setText("kg");
            } else {
                minWeight.setText("кг");
            }
        }

        distance = (TextView)findViewById(R.id.distanceTV);
        if(cGoal1!=null){
            if(typeLang.equals("en") || typeLang.equals("pl")) {
                distance.setText(String.valueOf(cGoal1.getDistance()) + " meters");
            } else {
                distance.setText(String.valueOf(cGoal1.getDistance()) + " м.");
            }
        }else {distance.setText("м");}
        speed = (TextView)findViewById(R.id.speedTV);
        if(cGoal2!=null){
            double s = new BigDecimal(cGoal2.getDistance()/cGoal2.getGoalResult()).setScale(2,RoundingMode.UP).doubleValue();
            if(typeLang.equals("en") || typeLang.equals("pl")) {
                speed.setText(String.valueOf(String.format("%.0f", s)) + " m/s");
            } else {
                speed.setText(String.valueOf(String.format("%.0f", s)) + " м/с");
            }
        }else {
            if(typeLang.equals("en") || typeLang.equals("pl")) {
                speed.setText(" m/s");
            } else {
                speed.setText(" м/с");
            }
        }

        repeats = (TextView)findViewById(R.id.repeatsTV);
        if(rcGoal!=null){
            double rcGoalText = rcGoal.getGoalResult();
            if(rcGoalText>=2&&rcGoalText<=4) {
                if(typeLang.equals("en") || typeLang.equals("pl")) {
                    repeats.setText(String.valueOf(String.format("%.0f", rcGoal.getGoalResult())) + " repeats");
                } else {
                    repeats.setText(String.valueOf(String.format("%.0f", rcGoal.getGoalResult())) + " раза");
                }
            }
            else {
                if(typeLang.equals("en") || typeLang.equals("pl")) {
                    repeats.setText(String.valueOf(String.format("%.0f", rcGoal.getGoalResult())) + " repeat");
                } else {
                    repeats.setText(String.valueOf(String.format("%.0f", rcGoal.getGoalResult())) + " раз");
                }
            }
        }else {
            if(typeLang.equals("en") || typeLang.equals("pl")) {
                repeats.setText("repeat");
            } else {
                repeats.setText("раз");
            }
        }
        exercise = (TextView)findViewById(R.id.exerciseTV);
        if(rcGoal!=null){
            exercise.setText(rcGoal.getNameGoal());
        }else {
            repeats.setText("0");
        }

        pages = (TextView)findViewById(R.id.pagesTV);
        if(typeLang.equals("en") || typeLang.equals("pl")) {
            pages.setText(String.valueOf(rbGoal3) + " pages");
        } else {
            pages.setText(String.valueOf(rbGoal3 + " стр."));
        }
        count = (TextView)findViewById(R.id.countBookTV);
        if(rbGoal2==1) {
            if(typeLang.equals("en") || typeLang.equals("pl")) {
                count.setText(rbGoal2 + " books");
            } else {
                count.setText(rbGoal2 + " книга");
            }
        }
        else if(rbGoal2>=2&&rbGoal2<=4) {
            if(typeLang.equals("en") || typeLang.equals("pl")) {
                count.setText(rbGoal2 + " books");
            } else {
                count.setText(rbGoal2 + " книги");
            }
        }
        else {
            if(typeLang.equals("en") || typeLang.equals("pl")) {
                count.setText(rbGoal2 + " book");
            } else {
                count.setText(rbGoal2 + " книг");
            }
        }
        sumOfHours = (TextView)findViewById(R.id.sumOfHoursTV);
        if(typeLang.equals("en") || typeLang.equals("pl")) {
            sumOfHours.setText(String.valueOf(String.format("%.0f", llGoal3)) + " hours");
        } else {
            sumOfHours.setText(String.valueOf(String.format("%.0f", llGoal3)) + " ч.");
        }
        maxHours = (TextView)findViewById(R.id.maxHoursTV);
        if(llGoal2!=null){
            double d1 = llGoal2.getGoalResult()-llGoal2.getInitialResult();
            Date today = new Date();
            long d2 = today.getTime() - llGoal2.getFromDate().getTime();
            d2 = Integer.parseInt(String.valueOf(TimeUnit.DAYS.convert(d2,TimeUnit.MILLISECONDS)));
            d2++;
            int d3=(int)(Math.round(d1/d2));
            if(typeLang.equals("en") || typeLang.equals("pl")) {
                maxHours.setText(String.valueOf(d3) + " h/days");
            } else {
                maxHours.setText(String.valueOf(d3) + " ч/день");
            }
        }else {
            if(typeLang.equals("en") || typeLang.equals("pl")) {
                maxHours.setText("h/days");
            } else {
                maxHours.setText("ч/день");
            }
        }

        duration = (TextView)findViewById(R.id.durationTV);
        if(aecGoal!=null){
            Date today = new Date();
            long d2 = today.getTime() - aecGoal.getFromDate().getTime();
            d2 = Integer.parseInt(String.valueOf(TimeUnit.DAYS.convert(d2,TimeUnit.MILLISECONDS)));
            d2++;
            if(d2==1)duration.setText(String.valueOf(d2));
            else if(d2>=2&&d2<=4)duration.setText(String.valueOf(d2));
            duration.setText(String.valueOf(d2));
        }else {
            duration.setText("0");
        }
        skill = (TextView)findViewById(R.id.skillTV);
        if(aecGoal!=null){
            skill.setText(aecGoal.getNameGoal());
        }else {
            if(typeLang.equals("en") || typeLang.equals("pl")) {
                skill.setText("Skill name");
            } else {
                skill.setText("Название навыка");
            }
        }


    }

    private WeightCorrectionGoal defineWeightGoal1(List<WeightCorrectionGoal> goals){
        WeightCorrectionGoal tempGoal;
        double result = 0;
        if(goals.isEmpty()){
            tempGoal=null;
        }else{
            tempGoal = goals.get(0);
            for(WeightCorrectionGoal goal:goals){
                if(goal.getProgress()>=100){
                    tempGoal = goal;
                    result = goal.getGoalResult();
                    break;
                }else{
                    tempGoal = null;
                }
            }
            if (tempGoal != null) {
                for(WeightCorrectionGoal goal:goals){
                    if(goal.getProgress()>=100){
                        if(goal.getGoalResult()>result){
                            result=goal.getGoalResult();
                            tempGoal = goal;
                        }
                    }
                }
            }
        }

        return tempGoal;
    }

    private WeightCorrectionGoal defineWeightGoal2(List<WeightCorrectionGoal> goals){
        WeightCorrectionGoal tempGoal;
        double result = 0;
        if(goals.isEmpty()){
            tempGoal=null;
        }else{
            tempGoal = goals.get(0);
            for(WeightCorrectionGoal goal:goals){
                if(goal.getProgress()>=100){
                    tempGoal = goal;
                    result = goal.getGoalResult();
                    break;
                }else{
                    tempGoal = null;
                }
            }
            if (tempGoal != null) {
                for(WeightCorrectionGoal goal:goals){
                    if(goal.getProgress()>=100){
                        if(goal.getGoalResult()<result){
                            result=goal.getGoalResult();
                            tempGoal = goal;
                        }
                    }
                }
            }
        }
        return tempGoal;
    }

    private CardioGoal defineCardioGoal1(List<CardioGoal> goals){
        CardioGoal tempGoal;
        double result = 0;
        if(goals.isEmpty()){
            tempGoal=null;
        }else{
            tempGoal = goals.get(0);
            result=tempGoal.getDistance();
            for(CardioGoal goal:goals){
                if(goal.getProgress()>=100){
                    tempGoal = goal;
                    result = goal.getDistance();
                    break;
                }else{
                    tempGoal = null;
                    result=0;
                }
            }
            if (tempGoal != null) {
                for(CardioGoal goal:goals){
                    if(goal.getProgress()>=100){
                        if(goal.getDistance()>result){
                            result=goal.getDistance();
                            tempGoal = goal;
                        }
                    }
                }
            }
        }

        return tempGoal;
    }

    private CardioGoal defineCardioGoal2(List<CardioGoal> goals){
        CardioGoal tempGoal;
        double result = 0;
        if(goals.isEmpty()){
            tempGoal=null;
        }else{
            tempGoal = goals.get(0);
            for(CardioGoal goal:goals){
                if(goal.getProgress()>=100){
                    tempGoal = goal;
                    result = new BigDecimal(tempGoal.getDistance()/tempGoal.getGoalResult()).setScale(2).doubleValue();
                    break;
                }else{
                    tempGoal = null;
                }
            }
        }
            if (tempGoal != null) {
                for(CardioGoal goal:goals){
                    if(goal.getProgress()>=100){
                        double d = new BigDecimal(goal.getDistance()/goal.getGoalResult()).setScale(2).doubleValue();
                        if(d>result){
                            result=d;
                            tempGoal = goal;
                        }
                    }
                }
            }
        return tempGoal;
    }


    private RepeatsCorrectionGoal defineRepeatGoals(List<RepeatsCorrectionGoal> goals){
        RepeatsCorrectionGoal tempGoal;
        double result = 0;
        if(goals.isEmpty()){
            tempGoal=null;
        }else{
            tempGoal = goals.get(0);
            for(RepeatsCorrectionGoal goal:goals){
                if(goal.getProgress()>=100){
                    tempGoal = goal;
                    result = goal.getGoalResult();
                    break;
                }else{
                    tempGoal = null;
                }
            }
            if (tempGoal != null) {
                for(RepeatsCorrectionGoal goal:goals){
                    if(goal.getProgress()>=100){
                        double d = goal.getGoalResult();
                        if(d>result){
                            result=d;
                            tempGoal = goal;
                        }
                    }
                }
            }
        }
        return tempGoal;
    }
    private ReadBookGoal defineBookGoals(List<ReadBookGoal> goals){
        ReadBookGoal tempGoal;
        double result = 0;
        if(goals.isEmpty()){
            tempGoal=null;
        }else{
            tempGoal = goals.get(0);
            for(ReadBookGoal goal:goals){
                if(goal.getProgress()>=100){
                    tempGoal = goal;
                    result = goal.getGoalResult();
                    break;
                }else{
                    tempGoal = null;
                }
            }
            if (tempGoal != null) {
                for(ReadBookGoal goal:goals){
                    if(goal.getProgress()>=100){
                        double d = goal.getGoalResult();
                        if(d>result){
                            result=d;
                            tempGoal = goal;
                        }
                    }
                }
            }
        }
        return tempGoal;
    }

    private int defineBookGoals3(List<ReadBookGoal> goals){
        int result = 0;
        for(ReadBookGoal goal:goals){
            result+=goal.getCurrentResult();
        }

        return result;
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
        LanguageLearningGoal tempGoal;
        double result = 0;
        if(goals.isEmpty()){
            tempGoal=null;
        }else{
            tempGoal = goals.get(0);
            for(LanguageLearningGoal goal:goals){
                if(goal.getProgress()>=100){
                    tempGoal = goal;
                    result = goal.getGoalResult();
                    break;
                }else{
                    tempGoal = null;
                }
            }
            if (tempGoal != null) {
                for(LanguageLearningGoal goal:goals){
                    if(goal.getProgress()>=100){
                        double d = goal.getGoalResult()-goal.getInitialResult();
                        if(d>result){
                            result=d;
                            tempGoal = goal;
                        }
                    }
                }
            }
        }
        return tempGoal;
    }

    private double defineLanguageGoal3(List<LanguageLearningGoal> goals){
        double result = 0;
        for(LanguageLearningGoal goal:goals){
            if(goal.getProgress()>=100){
                result+=goal.getGoalResult()-goal.getInitialResult();
            }
        }

        return result;
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
                double result2 =
                        goal.getGoalResult()-goal.getInitialResult();
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
        ElementCorrectionGoal tempGoal;
        long result = 0;
        if(goals.isEmpty()){
            tempGoal=null;
        }else{
            tempGoal = goals.get(0);
            for(ElementCorrectionGoal goal:goals){
                if(goal.getProgress()>=100){
                    tempGoal = goal;
                    result = tempGoal.getToDate().getTime()-tempGoal.getFromDate().getTime();
                    result = Integer.parseInt(String.valueOf(TimeUnit.DAYS.convert(result,TimeUnit.MILLISECONDS)));
                    result++;
                    break;
                }
            }

            if (tempGoal != null) {
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
            }
        }
        return tempGoal;

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

