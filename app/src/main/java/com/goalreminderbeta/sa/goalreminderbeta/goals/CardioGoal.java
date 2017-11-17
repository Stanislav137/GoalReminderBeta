package com.goalreminderbeta.sa.goalreminderbeta.goals;

import com.goalreminderbeta.sa.goalreminderbeta.all.science.languages.LanguageLevels;
import com.orm.SugarRecord;

import java.util.Date;
import java.util.concurrent.TimeUnit;

public class CardioGoal extends SugarRecord implements Goal{

    private Date toDate;
    private Date fromDate;
    private String nameGoal, descriptionGoal;
    private String themeCategory;
    private int currentResult;
    private double goalResult;
    private int distance;

    public CardioGoal() {
    }

    public CardioGoal(int distance, int currentRunDistance, double runTime, Date fromDate, Date toDate, String nameGoal, String descriptionGoal) {
        this.distance = distance;
        this.currentResult = currentRunDistance;
        this.goalResult = runTime;
        this.descriptionGoal = descriptionGoal;
        this.fromDate = fromDate;
        this.toDate = toDate;
        this.nameGoal = nameGoal;
        this.themeCategory = "КАРДИО";
    }

    @Override
    public Date getToDate() {
        return toDate;
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

    public void setCurrentResult(double currentResult) {
        this.currentResult = (int) currentResult;
    }

    @Override
    public double getGoalResult() {
        return goalResult;
    }

    public void setGoalResult(double goalResult) {
        this.goalResult = goalResult;
    }

    @Override
    public int getDistance() {
        return distance;
    }

    @Override
    public String getDataBook() {
        return null;
    }

    @Override
    public LanguageLevels getCurrentLanguageLevel() {
        return null;
    }

    @Override
    public LanguageLevels getGoalLanguageLevel() {
        return null;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    @Override
    public String toString() {
        return "CardioGoal{" +
                "toDate=" + toDate +
                ", fromDate=" + fromDate +
                ", nameGoal='" + nameGoal + '\'' +
                ", descriptionGoal='" + descriptionGoal + '\'' +
                ", themeCategory='" + themeCategory + '\'' +
                ", currentResult=" + currentResult +
                ", goalResult=" + goalResult +
                ", distance=" + distance +
                '}';
    }


}
