package com.goalreminderbeta.sa.goalreminderbeta.goals;

import com.orm.SugarRecord;

import java.util.Date;
import java.util.concurrent.TimeUnit;

public class RepeatsCorrectionGoal extends SugarRecord implements Goal{

    private Date fromDate;
    private Date toDate;
    private double currentResult, goalResult;
    private double differenceInDays;
    private String nameGoal, descriptionGoal;
    private String themeCategory;

    public RepeatsCorrectionGoal() {
    }

    public RepeatsCorrectionGoal(double currentRepeats, double goalRepeats, Date fromDate, Date toDate, String nameGoal, String descriptionGoal) {
        this.descriptionGoal = descriptionGoal;
        this.currentResult = currentRepeats;
        this.goalResult = goalRepeats;
        this.fromDate = fromDate;
        this.toDate = toDate;
        this.nameGoal = nameGoal;
        this.themeCategory = "ПОВТОРЕНИЯ";
        if (fromDate!=null && toDate!=null && (toDate.getTime() - fromDate.getTime()) > 0){
            long milliseconds = toDate.getTime() - fromDate.getTime();
            this.differenceInDays = (double) TimeUnit.DAYS.convert(milliseconds, TimeUnit.MILLISECONDS);
        }

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

    public void setToDate(Date toDate) {
        this.toDate = toDate;
    }

    @Override
    public double getCurrentResult() {
        return currentResult;
    }

    public void setCurrentResult(double currentResult) {
        this.currentResult = currentResult;
    }

    @Override
    public double getGoalResult() {
        return goalResult;
    }

    public void setGoalResult(double goalResult) {
        this.goalResult = goalResult;
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
    public String toString() {
        return "RepeatsCorrectionGoal{" +
                "fromDate=" + fromDate +
                ", toDate=" + toDate +
                ", currentResult=" + currentResult +
                ", goalResult=" + goalResult +
                ", differenceInDays=" + differenceInDays +
                ", nameGoal='" + nameGoal + '\'' +
                ", descriptionGoal='" + descriptionGoal + '\'' +
                ", themeCategory='" + themeCategory + '\'' +
                '}';
    }
}
