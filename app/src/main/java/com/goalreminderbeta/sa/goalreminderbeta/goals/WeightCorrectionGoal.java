package com.goalreminderbeta.sa.goalreminderbeta.goals;

import com.orm.SugarRecord;

import java.util.Date;
import java.util.concurrent.TimeUnit;


public class WeightCorrectionGoal extends SugarRecord implements Goal {

    private double currentResult;
    private double goalWeight;
    private Date fromDate;
    private Date toDate;
    private double differenceInDays;
    private String nameGoal, descriptionGoal;
    private String themeCategory;

    public WeightCorrectionGoal() {
    }

    public WeightCorrectionGoal(double currentWeight, double goalWeight, Date fromDate, Date toDate, String nameGoal, String descriptionGoal) {
        this.descriptionGoal = descriptionGoal;
        this.currentResult = currentWeight;
        this.goalWeight = goalWeight;
        this.fromDate = fromDate;
        this.toDate = toDate;
        this.nameGoal = nameGoal;
        this.themeCategory = "МАССА";
        if (fromDate!=null && toDate!=null && (toDate.getTime() - fromDate.getTime()) > 0){
            long milliseconds = toDate.getTime() - fromDate.getTime();
            this.differenceInDays = (double) TimeUnit.DAYS.convert(milliseconds, TimeUnit.MILLISECONDS);
        }
    }

    public String getDescriptionGoal() {
        return descriptionGoal;
    }

    public void setDescriptionGoal(String descriptionGoal) {
        this.descriptionGoal = descriptionGoal;
    }

    public double getCurrentResult() {
        return currentResult;
    }

    public void setCurrentResult(double currentWeight) {
        this.currentResult = currentWeight;
    }

    public double getGoalWeight() {
        return goalWeight;
    }

    public void setGoalWeight(double goalWeight) {
        this.goalWeight = goalWeight;
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
    public String getThemeCategory() {
        return themeCategory;
    }

    public void setThemeCategory(String themeCategory) {
        this.themeCategory = themeCategory;
    }

    @Override
    public String toString() {
        return "WeightCorrectionGoal{" +
                "currentResult=" + currentResult +
                ", goalWeight=" + goalWeight +
                ", fromDate=" + fromDate +
                ", toDate=" + toDate +
                ", differenceInDays=" + differenceInDays +
                ", nameGoal='" + nameGoal + '\'' +
                ", descriptionGoal='" + descriptionGoal + '\'' +
                ", themeCategory='" + themeCategory + '\'' +
                '}';
    }
}
