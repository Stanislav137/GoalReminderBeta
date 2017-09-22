package com.goalreminderbeta.sa.goalreminderbeta.additional;

import android.app.Activity;
import android.util.DisplayMetrics;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import java.util.ArrayList;

public class BootStrap {

    public void bootStrapAllSection(Activity currentView,  ArrayList<ImageView> allImages) {
        RelativeLayout.LayoutParams bootStrapSection = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        int width;
        DisplayMetrics metrics = currentView.getResources().getDisplayMetrics();
        width = metrics.widthPixels;
        realizationBootStrap(width, bootStrapSection, allImages);
    }

    public void realizationBootStrap(int width, RelativeLayout.LayoutParams bootStrapSection,  ArrayList<ImageView> allImages) {
        bootStrapSection.addRule(RelativeLayout.CENTER_HORIZONTAL);
        bootStrapSection.addRule(RelativeLayout.CENTER_VERTICAL);
        bootStrapSection.width = (width / 3) - 20;
        bootStrapSection.height = (width / 3) - 20;
        finishBootStrap(allImages, bootStrapSection);
    }

    public void finishBootStrap(ArrayList<ImageView> allImages, RelativeLayout.LayoutParams bootStrapSection) {
        for(ImageView actualImg : allImages) {
            actualImg.setLayoutParams(bootStrapSection);
        }
    }

    public void bootStrapBtnGoal(Activity activity, Button startGoal) {
        RelativeLayout.LayoutParams changeBtnParams = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        DisplayMetrics metrics = activity.getResources().getDisplayMetrics();
        int width = metrics.widthPixels;
        initStartGoal(width, changeBtnParams, startGoal);
    }

    public void initStartGoal(int width, RelativeLayout.LayoutParams changeBtnParams, Button startGoal) {
        changeBtnParams.addRule(RelativeLayout.CENTER_HORIZONTAL);
        changeBtnParams.addRule(RelativeLayout.CENTER_VERTICAL);
        changeBtnParams.width = (width / 2) + 30;
        changeBtnParams.height = (width / 2) + 30;
        startGoal.setLayoutParams(changeBtnParams);
    }
}
