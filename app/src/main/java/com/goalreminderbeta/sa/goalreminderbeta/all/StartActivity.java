package com.goalreminderbeta.sa.goalreminderbeta.all;

import android.app.ActivityManager;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.preference.PreferenceManager;
import android.support.v4.view.GestureDetectorCompat;
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

import com.goalreminderbeta.sa.goalreminderbeta.R;
import com.goalreminderbeta.sa.goalreminderbeta.additional.BootStrap;
import com.goalreminderbeta.sa.goalreminderbeta.additional.notification.CustomNotificationService;
import com.goalreminderbeta.sa.goalreminderbeta.additional.notification.NotificationService;
import com.goalreminderbeta.sa.goalreminderbeta.goals.ElementCorrectionGoal;
import com.goalreminderbeta.sa.goalreminderbeta.goals.Goal;
import com.goalreminderbeta.sa.goalreminderbeta.goals.LanguageLearningGoal;
import com.goalreminderbeta.sa.goalreminderbeta.goals.ReadBookGoal;
import com.goalreminderbeta.sa.goalreminderbeta.goals.RepeatsCorrectionGoal;
import com.goalreminderbeta.sa.goalreminderbeta.goals.CardioGoal;
import com.goalreminderbeta.sa.goalreminderbeta.goals.WeightCorrectionGoal;
import com.goalreminderbeta.sa.goalreminderbeta.options.ConfigActivity;
import com.goalreminderbeta.sa.goalreminderbeta.options.OptionsDTO;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StartActivity extends AppCompatActivity {

    private Button startAddGoal;
    private ExpandableListView allGoalsList;
    private List<Goal> allGoals;
    public static int sizeOfList=0;
    private BootStrap bootStrap;
    private boolean logicAddGoal;
    private Animation anim = null;
    private ExpListAdapter adapter;
    private boolean switchQuote;

    private GestureDetectorCompat gestureObject;
    private SharedPreferences sp;
    private SharedPreferences.Editor editor;
    private static int[]selectedDays = new int[]{1,2,3,4,5,6,7};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start_activity);

        startAddGoal = (Button) findViewById(R.id.startAddGoal);
        allGoalsList = (ExpandableListView) findViewById(R.id.allGoalsList);

        allGoals = new ArrayList<>();
        bootStrap = new BootStrap();

        sp = PreferenceManager.getDefaultSharedPreferences(this);
        editor = sp.edit();

        if(!sp.getBoolean("fromStart",false)) {
            Intent intent = new Intent(this, NotificationService.class);
            intent.putExtra("size",allGoals.size());
            if(allGoals.size()>0){
                intent.putExtra("title", "You goals are ready!");
                intent.putExtra("content", "Keep it up!");
                editor.putString("title","You goals are ready!");
                editor.putString("content","Keep it up!");
                editor.commit();
            }else{
            intent.putExtra("title", "You have no goals!");
            intent.putExtra("content", "Add some goal to start");
                editor.putString("title","You have no goals!");
                editor.putString("content","Add some goal to start");
                editor.commit();
            }
            intent.putExtra("frequency", 1);
            intent.putExtra("soundOn", true);
            intent.putExtra("vibrOn", true);
            intent.putExtra("days",selectedDays);
            startService(intent);
        }
        setListenersOnChild();
        setListenerOnGroup();
        printAllGoals();
        setListenersOnTitle();
        startAnimAddGoal();
    }

    @Override
    protected void onStart() {
        super.onStart();
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

        adapter = new ExpListAdapter(getApplicationContext(), groups, allGoalsMap);
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
    public void startNotification(){
        OptionsDTO optionsDTO = OptionsDTO.findById(OptionsDTO.class, 1);
        if(allGoals.isEmpty()){
            CustomNotificationService.scheduleNotification(
                    CustomNotificationService.createNotification(
                            "You have no goals!",
                            "Add some goal to start!",
                            getApplicationContext(),
                            optionsDTO.getSoundConfig()
                    ),
                    5000,
                    getApplicationContext()
            );
        }else {
            CustomNotificationService.scheduleNotification(
                    CustomNotificationService.createNotification(
                            "You goals are ready!",
                            "Keep it up!",
                            getApplicationContext(),
                            optionsDTO.getSoundConfig()
                    ),
                    5000,
                    getApplicationContext()
            );
        }
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

    /*@Override
    protected void onDestroy() {
        super.onDestroy();
        if(!sp.getBoolean("fromStart",false)) {
            Intent intent = new Intent(this, NotificationService.class);
            intent.putExtra("size",allGoals.size());
            if(allGoals.size()>0){
                intent.putExtra("title", "DS You goals are ready!");
                intent.putExtra("content", "Keep it up!");
                editor.putString("title","DS You goals are ready!");
                editor.putString("content","Keep it up!");
                editor.commit();
            }else{
                intent.putExtra("title", "DS You have no goals!");
                intent.putExtra("content", "Add some goal to start");
                editor.putString("title","DS You have no goals!");
                editor.putString("content","Add some goal to start");
                editor.commit();
            }
            intent.putExtra("frequency", Integer.parseInt(ConfigActivity.getFrequency()));
            intent.putExtra("soundOn", ConfigActivity.isSoundOn());
            intent.putExtra("vibrOn", ConfigActivity.isVibrOn());
            intent.putExtra("notifOn",ConfigActivity.isNotifOn());
            if(ConfigActivity.notifOn) {
                NotificationService.isService = true;
                startService(intent);
            }
            else{
                NotificationService.isService = false;
                stopService(intent);}
        }
        }*/


}
