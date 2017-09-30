package com.goalreminderbeta.sa.goalreminderbeta.all.science;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.goalreminderbeta.sa.goalreminderbeta.R;
import com.goalreminderbeta.sa.goalreminderbeta.all.StartActivity;

public class LanguageCorrectionActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.language_theme);

    }

    public void backToHome(View view) {
        Intent intent = new Intent(LanguageCorrectionActivity.this, StartActivity.class);
        startActivity(intent);
        this.finish();
    }
}
