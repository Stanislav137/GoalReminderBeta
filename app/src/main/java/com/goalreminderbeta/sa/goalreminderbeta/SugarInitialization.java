package com.goalreminderbeta.sa.goalreminderbeta;

import android.content.res.Configuration;

import com.goalreminderbeta.sa.goalreminderbeta.options.OptionsActivity;
import com.goalreminderbeta.sa.goalreminderbeta.options.OptionsDTO;
import com.orm.SchemaGenerator;
import com.orm.SugarApp;
import com.orm.SugarContext;
import com.orm.SugarDb;

import java.util.List;

public class SugarInitialization extends SugarApp {

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        SugarContext.init(getApplicationContext());
        SchemaGenerator schemaGenerator = new SchemaGenerator(this);
        schemaGenerator.createDatabase(new SugarDb(this).getDB());




        OptionsDTO options = new OptionsDTO();
        options.setId((long) 1);
        options.setSoundConfig(true);
        options.save();
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
