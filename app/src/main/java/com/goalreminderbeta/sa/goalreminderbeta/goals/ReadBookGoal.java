package com.goalreminderbeta.sa.goalreminderbeta.goals;


import com.orm.SugarRecord;

import java.util.Date;
import java.util.concurrent.TimeUnit;

public class ReadBookGoal extends SugarRecord implements Goal {

    private int pages;
    private Date currentDate;
    private Date goalDate;
    private String nameBook, nameAuthor;
    private double differenceInDays;
    private String nameGoal, descriptionGoal;
    private String themeCategory;

    public ReadBookGoal() {
    }

    public ReadBookGoal(int pages, String nameBook, String nameAuthor, Date currentDate, Date goalDate, String nameGoal, String descriptionGoal, String themeCategory) {
        this.descriptionGoal = descriptionGoal;
        this.pages = pages;
        this.currentDate = currentDate;
        this.goalDate = goalDate;
        this.nameBook = nameBook;
        this.nameAuthor = nameAuthor;
        this.nameGoal = nameGoal;
        this.themeCategory = themeCategory;
        if (currentDate!=null && goalDate!=null && (goalDate.getTime() - currentDate.getTime()) > 0){
            long milliseconds = goalDate.getTime() - currentDate.getTime();
            this.differenceInDays = (double) TimeUnit.DAYS.convert(milliseconds, TimeUnit.MILLISECONDS);
        }
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

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public Date getCurrentDate() {
        return currentDate;
    }

    public void setCurrentDate(Date currentDate) {
        this.currentDate = currentDate;
    }

    public Date getGoalDate() {
        return goalDate;
    }

    public void setGoalDate(Date goalDate) {
        this.goalDate = goalDate;
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
    public String toString() {
        return "ReadBookGoal{" +
                "pages=" + pages +
                ", currentDate=" + currentDate +
                ", goalDate=" + goalDate +
                ", nameBook='" + nameBook + '\'' +
                ", nameAuthor='" + nameAuthor + '\'' +
                ", differenceInDays=" + differenceInDays +
                ", nameGoal='" + nameGoal + '\'' +
                ", descriptionGoal='" + descriptionGoal + '\'' +
                ", themeCategory='" + themeCategory + '\'' +
                '}';
    }
}
