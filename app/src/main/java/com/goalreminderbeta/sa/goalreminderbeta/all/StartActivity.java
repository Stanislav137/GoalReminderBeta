package com.goalreminderbeta.sa.goalreminderbeta.all;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.preference.PreferenceManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.goalreminderbeta.sa.goalreminderbeta.R;
import com.goalreminderbeta.sa.goalreminderbeta.additional.BootStrap;
import com.goalreminderbeta.sa.goalreminderbeta.additional.DayPicker;
import com.goalreminderbeta.sa.goalreminderbeta.additional.notification.MyService;
import com.goalreminderbeta.sa.goalreminderbeta.goals.ElementCorrectionGoal;
import com.goalreminderbeta.sa.goalreminderbeta.goals.Goal;
import com.goalreminderbeta.sa.goalreminderbeta.goals.LanguageLearningGoal;
import com.goalreminderbeta.sa.goalreminderbeta.goals.ReadBookGoal;
import com.goalreminderbeta.sa.goalreminderbeta.goals.RepeatsCorrectionGoal;
import com.goalreminderbeta.sa.goalreminderbeta.goals.CardioGoal;
import com.goalreminderbeta.sa.goalreminderbeta.goals.WeightCorrectionGoal;
import com.goalreminderbeta.sa.goalreminderbeta.options.ConfigActivity;
import com.goalreminderbeta.sa.goalreminderbeta.options.OptionsActivity;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StartActivity extends AppCompatActivity {

    private SwipeRefreshLayout mSwipeRefreshLayout;
    private Button startAddGoal;
    private ExpandableListView allGoalsList;
    private List<Goal> allGoals;
    private BootStrap bootStrap;
    private boolean logicAddGoal;
    private Animation anim = null;
    private ExpListAdapter adapter;
    private boolean switchQuote, verifyDay;
    private String date,dateFromSP;
    Intent serviceIntent;
    private SharedPreferences sp;

    private SharedPreferences sPref;
    private int countHelpUser = 0;
    private SharedPreferences.Editor editor;
    private GregorianCalendar calendar;

    private Dialog dialog,warningDialog;
    private AlertDialog.Builder adb;
    private View warningView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start_activity);

        startAddGoal = (Button) findViewById(R.id.startAddGoal);
        allGoalsList = (ExpandableListView) findViewById(R.id.allGoalsList);

        allGoals = new ArrayList<>();
        bootStrap = new BootStrap();

        sp = PreferenceManager.getDefaultSharedPreferences(this);
        loadCount();
        if(countHelpUser == 0) {
            helpUserStart();
        }

        setListenersOnChild();
        setListenerOnGroup();
        printAllGoals();
        setListenersOnTitle();
        startAnimAddGoal();

    }

    private void helpUserStart() {
        final Dialog dialog;
        dialog = new Dialog(StartActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.help_user_start);
        Button closePopup = (Button) dialog.findViewById(R.id.closePopup);
        dialog.show();
        closePopup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        countHelpUser++;
    }

    void saveCount() {
        sPref = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor ed = sPref.edit();
        ed.putInt("SAVE_COUNT", countHelpUser);
        ed.commit();
    }

    void loadCount() {
        sPref = getPreferences(MODE_PRIVATE);
        countHelpUser = sPref.getInt("SAVE_COUNT", countHelpUser);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        saveCount();
    }
    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(allGoals.size() != 0) {
            calendar = new GregorianCalendar();
            int day = calendar.get(Calendar.DATE);
            int month = calendar.get(Calendar.MONTH);
            month += 1;
            int year = calendar.get(Calendar.YEAR);
            date = String.valueOf(day) + "." + String.valueOf(month) + "." + String.valueOf(year);
            sp = PreferenceManager.getDefaultSharedPreferences(this);
            dateFromSP = sp.getString("date", "");
            if (!date.equals(dateFromSP)) {
                verifyDay = true;
            } else {
                verifyDay = false;
            }
            editor = sp.edit();
            editor.putInt("goals", allGoals.size());
            editor.commit();
            if (verifyDay) {
                dialog = new Dialog(StartActivity.this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.verify_day);
                dialog.setCanceledOnTouchOutside(false);
                Button goToWorkOnGoals = (Button) dialog.findViewById(R.id.goToWorkOnGoals);
                Button goToRelax = (Button) dialog.findViewById(R.id.goToRelax);
                serviceIntent = new Intent(this, MyService.class);
                if (sp.getInt("goals", 0) > 0) {
                    serviceIntent.putExtra("title", "You goals are ready!");
                    serviceIntent.putExtra("text", "Keep it up!");
                } else {
                    serviceIntent.putExtra("title", "You have no goals!");
                    serviceIntent.putExtra("text", "Add some goal to start");
                }

                goToWorkOnGoals.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        editor = sp.edit();
                        editor.putString("date", date);
                        editor.commit();

                        if (sp.getBoolean("main", true)) {
                            serviceIntent.putExtra("interval", "1");
                            serviceIntent.putExtra("delay", 3600000);
                            serviceIntent.putExtra("notification", true);
                            serviceIntent.putExtra("sound", true);
                            serviceIntent.putExtra("vibration", true);
                        } else {
                            serviceIntent.putExtra("interval", sp.getString("interval", ""));
                            String delayStr = sp.getString("interval", "");
                            long delay = Integer.parseInt(delayStr) * 1000 * 3600;
                            serviceIntent.putExtra("delay", delay);
                            serviceIntent.putExtra("notification", sp.getBoolean("notification", true));
                            serviceIntent.putExtra("sound", sp.getBoolean("sound", true));
                            serviceIntent.putExtra("vibration", sp.getBoolean("vibration", true));
                        }
                        startService(serviceIntent);
                        dialog.cancel();

                    }
                });
                goToRelax.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        editor = sp.edit();
                        editor.putString("date", date);
                        editor.commit();
                        if (sp.getBoolean("main", true)) {
                            serviceIntent.putExtra("interval", "1");
                            serviceIntent.putExtra("delay", 3600000);
                            serviceIntent.putExtra("notification", true);
                            serviceIntent.putExtra("sound", true);
                            serviceIntent.putExtra("vibration", true);
                        } else {
                            serviceIntent.putExtra("interval", sp.getString("interval", ""));
                            long delay = DayPicker.getDelay();
                            serviceIntent.putExtra("delay", delay);
                            serviceIntent.putExtra("notification", sp.getBoolean("notification", true));
                            serviceIntent.putExtra("sound", sp.getBoolean("sound", true));
                            serviceIntent.putExtra("vibration", sp.getBoolean("vibration", true));
                        }
                        startService(serviceIntent);
                        finish();
                        dialog.dismiss();
                    }
                });
                dialog.show();
            }
        }
    }

    private void setListenersOnChild(){
        allGoalsList.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {


                final Goal goal = (Goal) parent.getExpandableListAdapter().getChild(groupPosition, childPosition);
                //Получаем объект по нажатию на него внутри групы (тепер можем его удалить либо модифицировать)

                if (goal!=null){ // Если объект нашелся, удаляем по нажатии на него внутри группы

                    final Dialog dialog;
                    dialog = new Dialog(StartActivity.this);
                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    dialog.setContentView(R.layout.confirm_deletion);
                    dialog.show();
                    Button delete = (Button) dialog.findViewById(R.id.delete);
                    Button back = (Button) dialog.findViewById(R.id.back);
                    delete.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            goal.delete();
                            dialog.dismiss();
                            printAllGoals();
                        }
                    });
                    back.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                        }
                    });
                }
                printAllGoals(); // обновляем наш лейаут после удаления

                if (allGoals.size() == 0) {
                    startAddGoal.setVisibility(View.VISIBLE);
                    bootStrapAddGoalCenter();
                } else {
                    startAddGoal.setVisibility(View.VISIBLE);
                    bootStrapAddGoalDown();
                }
                startAnimAddGoal();
                return false;
            }
        });
    }
    private void setLongListenersOnChild(){
        allGoalsList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                if (ExpandableListView.getPackedPositionType(id) == ExpandableListView.PACKED_POSITION_TYPE_CHILD) {
                    int groupPosition = ExpandableListView.getPackedPositionGroup(id);
                    int childPosition = ExpandableListView.getPackedPositionChild(id);

                    ArrayList<Goal> list = (ArrayList<Goal>) parent.getAdapter().getItem(childPosition);

                    final Goal goal = list.get(0);


                    if (goal!=null){ // Если объект нашелся, удаляем по нажатии на него внутри группы

                        final Dialog dialog;
                        dialog = new Dialog(StartActivity.this);
                        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                        dialog.setContentView(R.layout.confirm_deletion);
                        dialog.show();
                        Button delete = (Button) dialog.findViewById(R.id.delete);
                        Button back = (Button) dialog.findViewById(R.id.back);

                        delete.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                goal.delete();
                                dialog.dismiss();
                                printAllGoals();
                            }
                        });
                        back.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialog.dismiss();
                            }
                        });
                    }
                    printAllGoals();

                    return true;
                }

                return false;
            }
        });
    }
    private void setListenerOnGroup(){
        allGoalsList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                int itemType = ExpandableListView.getPackedPositionType(id);


                if (itemType == ExpandableListView.PACKED_POSITION_TYPE_CHILD) {
                    int childPosition = ExpandableListView.getPackedPositionChild(id);
                    int groupPosition = ExpandableListView.getPackedPositionGroup(id);

                    return true;

                }else if(itemType == ExpandableListView.PACKED_POSITION_TYPE_GROUP) {
                    int groupPosition = ExpandableListView.getPackedPositionGroup(id);

                    ArrayList<Goal> list = (ArrayList<Goal>) parent.getAdapter().getItem(groupPosition);
                    final Goal goal = list.get(0);

                    if (goal!=null){ // Если объект нашелся, удаляем по нажатии на него внутри группы

                        final Dialog dialog;
                        dialog = new Dialog(StartActivity.this);
                        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                        dialog.setContentView(R.layout.confirm_deletion);
                        dialog.show();
                        Button confirmDelete = (Button) dialog.findViewById(R.id.confirmDelete);
                        Button delete = (Button) dialog.findViewById(R.id.delete);
                        Button back = (Button) dialog.findViewById(R.id.back);

                        confirmDelete.setText("ВЫ ХОТИТЕ УДАЛИТЬ ВАШУ ЦЕЛЬ - " + goal.getNameGoal());

                        delete.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                goal.delete();
                                dialog.dismiss();
                                printAllGoals();
                            }
                        });
                        back.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialog.dismiss();
                            }
                        });
                    }
                    printAllGoals();

                    if (allGoals.size() == 0) {
                        startAddGoal.setVisibility(View.VISIBLE);
                        bootStrapAddGoalCenter();
                    } else {
                        startAddGoal.setVisibility(View.VISIBLE);
                        bootStrapAddGoalDown();
                    }
                    startAnimAddGoal();

                    return true;

                }else {
                    return false;
                }
            }
        });
    }
    private void setListenersOnTitle() {
        allGoalsList.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                if(startAddGoal.getVisibility() == View.VISIBLE) {
                    stopAnimAddGoal();
                    startAddGoal.setVisibility(View.INVISIBLE);
                } else {
                    startAnimAddGoal();
                    startAddGoal.setVisibility(View.VISIBLE);
                }
                ArrayList<Goal> list = (ArrayList) parent.getExpandableListAdapter().getGroup(groupPosition);
                Goal g = list.get(0);
                Date todayDate = new Date();
                Date goalDate = g.getToDate();
                Toast.makeText(getApplicationContext(),"groupPos "+groupPosition,Toast.LENGTH_SHORT).show();
               /* if (todayDate.after(goalDate)) {
                    showDialog(1);
                    return true;
                }*/
                return false;
            }
        });
    }
    private void printAllGoals(){
        allGoals.clear();
        ArrayList<ArrayList<Goal>> groups = new ArrayList<>();

        List<ReadBookGoal> allReadBookGoals = ReadBookGoal.listAll(ReadBookGoal.class);
        List<WeightCorrectionGoal> allWeightCorrectionGoals = WeightCorrectionGoal.listAll(WeightCorrectionGoal.class);
        List<CardioGoal> allCardioGoal = CardioGoal.listAll(CardioGoal.class);
        List<RepeatsCorrectionGoal> allRepeatsCorrectionGoal = RepeatsCorrectionGoal.listAll(RepeatsCorrectionGoal.class);
        List<ElementCorrectionGoal> allElementsCorrectionGoal = ElementCorrectionGoal.listAll(ElementCorrectionGoal.class);
        List<LanguageLearningGoal> allLanguageLearningGoal = LanguageLearningGoal.listAll(LanguageLearningGoal.class);
        //Находим все записи в базе

        for (ReadBookGoal goal : allReadBookGoals){ // Итерация по кажной записи в базе и добавления их в експандер
            ArrayList<Goal> children = new ArrayList<>();
            children.add(goal);
            groups.add(children);
            allGoals.add(goal);
        }
        for (WeightCorrectionGoal goal : allWeightCorrectionGoals  ){ // Итерация по кажной записи в базе и добавления их в експандер
            ArrayList<Goal> children = new ArrayList<>();
            children.add(goal);
            groups.add(children);
            allGoals.add(goal);
        }
        for (CardioGoal goal : allCardioGoal){ // Итерация по кажной записи в базе и добавления их в експандер
            ArrayList<Goal> children = new ArrayList<>();
            children.add(goal);
            groups.add(children);
            allGoals.add(goal);
        }
        for (ElementCorrectionGoal goal : allElementsCorrectionGoal  ){ // Итерация по кажной записи в базе и добавления их в експандер
            ArrayList<Goal> children = new ArrayList<>();
            children.add(goal);
            groups.add(children);
            allGoals.add(goal);
        }
        for (RepeatsCorrectionGoal goal : allRepeatsCorrectionGoal  ){ // Итерация по кажной записи в базе и добавления их в експандер
            ArrayList<Goal> children = new ArrayList<>();
            children.add(goal);
            groups.add(children);
            allGoals.add(goal);
        }
        for (LanguageLearningGoal goal : allLanguageLearningGoal){ // Итерация по кажной записи в базе и добавления их в експандер
            ArrayList<Goal> children = new ArrayList<>();
            children.add(goal);
            groups.add(children);
            allGoals.add(goal);
        }
        if (allGoals.size() == 0) {
            bootStrapAddGoalCenter();
        } else {
            bootStrapAddGoalDown();
        }

        Map<Long,Goal> allGoalsMap = new HashMap<>();

        for (long i = 0; i < allGoals.size(); i++){
            allGoalsMap.put(i,allGoals.get((int)i));
        }

        adapter = new ExpListAdapter(StartActivity.this, groups, allGoalsMap);
        allGoalsList.setAdapter(adapter);
    }
    public void openGoalChooser(View view) {
        Intent intent = new Intent(StartActivity.this, AllSectionTheme.class);
        startActivity(intent);
        this.finish();
    }
    public void openRecords(View view) {
        Intent intent = new Intent(StartActivity.this, RecordsActivity.class);
        startActivity(intent);
        this.finish();
    }
    public void openOptions(View view) {
        Intent intent = new Intent(StartActivity.this, ConfigActivity.class);
        startActivity(intent);
        this.finish();
    }
    public void bootStrapAddGoalCenter() {
        logicAddGoal = false;
        startAddGoal.setTextSize(25);
        bootStrap.bootStrapBtnGoal(StartActivity.this, startAddGoal, logicAddGoal);
        switchQuote = true;
        funcSwitchQuote(switchQuote);
    }
    public void bootStrapAddGoalDown() {
        logicAddGoal = true;
        startAddGoal.setTextSize(16);
        bootStrap.bootStrapBtnGoal(StartActivity.this, startAddGoal, logicAddGoal);
        switchQuote = false;
        funcSwitchQuote(switchQuote);
    }
    public void startAnimAddGoal() {
        Typeface faceBold = null;
        faceBold = Typeface.createFromAsset(getAssets(), "fonts/start_font.otf");
        startAddGoal.setTypeface(faceBold);
        anim = AnimationUtils.loadAnimation(this, R.anim.btn_anim);
        startAddGoal.startAnimation(anim);
    }
    public void stopAnimAddGoal() {
        startAddGoal.clearAnimation();
    }
    private void dataQuote() {
        Typeface face = null;
        TextView quote = (TextView) findViewById(R.id.quote);
        TextView quoteAuthor = (TextView) findViewById(R.id.quoteAuthor);
        face = Typeface.createFromAsset(getAssets(), "fonts/font.otf");
        quote.setTypeface(face);
        quoteAuthor.setTypeface(face);
    }
    private void funcSwitchQuote(Boolean current) {
        RelativeLayout rlQuote;
        rlQuote = (RelativeLayout) findViewById(R.id.rlQuote);
        if(current) {
            rlQuote.setVisibility(View.VISIBLE);
            dataQuote();
        } else rlQuote.setVisibility(View.INVISIBLE);
    }
    private boolean isMyServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }

    public void refresh() {
        //allGoalsList.refreshDrawableState();
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        if(id==1){
            adb = new AlertDialog.Builder(this);
            adb.setTitle(null);
            warningView = (View)getLayoutInflater().inflate(R.layout.warning_dialog,null);
            adb.setView(warningView);
            warningDialog=adb.create();
            Button accept = (Button)warningView.findViewById(R.id.acceptBTN);
            accept.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                if(warningDialog!=null){
                    warningDialog.cancel();
                }
                }
            });
            return warningDialog;
        }
        return super.onCreateDialog(id);
    }
}
