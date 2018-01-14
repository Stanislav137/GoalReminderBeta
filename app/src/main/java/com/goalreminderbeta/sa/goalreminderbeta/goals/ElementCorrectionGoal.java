package com.goalreminderbeta.sa.goalreminderbeta.goals;

import com.goalreminderbeta.sa.goalreminderbeta.all.science.languages.LanguageLevels;
import com.orm.SugarRecord;

import java.util.Date;
import java.util.concurrent.TimeUnit;

public class ElementCorrectionGoal extends SugarRecord implements Goal{

    private Date fromDate;
    private Date toDate;
    private double currentResult,initialLevel;
    private double goalResult,goalResult2;
    private String nameGoal, descriptionGoal;
    private double madeTodayResult,currentResult2;
    private double progress=0.0;
    private double initialResult;
    private String themeCategory;
    private boolean completed;
    private boolean blink;
    private boolean showDialog;
    private boolean display=true;

    public ElementCorrectionGoal() {
    }


    public boolean isCompleted() {
        return completed;
    }

    public ElementCorrectionGoal(int levelCurrent, Date fromDate, Date toDate, String nameGoal, String descriptionGoal) {
        this.descriptionGoal = descriptionGoal;
        this.currentResult = levelCurrent;
        this.initialLevel = levelCurrent;
        if(levelCurrent == 1) {
            this.initialResult  = 100;
            this.currentResult2 = 100;
        } else if (levelCurrent == 2) {
            this.initialResult = 200;
            this.currentResult2 = 200;
        } else if (levelCurrent == 3) {
            this.initialResult = 300;
            this.currentResult2 = 300;
        } else if (levelCurrent == 4) {
            this.initialResult = 400;
            this.currentResult2 = 400;
        } else if (levelCurrent == 5) {
            this.initialResult = 500;
            this.currentResult2 = 500;
        }

        this.goalResult = 5;
        this.goalResult2=500;
        this.fromDate = fromDate;
        this.toDate = toDate;
        this.nameGoal = nameGoal;
        this.themeCategory = "НАВЫКИ";
    }

    public double getInitialLevel() {
        return initialLevel;
    }

    public void setInitialLevel(double initialLevel) {
        this.initialLevel = initialLevel;
    }

    public double getGoalResult2() {
        return goalResult2;
    }

    public void setGoalResult2(double goalResult2) {
        this.goalResult2 = goalResult2;
    }

    public double getInitialResult() {
        return initialResult;
    }

    @Override
    public double getDayCompletedCardioRepeats() {
        return 0;
    }

    @Override
    public double setDayCompletedCardioRepeats(double dayCompletedCardioRepeats) {
        return 0;
    }

    public void setInitialResult(double initialResult) {
        this.initialResult = initialResult;
    }

    public double getProgress() {
        return progress;
    }

    public void setProgress(double progress) {
        this.progress = progress;
    }

    @Override
    public boolean getCompleted() {
        return completed;
    }

    @Override
    public boolean getRelax() {
        return false;
    }

    @Override
    public boolean getBlink() {
        return blink;
    }

    @Override
    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    @Override
    public void setRelax(boolean relax) {

    }

    @Override
    public void setBlink(boolean blink) {
        this.blink = blink;
    }

    @Override
    public Date getFromDate() {
        return fromDate;
    }

    public void setFromDate(Date fromDate) {
        this.fromDate = fromDate;
    }

    @Override
    public Date getToDate() {
        return toDate;
    }

    @Override
    public int getDistance() {
        return 0;
    }

    @Override
    public String getDataBook() {
        return null;
    }

    @Override
    public double getMadeTodayResult() {
        return madeTodayResult;
    }

    @Override
    public LanguageLevels getCurrentLanguageLevel() {
        return null;
    }

    @Override
    public LanguageLevels getGoalLanguageLevel() {
        return null;
    }

    public void setToDate(Date toDate) {
        this.toDate = toDate;
    }

    @Override
    public double getCurrentResult() {
        return currentResult;
    }

    public double getCurrentResult2() {
        return currentResult2;
    }

    public void setCurrentResult2(double currentResult2) {
        this.currentResult2 = currentResult2;
        if(currentResult2>=100&&currentResult2<200){
            setCurrentResult(1);
        }else if(currentResult2>=200&&currentResult2<300){
            setCurrentResult(2);
        }else if(currentResult2>=300&&currentResult2<400){
            setCurrentResult(3);
        }else if(currentResult2>=400&&currentResult2<500){
            setCurrentResult(4);
        }else if(currentResult2==500){
            setCurrentResult(5);
        }
    }

    public void setCurrentResult(double currentResult) {
        this.currentResult=currentResult;
    }

    @Override
    public void setMadeTodayResult(double madeTodayResult) {
        //this.currentResult2+=madeTodayResult;
        this.madeTodayResult = madeTodayResult;
        /**/
    }

    @Override
    public double getGoalResult() {
        return goalResult;
    }

    public void setGoalResult(double goalResult) {
        this.goalResult = goalResult;
    }

    @Override
    public String getNameGoal() {
        return nameGoal;
    }

    public void setNameGoal(String nameGoal) {
        this.nameGoal = nameGoal;
    }

    @Override
    public String getDescriptionGoal() {
        return descriptionGoal;
    }

    public void setDescriptionGoal(String descriptionGoal) {
        this.descriptionGoal = descriptionGoal;
    }

    @Override
    public String getThemeCategory() {
        return themeCategory;
    }

    public void setThemeCategory(String themeCategory) {
        this.themeCategory = themeCategory;
    }

    @Override
    public boolean getDialog() {
        return showDialog;
    }

    @Override
    public void setDialog(boolean showDialog) {
        this.showDialog=showDialog;
    }
    @Override
    public boolean getDisplay() {
        return display;
    }

    @Override
    public void setDisplay(boolean display) {
        this.display = display;
    }

    @Override
    public String toString() {
        return "ElementCorrectionGoal{" +
                "fromDate=" + fromDate +
                ", toDate=" + toDate +
                ", currentResult=" + currentResult +
                ", goalResult=" + goalResult +
                ", nameGoal='" + nameGoal + '\'' +
                ", descriptionGoal='" + descriptionGoal + '\'' +
                ", themeCategory='" + themeCategory + '\'' +
                '}';
    }
}
