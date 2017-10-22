package com.goalreminderbeta.sa.goalreminderbeta.goals;


import com.orm.SugarRecord;

import java.util.Date;
import java.util.concurrent.TimeUnit;

public class ReadBookGoal extends SugarRecord implements Goal {

    private int pages;
    private double currentResult;
    private double goalResult;
    private Date fromDate;
    private Date toDate;
    private String nameBook, nameAuthor;
    private double differenceInDays;
    private String nameGoal, descriptionGoal;
    private String themeCategory;

    public ReadBookGoal() {
    }

    public ReadBookGoal(double currentPages, int pages, String nameBook, String nameAuthor, Date fromDate, Date toDate, String nameGoal, String descriptionGoal) {
        this.currentResult = currentPages;
        this.descriptionGoal = descriptionGoal;
        this.goalResult = pages;
        this.fromDate = fromDate;
        this.toDate = toDate;
        this.nameBook = nameBook;
        this.nameAuthor = nameAuthor;
        this.nameGoal = nameGoal;
        this.themeCategory = "КНИГА";
        if (fromDate!=null && toDate!=null && (toDate.getTime() - fromDate.getTime()) > 0){
            long milliseconds = toDate.getTime() - fromDate.getTime();
            this.differenceInDays = (double) TimeUnit.DAYS.convert(milliseconds, TimeUnit.MILLISECONDS);
        }
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

    public void setToDate(Date toDate) {
        this.toDate = toDate;
    }

    public String getNameBook() {
        return nameBook;
    }

    public void setNameBook(String nameBook) {
        this.nameBook = nameBook;
    }

    public String getNameAuthor() {
        return nameAuthor;
    }

    public void setNameAuthor(String nameAuthor) {
        this.nameAuthor = nameAuthor;
    }

    @Override
    public double getDifferenceInDays() {
        return differenceInDays;
    }

    public void setDifferenceInDays(double differenceInDays) {
        this.differenceInDays = differenceInDays;
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
                ", nameBook='" + nameBook + '\'' +
                ", nameAuthor='" + nameAuthor + '\'' +
                ", differenceInDays=" + differenceInDays +
                ", nameGoal='" + nameGoal + '\'' +
                ", descriptionGoal='" + descriptionGoal + '\'' +
                ", themeCategory='" + themeCategory + '\'' +
                '}';
    }
}
