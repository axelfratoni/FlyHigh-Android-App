package com.app.hci.flyhigh;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.app.AlarmManager;

import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.os.SystemClock;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.util.Log;

import java.text.DateFormat;
import java.util.Date;

import static android.content.Context.ALARM_SERVICE;
import static android.content.Context.MODE_PRIVATE;

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
        setInterval();
        System.out.println("Intervalo de alarma: " + interval);
        System.out.println("Notification is Set?: " + isSet());
        startNotifications();
        Log.v("testapp", "Empezando las notificaciones");
    }

    public void startNotifications() {
        if (PreferenceManager.getDefaultSharedPreferences(context.getApplicationContext()).getInt("time", 1000) != 0) {
            System.out.println("Empieza notificacion con intervalo de " + interval);
            SharedPreferences isSet = context.getSharedPreferences("isSet", MODE_PRIVATE);
            SharedPreferences.Editor setEditor = isSet.edit();
            setEditor.putInt("ans", 1);
            setEditor.apply();
            alarmManager.setRepeating(AlarmManager.ELAPSED_REALTIME,
                    SystemClock.elapsedRealtime() + interval,
                    interval,
                    alarmNotificationReceiverPendingIntent);
        }
    }

    public void stopNotifications() {
        SharedPreferences isSet = context.getSharedPreferences("isSet", MODE_PRIVATE);
        SharedPreferences.Editor setEditor = isSet.edit();
        setEditor.putInt("ans", 0);
        setEditor.apply();
        alarmManager.cancel(alarmNotificationReceiverPendingIntent);
    }

    public void setInterval() {
        SharedPreferences actual = context.getSharedPreferences("actualInterval", MODE_PRIVATE);
        SharedPreferences.Editor actualEditor = actual.edit();
        int desired = PreferenceManager.getDefaultSharedPreferences(context.getApplicationContext()).getInt("time", 1000);
        interval = desired;
        if(changeInAlarm()) {
            actualEditor.putInt("time", desired);
            actualEditor.apply();
            if (isSet()) {
                stopNotifications();
                startNotifications();
            }
        }
    }

    public boolean changeInAlarm() {
        int actual = context.getSharedPreferences("actualInterval", MODE_PRIVATE).getInt("time", 1000);
        int desired = PreferenceManager.getDefaultSharedPreferences(context.getApplicationContext()).getInt("time", 1000);
        return actual != desired;
    }

    public boolean isSet() {
        int set = context.getSharedPreferences("isSet", MODE_PRIVATE).getInt("ans", 0);
        return set == 1;
    }
}

