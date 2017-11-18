package com.goalreminderbeta.sa.goalreminderbeta.additional;

import com.goalreminderbeta.sa.goalreminderbeta.R;
import com.goalreminderbeta.sa.goalreminderbeta.goals.Goal;
import android.app.Activity;
import android.app.Dialog;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

/**
 * Created by Yevgeniya on 15.11.2017.
 */

public abstract class DialogBuilder {
    protected static EditText goalName, goalDescr, dateFrom, dateTo;
    protected static Button confirm, cancel;
    protected static LinearLayout dialogLV;
    protected static LayoutParams lp;
    protected static Goal dialogBuilderGoal;
    protected static Dialog dialog;
    protected static Activity activity;
    public DialogBuilder(){

    }
    public static Goal getGoal(){
        return dialogBuilderGoal;
    }

    public Dialog createDialog(Activity activity, Goal goal){
        this.activity = activity;
        dialog = new Dialog(activity);
        dialogBuilderGoal = goal;
        dialog.setTitle("Confirmation");

        dialog.setContentView(R.layout.confirm_dialog);
        goalName = (EditText)dialog.findViewById(R.id.goalName);
        goalDescr = (EditText)dialog.findViewById(R.id.goalDescr);
        dateFrom = (EditText)dialog.findViewById(R.id.dateFrom);
        dateTo = (EditText)dialog.findViewById(R.id.dateTo);
        dateFrom = (EditText)dialog.findViewById(R.id.dateFrom);
        dialogLV = (LinearLayout)dialog.findViewById(R.id.fieldsLV);
        lp = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        confirm = (Button)dialog.findViewById(R.id.confirm);
        cancel = (Button)dialog.findViewById(R.id.cancel);
        goalName.setText(dialogBuilderGoal.getNameGoal());
        goalDescr.setText(dialogBuilderGoal.getDescriptionGoal());


        return dialog;

    }

    public void encourage(){
        Toast.makeText(activity.getApplicationContext(),"You are super super!",Toast.LENGTH_SHORT).show();
    }

}
