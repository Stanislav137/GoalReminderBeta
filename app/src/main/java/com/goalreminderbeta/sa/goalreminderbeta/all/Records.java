package com.goalreminderbeta.sa.goalreminderbeta.all;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.goalreminderbeta.sa.goalreminderbeta.R;

public class Records extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_records);
    }

    public void openGoals(View view) {
        Intent intent = new Intent(Records.this, StartActivity.class);
        startActivity(intent);
        this.finish();
    }

    public void openOptions(View view) {
        Intent intent = new Intent(Records.this, Options.class);
        startActivity(intent);
        this.finish();
    }
}
