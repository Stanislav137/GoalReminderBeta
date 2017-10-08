package com.goalreminderbeta.sa.goalreminderbeta.goals;

import com.orm.SugarRecord;

import java.util.Date;
import java.util.concurrent.TimeUnit;

public class RepeatsCorrectionGoal extends SugarRecord implements Goal{

    private Date fromDate;
    private Date toDate;
    private double currentResult, goalRepeats;
    private double differenceInDays;
    private String nameGoal, descriptionGoal;
    private String themeCategory;

    public RepeatsCorrectionGoal() {
    }

    public RepeatsCorrectionGoal(double currentRepeats, double goalRepeats, Date fromDate, Date toDate, String nameGoal, String descriptionGoal) {
        this.descriptionGoal = descriptionGoal;
        this.currentResult = currentRepeats;
        this.goalRepeats = goalRepeats;
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
    public String getDescriptionGoal() {
        return descriptionGoal;
    }

    public void setDescriptionGoal(String descriptionGoal) {
        this.descriptionGoal = descriptionGoal;
    }

    public double getCurrentResult() {
        return currentResult;
    }

    public void setCurrentResult(int currentRepeats) {
        this.currentResult = currentRepeats;
    }

    public double getGoalRepeats() {
        return goalRepeats;
    }

    public void setGoalRepeats(int goalRepeats) {
        this.goalRepeats = goalRepeats;
    }

    public Date getFromDate() {
        return fromDate;
    }

    public void setFromDate(Date currentDate) {
        this.fromDate = currentDate;
    }

    public Date getToDate() {
        return toDate;
    }

    public void setToDate(Date goalDate) {
        this.toDate = goalDate;
    }

    public double getDifferenceInDays() {
        return differenceInDays;
    }

    public void setDifferenceInDays(double differenceInDays) {
        this.differenceInDays = differenceInDays;
    }

    public String getNameGoal() {
        return nameGoal;
    }

    public void setNameGoal(String nameGoal) {
        this.nameGoal = nameGoal;
    }

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
                ", goalRepeats=" + goalRepeats +
                ", differenceInDays=" + differenceInDays +
                ", nameGoal='" + nameGoal + '\'' +
                ", descriptionGoal='" + descriptionGoal + '\'' +
                ", themeCategory='" + themeCategory + '\'' +
                '}';
    }
}
