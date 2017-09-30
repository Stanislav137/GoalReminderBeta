package com.goalreminderbeta.sa.goalreminderbeta.all.science;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.goalreminderbeta.sa.goalreminderbeta.R;
import com.goalreminderbeta.sa.goalreminderbeta.additional.CustomDatePicker;
import com.goalreminderbeta.sa.goalreminderbeta.all.StartActivity;
import com.goalreminderbeta.sa.goalreminderbeta.goals.ReadBookGoal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class BookCorrectionActivity extends AppCompatActivity {

    private TextView sportDateFrom, sportDateTo;
    private Button scienceGoalPage, minusPage, addPage, addX20;
    private int goalPage;
    private String nameBook, nameAuthor;
    private boolean boolNameBook = true;
    private Dialog dialog;
    private Date dateFrom, dateTo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.book_theme);

        findAllButtons();
        setListenersOnButtons();
    }

    private void findAllButtons(){
        sportDateFrom = (TextView) findViewById(R.id.sportDateFrom);
        sportDateTo = (TextView) findViewById(R.id.sportDateTo);
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
        CustomDatePicker.pickDate(BookCorrectionActivity.this, sportDateFrom);
    }
    public void pickDateTo(View view) {
        CustomDatePicker.pickDate(BookCorrectionActivity.this, sportDateTo);
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
            llNameBook.setBackgroundColor(Color.WHITE);
            editBook.setVisibility(View.VISIBLE);
        }
    }

    public void editPresentBook(View view) {
        showPresentBook();
    }

    private void showPresentBook(){
        dialog = new Dialog(BookCorrectionActivity.this);
        dialog.setContentView(R.layout.present_book);
        dialog.show();
    }

    public void savePresentBook(View view) {
        EditText enterNameBook = (EditText) dialog.findViewById(R.id.enterNameBook);
        EditText enterNameAuthor = (EditText) dialog.findViewById(R.id.enterNameAuthor);
        nameBook = enterNameBook.getText().toString();
        nameAuthor = enterNameAuthor.getText().toString();
        dialog.dismiss();
    }

    public void saveGoal(View view) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        dateFrom = formatter.parse(String.valueOf(sportDateFrom.getText()));
        dateTo = formatter.parse(String.valueOf(sportDateTo.getText()));

        Date dateFrom = this.dateFrom;
        Date dateTo = this.dateTo;
        ReadBookGoal readBook = new ReadBookGoal(goalPage, nameBook, nameAuthor, dateFrom, dateTo);
        readBook.save();
        Intent intent = new Intent(BookCorrectionActivity.this, StartActivity.class);
        startActivity(intent);
        this.finish();
    }

    public void backToHome(View view) {
        Intent intent = new Intent(BookCorrectionActivity.this, StartActivity.class);
        startActivity(intent);
        this.finish();
    }
}
