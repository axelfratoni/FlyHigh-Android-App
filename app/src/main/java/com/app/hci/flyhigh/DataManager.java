package com.app.hci.flyhigh;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Gaston on 21/06/2017.
 */

public class DataManager {
    private static final String HISTORY_FILE_NAME = "history";
    private static final String SUBSCRIPTIONS_FILE_NAME = "subscriptions";
    private static final String UPDATES_FILE_NAME = "updates";


    public static void saveFlightInHistory(Context context, Flight f) {
        try {
            Log.d("History", "Adding flight: " + f.getJsonRepresentation());
            saveFlight(context, f, HISTORY_FILE_NAME);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void subscribeToFlight(Context context, Flight f) {
        try{
            saveFlight(context, f,SUBSCRIPTIONS_FILE_NAME);
            ((MainActivity) context).getNotificationDealer().startNotifications();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private static void registerUpdate(Context context, Flight f) {
        SharedPreferences mSettings = context.getSharedPreferences(UPDATES_FILE_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = mSettings.edit();
        int count = mSettings.getInt("flightsCount", 0);
        editor.putString("flight" + (count+1), f.getJsonRepresentation());
        editor.putInt("flightsCount", count+1);
        editor.apply();
        if ( mSettings.getInt("flightsCount", 0) > 11) {
            trimUpdates(context);
        }
    }

    private static void trimUpdates (Context context) {
        try {
            Flight[] values = retrieveFlights(context, UPDATES_FILE_NAME);
            clearUpdates(context);
            for (int i = values.length - 10; values.length > i ; i++) {
                registerUpdate(context, values[i]);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private static void deleteFromUpdate (Context context, Flight flight) throws JSONException {
        Flight[] values = retrieveFlights(context, UPDATES_FILE_NAME);
        int count = getUpdatesCount(context);
        clearUpdates(context);
        for (int i = 1; i <= count; i++) {
            if (!flight.equals(values[i])) {
                registerUpdate(context, flight);
            }
        }
    }

    private static void clearUpdates (Context context) {
        SharedPreferences mSettings = context.getSharedPreferences(UPDATES_FILE_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = mSettings.edit();
        editor.clear();
        editor.apply();
    }

    public static Flight[] retrieveUpdates (Context context) {
        try{
            Flight[] values = retrieveFlights(context, UPDATES_FILE_NAME);
            return values;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }


    public static Flight[] retrieveHistoryFlights(Context context){
        try{
            Flight[] values = retrieveFlights(context, HISTORY_FILE_NAME);
            return values;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public static Flight[] retrieveSubscriptions(Context context){
        try{
            return retrieveFlights(context, SUBSCRIPTIONS_FILE_NAME);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public static void deleteFlightInHistory(Context context, Flight flight){
        try{
        deleteFlight(context, flight, HISTORY_FILE_NAME);
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    public static void unsubscribe(Context context, Flight flight){
        try{
            deleteFlight(context, flight, SUBSCRIPTIONS_FILE_NAME);
            deleteFromUpdate(context, flight);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static boolean isSubscribed(Context context, Flight f){
        try {
            SharedPreferences mSettings = context.getSharedPreferences(SUBSCRIPTIONS_FILE_NAME, MODE_PRIVATE);
            int count = mSettings.getInt("flightsCount", 0);
            for (int i = 1; i <= count; i++) {
                Flight aux = new Flight(new JSONObject(mSettings.getString("flight" + i, null)));
                if (aux.getAirlineId().equals(f.getAirlineId()) && aux.getFlightNumber().equals(f.getFlightNumber())) {
                    return true;
                }
            }
            return false;
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }
    }
    private static void saveFlight(Context context, Flight f, String fileName) throws JSONException{
        SharedPreferences mSettings = context.getSharedPreferences(fileName, MODE_PRIVATE);
        SharedPreferences.Editor editor = mSettings.edit();
        int count = mSettings.getInt("flightsCount", 0);
        for (int i = 1; i <= count; i++) {
            Flight aux = new Flight(new JSONObject(mSettings.getString("flight" + i, null)));
            if (aux.getAirlineId().equals(f.getAirlineId()) && aux.getFlightNumber().equals(f.getFlightNumber())) {
                return;
            }
        }
        editor.putString("flight" + (count+1), f.getJsonRepresentation());
        editor.putInt("flightsCount", count+1);
        editor.apply();
    }

    private static Flight[] retrieveFlights(Context context, String fileName)throws JSONException{
        SharedPreferences mSettings = context.getSharedPreferences(fileName, MODE_PRIVATE);
        int count = mSettings.getInt("flightsCount", 0);
        Flight[] flights = new Flight[count];
        for(int i = count; i > 0; i--){
            flights[count-i] = new Flight(new JSONObject(mSettings.getString("flight"+i, null)));
        }
        return flights;
    }

    private static void deleteFlight(Context context, Flight flight, String fileName)throws JSONException{
        SharedPreferences mSettings = context.getSharedPreferences(fileName, MODE_PRIVATE);
        int count = mSettings.getInt("flightsCount", 0);
        if (count == 0){
            return;
        }
        SharedPreferences.Editor editor = mSettings.edit();
        int i = 1;
        for (; i <= count; i++) {
            Flight aux = new Flight(new JSONObject(mSettings.getString("flight" + i, null)));
            if (aux.getAirlineId().equals(flight.getAirlineId()) && aux.getFlightNumber().equals(flight.getFlightNumber())) {
                editor.remove("flight" + i);
                break;
            }
        }
        if (i == count+1){
            return;
        }else{
            for( ; i<count; i++){
                editor.putString("flight" + i, new Flight(new JSONObject(mSettings.getString("flight" + (i+1), null))).getJsonRepresentation());
            }
            editor.remove("flight" + i);
            editor.putInt("flightsCount", count-1);
            editor.apply();
        }
    }

    public static void wipeData(Context context) {
        wipeHistory(context);
        wipeSubscriptions(context);
    }

    public static void wipeHistory(Context context) {
        SharedPreferences mSettings = context.getSharedPreferences(HISTORY_FILE_NAME, MODE_PRIVATE);;
        SharedPreferences.Editor editor = mSettings.edit();
        editor.clear();
        editor.apply();
    }

    public static void wipeSubscriptions(Context context){
        ((MainActivity) context).getNotificationDealer().stopNotifications();
        SharedPreferences mSettings = context.getSharedPreferences(SUBSCRIPTIONS_FILE_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = mSettings.edit();
        editor.clear();
        editor.apply();
        clearUpdates(context);
    }

    public static void updateSubscription(Context context, Flight f){
        try {
            SharedPreferences mSettings = context.getSharedPreferences(SUBSCRIPTIONS_FILE_NAME, MODE_PRIVATE);
            SharedPreferences.Editor editor = mSettings.edit();
            registerUpdate(context, f);
            int count = mSettings.getInt("flightsCount", 0);
            for (int i = 1; i <= count; i++) {
                Flight aux = new Flight(new JSONObject(mSettings.getString("flight" + i, null)));
                if (aux.getAirlineId().equals(f.getAirlineId()) && aux.getFlightNumber().equals(f.getFlightNumber())) {
                    editor.putString("flight"+i, f.getJsonRepresentation());
                    editor.commit();
                    return;
                }
            }

        }catch(Exception e){
            e.printStackTrace();
        }

    }

    public static int getSubsCount(Context context) {
        return context.getSharedPreferences(SUBSCRIPTIONS_FILE_NAME, MODE_PRIVATE).getInt("flightsCount", 0);
    }

    public static int getUpdatesCount(Context context) {
        return context.getSharedPreferences(UPDATES_FILE_NAME, MODE_PRIVATE).getInt("flightsCount", 0);
    }

}
