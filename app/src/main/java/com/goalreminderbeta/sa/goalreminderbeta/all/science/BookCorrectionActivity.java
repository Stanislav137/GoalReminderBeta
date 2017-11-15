package com.goalreminderbeta.sa.goalreminderbeta.all.science;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.goalreminderbeta.sa.goalreminderbeta.R;
import com.goalreminderbeta.sa.goalreminderbeta.additional.BootStrap;
import com.goalreminderbeta.sa.goalreminderbeta.additional.CustomDatePicker;
import com.goalreminderbeta.sa.goalreminderbeta.additional.DialogBuilder;
import com.goalreminderbeta.sa.goalreminderbeta.all.StartActivity;
import com.goalreminderbeta.sa.goalreminderbeta.goals.Goal;
import com.goalreminderbeta.sa.goalreminderbeta.goals.ReadBookGoal;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class BookCorrectionActivity extends AppCompatActivity {

    private TextView bookDateFrom, bookDateTo;
    private Button scienceGoalPage, minusPage, addPage, addX20;
    private int goalPage;
    private double currentPages = 0;
    private String nameBook, nameAuthor;
    private boolean boolNameBook = true;
    private Dialog dialog;
    private Date dateFrom, dateTo;
    private String goalName, goalDescription, dataBook;
    private BookDialogBuilder bdb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.book_theme);

        findAllBtnsBootStrap(); // using for BootStrap!
        findAllButtons();
        setListenersOnButtons();
        initializeUX();
    }

    private void findAllButtons(){
        bookDateFrom = (TextView) findViewById(R.id.bookDateFrom);
        bookDateTo = (TextView) findViewById(R.id.repeatsDateTo);
        scienceGoalPage = (Button) findViewById(R.id.scienceGoalPage);
        minusPage = (Button) findViewById(R.id.minusPage);
        addPage = (Button) findViewById(R.id.addPage);
        addX20 = (Button) findViewById(R.id.addX20);
    }

    private void setListenersOnButtons(){
        setTimemOnButton(minusPage, "-");
        setTimemOnButton(addPage, "+");
        setTimemOnButton(addX20, "x20");
    }

    private CountDownTimer getTimer(final String direction){

        CountDownTimer timer = new CountDownTimer(30000, 100) { // скорость и интервал добавления страниц
            @Override
            public void onTick(long l) {
                if(direction.equals("+") || direction.equals("x20")){
                    if(direction.equals("x20")){
                        goalPage+=20;
                    } else goalPage++;
                } else if(direction.equals("-")){
                    if(goalPage > 0) {
                        goalPage--;
                    }
                }
                scienceGoalPage.setText("" + goalPage);
            }
            @Override
            public void onFinish() {
            }
        };
        return timer;
    }

    private void setTimemOnButton(Button button, final String direction){
        button.setOnTouchListener(new View.OnTouchListener() {
            CountDownTimer timer = getTimer(direction);
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch(event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        timer.start();
                        return true;
                    case MotionEvent.ACTION_UP:
                        timer.cancel();
                        return true;
                }
                return false;
            }
        });
    }

    public void pickDateFrom(View view) throws ParseException {
        CustomDatePicker.pickDate(BookCorrectionActivity.this, bookDateFrom);
    }
    public void pickDateTo(View view) {
        CustomDatePicker.pickDate(BookCorrectionActivity.this, bookDateTo);
    }

    public void cbOnClick(View view){
        if(!boolNameBook){
            boolNameBook = true;
        } else boolNameBook = false;
        disablePresentBook(boolNameBook);
    }

    private void disablePresentBook(Boolean boolNameBook){
        LinearLayout llNameBook = (LinearLayout) findViewById(R.id.llNameBook);
        Button editBook = (Button) findViewById(R.id.editBook);
        if(!boolNameBook){
            llNameBook.setBackgroundColor(Color.argb(255,193,193,193));
            editBook.setVisibility(View.INVISIBLE);
        } else {
            llNameBook.setBackgroundColor(Color.argb(255, 228, 228, 228));
            editBook.setVisibility(View.VISIBLE);
        }
    }

    public void editPresentBook(View view) {
        showPresentBook();
    }

    private void showPresentBook(){
        dialog = new Dialog(BookCorrectionActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.present_book);
        dialog.show();
    }

    public void savePresentBook(View view) {
        EditText enterNameBook = (EditText) dialog.findViewById(R.id.enterNameBook);
        EditText enterNameAuthor = (EditText) dialog.findViewById(R.id.enterNameAuthor);
        nameBook = enterNameBook.getText().toString();
        nameAuthor = enterNameAuthor.getText().toString();
        dataBook = nameBook + " " + nameAuthor;
        dialog.dismiss();
    }

    public void editDescription(View view) {
        dialog = new Dialog(BookCorrectionActivity.this);
        dialog.setContentView(R.layout.description_goal);
        dialog.show();
    }

    public void saveDescription(View view) {
        EditText descriptionGoal = (EditText) dialog.findViewById(R.id.descriptionGoal);
        EditText nameGoal = (EditText) dialog.findViewById(R.id.nameGoal);
        goalDescription = descriptionGoal.getText().toString();
        goalName = nameGoal.getText().toString();
        if(!goalName.equals("") || !goalDescription.equals("")) {
            ImageView imgReadyDescription = (ImageView) findViewById(R.id.imgReadyDescription);
            imgReadyDescription.setBackground(getResources().getDrawable(R.drawable.ready));
            dialog.dismiss();
        } else {
            goalName = null;
            dialog.dismiss();
        }
    }

    public void saveGoal(View view) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        dateFrom = formatter.parse(String.valueOf(bookDateFrom.getText()));
        dateTo = formatter.parse(String.valueOf(bookDateTo.getText()));
        if (goalName != null && goalPage != 0 && !dateTo.equals(dateFrom)) {
            Date dateFrom = this.dateFrom;
            Date dateTo = this.dateTo;
            ReadBookGoal readBook = new ReadBookGoal(currentPages, goalPage, dataBook, dateFrom, dateTo, goalName, goalDescription);
            if(bdb==null){
                bdb = new BookDialogBuilder();
            }
            bdb.createDialog(BookCorrectionActivity.this,readBook).show();
            //readBook.save();
            Intent intent = new Intent(BookCorrectionActivity.this, StartActivity.class);
            startActivity(intent);
            this.finish();
        } else {
            Toast toast;
            if (dateTo.equals(dateFrom)) {
                toast = Toast.makeText(getApplicationContext(), "ВАША ДАТА ЦЕЛИ СОВПАДАЕТ С СЕГОДНЯШНЕЙ ДАТОЙ", Toast.LENGTH_SHORT);
            } else if (goalName == null) {
                toast = Toast.makeText(getApplicationContext(), "ВВЕДИТЕ ОПИСАНИЕ ЦЕЛИ", Toast.LENGTH_SHORT);
            } else {
                toast = Toast.makeText(getApplicationContext(), "ПОЖАЛУЙСТА, ЗАПОЛНИТЕ ВСЕ ДАННЫЕ", Toast.LENGTH_SHORT);
            }
            toast.show();
        }
    }

    public void backToHome(View view) {
        Intent intent = new Intent(BookCorrectionActivity.this, StartActivity.class);
        startActivity(intent);
        this.finish();
    }

    public void backToPrevActivity(View view) {
        Intent intent = new Intent(BookCorrectionActivity.this, AllSubThemesScience.class);
        startActivity(intent);
        this.finish();
    }

    private void findAllBtnsBootStrap() {
        ArrayList<Button> allBtnsRun = new ArrayList<>();
        Button scienceGoalPage = (Button) findViewById(R.id.scienceGoalPage);
        Button editBook = (Button) findViewById(R.id.editBook);
        allBtnsRun.add(scienceGoalPage);
        allBtnsRun.add(editBook);
        startBootStrap(allBtnsRun);
    }

    private void startBootStrap(ArrayList<Button> allBtnsRun) {
        BootStrap bootStrap = new BootStrap();
        bootStrap.bootStrapResultsBtns(BookCorrectionActivity.this, allBtnsRun);
    }

    public void showWarning(View view) {
        final Dialog dialog;
        dialog = new Dialog(BookCorrectionActivity.this);
        dialog.setContentView(R.layout.warning);

        Button closeWarning = (Button) dialog.findViewById(R.id.closeWarning);
        closeWarning.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImageView showWarningId = (ImageView) findViewById(R.id.showWarningId);
                showWarningId.setVisibility(View.INVISIBLE);
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    private void initializeUX(){
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        Date today = Calendar.getInstance().getTime();
        dateFrom = today;
        dateTo = today;
        String reportDate = df.format(today);
        bookDateFrom.setText(reportDate);
        bookDateTo.setText(reportDate);
    }

    public void setPages(View view) {
        final Dialog dialog;
        dialog = new Dialog(BookCorrectionActivity.this);
        dialog.setContentView(R.layout.choose_value);
        Button apply = (Button) dialog.findViewById(R.id.apply);
        final EditText value = (EditText) dialog.findViewById(R.id.value);

        apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scienceGoalPage.setText(value.getText());
                goalPage = Integer.parseInt(value.getText().toString());
                dialog.dismiss();
            }
        });

        dialog.show();
    }
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, AllSubThemesScience.class);
        startActivity(intent);
        this.finish();
        }

    private static class BookDialogBuilder extends DialogBuilder {
        private static EditText dataBookET, currentBookET, goalBookET;
        private static TextView dataBookTV, currentBookTV, goalBookTV;

        private static Dialog bookDialog;

        public BookDialogBuilder() {


        }
        @Override
        public Dialog createDialog(Activity activity, Goal goal){
            bookDialog = super.createDialog(activity,goal);

            dataBookTV = new TextView(bookDialog.getContext());
            dataBookTV.setText("Name of the book");
            dialogLV.addView(dataBookTV,lp);
            dataBookET = new EditText(bookDialog.getContext());
            dataBookET.setText(String.valueOf(dialogBuilderGoal.getDataBook()));
            dialogLV.addView(dataBookET,lp);

            currentBookTV = new TextView(bookDialog.getContext());
            currentBookTV.setText("Your current pages:");
            dialogLV.addView(currentBookTV,lp);
            currentBookET = new EditText(bookDialog.getContext());
            currentBookET.setText(String.valueOf(dialogBuilderGoal.getCurrentResult()));
            dialogLV.addView(currentBookET,lp);

            goalBookTV = new TextView(bookDialog.getContext());
            goalBookTV.setText("Your goal pages:");
            dialogLV.addView(goalBookTV,lp);
            goalBookET = new EditText(bookDialog.getContext());
            goalBookET.setText(String.valueOf(dialogBuilderGoal.getGoalResult()));
            dialogLV.addView(goalBookET,lp);

            return bookDialog;
        }
    }
        
    }
