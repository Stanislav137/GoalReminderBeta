package com.goalreminderbeta.sa.goalreminderbeta.goals;

import java.util.Date;

public interface Goal {

    boolean delete();
    Long getId();
    double getDifferenceInDays();
    String getNameGoal();
    String getThemeCategory();
    String getDescriptionGoal();
    double getCurrentResult();
    Date getFromDate();
    Date getToDate();
}
