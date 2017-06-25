package com.app.hci.flyhigh;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.util.Date;

/**
 * Created by axel on 23/06/17.
 */

public class AlarmNotificationReceiver extends BroadcastReceiver {
    Context context;

    @Override
    public void onReceive(Context context, Intent intent) {
        System.out.println("Nueva alarmaaa");
        Log.d("TAG", "Alarm at: " + DateFormat.getDateTimeInstance().format(new Date()));

        this.context = context;
        Flight[] subscriptions = new DataManager().retrieveSubscriptions(context);
        if (subscriptions != null) {
            for (Flight f : subscriptions) {
                new UpdateChecker(f).execute();
            }
        }

    }

    protected void updateAndNotificate(Flight updatedFlight) {
        //new DataManager().clearSubscriptions(context);
        new DataManager().updateSubscription(context, updatedFlight);

        Intent notificationIntent = new Intent(context, NotificationView.class);

        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
        stackBuilder.addParentStack(NotificationView.class);
        stackBuilder.addNextIntent(notificationIntent);

        final PendingIntent contentIntent =
                stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);

        Notification notification = new Notification.Builder(context)
                .setContentTitle(context.getResources().getString(R.string.notification_title))
                .setContentText(context.getResources().getString(R.string.notification_data1) + updatedFlight.getAirlineId() + "-" + updatedFlight.getFlightNumber() + context.getResources().getString(R.string.notification_data2) + updatedFlight.getStatus())
                .setSmallIcon(R.mipmap.ic_flyhigh_logo)
                .setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.mipmap.ic_flyhigh_logo))
                .setAutoCancel(true)
                .setContentIntent(contentIntent).build();

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(Integer.parseInt(updatedFlight.getFlightNumber()), notification);
    }

    private class UpdateChecker extends AsyncTask<Void,Void,String>{
        private Flight flightBeingChecked;

        public UpdateChecker(Flight flightBeingChecked){
            this.flightBeingChecked = flightBeingChecked;
        }

        @Override
        protected String doInBackground(Void... params) {
            return new GetJSON("http://hci.it.itba.edu.ar/v1/api/status.groovy?method=getflightstatus&airline_id="+ flightBeingChecked.getAirlineId() +"&flight_number="+ flightBeingChecked.getFlightNumber()).get();
        }

        @Override
        protected void onPostExecute(String newJson) {
            try {
                System.out.println(newJson);
                System.out.println("http://hci.it.itba.edu.ar/v1/api/status.groovy?method=getflightstatus&airline_id="+ flightBeingChecked.getAirlineId() +"&flight_number="+ flightBeingChecked.getFlightNumber());
                JSONObject newJsonFlight = new JSONObject(newJson).getJSONObject("status");
                Flight newFlight = new Flight(newJsonFlight);
                String newStatus = newFlight.getStatus();
                if (!flightBeingChecked.getStatus().equals(newStatus)) {
                    updateAndNotificate(newFlight);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
