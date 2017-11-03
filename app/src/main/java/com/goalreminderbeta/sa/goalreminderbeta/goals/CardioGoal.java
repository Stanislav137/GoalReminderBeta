package com.goalreminderbeta.sa.goalreminderbeta.goals;

import com.orm.SugarRecord;

import java.util.Date;
import java.util.concurrent.TimeUnit;

public class CardioGoal extends SugarRecord implements Goal{

    private Date toDate;
    private Date fromDate;
    private double differenceInDays;
    private String nameGoal, descriptionGoal;
    private String themeCategory;
    private int currentResult;
    private double goalResult;
    private int distance;

    public CardioGoal() {
    }

    public CardioGoal(int distance, int currentRunDistance, double runTime, Date toDate, Date fromDate, String nameGoal, String descriptionGoal) {
        this.distance = distance;
        this.currentResult = currentRunDistance;
        this.goalResult = runTime;
        this.descriptionGoal = descriptionGoal;
        this.fromDate = fromDate;
        this.toDate = toDate;
        this.nameGoal = nameGoal;
        this.themeCategory = "КАРДИО";
        if (fromDate!=null && toDate!=null && (toDate.getTime() - fromDate.getTime()) > 0){
            long milliseconds = toDate.getTime() - fromDate.getTime();
            this.differenceInDays = (double) TimeUnit.DAYS.convert(milliseconds, TimeUnit.MILLISECONDS);
        }

    }

    @Override
    public Date getToDate() {
        return toDate;
    }

    @Override
    public int getDistance() {
        return 0;
    }

    public void setToDate(Date toDate) {
        this.toDate = toDate;
    }

    @Override
    public Date getFromDate() {
        return fromDate;
    }

    public void setFromDate(Date fromDate) {
        this.fromDate = fromDate;
    }

    @Override
    public double getDifferenceInDays() {
        return differenceInDays;
    }

    public void setDifferenceInDays(double differenceInDays) {
        this.differenceInDays = differenceInDays;
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
    public double getCurrentResult() {
        return currentResult;
    }

    public void setCurrentResult(int currentResult) {
        this.currentResult = currentResult;
    }

    @Override
    public double getGoalResult() {
        return goalResult;
    }

    public void setGoalResult(int goalResult) {
        this.goalResult = goalResult;
    }

    @Override
    public String toString() {
        return "CardioGoal{" +
                "toDate=" + toDate +
                ", fromDate=" + fromDate +
                ", differenceInDays=" + differenceInDays +
                ", nameGoal='" + nameGoal + '\'' +
                ", descriptionGoal='" + descriptionGoal + '\'' +
                ", themeCategory='" + themeCategory + '\'' +
                ", currentResult=" + currentResult +
                ", goalResult=" + goalResult +
                '}';
    }
}
