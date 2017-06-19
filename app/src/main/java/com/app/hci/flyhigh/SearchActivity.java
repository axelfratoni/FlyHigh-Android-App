package com.app.hci.flyhigh;

import android.app.ListActivity;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.BaseColumns;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.widget.CursorAdapter;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by axel on 17/06/17.
 */

public class SearchActivity extends AppCompatActivity {
    private static ArrayList<City> cities = new ArrayList<>();
    private static String destination;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_act_layout);
        new CitiesRetriever().execute();
        /*Flight[] values = new Flight[] {
                new Flight("EZE", "JFK", "Buenos Aires", "Nueva York", "14h10m", "Aerolineas Argentinas", "21.00", "Aeropuerto internacional de ezeiza", "07.30", "Aeropuerto internacional John F. Kennedy", "AR1005", "12/06", "Cancelado"),
        };
        FlightArrayAdapter adapter = new FlightArrayAdapter(SearchActivity.this, values);
        ((ListView) findViewById(R.id.list)).setAdapter(adapter);*/
        handleIntent(getIntent());
    }
    @Override
    protected void onNewIntent(Intent intent) {
        setIntent(intent);
        handleIntent(intent);
    }
    private void handleIntent(Intent intent) {
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            doMySearch(query);
        }
    }
    private void doMySearch(String query){
        destination = null;
        for (City c : cities){
            if(c.equals(new City("",query))){
                destination = c.ID;
            }
        }

        if (destination != null){
            //((TextView) findViewById(R.id.prueba)).setText("True");

            new FlightsRetriever().execute();

        } else {
            //((TextView) findViewById(R.id.prueba)).setText("False");
        }

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.search_menu, menu);
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setIconifiedByDefault(false);

        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_search:
                onSearchRequested();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private class CitiesRetriever extends AsyncTask<Void, Void, ArrayList<City>> {

        @Override
        protected ArrayList<City> doInBackground(Void... params) {
            ArrayList<City> result = new ArrayList<>();
            try {
                String json = new GetJSON("http://hci.it.itba.edu.ar/v1/api/booking.groovy?method=getlastminuteflightdeals&from=BUE").get();
                JSONObject obj = new JSONObject(json);
                JSONArray deals = obj.getJSONArray("deals");

                for (int i = 0; i < deals.length(); i++) {
                    JSONObject d = deals.getJSONObject(i);
                    JSONObject city = d.getJSONObject("city");
                    result.add(new City(city.getString("id"),city.getString("name").split(",")[0]));
                }
            } catch (Exception exception) {
                exception.printStackTrace();
            }
            return  result;
        }

        @Override
        protected void onPostExecute(ArrayList<City> result) {
            cities = result;//((TextView) findViewById(R.id.prueba)).setText(cities.toString());
        }
    }

    private class FlightsRetriever extends AsyncTask<Void, Void, Flight[]> {

        @Override
        protected Flight[] doInBackground(Void... params) {
            Flight[] result = null;
            Calendar date = Calendar.getInstance();
            date.add(Calendar.DATE,2);
            try {
                String json = new GetJSON("http://hci.it.itba.edu.ar/v1/api/booking.groovy?method=getonewayflights&from=BUE&to="+ destination +"&dep_date="+ date.get(Calendar.YEAR) +"-"+ (date.get(Calendar.MONTH)+1) +"-"+ (date.get(Calendar.DATE)) +"&adults=1&children=0&infants=0").get();
                JSONObject obj = new JSONObject(json);
                JSONArray flights = obj.getJSONArray("flights");
                result = new Flight[flights.length()];

                for (int i = 0; i < flights.length(); i++) {
                    JSONObject f = flights.getJSONObject(i).getJSONArray("outbound_routes").getJSONObject(0).getJSONArray("segments").getJSONObject(0);
                    String arrID = f.getJSONObject("arrival").getJSONObject("airport").getJSONObject("city").getString("id");
                    String arrName = f.getJSONObject("arrival").getJSONObject("airport").getJSONObject("city").getString("name").split(",")[0];
                    String depID = f.getJSONObject("departure").getJSONObject("airport").getJSONObject("city").getString("id");
                    String depName = f.getJSONObject("departure").getJSONObject("airport").getJSONObject("city").getString("name").split(",")[0];
                    String duration = flights.getJSONObject(i).getJSONArray("outbound_routes").getJSONObject(0).getString("duration");
                    String aeroName = f.getJSONObject("airline").getString("name");
                    String depTime = f.getJSONObject("departure").getString("date").split(" ")[1];
                    String arrTime = f.getJSONObject("arrival").getString("date").split(" ")[1];
                    String depAirName = f.getJSONObject("departure").getJSONObject("airport").getString("description").split(",")[0];
                    String arrAirName = f.getJSONObject("arrival").getJSONObject("airport").getString("description").split(",")[0];
                    String airId = f.getJSONObject("airline").getString("id");
                    String flightNum = f.getString("number");
                    String fliDate = f.getJSONObject("departure").getString("date").split(" ")[0];
                    result[i] = new Flight(depID, arrID, depName, arrName, duration, aeroName, airId, depTime, depAirName, arrTime, arrAirName, airId + flightNum, "Activo", fliDate);
                }

            } catch (Exception exception) {
                exception.printStackTrace();
            }
            return  result;
        }

        @Override
        protected void onPostExecute(Flight[] result) {
            FlightArrayAdapter adapter = new FlightArrayAdapter(SearchActivity.this, result);
            ((ListView) findViewById(R.id.list)).setAdapter(adapter);
        }
    }

    private static class City{
        String ID;
        String name;

        public City (String ID, String name) {
            this.ID = ID;
            this.name = name;
        }

        @Override
        public boolean equals(Object o) {
            if(o.getClass() != this.getClass())
                return false;
            if (((City)o).name.equals(this.name)) {
                return true;
            }
            return false;
        }
    }
}
