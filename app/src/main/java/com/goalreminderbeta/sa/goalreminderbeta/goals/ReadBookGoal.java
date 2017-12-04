package com.goalreminderbeta.sa.goalreminderbeta.goals;


import com.goalreminderbeta.sa.goalreminderbeta.all.science.languages.LanguageLevels;
import com.orm.SugarRecord;

import java.util.Date;

public class ReadBookGoal extends SugarRecord implements Goal {

    private int pages;
    private double currentResult;
    private double goalResult;
    private Date fromDate;
    private Date toDate;
    private String dataBook = "";
    private String nameGoal, descriptionGoal;
    private String themeCategory;

    public ReadBookGoal() {
    }

    public ReadBookGoal(double currentPages, int pages, String dataBook, Date fromDate, Date toDate, String nameGoal, String descriptionGoal) {
        this.currentResult = currentPages;
        this.descriptionGoal = descriptionGoal;
        this.goalResult = pages;
        this.fromDate = fromDate;
        this.toDate = toDate;
        this.dataBook = dataBook;
        this.nameGoal = nameGoal;
        this.themeCategory = "КНИГА";
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    @Override
    public double getCurrentResult() {
        return currentResult;
    }

    public void setCurrentResult(double currentResult) {
        this.currentResult = currentResult;
    }

    @Override
    public double getGoalResult() {
        return goalResult;
    }

    public void setGoalResult(double goalResult) {
        this.goalResult = goalResult;
    }

    @Override
    public Date getFromDate() {
        return fromDate;
    }

    public void setFromDate(Date fromDate) {
        this.fromDate = fromDate;
    }

    @Override
    public Date getToDate() {
        return toDate;
    }

    @Override
    public int getDistance() {
        return 0;
    }

    @Override
    public LanguageLevels getCurrentLanguageLevel() {
        return null;
    }

    @Override
    public LanguageLevels getGoalLanguageLevel() {
        return null;
    }

    public void setToDate(Date toDate) {
        this.toDate = toDate;
    }

    public String getDataBook() {
        return dataBook;
    }

    public void setDataBook(String dataBook) {
        this.dataBook = dataBook;
    }

    @Override
    public String getNameGoal() {
        return nameGoal;
    }

    public void setNameGoal(String nameGoal) {
        this.nameGoal = nameGoal;
    }

    @Override
    public String getDescriptionGoal() {
        return descriptionGoal;
    }

    public void setDescriptionGoal(String descriptionGoal) {
        this.descriptionGoal = descriptionGoal;
    }

    @Override
    public String getThemeCategory() {
        return themeCategory;
    }

    public void setThemeCategory(String themeCategory) {
        this.themeCategory = themeCategory;
    }

    @Override
    public String toString() {
        return "ReadBookGoal{" +
                "pages=" + pages +
                ", currentResult=" + currentResult +
                ", goalResult=" + goalResult +
                ", fromDate=" + fromDate +
                ", toDate=" + toDate +
                ", dataBook='" + dataBook + '\'' +
                ", nameGoal='" + nameGoal + '\'' +
                ", descriptionGoal='" + descriptionGoal + '\'' +
                ", themeCategory='" + themeCategory + '\'' +
                '}';
    }
}
