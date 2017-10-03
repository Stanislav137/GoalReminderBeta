package com.goalreminderbeta.sa.goalreminderbeta.goals;

import com.orm.SugarRecord;

import java.util.Date;
import java.util.concurrent.TimeUnit;

public class RepeatsCorrectionGoal extends SugarRecord implements Goal{

    private Date currentDate;
    private Date goalDate;
    private int currentRepeats, goalRepeats;
    private int differenceInDays;
    private String nameGoal;
    private String themeCategory;

    public RepeatsCorrectionGoal() {
    }

    public RepeatsCorrectionGoal(int currentRepeats, int goalRepeats, Date currentDate, Date goalDate, String nameGoal, String themeCategory) {
        this.currentRepeats = currentRepeats;
        this.goalRepeats = goalRepeats;
        this.currentDate = currentDate;
        this.goalDate = goalDate;
        this.nameGoal = nameGoal;
        this.themeCategory = themeCategory;if (currentDate!=null && goalDate!=null && (goalDate.getTime() - currentDate.getTime()) > 0){
            long milliseconds = goalDate.getTime() - currentDate.getTime();
            this.differenceInDays = (int) TimeUnit.DAYS.convert(milliseconds, TimeUnit.MILLISECONDS);
        }

    }

    public int getCurrentRepeats() {
        return currentRepeats;
    }

    public void setCurrentRepeats(int currentRepeats) {
        this.currentRepeats = currentRepeats;
    }

    public int getGoalRepeats() {
        return goalRepeats;
    }

    public void setGoalRepeats(int goalRepeats) {
        this.goalRepeats = goalRepeats;
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

    public int getDifferenceInDays() {
        return differenceInDays;
    }

    public void setDifferenceInDays(int differenceInDays) {
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
        return "RepeatsCorrectionGoal{" +
                "currentDate=" + currentDate +
                ", goalDate=" + goalDate +
                ", currentRepeats=" + currentRepeats +
                ", goalRepeats=" + goalRepeats +
                ", differenceInDays=" + differenceInDays +
                ", nameGoal='" + nameGoal + '\'' +
                ", themeCategory='" + themeCategory + '\'' +
                '}';
    }
}
