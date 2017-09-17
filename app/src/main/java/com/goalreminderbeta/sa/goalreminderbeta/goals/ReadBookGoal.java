package com.goalreminderbeta.sa.goalreminderbeta.goals;


import com.orm.SugarRecord;

import java.util.Date;

public class ReadBookGoal extends SugarRecord implements Goal {

    private int pages;
    private Date currentDate;
    private Date goalDate;
    private String nameBook, nameAuthor;

    public ReadBookGoal() {
    }

    public ReadBookGoal(int pages, String nameBook, String nameAuthor, Date currentDate, Date goalDate) {
        this.pages = pages;
        this.currentDate = currentDate;
        this.goalDate = goalDate;
        this.nameBook = nameBook;
        this.nameAuthor = nameAuthor;
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
                '}';
    }
}
