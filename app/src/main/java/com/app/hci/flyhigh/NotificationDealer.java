package com.app.hci.flyhigh;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.app.AlarmManager;

import android.graphics.BitmapFactory;
import android.os.SystemClock;
import android.provider.Settings;
import android.util.Log;

import java.text.DateFormat;
import java.util.Date;

import static android.content.Context.ALARM_SERVICE;

/**
 * Created by axel on 22/06/17.
 */

public class NotificationDealer {
    private AlarmManager alarmManager;
    private PendingIntent alarmNotificationReceiverPendingIntent;
    private Context context;
    private int interval = 1000;

    public NotificationDealer(Context context) {
        this.context = context;
        Intent alarmNotificationReceiverIntent = new Intent(context, AlarmNotificationReceiver.class);
        alarmNotificationReceiverPendingIntent =  PendingIntent.getBroadcast(context, 0, alarmNotificationReceiverIntent, 0);
        alarmManager = (AlarmManager) context.getSystemService(ALARM_SERVICE);
    }

    public void startNotifications() {
        alarmManager.setRepeating(AlarmManager.ELAPSED_REALTIME,
                SystemClock.elapsedRealtime() + interval,
                interval,
                alarmNotificationReceiverPendingIntent);
        System.out.println("Empieza la alarma");
    }

    public void stopNotifications() {
        alarmManager.cancel(alarmNotificationReceiverPendingIntent);
    }

    public void setInterval(int newInterval) {
        interval = newInterval;
        stopNotifications();
        startNotifications();
    }

}

