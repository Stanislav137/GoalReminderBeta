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
    private double initialResult;
    private double currentResult;
    private double goalResult;
    private double madeTodayResult;
    private double progress=0.0;
    private double dayCompletedCardioRepeats;
    private int distance;
    private boolean completed;
    private boolean blink;
    private boolean showDialog;
    private boolean relax;
    private boolean display=true;

    public CardioGoal() {
    }

    public CardioGoal(int distance, double currentRunDistance, double runTime, Date fromDate, Date toDate, String nameGoal, String descriptionGoal) {
        this.distance = distance;
        this.currentResult = currentRunDistance;
        this.initialResult = currentResult;
        this.goalResult = runTime;
        this.descriptionGoal = descriptionGoal;
        this.fromDate = fromDate;
        this.toDate = toDate;
        this.nameGoal = nameGoal;
        this.themeCategory = "КАРДИО";
    }

    public boolean isCompleted() {
        return completed;
    }

    public double getInitialResult() {
        return initialResult;
    }

    @Override
    public double getDayCompletedCardioRepeats() {
        return dayCompletedCardioRepeats;
    }

    @Override
    public double setDayCompletedCardioRepeats(double dayCompletedCardioRepeats) {
        return this.dayCompletedCardioRepeats = dayCompletedCardioRepeats;
    }

    public void setInitialResult(double initialResult) {
        this.initialResult = initialResult;
    }

    @Override
    public Date getToDate() {
        return toDate;
    }

    public void setToDate(Date toDate) {
        this.toDate = toDate;
    }

    public double getProgress() {
        return progress;
    }

    public void setCurrentResult(int currentResult) {
        this.currentResult += currentResult;
    }

    public void setProgress(double progress) {
        this.progress = progress;
    }


    @Override
    public Date getFromDate() {
        return fromDate;
    }

    public void setFromDate(Date fromDate) {
        this.fromDate = fromDate;
    }

    @Override
    public boolean getCompleted() {
        return completed;
    }

    @Override
    public boolean getRelax() {
        return relax;
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
        this.relax = relax;
    }

    @Override
    public void setBlink(boolean blink) {
        this.blink = blink;
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

    public double getCurrentResult() {
        return currentResult;
    }

    public void setCurrentResult(double currentResult) {
        this.currentResult = currentResult;
    }

    @Override
    public void setMadeTodayResult(double madeTodayResult) {
        this.madeTodayResult = madeTodayResult;
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

    public void setDistance(int distance) {
        this.distance = distance;
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
