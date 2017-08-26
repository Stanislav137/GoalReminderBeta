package com.goalreminderbeta.sa.goalreminderbeta.all;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.goalreminderbeta.sa.goalreminderbeta.R;
import com.goalreminderbeta.sa.goalreminderbeta.theme.childTheme.ChildTheme;

public class SectionTheme extends AppCompatActivity {

    private TextView goalName;
    private TextView categoryName;
    private ImageView image;
    private ProgressBar progressGoal;
    private ChildTheme child;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_section_theme);


    }
}
