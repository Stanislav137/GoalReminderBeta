package com.goalreminderbeta.sa.goalreminderbeta.additional;

import android.app.Activity;
import android.util.DisplayMetrics;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import java.util.ArrayList;

public class BootStrap {

    public void bootStrapAllSection(Activity activity,  ArrayList<Button> allBtns) {
        RelativeLayout.LayoutParams bootStrapSection = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        int width;
        DisplayMetrics metrics = activity.getResources().getDisplayMetrics();
        width = metrics.widthPixels;
        realizationBootStrapSections(width, bootStrapSection, allBtns);
    }

    private void realizationBootStrapSections(int width, RelativeLayout.LayoutParams bootStrapSection,  ArrayList<Button> allBtns) {
        bootStrapSection.addRule(RelativeLayout.CENTER_HORIZONTAL);
        bootStrapSection.addRule(RelativeLayout.CENTER_VERTICAL);
        bootStrapSection.width = (width / 4) + 5;
        bootStrapSection.height = (width / 4) + 5;
        finishBootStrapSection(allBtns, bootStrapSection);
    }

    private void finishBootStrapSection(ArrayList<Button> allBtns, RelativeLayout.LayoutParams bootStrapSection) {
        for(Button actualImg : allBtns) {
            actualImg.setLayoutParams(bootStrapSection);
        }
    }

    public void bootStrapBtnGoal(Activity activity, Button startGoal, Boolean logicAddGoal) {
        RelativeLayout.LayoutParams changeBtnParams = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        DisplayMetrics metrics = activity.getResources().getDisplayMetrics();
        int width = metrics.widthPixels;
        if(!logicAddGoal) {
            realizationAndFinisStartGoalCenter(width, changeBtnParams, startGoal);
        } else {
            realizationAndFinisStartGoalDown(width, changeBtnParams, startGoal);
        }
    }

    public void bootStrapResultsBtns(Activity activity,  ArrayList<Button> allButtons) {
        RelativeLayout.LayoutParams bootStrapResultsBtn = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        int width;
        DisplayMetrics metrics = activity.getResources().getDisplayMetrics();
        width = metrics.widthPixels;
        realizationBootStrapResultsBtns(width, bootStrapResultsBtn, allButtons);
    }

    private void realizationBootStrapResultsBtns(int width,  RelativeLayout.LayoutParams bootStrapResultsBtn, ArrayList<Button> allButtons) {
        bootStrapResultsBtn.addRule(RelativeLayout.CENTER_VERTICAL);
        bootStrapResultsBtn.width = (width / 4);
        bootStrapResultsBtn.height = (width / 4);
        finishBootStrapResultsBtns(bootStrapResultsBtn, allButtons);
    }

    private void finishBootStrapResultsBtns(RelativeLayout.LayoutParams bootStrapResultsBtn, ArrayList<Button> allButtons) {
        for(Button actualBtn : allButtons) {
            actualBtn.setLayoutParams(bootStrapResultsBtn);
        }
    }

    private void realizationAndFinisStartGoalCenter(int width, RelativeLayout.LayoutParams changeBtnParams, Button startGoal) {
        changeBtnParams.addRule(RelativeLayout.CENTER_HORIZONTAL);
        changeBtnParams.addRule(RelativeLayout.CENTER_VERTICAL);
        changeBtnParams.width = (width / 2) + 30;
        changeBtnParams.height = (width / 2) + 30;
        startGoal.setLayoutParams(changeBtnParams);
    }

    private void realizationAndFinisStartGoalDown(int width, RelativeLayout.LayoutParams changeBtnParams, Button startGoal) {
        changeBtnParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        changeBtnParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        changeBtnParams.rightMargin = 30;
        changeBtnParams.bottomMargin = 30;
        changeBtnParams.width = (width / 3) - 30;
        changeBtnParams.height = (width / 3) - 30;
        startGoal.setLayoutParams(changeBtnParams);
    }
}
