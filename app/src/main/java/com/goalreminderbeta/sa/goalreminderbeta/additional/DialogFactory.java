package com.goalreminderbeta.sa.goalreminderbeta.additional;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.icu.text.SimpleDateFormat;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.goalreminderbeta.sa.goalreminderbeta.R;
import com.goalreminderbeta.sa.goalreminderbeta.goals.CardioGoal;
import com.goalreminderbeta.sa.goalreminderbeta.goals.RepeatsCorrectionGoal;

public class DialogFactory {
    private static DialogFactory factory;
    final static int GOAL_DIALOG = 1;
    private static Dialog dialog;
    private static LinearLayout lv;
    private static EditText goalName, goalDescr, distance, currentRunTime, goalRunTime, dateFrom, dateTo;
    private static TextView currentResult;
    private static Button confirm, cancel;
    private static SimpleDateFormat sdf;
     static
    {
        if(factory==null){
            factory = new DialogFactory();
        }
    }

    private DialogFactory(){}

    public static void createCardioDialog(Activity activity, CardioGoal goal){
        dialog = new CardioDialog(activity, goal);
    }

    private static class CardioDialog extends Dialog{
        CardioGoal cardioGoal;
        public CardioDialog(Context context, final CardioGoal goal) {
            super(context);
            this.cardioGoal = goal;
            setTitle(R.string.adb_setTitle);
            setContentView(R.layout.confirm_dialog);
            findElements();
            sdf = new SimpleDateFormat("dd/MM/yyyy");
            currentResult.setText(R.string.currentResult1);
            goalName.setText(cardioGoal.getNameGoal());
            goalDescr.setText(cardioGoal.getDescriptionGoal());
            //distance.setText(cardioGoal.getDistance());
            currentRunTime.setText(String.valueOf(cardioGoal.getCurrentResult()));
            goalRunTime.setText(String.valueOf(cardioGoal.getGoalResult()));
            String date_from = sdf.format(cardioGoal.getFromDate());
            dateFrom.setText(date_from);
            String date_to = sdf.format(cardioGoal.getToDate());
            dateTo.setText(date_to);
            confirm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    cardioGoal.save();
                }
            });
            cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    hide();
                }
            });
            show();
        }

        private void findElements(){
            goalName = (EditText)findViewById(R.id.goalName);
            goalDescr = (EditText)findViewById(R.id.goalDescr);
            //distance = (EditText) findViewById(R.id.distance);
            //currentRunTime = (EditText)findViewById(R.id.currentRunTime);
            //goalRunTime = (EditText)findViewById(R.id.goalRunTime);
            dateFrom = (EditText)findViewById(R.id.dateFrom);
            dateTo = (EditText) findViewById(R.id.dateTo);
            confirm = (Button)findViewById(R.id.confirm);
            cancel = (Button)findViewById(R.id.cancel);
           // currentResult = (TextView)findViewById(R.id.currentProgress);
        }


    }
    public static void createRepeatsDialog(Activity activity, RepeatsCorrectionGoal goal){
        dialog = new RepeatsDialog(activity,goal);
    }

    private static class RepeatsDialog extends Dialog{
        RepeatsCorrectionGoal repeatsCorrectionGoal;

            public RepeatsDialog(Activity activity, final RepeatsCorrectionGoal goal){
                super(activity);
                this.repeatsCorrectionGoal= goal;
                AlertDialog.Builder adb = new AlertDialog.Builder(activity);
                adb.setTitle(R.string.adb_setTitle2);
                lv = (LinearLayout)activity.getLayoutInflater().inflate(R.layout.confirm_dialog,null);
                adb.setView(lv);
                findElements();
                currentResult.setText(R.string.current_Result);
                sdf = new SimpleDateFormat("dd/MM/yyyy");
                goalName.setText(repeatsCorrectionGoal.getNameGoal());
                goalDescr.setText(repeatsCorrectionGoal.getDescriptionGoal());
               // distance.setText(repeatsCorrectionGoal.getCurrentResult());
                currentRunTime.setText(String.valueOf(repeatsCorrectionGoal.getCurrentResult()));
                goalRunTime.setText(String.valueOf(repeatsCorrectionGoal.getGoalResult()));
                String date_from = sdf.format(repeatsCorrectionGoal.getFromDate());
                dateFrom.setText(date_from);
                String date_to = sdf.format(repeatsCorrectionGoal.getToDate());
                dateTo.setText(date_to);
                confirm.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        repeatsCorrectionGoal.save();
                    }
                });
                adb.setNeutralButton(R.string.setNeutralButton, new OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                adb.create().show();
            }
        private void findElements(){
            goalName = (EditText)findViewById(R.id.goalName);
            goalDescr = (EditText)findViewById(R.id.goalDescr);
            //distance = (EditText) findViewById(R.id.distance);
            //currentRunTime = (EditText)findViewById(R.id.currentRunTime);
            //goalRunTime = (EditText)findViewById(R.id.goalRunTime);
            dateFrom = (EditText)findViewById(R.id.dateFrom);
            dateTo = (EditText) findViewById(R.id.dateTo);
            confirm = (Button)findViewById(R.id.confirm);
            cancel = (Button)findViewById(R.id.cancel);
            //currentResult = (TextView)findViewById(R.id.currentProgress);
        }

    }







} 