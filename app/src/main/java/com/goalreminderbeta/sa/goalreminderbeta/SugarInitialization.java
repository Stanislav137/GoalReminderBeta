package com.goalreminderbeta.sa.goalreminderbeta;

import android.content.res.Configuration;

import com.goalreminderbeta.sa.goalreminderbeta.goals.LanguageLearningGoal;
import com.goalreminderbeta.sa.goalreminderbeta.goals.ReadBookGoal;
import com.goalreminderbeta.sa.goalreminderbeta.goals.WeightCorrectionGoal;
import com.orm.SugarApp;
import com.orm.SugarContext;

public class SugarInitialization extends SugarApp {

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        SugarContext.init(getApplicationContext());
        WeightCorrectionGoal.findById(WeightCorrectionGoal.class, (long) 1);
        ReadBookGoal.findById(ReadBookGoal.class, (long) 1);
        LanguageLearningGoal.findById(LanguageLearningGoal.class, (long) 1);
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }
}