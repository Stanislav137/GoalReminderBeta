package com.goalreminderbeta.sa.goalreminderbeta;

import android.content.res.Configuration;

import com.goalreminderbeta.sa.goalreminderbeta.db.SportGoal;
import com.orm.SugarApp;
import com.orm.SugarContext;

public class SugarOrmTestApplication extends SugarApp {

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        SugarContext.init(getApplicationContext());
        SportGoal.findById(SportGoal.class, (long) 1);
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
