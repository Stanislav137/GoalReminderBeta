package com.goalreminderbeta.sa.goalreminderbeta.all.science;

import android.app.Dialog;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.goalreminderbeta.sa.goalreminderbeta.R;

public class BookCorrectionActivity extends AppCompatActivity {

    private Button scienceGoalPage, minusPage, addPage, addMinusPage;
    private int goalPage;
    private String nameBook, nameAuthor;
    private boolean boolNameBook = true;
    private Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.science_theme);

        findAllButtons();
        setListenersOnButtons();
    }

    private void findAllButtons(){
        scienceGoalPage = (Button) findViewById(R.id.scienceGoalPage);
        minusPage = (Button) findViewById(R.id.minusPage);
        addPage = (Button) findViewById(R.id.addPage);
        addMinusPage = (Button) findViewById(R.id.addMinusPage);
    }

    private void setListenersOnButtons(){
        setTimemOnButton(minusPage, "-");
        setTimemOnButton(addPage, "+");
        setTimemOnButton(addMinusPage, "x20");
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

    public void saveGoalBtn(View view) {
        EditText enterNameBook = (EditText) dialog.findViewById(R.id.enterNameBook);
        EditText enterNameAuthor = (EditText) dialog.findViewById(R.id.enterNameAuthor);
        nameBook = enterNameBook.getText().toString();
        nameAuthor = enterNameAuthor.getText().toString();
        dialog.dismiss();
        Log.d("log", nameBook + " " +  nameAuthor);
    }
}
