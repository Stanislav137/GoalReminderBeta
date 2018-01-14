package com.goalreminderbeta.sa.goalreminderbeta.goals;

import com.goalreminderbeta.sa.goalreminderbeta.all.science.languages.LanguageLevels;
import com.orm.SugarRecord;

import java.util.Date;
import java.util.concurrent.TimeUnit;

public class RepeatsCorrectionGoal extends SugarRecord implements Goal{

    private Date fromDate;
    private Date toDate;
    private double currentResult, goalResult;
    private double madeTodayResult;
    private String nameGoal, descriptionGoal;
    private double dayCompletedCardioRepeats;
    private String themeCategory;
    private boolean completed;
    private double initialResult;
    private boolean blink;
    private double progress=0.0;
    private boolean relax;
    private boolean showDialog;
    private boolean display=true;


    public RepeatsCorrectionGoal() {
    }

    public boolean isCompleted() {
        return completed;
    }

    public RepeatsCorrectionGoal(double currentRepeats, double goalRepeats, Date fromDate, Date toDate, String nameGoal, String descriptionGoal) {
        this.descriptionGoal = descriptionGoal;
        this.currentResult = currentRepeats;
        this.initialResult = currentRepeats;
        this.goalResult = goalRepeats;
        this.fromDate = fromDate;
        this.toDate = toDate;
        this.nameGoal = nameGoal;
        this.themeCategory = "ПОВТОРЕНИЯ";
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
        return "RepeatsCorrectionGoal{" +
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
