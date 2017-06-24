package com.app.hci.flyhigh;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v4.app.FragmentActivity;
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

    @Deprecated
    public static void subscribeFlight(Context context, Flight f) {
        SharedPreferences preferences = context.getSharedPreferences("subs",MODE_PRIVATE);
        String data = preferences.getString("subs", "");
        if (!data.equals("")) {
            data += "#";
        } else {
            ((MainActivity) context).getNotificationDealer().startNotifications();
        }
        data += f.getJsonRepresentation();
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("subs",data);
        editor.apply();
    }

    @Deprecated
    public static Flight[] retrieveSubscriptionsDep(Context context) {
        SharedPreferences preferences = context.getSharedPreferences("subs",MODE_PRIVATE);
        String data = preferences.getString("subs", "");
        Flight[] flights = null;
        if (!data.equals("")) {
            String[] flightJSONs = data.split("#");
            flights = new Flight[flightJSONs.length];
            for (int i = 0; i < flightJSONs.length; i++) {
                try {
                    flights[i] = new Flight(new JSONObject(flightJSONs[i]));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                if (flights[i] == null) {
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString("subs","");
                    editor.apply();
                    throw new RuntimeException("Corrupted data");
                }
            }
        }
        return flights;
    }

    @Deprecated
    public static void clearSubscriptions(Context context) {
        SharedPreferences preferences = context.getSharedPreferences("subs",MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("subs","");
        editor.apply();
        ((MainActivity) context).getNotificationDealer().stopNotifications();
    }

    @Deprecated
    public static void updateSubcription(Context context, Flight f) {
        SharedPreferences preferences = context.getSharedPreferences("subs",MODE_PRIVATE);
        String data = "";
        Flight[] actualSubs = new DataManager().retrieveSubscriptionsDep(context);
        for (int i=0; i<actualSubs.length; i++) {
            if (i != 0) {
                data += "#";
            }
            if(actualSubs[i].equals(f)) {
                data += f.getJsonRepresentation();
            } else {
                data += actualSubs[i].getJsonRepresentation();
            }
        }
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("subs",data);
        editor.apply();
    }

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
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    public static Flight[] retrieveHistoryFlights(Context context){
        try{
            Flight[] values = retrieveFlights(context, HISTORY_FILE_NAME);
            Log.d("Vuelos size: " , "Cantidad de vuelos en historial: " + values.length);
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
        }catch (Exception e){
            e.printStackTrace();
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
        Log.d("SaveFlight", "Adding flight: " + f.getAirlineId() + "-" + f.getFlightNumber() + " to " + fileName + "called: flight" + (count+1));
    }

    private static Flight[] retrieveFlights(Context context, String fileName)throws JSONException{
        Log.d("xdd", "XDDD");
        SharedPreferences mSettings = context.getSharedPreferences(fileName, MODE_PRIVATE);
        int count = mSettings.getInt("flightsCount", 0);
        Flight[] flights = new Flight[count];
        for(int i = count; i > 0; i--){
            Log.d("RetrieveFlight", mSettings.getString("flight"+ (i+1), "NoSuchFlight"));
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
        Log.d("Wipe", "Wiping data");
        wipeHistory(context);
        wipeSubscriptions(context);
    }

    public static void wipeHistory(Context context) {
        Log.d("Wipe", "Wiping history");
        SharedPreferences mSettings = context.getSharedPreferences(HISTORY_FILE_NAME, MODE_PRIVATE);;
        SharedPreferences.Editor editor = mSettings.edit();
        editor.clear();
        editor.apply();
    }

    public static void wipeSubscriptions(Context context){
        Log.d("Wipe", "Wiping subscription");
        SharedPreferences mSettings = context.getSharedPreferences(SUBSCRIPTIONS_FILE_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = mSettings.edit();
        editor.clear();
        editor.apply();
    }
}
