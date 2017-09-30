package com.goalreminderbeta.sa.goalreminderbeta.all.sport;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.goalreminderbeta.sa.goalreminderbeta.R;
import com.goalreminderbeta.sa.goalreminderbeta.additional.BootStrap;
import com.goalreminderbeta.sa.goalreminderbeta.all.StartActivity;

import java.util.ArrayList;

public class RunCorrectionActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.run_theme);

        findAllBtnsBootStrap(); // using for BootStrap!
    }

    public void backToHome(View view) {
        Intent intent = new Intent(RunCorrectionActivity.this, StartActivity.class);
        startActivity(intent);
        this.finish();
    }

    private void findAllBtnsBootStrap() {
        ArrayList<Button> allBtnsRun = new ArrayList<>();
        Button runDistance = (Button) findViewById(R.id.runDistance);
        Button runTime = (Button) findViewById(R.id.runTime);
        allBtnsRun.add(runDistance);
        allBtnsRun.add(runTime);
        startBootStrap(allBtnsRun);
    }

    private void startBootStrap(ArrayList<Button> allBtnsRun) {
        BootStrap bootStrap = new BootStrap();
        bootStrap.bootStrapResultsBtns(RunCorrectionActivity.this, allBtnsRun);
    }
}
