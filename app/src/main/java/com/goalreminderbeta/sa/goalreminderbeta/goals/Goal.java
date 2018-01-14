package com.goalreminderbeta.sa.goalreminderbeta.goals;

import com.goalreminderbeta.sa.goalreminderbeta.all.science.languages.LanguageLevels;
import com.orm.SugarRecord;

import java.util.Date;

public interface Goal {

    boolean delete();
    Long getId();
    void setCompleted(boolean completed);
    void setRelax(boolean relax);
    void setBlink(boolean blink);
    boolean getCompleted();
    boolean getRelax();
    boolean getBlink();
    String getNameGoal();
    String getThemeCategory();
    String getDescriptionGoal();
    void setCurrentResult(double currentResult);
    void setMadeTodayResult(double madeTodayResult);
    double getCurrentResult();
    double getGoalResult();
    Date getFromDate();
    Date getToDate();
    int getDistance();
    String getDataBook();
    double getMadeTodayResult();
    LanguageLevels getCurrentLanguageLevel();
    LanguageLevels getGoalLanguageLevel();
    long save();
    double getProgress();
    void setProgress(double progress);
    double getInitialResult();
    double getDayCompletedCardioRepeats();
    double setDayCompletedCardioRepeats(double dayCompletedCardioRepeats);
    void setInitialResult(double initialResult);
    boolean getDialog();
    void setDialog(boolean showDialog);
    boolean getDisplay();
    void setDisplay(boolean display);
}
