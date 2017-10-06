package com.goalreminderbeta.sa.goalreminderbeta.goals;

public interface Goal {

    boolean delete();
    Long getId();
    double getDifferenceInDays();
    String getNameGoal();
    String getThemeCategory();
    String getDescriptionGoal();
}
