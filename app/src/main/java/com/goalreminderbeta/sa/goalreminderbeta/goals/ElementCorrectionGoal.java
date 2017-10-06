package com.goalreminderbeta.sa.goalreminderbeta.goals;

import com.orm.SugarRecord;

import java.util.Date;
import java.util.concurrent.TimeUnit;

public class ElementCorrectionGoal extends SugarRecord implements Goal{

    private Date currentDate;
    private Date goalDate;
    private double differenceInDays;
    private int levelCurrent;
    private String nameGoal, descriptionGoal;
    private String themeCategory;

    public ElementCorrectionGoal() {
    }

    public ElementCorrectionGoal(int levelCurrent, Date currentDate, Date goalDate, String nameGoal, String descriptionGoal, String themeCategory) {
        this.descriptionGoal = descriptionGoal;
        this.levelCurrent = levelCurrent;
        this.currentDate = currentDate;
        this.goalDate = goalDate;
        this.nameGoal = nameGoal;
        this.themeCategory = themeCategory;if (currentDate!=null && goalDate!=null && (goalDate.getTime() - currentDate.getTime()) > 0){
            long milliseconds = goalDate.getTime() - currentDate.getTime();
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

    public int getLevelCurrent() {
        return levelCurrent;
    }

    public void setLevelCurrent(int levelCurrent) {
        this.levelCurrent = levelCurrent;
    }

    public Date getCurrentDate() {
        return currentDate;
    }

    public void setCurrentDate(Date currentDate) {
        this.currentDate = currentDate;
    }

    public Date getGoalDate() {
        return goalDate;
    }

    public void setGoalDate(Date goalDate) {
        this.goalDate = goalDate;
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
        return "ElementCorrectionGoal{" +
                "currentDate=" + currentDate +
                ", goalDate=" + goalDate +
                ", differenceInDays=" + differenceInDays +
                ", levelCurrent=" + levelCurrent +
                ", nameGoal='" + nameGoal + '\'' +
                ", descriptionGoal='" + descriptionGoal + '\'' +
                ", themeCategory='" + themeCategory + '\'' +
                '}';
    }
}
