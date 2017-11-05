package com.goalreminderbeta.sa.goalreminderbeta.goals;

import com.goalreminderbeta.sa.goalreminderbeta.all.science.languages.LanguageLevels;
import com.orm.SugarRecord;

import java.util.Date;
import java.util.concurrent.TimeUnit;


public class WeightCorrectionGoal extends SugarRecord implements Goal {

    private double currentResult;
    private double goalResult;
    private double currentResultStatus;
    private Date fromDate;
    private Date toDate;
    private String nameGoal, descriptionGoal;
    private String themeCategory;

    public WeightCorrectionGoal() {
    }

    public WeightCorrectionGoal(double currentWeight, double goalWeight, Date fromDate, Date toDate, String nameGoal, String descriptionGoal) {
        this.descriptionGoal = descriptionGoal;
        this.currentResult = currentWeight;
        this.goalResult = goalWeight;
        this.fromDate = fromDate;
        this.toDate = toDate;
        this.nameGoal = nameGoal;
        this.themeCategory = "МАССА";
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

    public double getGoalResult() {
        return goalResult;
    }

    public void setGoalResult(double goalWeight) {
        this.goalResult = goalWeight;
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

    @Override
    public int getDistance() {
        return 0;
    }

    @Override
    public LanguageLevels getCurrentLanguageLevel() {
        return null;
    }

    @Override
    public LanguageLevels getGoalLanguageLevel() {
        return null;
    }

    public void setToDate(Date goalDate) {
        this.toDate = goalDate;
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
                ", goalResult=" + goalResult +
                ", fromDate=" + fromDate +
                ", toDate=" + toDate +
                ", nameGoal='" + nameGoal + '\'' +
                ", descriptionGoal='" + descriptionGoal + '\'' +
                ", themeCategory='" + themeCategory + '\'' +
                '}';
    }
}
