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
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
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
    private String nameBook = "", nameAuthor = "";
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
        EditText enterNameBook = (EditText) dialog.findViewById(R.id.enterNameBook);
        EditText enterNameAuthor = (EditText) dialog.findViewById(R.id.enterNameAuthor);
        enterNameBook.setText(nameBook + "");
        enterNameAuthor.setText(nameAuthor + "");
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
        EditText descriptionGoal = (EditText) dialog.findViewById(R.id.descriptionGoal);
        EditText nameGoal = (EditText) dialog.findViewById(R.id.nameGoal);
        nameGoal.setText(goalName);
        descriptionGoal.setText(goalDescription);
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
        if (goalName != null && goalPage != 0 && !dateTo.equals(dateFrom) && dateFrom.getTime() < dateTo.getTime()) {
            Date dateFrom = this.dateFrom;
            Date dateTo = this.dateTo;
            ReadBookGoal readBook = new ReadBookGoal(currentPages, goalPage, dataBook, dateFrom, dateTo, goalName, goalDescription);
            if(bdb==null){
                bdb = new BookDialogBuilder();
            }
            bdb.createDialog(BookCorrectionActivity.this,readBook).show();
            //readBook.save();

        } else {
            Toast toast;
            if (dateTo.equals(dateFrom)) {
                toast = Toast.makeText(getApplicationContext(), getString(R.string.your_date_going_with_todays_date), Toast.LENGTH_SHORT);
            } else if (goalName == null) {
                toast = Toast.makeText(getApplicationContext(), getString(R.string.enter_description_objective), Toast.LENGTH_SHORT);
            } else {
                toast = Toast.makeText(getApplicationContext(), getString(R.string.please_chec_data_entered), Toast.LENGTH_SHORT);
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
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.warning);
        Button closeWarning = (Button) dialog.findViewById(R.id.closeWarning);
        TextView descrCategory = (TextView) dialog.findViewById(R.id.descrCategory);
        TextView instruction = (TextView) dialog.findViewById(R.id.instruction);
        descrCategory.setText(R.string.descr_book);
        instruction.setText(R.string.instruct_book);
        closeWarning.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.choose_value);
        Button apply = (Button) dialog.findViewById(R.id.apply);
        final EditText value = (EditText) dialog.findViewById(R.id.value);
        value.setText(goalPage + "");
        value.isShown();
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
        private static TextView dataBookET, currentBookET, goalBookET;
        private static TextView dataBookTV, currentBookTV, goalBookTV;
        private static LinearLayout.LayoutParams params;
        private static LinearLayout ll, ll_2;

        private static Dialog bookDialog;

        public BookDialogBuilder() {
        }
        @Override
        public Dialog createDialog(final Activity activity, Goal goal){
            bookDialog = super.createDialog(activity,goal);
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            String date_from = sdf.format(dialogBuilderGoal.getFromDate());
            dateFrom.setText(date_from);
            String date_to = sdf.format(dialogBuilderGoal.getToDate());
            dateTo.setText(date_to);

            params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 1);
            ll = new LinearLayout(bookDialog.getContext());
            ll_2 = new LinearLayout(bookDialog.getContext());
            ll.setOrientation(LinearLayout.HORIZONTAL);
            ll_2.setOrientation(LinearLayout.HORIZONTAL);
            ll.setBackgroundColor(Color.GRAY);
            ll_2.setBackgroundColor(Color.GRAY);

            dataBookTV = new TextView(bookDialog.getContext());
            dataBookTV.setTextColor(Color.rgb(68,182,72));
            dataBookTV.setTextSize(20);
            dataBookTV.setPadding(20,0,0,0);
            dataBookTV.setText(R.string.dataBookTV_book_Activity);
            dialogLV.addView(dataBookTV,lp);
            dataBookET = new TextView(bookDialog.getContext());
            dataBookET.setPadding(20,20,20,20);
            dataBookET.setTextSize(20);
            dataBookET.setTextColor(Color.BLACK);
            if(dialogBuilderGoal.getDataBook() == null) {
                dataBookET.setText("");
            } else {
                dataBookET.setText(String.valueOf(dialogBuilderGoal.getDataBook()));
            }
            dialogLV.addView(dataBookET, lp);
            dialogLV.addView(ll, params);

            currentBookTV = new TextView(bookDialog.getContext());
            currentBookTV.setTextColor(Color.rgb(68,182,72));
            currentBookTV.setTextSize(20);
            currentBookTV.setPadding(20,0,0,0);
            currentBookTV.setText(R.string.currentBookTV_book_Activity);
            dialogLV.addView(currentBookTV,lp);
            currentBookET = new TextView(bookDialog.getContext());
            currentBookET.setPadding(20,20,20,20);
            currentBookET.setTextSize(20);
            currentBookET.setTextColor(Color.BLACK);
            currentBookET.setText(String.valueOf(String.format("%.0f", dialogBuilderGoal.getCurrentResult())));
            dialogLV.addView(currentBookET,lp);
            dialogLV.addView(ll_2, params);

            goalBookTV = new TextView(bookDialog.getContext());
            goalBookTV.setTextColor(Color.rgb(68,182,72));
            goalBookTV.setTextSize(20);
            goalBookTV.setPadding(20,0,0,0);
            goalBookTV.setText(R.string.goalBookTV_book_Activity);
            dialogLV.addView(goalBookTV,lp);
            goalBookET = new TextView(bookDialog.getContext());
            goalBookET.setPadding(20,20,20,20);
            goalBookET.setTextSize(20);
            goalBookET.setTextColor(Color.BLACK);
            goalBookET.setText(String.valueOf(String.format("%.0f", dialogBuilderGoal.getGoalResult())));
            dialogLV.addView(goalBookET,lp);
            confirm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Class c = dialogBuilderGoal.getClass();
                    if(c== ReadBookGoal.class){
                        ((ReadBookGoal)dialogBuilderGoal).save();
                    }
                    encourage();
                    Intent intent = new Intent(activity, StartActivity.class);
                    activity.startActivity(intent);
                    activity.finish();
                }
            });
            cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.hide();
                }
            });


            return bookDialog;
        }
    }
    public void onStart() {
        super.onStart();
        Animation anim = null;
        ImageView showWarningId = (ImageView) findViewById(R.id.showWarningId);
        anim = AnimationUtils.loadAnimation(this, R.anim.btn_anim2);
        showWarningId.startAnimation(anim);
        return;
    }
}
