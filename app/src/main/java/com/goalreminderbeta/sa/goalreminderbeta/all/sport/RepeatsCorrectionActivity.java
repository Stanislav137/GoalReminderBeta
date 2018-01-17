package com.goalreminderbeta.sa.goalreminderbeta.all.sport;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
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
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.goalreminderbeta.sa.goalreminderbeta.R;
import com.goalreminderbeta.sa.goalreminderbeta.additional.BootStrap;
import com.goalreminderbeta.sa.goalreminderbeta.additional.CustomDatePicker;
import com.goalreminderbeta.sa.goalreminderbeta.additional.DialogBuilder;
import com.goalreminderbeta.sa.goalreminderbeta.additional.DialogFactory;
import com.goalreminderbeta.sa.goalreminderbeta.all.StartActivity;
import com.goalreminderbeta.sa.goalreminderbeta.goals.Goal;
import com.goalreminderbeta.sa.goalreminderbeta.goals.RepeatsCorrectionGoal;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class RepeatsCorrectionActivity extends AppCompatActivity {

    private Button currentRepeatsResult, goalRepeatsResult;
    private Button sportMinusRepeatCurrent, sportPlusRepeatCurrent, sportMinusRepeatGoal, sportPlusRepeatGoal;
    private Button addX10CurrentRepeat, addX10GoalRepeat;
    private Date dateFrom, dateTo;
    private TextView repeatsDateFrom, repeatsDateTo;
    private Dialog dialog;
    private String goalDescription, goalName;
    private int repeatsGoal;
    private int repeatsCurrent;
    private RepeatsDialogBuilder rdb;
    private boolean type = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.repeats_theme);

        chooseType();
        findAllButtons();
        addToList(); // using for BootStrap!
        setListenersOnButtons();
        initializeUX();
    }

    private void findAllButtons() {
        sportMinusRepeatCurrent = (Button) findViewById(R.id.sportMinusRepeatCurrent);
        sportPlusRepeatCurrent = (Button) findViewById(R.id.sportPlusRepeatCurrent);
        sportMinusRepeatGoal = (Button) findViewById(R.id.sportMinusRepeatGoal);
        sportPlusRepeatGoal = (Button) findViewById(R.id.sportPlusRepeatGoal);
        addX10CurrentRepeat = (Button) findViewById(R.id.addX10CurrentRepeat);
        addX10GoalRepeat = (Button) findViewById(R.id.addX10GoalRepeat);
        currentRepeatsResult = (Button) findViewById(R.id.currentRepeatsResult);
        goalRepeatsResult = (Button) findViewById(R.id.goalRepeatsResult);
        repeatsDateFrom = (TextView) findViewById(R.id.repeatsDateFrom);
        repeatsDateTo = (TextView) findViewById(R.id.repeatsDateTo);
    }

    public void editDescription(View view) {
        dialog = new Dialog(RepeatsCorrectionActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
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
        if (!goalName.equals("") || !goalDescription.equals("")) {
            ImageView imgReadyDescription = (ImageView) findViewById(R.id.imgReadyDescription);
            imgReadyDescription.setBackground(getResources().getDrawable(R.drawable.ready));
            dialog.dismiss();
        } else {
            goalName = null;
            dialog.dismiss();
        }
    }

    private void chooseType() {
        dialog = new Dialog(RepeatsCorrectionActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.popup_choose_type);
        Button newRecord = (Button) dialog.findViewById(R.id.newRecord);
        Button regularAttack = (Button) dialog.findViewById(R.id.regularAttack);
        TextView descrRecord = (TextView) dialog.findViewById(R.id.descrRecord);
        TextView descrRA = (TextView) dialog.findViewById(R.id.descrRA);
        descrRecord.setText(R.string.descr_new_record);
        descrRA.setText(R.string.descr_regular_attack);
        newRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                type = false;
                dialog.dismiss();
                validateType();
            }
        });
        regularAttack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                type = true;
                dialog.dismiss();
                validateType();
            }
        });
        dialog.show();
    }

    private void validateType() {
        if(!type) {
            return;
        } else {
            LinearLayout llCurrentRepeats =(LinearLayout) findViewById(R.id.llCurrentRepeats);
            TextView titleRepeatsCurrent = (TextView) findViewById(R.id.titleRepeatsCurrent);
            llCurrentRepeats.setBackgroundColor(Color.rgb(202,202,202));
            sportMinusRepeatCurrent.setVisibility(View.INVISIBLE);
            sportPlusRepeatCurrent.setVisibility(View.INVISIBLE);
            addX10CurrentRepeat.setVisibility(View.INVISIBLE);
            currentRepeatsResult.setVisibility(View.INVISIBLE);
            titleRepeatsCurrent.setVisibility(View.INVISIBLE);
        }
    }

    public void pickDateTo(View view) {
        CustomDatePicker.pickDate(RepeatsCorrectionActivity.this, repeatsDateTo);
    }

    private void setListenersOnButtons(){
        setTimerOnButton(sportMinusRepeatCurrent, "-", true); // true это текущее кол-во повторений
        setTimerOnButton(sportPlusRepeatCurrent, "+", true);
        setTimerOnButton(addX10CurrentRepeat, "x10", true);
        setTimerOnButton(sportMinusRepeatGoal, "-", false); // false это конечное кол-во повторений
        setTimerOnButton(sportPlusRepeatGoal, "+", false);
        setTimerOnButton(addX10GoalRepeat, "x10", false);
    }

    private void setTimerOnButton(Button button, final String direction, final boolean current){
        button.setOnTouchListener(new View.OnTouchListener() {
            CountDownTimer timer = getTimer(direction, current);
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

    private CountDownTimer getTimer(final String direction, final boolean current){

        CountDownTimer timer = new CountDownTimer(30000, 100) { // скорость и интервал добавления веса
            @Override
            public void onTick(long l) {
                if (direction.equals("+") || direction.equals("x10")){
                    if(current) {
                        if(direction.equals("x10")) {
                            repeatsCurrent+=10;
                        } else repeatsCurrent++;
                    } else if(direction.equals("x10")){
                        repeatsGoal+=10;
                    } else repeatsGoal++;
                }
                if (direction.equals("-")){
                    if (current){
                        if (repeatsCurrent > 0)
                            repeatsCurrent--;
                    }
                    else {
                        if (repeatsGoal > 0)
                            repeatsGoal--;
                    }
                }
                if (current){
                    currentRepeatsResult.setText("" + repeatsCurrent);
                }else {
                    goalRepeatsResult.setText("" + repeatsGoal);
                }
            }
            @Override
            public void onFinish() {
            }
        };
        return timer;
    }

    public void saveGoal(View view) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        dateFrom = formatter.parse(String.valueOf(repeatsDateFrom.getText()));
        dateTo = formatter.parse(String.valueOf(repeatsDateTo.getText()));

        if (goalName != null && repeatsGoal != 0 && !dateTo.equals(dateFrom) && dateFrom.getTime() < dateTo.getTime() && repeatsGoal > repeatsCurrent) {
            Date dateFrom = this.dateFrom;
            Date dateTo = this.dateTo;
            RepeatsCorrectionGoal runCorrectionGoal = new RepeatsCorrectionGoal(repeatsCurrent, repeatsGoal, dateFrom, dateTo, goalName, goalDescription);
            if (rdb == null) {
                rdb = new RepeatsDialogBuilder();
            }
            rdb.createDialog(RepeatsCorrectionActivity.this, runCorrectionGoal).show();
            // runCorrectionGoal.save();
            //DialogFactory.createRepeatsDialog(RepeatsCorrectionActivity.this,runCorrectionGoal);

        } else {
            Toast toast;
            if (dateTo.equals(dateFrom)) {
                toast = Toast.makeText(getApplicationContext(), getString(R.string.your_date_going_with_todays_date), Toast.LENGTH_SHORT);
            } else if (goalName == null) {
                toast = Toast.makeText(getApplicationContext(), getString(R.string.enter_description_objective), Toast.LENGTH_SHORT);
            } else if (repeatsGoal <= repeatsCurrent) {
                toast = Toast.makeText(getApplicationContext(), getString(R.string.your_desired_goal_below_current_one), Toast.LENGTH_SHORT);
            } else {
                toast = Toast.makeText(getApplicationContext(), getString(R.string.please_chec_data_entered), Toast.LENGTH_SHORT);
            }
            toast.show();
        }
    }
    public void backToHome(View view) {
        Intent intent = new Intent(RepeatsCorrectionActivity.this, StartActivity.class);
        startActivity(intent);
        this.finish();
    }

    public void backToPrevActivity(View view) {
        Intent intent = new Intent(RepeatsCorrectionActivity.this, AllSubThemesSport.class);
        startActivity(intent);
        this.finish();
    }

    private void addToList() {
        ArrayList<Button> allBtnsRun = new ArrayList<>();
        allBtnsRun.add(currentRepeatsResult);
        allBtnsRun.add(goalRepeatsResult);
        startBootStrap(allBtnsRun);
    }

    private void startBootStrap(ArrayList<Button> allBtnsRun) {
        BootStrap bootStrap = new BootStrap();
        bootStrap.bootStrapResultsBtns(RepeatsCorrectionActivity.this, allBtnsRun);
    }

    public void showWarning(View view) {
        final Dialog dialog;
        dialog = new Dialog(RepeatsCorrectionActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.warning);
        Button closeWarning = (Button) dialog.findViewById(R.id.closeWarning);
        TextView descrCategory = (TextView) dialog.findViewById(R.id.descrCategory);
        TextView instruction = (TextView) dialog.findViewById(R.id.instruction);
        if(!type) {
            descrCategory.setText(R.string.descr_repeats_nr);
            instruction.setText(R.string.instruct_repeats_nr);
        } else {
            descrCategory.setText(R.string.descr_repeats_ra);
            instruction.setText(R.string.instruct_repeats_ra);
        }
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
        repeatsDateFrom.setText(reportDate);
        repeatsDateTo.setText(reportDate);
    }

    public void setCurrentRepeats(View view) {
        final Dialog dialog;
        dialog = new Dialog(RepeatsCorrectionActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.choose_value);
        Button apply = (Button) dialog.findViewById(R.id.apply);
        final EditText value = (EditText) dialog.findViewById(R.id.value);
        value.setText(repeatsCurrent + "");
        value.isShown();
        apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentRepeatsResult.setText(value.getText());
                repeatsCurrent = Integer.parseInt(value.getText().toString());
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    public void setGoalRepeats(View view) {
        final Dialog dialog;
        dialog = new Dialog(RepeatsCorrectionActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.choose_value);
        Button apply = (Button) dialog.findViewById(R.id.apply);
        final EditText value = (EditText) dialog.findViewById(R.id.value);
        value.setText(repeatsGoal + "");
        value.isShown();
        apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goalRepeatsResult.setText(value.getText());
                repeatsGoal = Integer.parseInt(value.getText().toString());
                dialog.dismiss();
            }
        });

        dialog.show();
    }
    @Override
    public void onBackPressed(){
        Intent intent = new Intent(this, AllSubThemesSport.class);
        startActivity(intent);
        this.finish();
    }

    private static class RepeatsDialogBuilder extends DialogBuilder {
        private static TextView currentRepeatsET, goalRepeatsET;
        private static TextView currentRepeatsTV, goalRepeatsTV;
        private static LinearLayout.LayoutParams params;
        private static LinearLayout ll;

        private static Dialog repeatsDialog;

        public RepeatsDialogBuilder() {
        }
        @Override
        public Dialog createDialog(final Activity activity, Goal goal){
            repeatsDialog = super.createDialog(activity,goal);
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            String date_from = sdf.format(dialogBuilderGoal.getFromDate());
            dateFrom.setText(date_from);
            String date_to = sdf.format(dialogBuilderGoal.getToDate());
            dateTo.setText(date_to);

            params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 1);
            ll = new LinearLayout(repeatsDialog.getContext());
            ll.setOrientation(LinearLayout.HORIZONTAL);
            ll.setBackgroundColor(Color.GRAY);

            if(dialogBuilderGoal.getCurrentResult() != 0) {
                currentRepeatsTV = new TextView(repeatsDialog.getContext());
                currentRepeatsTV.setTextColor(Color.rgb(68, 182, 72));
                currentRepeatsTV.setTextSize(20);
                currentRepeatsTV.setPadding(20, 0, 0, 0);
                currentRepeatsTV.setText(R.string.currentRepeatsTV_repeats_Activity);
                dialogLV.addView(currentRepeatsTV, lp);
                currentRepeatsET = new TextView(repeatsDialog.getContext());
                currentRepeatsET.setText(String.valueOf(String.format("%.0f", dialogBuilderGoal.getCurrentResult())));
                currentRepeatsET.setPadding(20, 20, 20, 20);
                currentRepeatsET.setTextSize(20);
                currentRepeatsET.setTextColor(Color.BLACK);
                dialogLV.addView(currentRepeatsET, lp);
                dialogLV.addView(ll, params);
            }
            goalRepeatsTV = new TextView(repeatsDialog.getContext());
            goalRepeatsTV.setTextColor(Color.rgb(68,182,72));
            goalRepeatsTV.setTextSize(20);
            goalRepeatsTV.setPadding(20,0,0,0);
            goalRepeatsTV.setText(R.string.goalRepeatsTVrepeats_Activity);
            dialogLV.addView(goalRepeatsTV,lp);
            goalRepeatsET = new TextView(repeatsDialog.getContext());
            goalRepeatsET.setText(String.valueOf(String.format("%.0f", dialogBuilderGoal.getGoalResult())));
            goalRepeatsET.setPadding(20,20,20,20);
            goalRepeatsET.setTextSize(20);
            goalRepeatsET.setTextColor(Color.BLACK);
            dialogLV.addView(goalRepeatsET,lp);
            confirm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Class c = dialogBuilderGoal.getClass();
                    if(c== RepeatsCorrectionGoal.class){
                        ((RepeatsCorrectionGoal)dialogBuilderGoal).save();
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

            return repeatsDialog;
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
