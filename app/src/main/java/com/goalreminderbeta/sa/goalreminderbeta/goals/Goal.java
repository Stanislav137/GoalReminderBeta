package com.goalreminderbeta.sa.goalreminderbeta.goals;

import com.goalreminderbeta.sa.goalreminderbeta.all.science.languages.LanguageLevels;
import com.orm.SugarRecord;

import java.util.Date;

public interface Goal {

    boolean delete();
    Long getId();
    void setCompleted(boolean completed);
    boolean getCompleted();
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
    long save();
}
