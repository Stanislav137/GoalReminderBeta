package com.goalreminderbeta.sa.goalreminderbeta.additional.notifications;

import android.media.RingtoneManager;
import android.net.Uri;

public class NotificationConfig {

    public static final long[] vibrationArray = new long[] { 1000, 1000, 1000, 1000, 1000 };
    public static final Uri ringURI =
            RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

}
