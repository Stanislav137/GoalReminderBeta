package com.goalreminderbeta.sa.goalreminderbeta.goals;

import com.orm.SugarRecord;

import java.util.Date;
import java.util.concurrent.TimeUnit;

public class LanguageCorrectionGoal extends SugarRecord implements Goal{

    private Date fromDate;
    private Date toDate;
    private double differenceInDays;
    private String nameGoal, descriptionGoal;
    private String themeCategory;
    private double currentResult;

    public LanguageCorrectionGoal() {
    }

    public LanguageCorrectionGoal(double currentLevel, Date fromDate, Date toDate, String nameGoal, String descriptionGoal) {
        this.currentResult = currentLevel;
        this.descriptionGoal = descriptionGoal;
        this.fromDate = fromDate;
        this.toDate = toDate;
        this.nameGoal = nameGoal;
        this.themeCategory = "ЯЗЫКИ";
        if (fromDate!=null && toDate!=null && (toDate.getTime() - fromDate.getTime()) > 0){
            long milliseconds = toDate.getTime() - fromDate.getTime();
            this.differenceInDays = (double) TimeUnit.DAYS.convert(milliseconds, TimeUnit.MILLISECONDS);
        }

    }

    @Override
    public double getCurrentResult() {
        return currentResult;
    }

    public void setCurrentResult(double currentResult) {
        this.currentResult = currentResult;
    }

    @Override
    public String getDescriptionGoal() {
        return descriptionGoal;
    }

    public void setDescriptionGoal(String descriptionGoal) {
        this.descriptionGoal = descriptionGoal;
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

    public void setToDate(Date toDate) {
        this.toDate = toDate;
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
        return "LanguageCorrectionGoal{" +
                "fromDate=" + fromDate +
                ", toDate=" + toDate +
                ", differenceInDays=" + differenceInDays +
                ", nameGoal='" + nameGoal + '\'' +
                ", descriptionGoal='" + descriptionGoal + '\'' +
                ", themeCategory='" + themeCategory + '\'' +
                ", currentResult=" + currentResult +
                '}';
    }
}
