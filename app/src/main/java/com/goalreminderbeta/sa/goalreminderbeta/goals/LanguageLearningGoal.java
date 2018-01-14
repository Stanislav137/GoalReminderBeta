package com.goalreminderbeta.sa.goalreminderbeta.goals;

import com.goalreminderbeta.sa.goalreminderbeta.all.science.languages.LanguageLevels;
import com.orm.SugarRecord;

import java.util.Date;

public class LanguageLearningGoal extends SugarRecord implements Goal{

    private Date fromDate;
    private Date toDate;
    private String nameGoal, descriptionGoal;
    private String themeCategory;
    private LanguageLevels currentLanguageLevel;
    private LanguageLevels goalLanguageLevel;
    private boolean completed;
    private double progress=0.0;
    private boolean blink;
    private LanguageLevels initialLevel;
    private double madeTodayResult;
    private double currentResult;
    private double initialResult;
    private double goalResult;
    private boolean showDialog;
    private boolean display=true;

    public LanguageLearningGoal() {
    }

    public LanguageLearningGoal(Date fromDate, Date toDate, String nameGoal, String descriptionGoal,
                                LanguageLevels currentLanguageLevel, LanguageLevels goalLanguageLevel) {
        this.fromDate = fromDate;
        this.toDate = toDate;
        this.initialLevel = currentLanguageLevel;
        this.nameGoal = nameGoal;
        this.descriptionGoal = descriptionGoal;
        this.currentLanguageLevel = currentLanguageLevel;
        this.goalLanguageLevel = goalLanguageLevel;
        this.themeCategory = "ЯЗЫКИ";
        if(currentLanguageLevel.equals(LanguageLevels.A1)){
            currentResult=70;
            initialResult = 70;
        }else if(currentLanguageLevel.equals(LanguageLevels.A2)){
            currentResult=140;
            initialResult = 140;
        }else if(currentLanguageLevel.equals(LanguageLevels.B1)){
            currentResult=210;
            initialResult = 210;
        }else if(currentLanguageLevel.equals(LanguageLevels.B2)){
            currentResult=280;
            initialResult = 280;
        }else if(currentLanguageLevel.equals(LanguageLevels.C1)){
            currentResult=350;
            initialResult = 350;
        }else if(currentLanguageLevel.equals(LanguageLevels.C2)){
            currentResult=420;
            initialResult = 420;
        }else{
            currentResult=0;
            initialResult = 0;
        }

        if(goalLanguageLevel.equals(LanguageLevels.A1)){
            goalResult=70;
        }else if(goalLanguageLevel.equals(LanguageLevels.A2)){
            goalResult = 140;
        }else if(goalLanguageLevel.equals(LanguageLevels.B1)){
            goalResult = 210;
        }else if(goalLanguageLevel.equals(LanguageLevels.B2)){
            goalResult = 280;
        }else if(goalLanguageLevel.equals(LanguageLevels.C1)){
            goalResult = 350;
        }else if(goalLanguageLevel.equals(LanguageLevels.C2)){
            goalResult = 420;
        }else{
            goalResult = 0;
        }
    }

    @Override
    public double getInitialResult() {
        initialResult=70;
        if(this.initialLevel.equals(LanguageLevels.A1)){
            return initialResult;
        }else if(this.initialLevel.equals(LanguageLevels.A2)){
            return initialResult*2;
        }else if(this.initialLevel.equals(LanguageLevels.B1)){
            return initialResult*3;
        }else if(this.initialLevel.equals(LanguageLevels.B2)){
            return initialResult*4;
        }else if(this.initialLevel.equals(LanguageLevels.C1)){
            return initialResult*5;
        }else if(this.initialLevel.equals(LanguageLevels.C2)){
            return initialResult*6;
        }
        return 0;
    }

    @Override
    public double getDayCompletedCardioRepeats() {
        return 0;
    }

    @Override
    public double setDayCompletedCardioRepeats(double dayCompletedCardioRepeats) {
        return 0;
    }

    @Override
    public void setInitialResult(double initialResult) {
        this.initialResult = initialResult;
    }

    public LanguageLevels getInitialLevel() {
        return initialLevel;
    }

    public void setInitialLevel(LanguageLevels initialLevel) {
        this.initialLevel = initialLevel;
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


    public void setToDate(Date toDate) {
        this.toDate = toDate;
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

    @Override
    public void setCurrentResult(double currentResult) {
        this.currentResult += currentResult;
        if(this.currentResult>0&&this.currentResult<=70){
            setCurrentLanguageLevel(LanguageLevels.Begin);
        }else if(this.currentResult>70&&this.currentResult<=140){
            setCurrentLanguageLevel(LanguageLevels.A1);
        }else if(this.currentResult>140&&this.currentResult<=210){
            setCurrentLanguageLevel(LanguageLevels.A2);
        }
        else if(this.currentResult>210&&this.currentResult<=280){
            setCurrentLanguageLevel(LanguageLevels.B1);
        }
        else if(this.currentResult>280&&this.currentResult<=350){
            setCurrentLanguageLevel(LanguageLevels.B2);
        }
        else if(this.currentResult>350&&this.currentResult<=420){
            setCurrentLanguageLevel(LanguageLevels.C1);
        }else{
            setCurrentLanguageLevel(LanguageLevels.C2);
        }

    }

    @Override
    public void setMadeTodayResult(double madeTodayResult) {
        this.madeTodayResult = madeTodayResult;

    }

    @Override
    public double getCurrentResult() {
        return currentResult;
    }

    @Override
    public double getGoalResult() {
        goalResult=70;
        if(this.goalLanguageLevel.equals(LanguageLevels.A1)){
            return goalResult;
        }else if(this.goalLanguageLevel.equals(LanguageLevels.A2)){
            return goalResult*2;
        }else if(this.goalLanguageLevel.equals(LanguageLevels.B1)){
            return goalResult*3;
        }else if(this.goalLanguageLevel.equals(LanguageLevels.B2)){
            return goalResult*4;
        }else if(this.goalLanguageLevel.equals(LanguageLevels.C1)){
            return goalResult*5;
        }else if(this.goalLanguageLevel.equals(LanguageLevels.C2)){
            return goalResult*6;
        }
        return 0;
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

    public LanguageLevels getCurrentLanguageLevel() {
        return currentLanguageLevel;
    }

    public void setCurrentLanguageLevel(LanguageLevels currentLanguageLevel) {
        this.currentLanguageLevel = currentLanguageLevel;
    }

    public LanguageLevels getGoalLanguageLevel() {
        return goalLanguageLevel;
    }

    public void setGoalLanguageLevel(LanguageLevels goalLanguageLevel) {
        this.goalLanguageLevel = goalLanguageLevel;
    }

    @Override
    public boolean getDialog() {
        return showDialog;
    }

    @Override
    public void setDialog(boolean showDialog) {
        this.showDialog = showDialog;
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
        return "LanguageLearningGoal{" +
                "fromDate=" + fromDate +
                ", toDate=" + toDate +
                ", nameGoal='" + nameGoal + '\'' +
                ", descriptionGoal='" + descriptionGoal + '\'' +
                ", themeCategory='" + themeCategory + '\'' +
                ", currentLanguageLevel=" + currentLanguageLevel +
                ", goalLanguageLevel=" + goalLanguageLevel +
                '}';
    }
}
