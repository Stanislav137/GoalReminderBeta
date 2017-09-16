package com.goalreminderbeta.sa.goalreminderbeta.all;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.goalreminderbeta.sa.goalreminderbeta.R;

public class Options extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options);
    }

    public void openGoals(View view) {
        Intent intent = new Intent(Options.this, StartActivity.class);
        startActivity(intent);
        this.finish();
    }

    public void openRecords(View view) {
        Intent intent = new Intent(Options.this, Records.class);
        startActivity(intent);
        this.finish();
    }
}
