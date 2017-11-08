package com.goalreminderbeta.sa.goalreminderbeta.goals;

import com.goalreminderbeta.sa.goalreminderbeta.all.science.languages.LanguageLevels;

import java.util.Date;

public interface Goal {

    boolean delete();
    Long getId();
    String getNameGoal();
    String getThemeCategory();
    String getDescriptionGoal();
    double getCurrentResult();
    double getGoalResult();
    Date getFromDate();
    Date getToDate();
    int getDistance();
    String getDataBook();
    LanguageLevels getCurrentLanguageLevel();
    LanguageLevels getGoalLanguageLevel();
}
