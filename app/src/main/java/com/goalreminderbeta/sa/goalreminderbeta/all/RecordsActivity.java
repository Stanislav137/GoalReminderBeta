package com.goalreminderbeta.sa.goalreminderbeta.all;

import android.content.Intent;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

import com.goalreminderbeta.sa.goalreminderbeta.R;
import com.goalreminderbeta.sa.goalreminderbeta.additional.BootStrap;
import com.goalreminderbeta.sa.goalreminderbeta.all.science.AllSubThemesScience;

import java.util.ArrayList;

public class RecordsActivity extends AppCompatActivity {

    private GestureDetectorCompat gestureObject;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_records);
        gestureObject = new GestureDetectorCompat(this, new GestureRecords());
    }

    public void openGoals(View view) {
        Intent intent = new Intent(RecordsActivity.this, StartActivity.class);
        startActivity(intent);
        this.finish();
    }

    public void openOptions(View view) {
        Intent intent = new Intent(RecordsActivity.this, OptionsActivity.class);
        startActivity(intent);
        this.finish();
    }
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        this.gestureObject.onTouchEvent(event);
        return super.onTouchEvent(event);
    }
    class GestureRecords extends GestureDetector.SimpleOnGestureListener{
        @Override
        public boolean onFling(MotionEvent event1, MotionEvent event2, float velocityX, float velocityY) {

            if (event2.getX() > event1.getX()){
                Intent intent = new Intent(RecordsActivity.this, StartActivity.class);
                finish();
                startActivity(intent);
            }
            else
            if (event2.getX() < event1.getX()){

                Intent intent = new Intent(RecordsActivity.this, OptionsActivity.class);
                finish();
                startActivity(intent);
            }

            return true;
        }
    }
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, StartActivity.class);
        startActivity(intent);
        this.finish();
    }
}
