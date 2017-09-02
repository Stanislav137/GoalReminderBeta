package com.goalreminderbeta.sa.goalreminderbeta.db;

import com.orm.SugarRecord;

import java.util.Date;

public class Goal extends SugarRecord {

    private int currentWeight;
    private int goalWeight;
    private Date currentDate;
    private Date goalDate;

    public Goal() {
    }

    public Goal(int currentWeight, int goalWeight, Date currentDate, Date goalDate) {
        this.currentWeight = currentWeight;
        this.goalWeight = goalWeight;
        this.currentDate = currentDate;
        this.goalDate = goalDate;
    }

    public int getCurrentWeight() {
        return currentWeight;
    }

    public void setCurrentWeight(int currentWeight) {
        this.currentWeight = currentWeight;
    }

    public int getGoalWeight() {
        return goalWeight;
    }

    public void setGoalWeight(int goalWeight) {
        this.goalWeight = goalWeight;
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

    @Override
    public String toString() {
        return "Goal{" +
                "currentWeight=" + currentWeight +
                ", goalWeight=" + goalWeight +
                ", currentDate=" + currentDate +
                ", goalDate=" + goalDate +
                '}';
    }
}
