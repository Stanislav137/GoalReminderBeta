package com.goalreminderbeta.sa.goalreminderbeta.theme;

import android.widget.ImageView;
import android.widget.ProgressBar;

import com.goalreminderbeta.sa.goalreminderbeta.theme.childTheme.ChildTheme;

public class InitialTheme {

    private String goalName;
    private String categoryName;
    private ImageView image;
    private ProgressBar progressGoal;
    private ChildTheme child;

    public InitialTheme() { // TODO зачем это?
        // пустой конструктор что бы можно было создать обект так InitialTheme theme = new InitialTheme();
    }

    public InitialTheme(String goalName, String categoryName, ImageView image, ProgressBar progressGoal, ChildTheme child) {
        this.goalName = goalName;
        this.categoryName = categoryName;
        this.image = image;
        this.progressGoal = progressGoal;
        this.child = child;
    }

    public String getGoalName() {
        return goalName;
    }

    public void setGoalName(String goalName) {
        this.goalName = goalName;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public ImageView getImage() {
        return image;
    }

    public void setImage(ImageView image) {
        this.image = image;
    }

    public ProgressBar getProgressGoal() {
        return progressGoal;
    }

    public void setProgressGoal(ProgressBar progressGoal) {
        this.progressGoal = progressGoal;
    }

    public ChildTheme getChild() {
        return child;
    }

    public void setChild(ChildTheme child) {
        this.child = child;
    }

    @Override
    public String toString() {
        return "InitialTheme{" +
                "goalName='" + goalName +
                ", categoryName='" + categoryName +
                ", image=" + image +
                ", progressGoal=" + progressGoal +
                ", child=" + child + "}";
    }
}
