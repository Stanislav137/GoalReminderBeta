package com.goalreminderbeta.sa.goalreminderbeta.all;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.goalreminderbeta.sa.goalreminderbeta.R;

public class HabbitsThemeActivity extends AppCompatActivity {

    private boolean smokeDrink = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.habbits_theme);

        validation(true);
    }

    public void onSmoke(View view) {
        smokeDrink = true;
        validation(smokeDrink);
    }

    public void onDrink(View view) {
        smokeDrink = false;
        validation(smokeDrink);
    }

    private void validation(boolean smokeDrink){
        LinearLayout llNumberPagesDrink = (LinearLayout) findViewById(R.id.llNumberPagesDrink);
        LinearLayout llNumberDaysSmoke = (LinearLayout) findViewById(R.id.llNumberDaysSmoke);
        LinearLayout llSmokeHide = (LinearLayout) findViewById(R.id.llSmokeHide);
        LinearLayout llDrinkHide = (LinearLayout) findViewById(R.id.llDrinkHide);
        Button daysCircleSmoke = (Button) findViewById(R.id.daysCircleSmoke);
        Button daysCircleDrink = (Button) findViewById(R.id.daysCircleDrink);

        if(smokeDrink){
            llNumberDaysSmoke.setBackgroundColor(Color.WHITE);
            llNumberPagesDrink.setBackgroundColor(Color.GRAY);
            llSmokeHide.setVisibility(View.VISIBLE);
            llDrinkHide.setVisibility(View.INVISIBLE);
            daysCircleSmoke.setVisibility(View.VISIBLE);
            daysCircleDrink.setVisibility(View.INVISIBLE);
        } else {
            llNumberDaysSmoke.setBackgroundColor(Color.GRAY);
            llNumberPagesDrink.setBackgroundColor(Color.WHITE);
            llDrinkHide.setVisibility(View.VISIBLE);
            llSmokeHide.setVisibility(View.INVISIBLE);
            daysCircleSmoke.setVisibility(View.INVISIBLE);
            daysCircleDrink.setVisibility(View.VISIBLE);
        }
    }
}
