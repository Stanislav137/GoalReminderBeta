package com.goalreminderbeta.sa.goalreminderbeta.all;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.preference.PreferenceManager;
import android.support.v4.content.ContextCompat;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.goalreminderbeta.sa.goalreminderbeta.R;
import com.goalreminderbeta.sa.goalreminderbeta.all.science.languages.LanguageLevels;
import com.goalreminderbeta.sa.goalreminderbeta.goals.CardioGoal;
import com.goalreminderbeta.sa.goalreminderbeta.goals.ElementCorrectionGoal;
import com.goalreminderbeta.sa.goalreminderbeta.goals.Goal;
import com.goalreminderbeta.sa.goalreminderbeta.goals.LanguageLearningGoal;
import com.goalreminderbeta.sa.goalreminderbeta.goals.ReadBookGoal;
import com.goalreminderbeta.sa.goalreminderbeta.goals.RepeatsCorrectionGoal;
import com.goalreminderbeta.sa.goalreminderbeta.goals.WeightCorrectionGoal;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class ExpListAdapter extends BaseExpandableListAdapter {

    private ArrayList<ArrayList<Goal>> mGroups;
    private Activity mActivity;
    private Context mContext;
    private ImageView arrowDownUp, statusGoal;
    private Map<Long, Goal> allGoalsMap;
    //private Goal goal;
    private TextView fromGoal, toGoal, goalDescription, currentResultUnits, goalResultUnits, distanceRunUnits, leftDaysGoal;
    private TextView leftToGoalUnits, dataBook, taskOfDayUnits, taskOfWeekUnits;
    private RelativeLayout bookPresent;
    private LinearLayout runDistance;
    //private boolean checkComplete = false;
    private LinearLayout separator1, separator2;
    private Typeface faceBold = null;
    private double lvlLangHoursCurrent = 0, lvlLangHoursGoal = 0, pointsSkillsCurrent = 0, pointsSkillsGoal = 0;
    private double madeTodayResult = 0;
    private String unitsCurrent, unitsGoal, unitsToday, unitsLeft, unitsLvlLeft;
    private String units = "";
    Button completed, relax;
    Dialog congrDialog;
    AlertDialog.Builder adb;
    SharedPreferences sp;
    ProgressBar progressBar;
    TextView textCircleProgress;
    private ViewParent parentView;
    private View groupItem;

    /* FOR DAY GOAL */

    private TextView nameData, distanceDG, currentResultDG, goalResultDG, taskDG, madeToday, goalDescriptionDG;

    public ExpListAdapter(Activity activity, ArrayList<ArrayList<Goal>> groups, Map<Long, Goal> allGoalsMap) {
        mActivity = activity;
        mContext = mActivity.getApplicationContext();
        mGroups = groups;
        this.allGoalsMap = allGoalsMap;
    }

    @Override
    public int getGroupCount() {
        return mGroups.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return mGroups.get(groupPosition).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return mGroups.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return mGroups.get(groupPosition).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    private void checkProgress(double currentProgress, View convertView) {
        progressBar = (ProgressBar) convertView.findViewById(R.id.circleProgress);
        textCircleProgress = (TextView) convertView.findViewById(R.id.textCircleProgress);

        progressBar.setProgress((int) currentProgress);

        if (currentProgress <= 30) {
            progressBar.getProgressDrawable().setColorFilter(mContext.
                    getResources().getColor(R.color.colorRed), PorterDuff.Mode.SRC_IN);
            textCircleProgress.setTextColor(mContext.getResources().getColor(R.color.colorRed));
        }
        if (currentProgress > 30 && currentProgress <= 60) {
            progressBar.getProgressDrawable().setColorFilter(mContext.
                    getResources().getColor(R.color.colorYellow), PorterDuff.Mode.SRC_IN);
            textCircleProgress.setTextColor(mContext.getResources().getColor(R.color.colorYellow));
        }
        if (currentProgress > 60 && currentProgress < 100) {
            progressBar.getProgressDrawable().setColorFilter(mContext.
                    getResources().getColor(R.color.colorGreen), PorterDuff.Mode.SRC_IN);
            textCircleProgress.setTextColor(mContext.getResources().getColor(R.color.colorGreen));
        }
        if (currentProgress >= 100) {
            textCircleProgress.setText("100%");
            progressBar.getProgressDrawable().setColorFilter(mContext.
                    getResources().getColor(R.color.colorGreen), PorterDuff.Mode.SRC_IN);
            textCircleProgress.setTextColor(mContext.getResources().getColor(R.color.colorGreen));
        } else {
            textCircleProgress.setText((int) currentProgress + "%");
        }
    }

    private void findWidgetsChild(View view) {
        fromGoal = (TextView) view.findViewById(R.id.fromGoal);
        toGoal = (TextView) view.findViewById(R.id.toGoal);
        distanceRunUnits = (TextView) view.findViewById(R.id.distanceRunUnits);
        goalDescription = (TextView) view.findViewById(R.id.goalDescription);
        currentResultUnits = (TextView) view.findViewById(R.id.currentResultUnits);
        goalResultUnits = (TextView) view.findViewById(R.id.yourGoalUnits);
        bookPresent = (RelativeLayout) view.findViewById(R.id.bookPresent);
        runDistance = (LinearLayout) view.findViewById(R.id.runDistance);
        leftDaysGoal = (TextView) view.findViewById(R.id.leftDaysGoal);
        leftToGoalUnits = (TextView) view.findViewById(R.id.leftToGoalUnits);
        dataBook = (TextView) view.findViewById(R.id.dataBook);
        taskOfDayUnits = (TextView) view.findViewById(R.id.taskOfDayUnits);
        statusGoal = (ImageView) view.findViewById(R.id.statusGoal);
        separator1 = (LinearLayout) view.findViewById(R.id.separator1);
        separator2 = (LinearLayout) view.findViewById(R.id.separator2);
        taskOfWeekUnits = (TextView) view.findViewById(R.id.taskOfWeekUnits);
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView,
                             ViewGroup parent) {

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.section_theme, null);

            Long groupPos = Long.parseLong(String.valueOf(groupPosition));
            Goal goal = allGoalsMap.get(groupPos); //actual goal

            fillDataGroup(goal.getThemeCategory(), goal, convertView);

            if (goal.getProgress() >= 100) {
                RelativeLayout list1 = (RelativeLayout) convertView.findViewById(R.id.list1);
                list1.setBackgroundColor(mContext.getResources().getColor(R.color.colorGreenBS));
            }
        }
        expandGoal(isExpanded, convertView);

        return convertView;
    }

    private void fillDataGroup(String themeCategory, Goal goal, View convertView) {
        ImageView ivThemeGoal = (ImageView) convertView.findViewById(R.id.ivTheme);
        switch (themeCategory) {
            case "МАССА":
                ivThemeGoal.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.weight_image));
                break;
            case "КАРДИО":
                ivThemeGoal.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.cardio_image));
                break;
            case "НАВЫКИ":
                ivThemeGoal.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.skills_image));
                break;
            case "ПОВТОРЕНИЯ":
                ivThemeGoal.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.repeats_image));
                break;
            case "КНИГА":
                ivThemeGoal.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.book_image));
                break;
            case "ЯЗЫКИ":
                ivThemeGoal.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.language_image));
                break;

        }
        showDataGroup(goal, convertView, goal.getProgress());
    }

    private void showDataGroup(Goal goal, View convertView, double progress) {
        String typeLang = mContext.getResources().getConfiguration().locale.getLanguage();
        TextView nameGoal = (TextView) convertView.findViewById(R.id.nameGoal);
        nameGoal.setText(goal.getNameGoal());
        TextView themeCategory = (TextView) convertView.findViewById(R.id.themeCategory);
        if (typeLang.equals("en") || typeLang.equals("pl")) {
            if (goal.getThemeCategory().equals("МАССА")) {
                themeCategory.setText("WEIGHT");
            } else if (goal.getThemeCategory().equals("КАРДИО")) {
                themeCategory.setText("CARDIO");
            } else if (goal.getThemeCategory().equals("ПОВТОРЕНИЯ")) {
                themeCategory.setText("REPEATS");
            } else if (goal.getThemeCategory().equals("КНИГА")) {
                themeCategory.setText("BOOK");
            } else if (goal.getThemeCategory().equals("ЯЗЫКИ")) {
                themeCategory.setText("LANGUAGE");
            } else if (goal.getThemeCategory().equals("НАВЫКИ")) {
                themeCategory.setText("SKILLS");
            }
        } else {
            themeCategory.setText(goal.getThemeCategory());
        }

        //Double progress = 30.0;
        double currentProgress = goal.getProgress();
        checkProgress(currentProgress, convertView);
    }

    @Override
    public View getChildView(final int groupPosition, final int childPosition, final boolean isLastChild,
                             View convertView, final ViewGroup parent) {

        final LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final Long groupPos = Long.parseLong(String.valueOf(groupPosition));
        final Goal goal = allGoalsMap.get(groupPos); //actual goal
        GregorianCalendar calendar = new GregorianCalendar();
        int day = calendar.get(Calendar.DATE);
        int month = calendar.get(Calendar.MONTH);
        month += 1;
        int year = calendar.get(Calendar.YEAR);
        final String date = String.valueOf(day) + "." + String.valueOf(month) + "." + String.valueOf(year);
        sp = PreferenceManager.getDefaultSharedPreferences(mContext);
        final String date2 = sp.getString("date" + goal.getThemeCategory() + goal.getNameGoal() + goal.getDescriptionGoal(), "");
        if (!date.equals(date2)) {
            goal.setCompleted(false);
            goal.setRelax(false);
            goal.save();
        } else {
            goal.setBlink(false);
            goal.setMadeTodayResult(0);
            if (goal.getCurrentResult() == 0 && goal instanceof CardioGoal) {
                goal.setRelax(true);
            } else goal.setRelax(false);
            if (goal.getCurrentResult() == 0 && goal instanceof RepeatsCorrectionGoal) {
                goal.setRelax(true);
            } else goal.setRelax(false);
            goal.setCompleted(true);
            goal.save();
        }

        if (goal.getCompleted() || goal.getRelax() || goal.getProgress() >= 100) {
            convertView = inflater.inflate(R.layout.child_section_stat, null);
            //convertView.setMinimumHeight(1500);
            findWidgetsChild(convertView);
            fillDataChild(goal, convertView, goal.getThemeCategory());
        } else {
            convertView = inflater.inflate(R.layout.child_section_complete, null);
            //convertView.setMinimumHeight(1500);

            findUxDayGoal(convertView);
            if (!goal.getBlink()) {
                blink(convertView);
            }
            showDataDG(goal, convertView);

            //  groupItem =((ExpandableListView)parent).getExpandableListAdapter().getGroupView(groupPosition,true,null,parent);


            final LinearLayout showPopupDayTask = (LinearLayout) convertView.findViewById(R.id.showPopupDayTask);
            showPopupDayTask.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(mActivity);
                    String typeLang = mActivity.getResources().getConfiguration().locale.getLanguage();
                    if (typeLang.equals("en") || typeLang.equals("pl")) {
                        if (goal instanceof ReadBookGoal) {
                            alertDialog.setTitle("TODAY READ");
                            alertDialog.setMessage("Enter the number of pages read");
                        } else {
                            alertDialog.setTitle("NEW RESULT");
                            alertDialog.setMessage("Enter new progress");
                        }
                    } else {
                        if (goal instanceof ReadBookGoal) {
                            alertDialog.setTitle("СЕГОДНЯ ПРОЧИТАНО");
                            alertDialog.setMessage("Введите кол-во прочитаных страниц");
                        } else if (goal instanceof LanguageLearningGoal) {
                            alertDialog.setTitle("СЕГОДНЯ ИЗУЧАЛ");
                            alertDialog.setMessage("Введите кол-во часов изучения языка");
                        } else {
                            alertDialog.setTitle("НОВЫЙ РЕЗУЛЬТАТ");
                            alertDialog.setMessage("Введите новый прогресс");
                        }
                    }

                    final EditText input = new EditText(mActivity);
                    LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.MATCH_PARENT,
                            LinearLayout.LayoutParams.MATCH_PARENT);
                    input.setInputType(InputType.TYPE_CLASS_PHONE);
                    input.setLayoutParams(lp);
                    alertDialog.setView(input);

                    String btnOk;
                    String btnCancel;
                    if (typeLang.equals("en") || typeLang.equals("pl")) {
                        btnOk = "OK";
                        btnCancel = "CANCEL";
                    } else {
                        btnOk = "ОК";
                        btnCancel = "ОТМЕНА";
                    }

                    alertDialog.setPositiveButton(btnOk,
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    madeTodayResult = Double.parseDouble(input.getText().toString());
                                    madeToday.setText(madeTodayResult + "");
                                    goal.setMadeTodayResult(madeTodayResult);
//                                    if(goal instanceof  WeightCorrectionGoal
//                                            ||goal instanceof RepeatsCorrectionGoal
//                                            ||goal instanceof CardioGoal){
//                                        goal.setCurrentResult(madeTodayResult);
//                                    }
                                   /* double iR;
                                    double cR;
                                    double gR;
                                    double percent = 0;
                                    if(goal instanceof ReadBookGoal){
                                        cR = ((ReadBookGoal)goal).getCurrentResult();
                                        gR = ((ReadBookGoal)goal).getGoalResult();
                                        percent = Math.abs((cR/gR)*100);
                                    }else if(goal instanceof LanguageLearningGoal){
//                                        double mR = goal.getMadeTodayResult();
//                                        iR = goal.getInitialResult();
//                                        gR = goal.getGoalResult();
//                                        percent = Math.abs((mR/(gR-iR))*100);
                                    }else if(goal instanceof CardioGoal
                                            ||goal instanceof RepeatsCorrectionGoal||goal instanceof WeightCorrectionGoal) {
//                                        iR = goal.getInitialResult();
//                                        cR = goal.getCurrentResult();
//                                        gR = goal.getGoalResult();
//                                        percent = Math.abs(((iR - cR) / (gR - iR)) * 100);
                                    }else if(goal instanceof ElementCorrectionGoal){
                                        iR = ((ElementCorrectionGoal)goal).getInitialResult();
                                        cR =((ElementCorrectionGoal)goal).getCurrentResult2();
                                        percent = Math.round(Math.abs((iR - cR)/
                                                (500-((ElementCorrectionGoal)goal).getInitialResult())*100));
                                    }else {
                                        percent = 0;
                                    }

                                    goal.setProgress(Math.round(percent));*/
                                    //    int currentProgress = (int)Math.round(goal.getProgress());
                                    //    progressBar.setProgress(currentProgress);

                                    //    textCircleProgress.setText((int) currentProgress + "%");
                                    goal.setBlink(true);
                                    goal.save();
                                    dialog.cancel();
                                }
                            });

                    alertDialog.setNegativeButton(btnCancel,
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                }
                            });

                    alertDialog.show();
                }
            });
            showDataDG(goal, convertView);

            completed = (Button) convertView.findViewById(R.id.completed);
            relax = (Button) convertView.findViewById(R.id.relax);

            faceBold = Typeface.createFromAsset(mContext.getAssets(), "fonts/start_font.otf");
            completed.setTypeface(faceBold);
            relax.setTypeface(faceBold);
            completed.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SharedPreferences.Editor editor = sp.edit();
                    editor.putString("date" + goal.getThemeCategory() + goal.getNameGoal() + goal.getDescriptionGoal(), date);
                    editor.commit();
                    goal.setCompleted(true);
                    if (goal.getMadeTodayResult() == 0) {
                        goal.setCurrentResult(goal.getCurrentResult());
                    } else {
                        if (goal instanceof CardioGoal || goal instanceof WeightCorrectionGoal || goal instanceof RepeatsCorrectionGoal) {
                            goal.setCurrentResult(goal.getMadeTodayResult());
                        }
                        if (goal instanceof ReadBookGoal || goal instanceof LanguageLearningGoal) {
                            goal.setCurrentResult(goal.getMadeTodayResult());
                        }
                        if (goal instanceof ElementCorrectionGoal) {
                            ((ElementCorrectionGoal) goal).setCurrentResult2(goal.getMadeTodayResult());
                        }
                    }
                    double iR;
                    double cR;
                    double gR;
                    double percent = 0;
                    if (goal instanceof ReadBookGoal) {
                        cR = ((ReadBookGoal) goal).getCurrentResult();
                        gR = ((ReadBookGoal) goal).getGoalResult();
                        percent = Math.abs((cR / gR) * 100);
                    } else if (goal instanceof LanguageLearningGoal) {
                        double mR = goal.getMadeTodayResult();
                        iR = ((LanguageLearningGoal) goal).getInitialResult();
                        gR = ((LanguageLearningGoal) goal).getGoalResult();
                        percent = Math.abs((mR / (gR - iR)) * 100);
                    } else if (goal instanceof CardioGoal
                            || goal instanceof RepeatsCorrectionGoal || goal instanceof WeightCorrectionGoal) {
                        if (goal.getCurrentResult() == 0) {
                            double dayCompletedCardioRepeats = goal.getDayCompletedCardioRepeats() + 1;
                            goal.setDayCompletedCardioRepeats(dayCompletedCardioRepeats);
                            double allDays = getDifferenceInDays(goal.getFromDate(), goal.getToDate());
                            allDays = allDays / dayCompletedCardioRepeats;
                            percent = 100 / allDays;
                        } else {
                            iR = goal.getInitialResult();
                            cR = goal.getCurrentResult();
                            gR = goal.getGoalResult();
                            percent = Math.abs(((iR - cR) / (gR - iR)) * 100);
                        }
                    } else if (goal instanceof ElementCorrectionGoal) {
                        iR = ((ElementCorrectionGoal) goal).getInitialResult();
                        cR = ((ElementCorrectionGoal) goal).getCurrentResult2();
                        percent = Math.round(Math.abs((iR - cR) /
                                (500 - ((ElementCorrectionGoal) goal).getInitialResult()) * 100));
                    } else {
                        percent = 0;
                    }
                    goal.setProgress(Math.round(percent));
                    int currentProgress = (int) Math.round(goal.getProgress());
                    progressBar.setProgress(currentProgress);

                    if (currentProgress <= 30) {
                        progressBar.getProgressDrawable().setColorFilter(mActivity.
                                getResources().getColor(R.color.colorRed), PorterDuff.Mode.SRC_IN);
                        textCircleProgress.setTextColor(mActivity.getResources().getColor(R.color.colorRed));
                    }
                    if (currentProgress > 30 && currentProgress <= 60) {
                        progressBar.getProgressDrawable().setColorFilter(mActivity.
                                getResources().getColor(R.color.colorYellow), PorterDuff.Mode.SRC_IN);
                        textCircleProgress.setTextColor(mActivity.getResources().getColor(R.color.colorYellow));
                    }
                    if (currentProgress > 60 && currentProgress < 100) {
                        progressBar.getProgressDrawable().setColorFilter(mActivity.
                                getResources().getColor(R.color.colorGreen), PorterDuff.Mode.SRC_IN);
                        textCircleProgress.setTextColor(mActivity.getResources().getColor(R.color.colorGreen));
                    }
                    goal.save();
                    String typeLang = mActivity.getResources().getConfiguration().locale.getLanguage();
                    if (currentProgress >= 100) {
                        adb = new AlertDialog.Builder(mActivity);
                        if (typeLang.equals("en") || typeLang.equals("pl")) {
                            adb.setTitle("THE PURPOSE IS IMPLEMENTED! KEEP IT UP!");
                            adb.setMessage("WRITE THE FOLLOWING PURPOSE! FORWARD TO VALUE!");
                        } else {
                            adb.setTitle("ЦЕЛЬ ВЫПОЛНЕНА! ТАК ДЕРЖАТЬ!");
                            adb.setMessage("ЗАПИШИТЕ СЛЕДУЮЩУЮ ЦЕЛЬ! ВПЕРЁД К ВЕЛИЧИЮ!");
                        }
                        progressBar.getProgressDrawable().setColorFilter(mContext.
                                getResources().getColor(R.color.colorGreen), PorterDuff.Mode.SRC_IN);
                        textCircleProgress.setTextColor(mContext.getResources().getColor(R.color.colorGreen));
                        textCircleProgress.setText("100%");
                        adb.setPositiveButton("OK",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        notifyDataSetChanged();
                                        reload();
                                    }
                                });
                        adb.show();
                    } else {
                        adb = new AlertDialog.Builder(mActivity);
                        if (typeLang.equals("en") || typeLang.equals("pl")) {
                            adb.setTitle("Congratulations");
                            adb.setMessage("Go on! You are the best!");
                            adb.setPositiveButton("Thanks", null);
                        } else {
                            adb.setTitle("Поздравляем!");
                            adb.setMessage("Молодец! Продолжай в том же духе!");
                            adb.setPositiveButton("ОКЕЙ", null);
                        }
                        adb.setPositiveButton("OK",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        notifyDataSetChanged();
                                        reload();
                                    }
                                });
                        adb.setCancelable(true);
                        congrDialog = adb.create();
                        congrDialog.setCanceledOnTouchOutside(false);
                        congrDialog.show();
                    }
                }
            });
            relax.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SharedPreferences.Editor editor = sp.edit();
                    editor.putString("date" + goal.getThemeCategory() + goal.getNameGoal() + goal.getDescriptionGoal(), date);
                    editor.commit();
                    goal.setRelax(true);
                    goal.save();
                    notifyDataSetChanged();
                    //reload();
                }
            });
        }
        return convertView;
    }

    private void findUxDayGoal(View view) {
        nameData = (TextView) view.findViewById(R.id.nameData);
        distanceDG = (TextView) view.findViewById(R.id.distanceDG);
        currentResultDG = (TextView) view.findViewById(R.id.currentResultDG);
        goalResultDG = (TextView) view.findViewById(R.id.goalResultDG);
        taskDG = (TextView) view.findViewById(R.id.taskDG);
        madeToday = (TextView) view.findViewById(R.id.madeToday);
        goalDescriptionDG = (TextView) view.findViewById(R.id.goalDescriptionDG);
        taskOfWeekUnits = (TextView) view.findViewById(R.id.taskOfWeekUnits);
    }

    private void showDataDG(Goal goal, View view) {

        double dayTask = (goal.getGoalResult() - goal.getCurrentResult()) / getDifferenceInDays(new Date(), goal.getToDate());
        units = "";
        double leftGoalUnits = goal.getGoalResult() - goal.getCurrentResult();
        double currentNumber = 0, currentNumberSkills = 0;
        if (!(goal instanceof ElementCorrectionGoal)) {
            currentNumber = goal.getCurrentResult();
        } else currentNumberSkills = ((ElementCorrectionGoal) goal).getCurrentResult();

        if (goal.getThemeCategory().equals("КНИГА")) {
            if (goal.getDataBook().equals("")) {
                nameData.setText(goal.getNameGoal() + "");
            } else {
                nameData.setText(goal.getNameGoal() + " | " + goal.getDataBook());
            }
        } else {
            nameData.setText(goal.getNameGoal() + "");
        }
        String typeLang = mContext.getResources().getConfiguration().locale.getLanguage();
        switch (goal.getThemeCategory()) {
            case "МАССА":
                TextView titleTxt = (TextView) view.findViewById(R.id.titleTxt);
                TextView titleDG = (TextView) view.findViewById(R.id.titleDG);
                if (typeLang.equals("en") || typeLang.equals("pl")) {
                    titleTxt.setText("MY WEIGHT MAKES:");
                    units = "kg";
                } else {
                    titleTxt.setText("МОЙ ВЕС СОСТАВЛЯЕТ:");
                    units = "кг";
                }
                LinearLayout llTaskWeek = (LinearLayout) view.findViewById(R.id.llTaskWeek);
                LinearLayout separator3 = (LinearLayout) view.findViewById(R.id.separator3);
                TextView taskOfWeek = (TextView) view.findViewById(R.id.taskOfWeek);
                llTaskWeek.setVisibility(View.VISIBLE);
                separator3.setVisibility(View.VISIBLE);
                if (typeLang.equals("en") || typeLang.equals("pl")) {
                    taskDG.setText("training");
                    taskOfWeek.setText("TASK FOR A WEEK:");
                } else {
                    taskDG.setText("тренировка");
                    taskOfWeek.setText("ЗАДАЧА НА НЕДЕЛЮ:");
                }
                taskDG.setTextColor(Color.parseColor("#d23134"));
                if (goal.getCurrentResult() <= goal.getGoalResult()) {
                    taskOfWeekUnits.setText("+" + String.format("%.1f", dayTask * 7) + " " + units);
                } else {
                    taskOfWeekUnits.setText(String.format("%.1f", dayTask * 7) + " " + units);
                }
                titleDG.setTextColor(Color.parseColor("#d23134"));
                currentResultDG.setText(String.format("%.1f", currentNumber) + " " + units);
                goalResultDG.setText(String.format("%.1f", goal.getGoalResult()) + " " + units);
                madeToday.setText(String.format("%.1f", goal.getMadeTodayResult()) + " " + units);
                break;
            case "КАРДИО":
                String distanceMeters = "м";
                TextView distanceTitle = (TextView) view.findViewById(R.id.distanceTitle);
                if (typeLang.equals("en") || typeLang.equals("pl")) {
                    units = "seconds";
                } else units = "сек";
                if (goal.getCurrentResult() == 0 && goal.getGoalResult() == 0) { // regular attack
                    Button relax = (Button) view.findViewById(R.id.relax);
                    relax.setVisibility(View.VISIBLE);
                    LinearLayout llCurrentResult = (LinearLayout) view.findViewById(R.id.llCurrentResult);
                    LinearLayout showPopupDayTask = (LinearLayout) view.findViewById(R.id.showPopupDayTask);
                    LinearLayout separator4 = (LinearLayout) view.findViewById(R.id.separator4);
                    LinearLayout separator3RA = (LinearLayout) view.findViewById(R.id.separator3);
                    LinearLayout separator5 = (LinearLayout) view.findViewById(R.id.separator5);
                    llCurrentResult.setVisibility(View.GONE);
                    showPopupDayTask.setVisibility(View.GONE);
                    separator4.setVisibility(View.GONE);
                    separator3RA.setVisibility(View.GONE);
                    separator5.setVisibility(View.GONE);
                    if (typeLang.equals("en") || typeLang.equals("pl")) {
                        distanceDG.setText(goal.getDistance() + " meters");
                        taskDG.setText("bridge the distance");
                        goalResultDG.setText(goal.getDistance() + " " + "meters");
                        distanceTitle.setText("DISTANCE");
                    } else {
                        double distance = Math.abs(goal.getDistance() % 10);
                        if(distance >= 5 || distance == 0) distanceMeters = "метров";
                        else if(distance == 1) distanceMeters = "метр";
                        else distanceMeters = "метра";
                        if (typeLang.equals("en") || typeLang.equals("pl")) {
                            distanceMeters = "pages";
                        }
                        distanceDG.setText(goal.getDistance() + " " + distanceMeters);
                        taskDG.setText("преодолеть дистанцию");
                        goalResultDG.setText(goal.getDistance() + " " + distanceMeters);
                        distanceTitle.setText("ДИСТАНЦИЯ");
                    }
                    //madeToday.setText("ТРЕНИРОВКА");
                } else {
                    LinearLayout llDistance = (LinearLayout) view.findViewById(R.id.llDistance);
                    LinearLayout separator = (LinearLayout) view.findViewById(R.id.separator);
                    llDistance.setVisibility(View.VISIBLE);
                    separator.setVisibility(View.VISIBLE);
                    TextView titleTxtCardio = (TextView) view.findViewById(R.id.titleTxt);
                    double distance = Math.abs(goal.getDistance() % 10);
                    if(distance >= 5 || distance == 0) distanceMeters = "метров";
                    else if(distance == 1) distanceMeters = "метр";
                    else distanceMeters = "метра";
                    if (typeLang.equals("en") || typeLang.equals("pl")) {
                        titleTxtCardio.setText("MY NEW TIME:");
                        distanceDG.setText(goal.getDistance() + " meters");
                        taskDG.setText("bridge the distance");
                        distanceTitle.setText("DISTANCE:");
                    } else {
                        titleTxtCardio.setText("МОЁ НОВОЕ ВРЕМЯ:");
                        distanceDG.setText(goal.getDistance() + " " + distanceMeters);
                        taskDG.setText("преодолеть дистанцию");
                        distanceTitle.setText("ДИСТАНЦИЯ:");
                    }
                    currentResultDG.setText(currentNumber + " " + units);
                    goalResultDG.setText(goal.getGoalResult() + " " + units);
                    madeToday.setText(String.format("%.1f", goal.getMadeTodayResult()) + " " + units);
                }
                break;
            case "НАВЫКИ":
                skillsPoints(goal);
                TextView titleTxtSkill = (TextView) view.findViewById(R.id.titleTxt);
                unitsValidation(goal, ((ElementCorrectionGoal) goal).getCurrentResult2(), pointsSkillsGoal, goal.getMadeTodayResult()); //4 param will be madeToday instead left
                if (typeLang.equals("en") || typeLang.equals("pl")) {
                    titleTxtSkill.setText("ENTER THE NEW PROGRESS:");
                    units = "points";
                    taskDG.setText("training");
                    currentResultDG.setText(String.format("%.0f", ((ElementCorrectionGoal) goal).getCurrentResult()) + " level" + " / " + String.format("%.0f", ((ElementCorrectionGoal) goal).getCurrentResult2()) + " " + units);
                    goalResultDG.setText(String.format("%.0f", goal.getGoalResult()) + " level" + " / " + String.format("%.0f", pointsSkillsGoal) + " " + units);
                    madeToday.setText(String.format("%.0f", goal.getMadeTodayResult()) + " points");
                } else {
                    titleTxtSkill.setText("ВВЕДИ НОВЫЙ ПРОГРЕСС:");
                    units = "очки";
                    taskDG.setText("тренировка");
                    currentResultDG.setText(String.format("%.0f", ((ElementCorrectionGoal) goal).getCurrentResult()) + " уровень" + " / " + String.format("%.0f", ((ElementCorrectionGoal) goal).getCurrentResult2()) + " " + unitsCurrent);
                    goalResultDG.setText(String.format("%.0f", goal.getGoalResult()) + " уровень" + " / " + String.format("%.0f", pointsSkillsGoal) + " " + unitsGoal);
                    madeToday.setText(String.format("%.0f", goal.getMadeTodayResult()) + " " + unitsLeft);
                }
                break;
            case "ПОВТОРЕНИЯ":
                unitsValidation(goal, currentNumber, goal.getGoalResult(), goal.getMadeTodayResult()); //4 param will be madeToday instead left
                if (goal.getCurrentResult() == 0) { // regular attack
                    Button relax2 = (Button) view.findViewById(R.id.relax);
                    relax2.setVisibility(View.VISIBLE);
                    LinearLayout llCurrentResult = (LinearLayout) view.findViewById(R.id.llCurrentResult);
                    LinearLayout showPopupDayTask = (LinearLayout) view.findViewById(R.id.showPopupDayTask);
                    LinearLayout separator4 = (LinearLayout) view.findViewById(R.id.separator4);
                    LinearLayout separator5 = (LinearLayout) view.findViewById(R.id.separator5);
                    llCurrentResult.setVisibility(View.GONE);
                    separator4.setVisibility(View.GONE);
                    separator5.setVisibility(View.GONE);
                    showPopupDayTask.setVisibility(View.GONE);
                    if (typeLang.equals("en") || typeLang.equals("pl")) {
                        taskDG.setText("execute repeats");
                    } else {
                        taskDG.setText("выполнить повторения");
                    }
                } else {
                    titleTxt = (TextView) view.findViewById(R.id.titleTxt);
                    if (typeLang.equals("en") || typeLang.equals("pl")) {
                        taskDG.setText("max. number of repetitions");
                        titleTxt.setText("MY NEW PROGRESS:");
                    } else {
                        taskDG.setText("макс. кол-во повторений");
                        titleTxt.setText("МОЙ НОВЫЙ ПРОГРЕСС:");
                    }
                }
                if(typeLang.equals("en") || typeLang.equals("pl")) {
                    unitsCurrent = "repetitions";
                    unitsGoal = "repetitions";
                    unitsLeft = "repetitions";
                }
                //graduationUnits(goal, currentNumber, goal.getGoalResult(), goal.getMadeTodayResult());
                currentResultDG.setText(String.format("%.0f", currentNumber) + " " + unitsCurrent);
                goalResultDG.setText(String.format("%.0f", goal.getGoalResult()) + " " + unitsGoal);
                madeToday.setText(String.format("%.0f", goal.getMadeTodayResult()) + " " + unitsLeft);
                break;
            case "КНИГА":
                unitsValidation(goal, currentNumber, goal.getGoalResult(), goal.getMadeTodayResult()); //4 param will be madeToday instead left
                titleTxt = (TextView) view.findViewById(R.id.titleTxt);
                double dayTask2 = Math.abs(dayTask % 10);
                if(dayTask2 >= 5 || dayTask2 == 0) units = "страниц";
                else if(dayTask2 == 1) units = "страница";
                else units = "страницы";
                if (typeLang.equals("en") || typeLang.equals("pl")) {
                    units = "pages";
                    unitsCurrent = "pages";
                    unitsGoal = "pages";
                    unitsLeft = "pages";
                    titleTxt.setText("TODAY READ:");
                }
                dayTask = Math.ceil(dayTask);
                taskDG.setText(String.format("%.0f", dayTask) + " " + units);
                currentResultDG.setText(String.format("%.0f", currentNumber) + " " + unitsCurrent);
                goalResultDG.setText(String.format("%.0f", goal.getGoalResult()) + " " + unitsGoal);
                madeToday.setText(String.format("%.0f", goal.getMadeTodayResult()) + " " + unitsLeft);
                break;
            case "ЯЗЫКИ":
                unitsValidation(goal, currentNumber, goal.getGoalResult(), goal.getMadeTodayResult()); //4 param will be madeToday instead left
                titleTxt = (TextView) view.findViewById(R.id.titleTxt);
                languageLevelInHours(goal);
                double lvlDayTask = (lvlLangHoursGoal - lvlLangHoursCurrent) / getDifferenceInDays(new Date(), goal.getToDate());
                double lvlDayTask2 = Math.abs(lvlDayTask % 10);
                if(lvlDayTask2 >= 5 || lvlDayTask2 == 0) units = "часов";
                else if(lvlDayTask2 == 1) units = "час";
                else units = "часа";
                if (typeLang.equals("en") || typeLang.equals("pl")) {
                    units = "hours";
                    unitsCurrent = "hours";
                    unitsGoal = "hours";
                    unitsLeft = "hours";
                    titleTxt.setText("TODAY READ:");
                } else {
                    titleTxt.setText("СЕГОДНЯ ИЗУЧЕНО:");
                }
                lvlDayTask = Math.round(lvlDayTask);
                taskDG.setText(String.format("%.0f", lvlDayTask) + " " + units);
                currentResultDG.setText(goal.getCurrentLanguageLevel() + " / " + String.format("%.0f", currentNumber) + " " + unitsCurrent);
                goalResultDG.setText(goal.getGoalLanguageLevel() + " / " + String.format("%.0f", goal.getGoalResult()) + " " + unitsGoal);
                madeToday.setText(String.format("%.0f", goal.getMadeTodayResult()) + " " + unitsLeft);
                break;
        }

        /* FOR ALL GOALS */
        if (goal.getDescriptionGoal().equals("")) {
            if (typeLang.equals("en") || typeLang.equals("pl")) {
                goalDescriptionDG.setText("YOU DID NOT LOSE UNTIL YOU CAME !"); // default string
            } else goalDescriptionDG.setText("ТЫ НЕ ПРОИГРАЛ ПОКА НЕ СДАЛСЯ !"); // default string
        } else {
            goalDescriptionDG.setText(goal.getDescriptionGoal() + "");
        }
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    private void fillDataChild(Goal goal, View convertView, String themeCategory) {

        String units = "";
        double currentNumber = 0, goalNumber = 0;

        currentNumber = goal.getCurrentResult();
        goalNumber = goal.getGoalResult();

        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        String fromDate = String.valueOf(formatter.format(goal.getFromDate()));
        String toDate = String.valueOf(formatter.format(goal.getToDate()));
        double leftGoalUnits = goal.getGoalResult() - goal.getCurrentResult();
        double dayTask = (goal.getGoalResult() - goal.getCurrentResult()) / getDifferenceInDays(new Date(), goal.getToDate());
        String typeLang = mContext.getResources().getConfiguration().locale.getLanguage();
        switch (themeCategory) {
            case "МАССА":
                LinearLayout llTaskWeek = (LinearLayout) convertView.findViewById(R.id.llTaskWeek);
                LinearLayout separator3 = (LinearLayout) convertView.findViewById(R.id.separator3);
                TextView taskOfWeek = (TextView) convertView.findViewById(R.id.taskOfWeek);
                llTaskWeek.setVisibility(View.VISIBLE);
                separator3.setVisibility(View.VISIBLE);
                if (typeLang.equals("en") || typeLang.equals("pl")) {
                    units = "kg";
                    taskOfDayUnits.setText("training");
                    taskOfWeek.setText("TASK FOR A WEEK:");
                } else {
                    units = "кг";
                    taskOfDayUnits.setText("тренировка");
                    taskOfWeek.setText("ЗАДАЧА НА НЕДЕЛЮ:");
                }
                currentResultUnits.setText(String.format("%.1f", currentNumber) + " " + units);
                goalResultUnits.setText(String.format("%.1f", goalNumber) + " " + units);
                if (goal.getCurrentResult() <= goal.getGoalResult()) {
                    leftToGoalUnits.setText("+" + String.format("%.1f", leftGoalUnits) + " " + units);
                    taskOfWeekUnits.setText("+" + String.format("%.1f", dayTask * 7) + " " + units);
                } else {
                    leftToGoalUnits.setText(String.format("%.1f", leftGoalUnits) + " " + units);
                    taskOfWeekUnits.setText(String.format("%.1f", dayTask * 7) + " " + units);
                }
                break;
            case "КАРДИО":
                String unitsDistance;
                if (typeLang.equals("en") || typeLang.equals("pl")) {
                    units = "seconds";
                    taskOfDayUnits.setText("bridge the distance");
                } else {
                    units = "сек";
                    taskOfDayUnits.setText("преодолеть дистанцию");
                }
                if (goal.getCurrentResult() == 0) {
                    LinearLayout runDistance = (LinearLayout) convertView.findViewById(R.id.runDistance);
                    LinearLayout llCurrentResult = (LinearLayout) convertView.findViewById(R.id.llCurrentResult);
                    LinearLayout llLeftToGoal = (LinearLayout) convertView.findViewById(R.id.llLeftToGoal);
                    LinearLayout separator1 = (LinearLayout) convertView.findViewById(R.id.separator1);
                    LinearLayout separator7 = (LinearLayout) convertView.findViewById(R.id.separator7);
                    LinearLayout separator8 = (LinearLayout) convertView.findViewById(R.id.separator8);
                    runDistance.setVisibility(View.GONE);
                    llCurrentResult.setVisibility(View.GONE);
                    llLeftToGoal.setVisibility(View.GONE);
                    separator1.setVisibility(View.GONE);
                    separator7.setVisibility(View.GONE);
                    separator8.setVisibility(View.GONE);
                    double distance2 = Math.abs(goal.getDistance() % 10);
                    if(distance2 >= 5 || distance2 == 0) unitsDistance = "метров";
                    else if(distance2 == 1) unitsDistance = "метр";
                    else unitsDistance = "метра";
                    if (typeLang.equals("en") || typeLang.equals("pl")) {
                        goalResultUnits.setText(goal.getDistance() + " meters");
                    } else {
                        goalResultUnits.setText(goal.getDistance() + " " + unitsDistance);
                    }
                } else {
                    TextView distanceRun = (TextView) convertView.findViewById(R.id.distanceRun);
                    if (typeLang.equals("en") || typeLang.equals("pl")) {
                        distanceRun.setText("DISTANCE:");
                    } else {
                        distanceRun.setText("ДИСТАНЦИЯ:");
                    }
                    runDistance.setVisibility(View.VISIBLE);
                    separator2.setVisibility(View.VISIBLE);
                    if (typeLang.equals("en") || typeLang.equals("pl")) {
                        goalResultUnits.setText(goal.getGoalResult() + " meters");
                    }
                    goalResultUnits.setText(goal.getGoalResult() + " " + units);
                }
                currentResultUnits.setText(String.format("%.1f", currentNumber) + " " + units);
                leftToGoalUnits.setText(String.format("%.1f", leftGoalUnits) + " " + units);
                break;
            case "НАВЫКИ":
                skillsPoints(goal);
                //double leftPointsSkill = (pointsSkillsGoal - pointsSkillsCurrent) / getDifferenceInDays(new Date(), goal.getToDate()
                double left = 5 - ((ElementCorrectionGoal) goal).getCurrentResult();
                double leftUnits = 500 - ((ElementCorrectionGoal) goal).getCurrentResult2();
                unitsValidation(goal, ((ElementCorrectionGoal) goal).getCurrentResult2(), 0, leftUnits);
                if(left == 0 || left == 5) {
                    unitsLvlLeft = "уровней";
                } else if(left == 1) {
                    unitsLvlLeft = "уровень";
                } else unitsLvlLeft = "уровня";
                if (typeLang.equals("en") || typeLang.equals("pl")) {
                    units = "level";
                    currentResultUnits.setText(String.format("%.0f", ((ElementCorrectionGoal) goal).getCurrentResult()) + " " + units + " / " + String.format("%.0f", ((ElementCorrectionGoal) goal).getCurrentResult2()) + " points");
                    goalResultUnits.setText(String.valueOf(5) + " " + units + " / " + "500 points");
                    taskOfDayUnits.setText("training");
                    leftToGoalUnits.setText(String.format("%.0f", left) + " " + "level" + " / " + String.format("%.0f", leftUnits) + " points");
                } else {
                    units = "уровень";
                    currentResultUnits.setText(String.format("%.0f", ((ElementCorrectionGoal) goal).getCurrentResult()) + " " + units + " / " + String.format("%.0f", ((ElementCorrectionGoal) goal).getCurrentResult2()) + " " + unitsCurrent);
                    goalResultUnits.setText(String.valueOf(5) + " " + units + " / " + "500 очков");
                    taskOfDayUnits.setText("тренировка");
                    leftToGoalUnits.setText(String.format("%.0f", left) + " " + unitsLvlLeft + " / " + String.format("%.0f", leftUnits) + " " + unitsLeft);
                }
                break;
            case "ПОВТОРЕНИЯ":
                unitsValidation(goal, currentNumber, goalNumber, leftGoalUnits);
                if (goal.getCurrentResult() == 0) {
                    LinearLayout runDistance = (LinearLayout) convertView.findViewById(R.id.runDistance);
                    LinearLayout llCurrentResult = (LinearLayout) convertView.findViewById(R.id.llCurrentResult);
                    LinearLayout llLeftToGoal = (LinearLayout) convertView.findViewById(R.id.llLeftToGoal);
                    LinearLayout separator1 = (LinearLayout) convertView.findViewById(R.id.separator1);
                    LinearLayout separator7 = (LinearLayout) convertView.findViewById(R.id.separator7);
                    LinearLayout separator8 = (LinearLayout) convertView.findViewById(R.id.separator8);
                    runDistance.setVisibility(View.GONE);
                    llCurrentResult.setVisibility(View.GONE);
                    llLeftToGoal.setVisibility(View.GONE);
                    separator1.setVisibility(View.GONE);
                    separator7.setVisibility(View.GONE);
                    separator8.setVisibility(View.GONE);
                    if (typeLang.equals("en") || typeLang.equals("pl")) {
                        taskOfDayUnits.setText("max. number of repetitions");
                        unitsGoal = "repeats";
                    } else {
                        taskOfDayUnits.setText("макс. кол-во повторений");
                    }
                    goalResultUnits.setText(String.format("%.0f", goalNumber) + " " + unitsGoal);
                } else {
                    if (typeLang.equals("en") || typeLang.equals("pl")) {
                        unitsGoal = "repeats";
                        taskOfDayUnits.setText("max. number of repetitions");
                    } else {
                        taskOfDayUnits.setText("макс. кол-во повторений");
                    }
                    goalResultUnits.setText(String.format("%.0f", goalNumber) + " " + unitsGoal);
                }
                if (typeLang.equals("en") || typeLang.equals("pl")) {
                    unitsCurrent = "repeats";
                    unitsLeft = "repeats";
                }
                currentResultUnits.setText(String.format("%.0f", currentNumber) + " " + unitsCurrent);
                leftToGoalUnits.setText(String.format("%.0f", leftGoalUnits) + " " + unitsLeft);
                break;
            case "КНИГА":
                unitsValidation(goal, currentNumber, goalNumber, leftGoalUnits);
                if (goal.getDataBook().equals(" ") || goal.getDataBook().equals("")) {
                    bookPresent.setVisibility(View.GONE);
                    separator1.setVisibility(View.GONE);
                } else {
                    bookPresent.setVisibility(View.VISIBLE);
                    separator1.setVisibility(View.VISIBLE);
                }
                dayTask = Math.ceil(dayTask);
                double dayTask2 = Math.abs(dayTask % 10);
                if(dayTask2 == 0 || dayTask2 >= 5) units = "страниц";
                else if(dayTask2 == 1) units = "страница";
                else units = "страницы";
                if (typeLang.equals("en") || typeLang.equals("pl")) {
                    units = "pages";
                    unitsLeft = "pages";
                    unitsCurrent = "pages";
                    unitsGoal = "pages";
                }
                taskOfDayUnits.setText(String.format("%.0f", dayTask) + " " + units);
                leftToGoalUnits.setText(String.format("%.0f", leftGoalUnits) + " " + unitsLeft);
                currentResultUnits.setText(String.format("%.0f", currentNumber) + " " + unitsCurrent);
                goalResultUnits.setText(String.format("%.0f", goalNumber) + " " + unitsGoal);
                break;
            case "ЯЗЫКИ":
                unitsValidation(goal, goal.getCurrentResult(), lvlLangHoursGoal, goal.getGoalResult() - goal.getCurrentResult());
                languageLevelInHours(goal);
                double lvlLangLeftGoal = (lvlLangHoursGoal - lvlLangHoursCurrent) / getDifferenceInDays(new Date(), goal.getToDate());
                double lvlLangLeftGoal2 = Math.abs(lvlLangLeftGoal % 10);
                if(lvlLangLeftGoal2 >= 5 || lvlLangLeftGoal2 == 0) units = "часов";
                else if(lvlLangLeftGoal2 == 1) units = "час";
                else units = "часа";
                if (typeLang.equals("en") || typeLang.equals("pl")) {
                    units = "hours";
                    unitsGoal = "hours";
                    unitsLeft = "hours";
                    unitsCurrent = "hours";
                }
                currentResultUnits.setText(((LanguageLearningGoal) goal).getCurrentLanguageLevel() + " / " + String.format("%.0f", goal.getCurrentResult()) + " " + unitsCurrent);
                goalResultUnits.setText(goal.getGoalLanguageLevel() + " / " + String.format("%.0f", lvlLangHoursGoal) + " " + unitsGoal);
                lvlLangLeftGoal = Math.round(lvlLangLeftGoal);
                taskOfDayUnits.setText(String.format("%.0f", lvlLangLeftGoal) + " " + units);
                leftToGoalUnits.setText(String.format("%.0f", (goal.getGoalResult() - goal.getCurrentResult())) + " " + unitsLeft);
                break;
        }

        /* FOR ALL GOALS */
        if (typeLang.equals("en") || typeLang.equals("pl")) {
            distanceRunUnits.setText("" + goal.getDistance() + " meters");
        } else distanceRunUnits.setText("" + goal.getDistance() + " метров");
        dataBook.setText(goal.getDataBook());
        double currentProgressStatus = goal.getProgress();
        verifyStatus(currentProgressStatus, convertView); // СТАТУС
        if (goal.getDescriptionGoal().equals("")) {
            if (typeLang.equals("en") || typeLang.equals("pl")) {
                goalDescription.setText("You did not lose until you came!"); // в том случае если никто не ввел описание
            } else
                goalDescription.setText("Ты не проиграл пока не сдался!"); // в том случае если никто не ввел описание
        } else {
            goalDescription.setText(goal.getDescriptionGoal() + ""); // ОПИСАНИЕ ЦЕЛИ
        }
        leftDaysGoal.setText(getDifferenceInDays(new Date(), goal.getToDate()) + "");   // СКОЛЬКО ДНЕЙ ОСТАЛОСЬ
        if (typeLang.equals("en") || typeLang.equals("pl")) {
            fromGoal.setText("FROM " + fromDate); // ОТ ЧИСЛА
            toGoal.setText("TO " + toDate); // ДО ЧИСЛА
        } else {
            fromGoal.setText("ОТ " + fromDate); // ОТ ЧИСЛА
            toGoal.setText("ДО " + toDate); // ДО ЧИСЛА
        }
    }

    private int getDifferenceInDays(Date from, Date to) {
        long milliseconds = to.getTime() - from.getTime();
        return 1 + (int) (milliseconds = Integer.parseInt(String.valueOf(TimeUnit.DAYS.convert(milliseconds, TimeUnit.MILLISECONDS))));
    }

    private void verifyStatus(double currentProgressStatus, View view) {
        statusGoal = (ImageView) view.findViewById(R.id.statusGoal);
        if (currentProgressStatus <= 30) {
            if (android.os.Build.VERSION.SDK_INT >= 21) {
                statusGoal.setBackground(mContext.getResources().getDrawable(R.drawable.circle_status_red));
            } else {
                statusGoal.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.circle_status_red));
            }
        }
        if (currentProgressStatus > 30 && currentProgressStatus <= 60) {
            if (android.os.Build.VERSION.SDK_INT >= 21) {
                statusGoal.setBackground(mContext.getResources().getDrawable(R.drawable.circle_status_yellow));
            } else {
                statusGoal.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.circle_status_yellow));
            }
        }
        if (currentProgressStatus > 60 && currentProgressStatus < 100 || currentProgressStatus >= 100) {
            if (android.os.Build.VERSION.SDK_INT >= 21) {
                statusGoal.setBackground(mContext.getResources().getDrawable(R.drawable.circle_status_green));
            } else {
                statusGoal.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.circle_status_green));
            }
        }
    }

    private void expandGoal(Boolean isExpanded, View convertView) {
        if (isExpanded) {
            arrowDownUp = (ImageView) convertView.findViewById(R.id.arrowDownUp);
            arrowDownUp.setRotation(180);
            arrowDownUp.getResources().getDrawable(R.drawable.arrow_goal);
        } else {
            arrowDownUp = (ImageView) convertView.findViewById(R.id.arrowDownUp);
            arrowDownUp.setRotation(0);
            arrowDownUp.getResources().getDrawable(R.drawable.arrow_goal);
        }
    }

    private void blink(final View view) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                int timeToBlink = 1000;    //in milissegunds
                try {
                    Thread.sleep(timeToBlink);
                } catch (Exception e) {
                }
                view.post(new Runnable() {
                    @Override
                    public void run() {
                        TextView txt = (TextView) view.findViewById(R.id.madeToday);
                        TextView titleTxt = (TextView) view.findViewById(R.id.titleTxt);
                        LinearLayout showPopupDayTask = (LinearLayout) view.findViewById(R.id.showPopupDayTask);
                        if (txt.getVisibility() == View.VISIBLE) {
                            txt.setVisibility(View.INVISIBLE);
                            showPopupDayTask.setBackgroundColor(Color.parseColor("#44b648"));
                            titleTxt.setTextColor(Color.WHITE);
                        } else {
                            txt.setVisibility(View.VISIBLE);
                            showPopupDayTask.setBackgroundColor(Color.parseColor("#FFE4E4E4"));
                            titleTxt.setTextColor(Color.BLACK);
                        }
                        blink(view);
                    }
                });
            }
        }).start();
    }

    private void languageLevelInHours(Goal goal) {
        if (goal.getCurrentLanguageLevel().equals(LanguageLevels.A1)) {
            lvlLangHoursCurrent = 70;
        } else if (goal.getCurrentLanguageLevel().equals(LanguageLevels.A2)) {
            lvlLangHoursCurrent = 140;
        } else if (goal.getCurrentLanguageLevel().equals(LanguageLevels.B1)) {
            lvlLangHoursCurrent = 210;
        } else if (goal.getCurrentLanguageLevel().equals(LanguageLevels.B2)) {
            lvlLangHoursCurrent = 280;
        } else if (goal.getCurrentLanguageLevel().equals(LanguageLevels.C1)) {
            lvlLangHoursCurrent = 350;
        } else if (goal.getCurrentLanguageLevel().equals(LanguageLevels.C2)) {
            lvlLangHoursCurrent = 420;
        }

        if (goal.getGoalLanguageLevel().equals(LanguageLevels.A1)) {
            lvlLangHoursGoal = 70;
        } else if (goal.getGoalLanguageLevel().equals(LanguageLevels.A2)) {
            lvlLangHoursGoal = 140;
        } else if (goal.getGoalLanguageLevel().equals(LanguageLevels.B1)) {
            lvlLangHoursGoal = 210;
        } else if (goal.getGoalLanguageLevel().equals(LanguageLevels.B2)) {
            lvlLangHoursGoal = 280;
        } else if (goal.getGoalLanguageLevel().equals(LanguageLevels.C1)) {
            lvlLangHoursGoal = 350;
        } else if (goal.getGoalLanguageLevel().equals(LanguageLevels.C2)) {
            lvlLangHoursGoal = 420;
        }
    }

    private void skillsPoints(Goal goal) {
        if (goal.getCurrentResult() == 1) {
            pointsSkillsCurrent = 100;
        } else if (goal.getCurrentResult() == 2) {
            pointsSkillsCurrent = 200;
        } else if (goal.getCurrentResult() == 3) {
            pointsSkillsCurrent = 300;
        } else if (goal.getCurrentResult() == 4) {
            pointsSkillsCurrent = 400;
        } else if (goal.getCurrentResult() == 5) {
            pointsSkillsCurrent = 500;
        }

        if (goal.getGoalResult() == 1) {
            pointsSkillsGoal = 100;
        } else if (goal.getGoalResult() == 2) {
            pointsSkillsGoal = 200;
        } else if (goal.getGoalResult() == 3) {
            pointsSkillsGoal = 300;
        } else if (goal.getGoalResult() == 4) {
            pointsSkillsGoal = 400;
        } else if (goal.getGoalResult() == 5) {
            pointsSkillsGoal = 500;
        }
    }

    private void reload() {
        Intent intent = mActivity.getIntent();
        mActivity.overridePendingTransition(0, 0);//4
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);//5
        mActivity.finish();//6
        mActivity.overridePendingTransition(0, 0);//7
        mActivity.startActivity(intent);//8*/
    }

    private void unitsValidation(Goal goal, double current, double goalResult, double left) {
        current = Math.abs(current % 10);
        goalResult = Math.abs(goalResult % 10);
        left = Math.abs(left % 10);
        if (goal instanceof ElementCorrectionGoal) {
            if(current >= 5 || current == 0) unitsCurrent = "очков";
            else if(current == 1) unitsCurrent = "очко";
            else unitsCurrent = "очка";
            if(goalResult >= 5 || goalResult == 0) unitsGoal = "очков";
            else if(goalResult == 1) unitsGoal = "очко";
            else unitsGoal = "очка";
            if(left >= 5 || left == 0) unitsLeft = "очков";
            else if(left == 1) unitsLeft = "очко";
            else unitsLeft = "очка";
        } else if(goal instanceof RepeatsCorrectionGoal) {
            if(current >= 5 || current == 0) unitsCurrent = "повторений";
            else if(current == 1) unitsCurrent = "повторение";
            else unitsCurrent = "повторения";
            if(goalResult >= 5 || goalResult == 0) unitsGoal = "повторений";
            else if(goalResult == 1) unitsGoal = "повторение";
            else unitsGoal = "повторения";
            if(left >= 5 || left == 0) unitsLeft = "повторений";
            else if(left == 1) unitsLeft = "повторение";
            else unitsLeft = "повторения";
        } else if(goal instanceof LanguageLearningGoal) {
            if(current >= 5 || current == 0) unitsCurrent = "часов";
            else if(current == 1) unitsCurrent = "час";
            else unitsCurrent = "часа";
            if(goalResult >= 5 || goalResult == 0) unitsGoal = "часов";
            else if(goalResult == 1) unitsGoal = "час";
            else unitsGoal = "часа";
            if(left >= 5 || left == 0) unitsLeft = "часов";
            else if(left == 1) unitsLeft = "час";
            else unitsLeft = "часа";
        } else if(goal instanceof ReadBookGoal) {
            if(current >= 5 || current == 0) unitsCurrent = "страниц";
            else if(current == 1) unitsCurrent = "страница";
            else unitsCurrent = "страницы";
            if(goalResult >= 5 || goalResult == 0) unitsGoal = "страниц";
            else if(goalResult == 1) unitsGoal = "страница";
            else unitsGoal = "страницы";
            if(left >= 5 || left == 0) unitsLeft = "страниц";
            else if(left == 1) unitsLeft = "страница";
            else unitsLeft = "страницы";
        }
    }
}