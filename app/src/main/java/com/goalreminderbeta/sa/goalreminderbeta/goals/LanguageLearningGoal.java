package com.goalreminderbeta.sa.goalreminderbeta.goals;

import com.goalreminderbeta.sa.goalreminderbeta.all.science.languages.LanguageLevels;
import com.orm.SugarRecord;

import java.util.Date;
import java.util.concurrent.TimeUnit;

public class LanguageLearningGoal extends SugarRecord implements Goal{

    private Date fromDate;
    private Date toDate;
    private String nameGoal, descriptionGoal;
    private String themeCategory;
    private LanguageLevels currentLanguageLevel;
    private LanguageLevels goalLanguageLevel;

    public LanguageLearningGoal() {
    }

    public LanguageLearningGoal(Date fromDate, Date toDate, String nameGoal, String descriptionGoal,
                                LanguageLevels currentLanguageLevel, LanguageLevels goalLanguageLevel) {
        this.fromDate = fromDate;
        this.toDate = toDate;
        this.nameGoal = nameGoal;
        this.descriptionGoal = descriptionGoal;
        this.currentLanguageLevel = currentLanguageLevel;
        this.goalLanguageLevel = goalLanguageLevel;
        this.themeCategory = "ЯЗЫКИ";
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
    public double getCurrentResult() {
        return 0;
    }

    @Override
    public double getGoalResult() {
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
