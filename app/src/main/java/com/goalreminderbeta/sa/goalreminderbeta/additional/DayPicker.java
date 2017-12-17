package com.goalreminderbeta.sa.goalreminderbeta.additional;

import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Created by Yevgeniya on 16.12.2017.
 */

public class DayPicker {
    private static GregorianCalendar calendar;
    private static long delay;
    public static long getDelay(){
        calendar = new GregorianCalendar();
        int hour = calendar.get(Calendar.HOUR);
        if(hour==0){
            hour=24;
        }
        int minute = calendar.get(Calendar.MINUTE);
        int second = calendar.get(Calendar.SECOND);
        int millisecond = calendar.get(Calendar.MILLISECOND);

        int remainHour = 32-hour;
        int remainMinute=59-minute;
        int remainSecond=59-second;
        int remainMilisecond=1000-millisecond;
        delay = remainMilisecond+1000*remainSecond+60000*remainMinute+3600*1000*remainHour;
        return delay;
    }
}
