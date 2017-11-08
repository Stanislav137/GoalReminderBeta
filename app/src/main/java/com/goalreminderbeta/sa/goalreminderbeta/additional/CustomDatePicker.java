package com.goalreminderbeta.sa.goalreminderbeta.additional;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.widget.TextView;

import com.goalreminderbeta.sa.goalreminderbeta.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class CustomDatePicker {

    public static void pickDate(Activity activity, final TextView view){
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog dialog = new DatePickerDialog(activity, R.style.datepicker, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(android.widget.DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
                    String stringDate = day+"/"+month+"/"+year;
                Date date = null;
                try {
                    date = formatter.parse(stringDate);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                view.setText(formatter.format(date));
            }
        }, year, month, day);
        dialog.show();
    }
}
