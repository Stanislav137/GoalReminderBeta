package com.goalreminderbeta.sa.goalreminderbeta.goals;

import com.orm.SugarRecord;

import java.util.Date;
import java.util.concurrent.TimeUnit;


public class WeightCorrectionGoal extends SugarRecord implements Goal {

    private int currentWeight;
    private int goalWeight;
    private Date currentDate;
    private Date goalDate;
    private int differenceInDays;
    private String nameGoal;
    private String themeCategory;

    public WeightCorrectionGoal() {
    }

    public WeightCorrectionGoal(int currentWeight, int goalWeight, Date currentDate, Date goalDate, String nameGoal, String themeCategory) {
        this.currentWeight = currentWeight;
        this.goalWeight = goalWeight;
        this.currentDate = currentDate;
        this.goalDate = goalDate;
        this.nameGoal = nameGoal;
        this.themeCategory = themeCategory;
        if (currentDate!=null && goalDate!=null && (goalDate.getTime() - currentDate.getTime()) > 0){
            long milliseconds = goalDate.getTime() - currentDate.getTime();
            this.differenceInDays = (int) TimeUnit.DAYS.convert(milliseconds, TimeUnit.MILLISECONDS);
        }
    }

    public String getThemeCategory() {
        return themeCategory;
    }

    public void setThemeCategory(String themeCategory) {
        this.themeCategory = themeCategory;
    }

    @Override
    public String getNameGoal() {
        return nameGoal;
    }

    public void setNameGoal(String nameGoal) {
        this.nameGoal = nameGoal;
    }

    public int getCurrentWeight() {
        return currentWeight;
    }

    public void setCurrentWeight(int currentWeight) {
        this.currentWeight = currentWeight;
    }

    public int getGoalWeight() {
        return goalWeight;
    }

    public void setGoalWeight(int goalWeight) {
        this.goalWeight = goalWeight;
    }

    public Date getCurrentDate() {
        return currentDate;
    }

    public void setCurrentDate(Date currentDate) {
        this.currentDate = currentDate;
    }

    public Date getGoalDate() {
        return goalDate;
    }

    public void setGoalDate(Date goalDate) {
        this.goalDate = goalDate;
    }

    public int getDifferenceInDays() {
        return differenceInDays;
    }

    public void setDifferenceInDays(int differenceInDays) {
        this.differenceInDays = differenceInDays;
    }

    @Override
    public String toString() {
        return "WeightCorrectionGoal{" +
                "currentWeight=" + currentWeight +
                ", goalWeight=" + goalWeight +
                ", currentDate=" + currentDate +
                ", goalDate=" + goalDate +
                ", differenceInDays=" + differenceInDays +
                ", nameGoal='" + nameGoal + '\'' +
                ", themeCategory='" + themeCategory + '\'' +
                '}';
    }
}
