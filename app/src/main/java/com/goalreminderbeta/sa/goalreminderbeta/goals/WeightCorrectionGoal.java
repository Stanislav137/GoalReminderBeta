package com.goalreminderbeta.sa.goalreminderbeta.goals;

import com.goalreminderbeta.sa.goalreminderbeta.all.science.languages.LanguageLevels;
import com.orm.SugarRecord;

import java.util.Date;

public class WeightCorrectionGoal extends SugarRecord implements Goal {

    private double currentResult;
    private double goalResult;
    private double currentResultStatus, madeTodayResult;
    private Date fromDate;
    private Date toDate;
    private double initialResult;
    private String nameGoal, descriptionGoal;
    private String themeCategory;
    private boolean completed;
    private boolean blink;
    private double progress=0.0;
    private boolean showDialog;
    private boolean display=true;

    public WeightCorrectionGoal() {
    }


    public boolean isCompleted() {
        return completed;
    }

    public WeightCorrectionGoal(double currentWeight, double goalWeight, Date fromDate, Date toDate, String nameGoal, String descriptionGoal) {
        this.descriptionGoal = descriptionGoal;
        this.currentResult = currentWeight;
        this.initialResult = currentWeight;
        this.goalResult = goalWeight;
        this.fromDate = fromDate;
        this.toDate = toDate;
        this.nameGoal = nameGoal;
        this.themeCategory = "МАССА";
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

    public double getCurrentResultStatus() {
        return currentResultStatus;
    }

    public void setCurrentResultStatus(double currentResultStatus) {
        this.currentResultStatus = currentResultStatus;
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

    @Override
    public void setMadeTodayResult(double madeTodayResult) {
        this.madeTodayResult = madeTodayResult;
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
    public boolean getDialog() {
        return showDialog;
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
    public void setDialog(boolean showDialog) {
        this.showDialog=showDialog;
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
