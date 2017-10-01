package com.goalreminderbeta.sa.goalreminderbeta.goals;

public interface Goal {

    boolean delete();
    Long getId();
    int getDifferenceInDays();
    String getNameGoal();
    String getThemeCategory();
}
