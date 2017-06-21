package com.app.hci.flyhigh;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by axel on 15/06/17.
 */

public class SearchFragment extends ListFragment {
    String DATA = "HISTORIAL";
    Flight[] searchHist;
    SharedPreferences preferences;
    String flyCode;
    View view;
    private boolean called;
    OnFlightSelectedListener mCallback;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        view =  inflater.inflate(R.layout.search_layout, container, false);
        setHasOptionsMenu(true);
        Button button = (Button) view.findViewById(R.id.button);
        if (button != null) {
            button.setOnClickListener(new Button.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString(DATA,"");
                    editor.apply();
                    showHistory();
                }
            });
        }

        return view;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        preferences = getActivity().getPreferences(MODE_PRIVATE);
        showHistory();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.search_menu, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        MenuItemCompat.setOnActionExpandListener(searchItem, new MenuItemCompat.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionCollapse(MenuItem item) {
                // Do something when action item collapses
                return true;  // Return true to collapse action view
            }

            @Override
            public boolean onMenuItemActionExpand(MenuItem item) {
                // Do something when expanded
                return true;  // Return true to expand action view
            }
        });
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            public boolean onQueryTextChange(String newText) {
                called = false;
                ((TextView) view.findViewById(R.id.prueba)).setText(newText);
                return false;
            }

            public boolean onQueryTextSubmit(String query) {
                if (query.matches(".+-\\d+")) {
                    if (!called) {
                        flyCode = query;
                        new flyRetriever().execute();
                        called = true;
                    }
                } else {

                }
                return false;
            }
        });
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle item selection
        switch (item.getItemId()) {
            case R.id.action_search:
                // do s.th.
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void showHistory() {
        String history = preferences.getString(DATA, "");
        if (!history.equals("")) {
            String[] flights = history.split("#");
            Flight[] values = new Flight[flights.length];
            for (int i = 0; i < flights.length; i++) {
                values[i] = JSONtoFly(flights[flights.length-1-i]);
                if (values[i] == null) {
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString(DATA,"");
                    editor.apply();
                    return;
                }
            }
            FlightArrayAdapter adapter = new FlightArrayAdapter(getActivity(), values);
            setListAdapter(adapter);
        }
    }

    public Flight JSONtoFly(String json) {
        try {
            JSONObject obj = new JSONObject(json);
            JSONObject stat = obj.getJSONObject("status");
            String arrID = stat.getJSONObject("arrival").getJSONObject("airport").getJSONObject("city").getString("id");
            String arrName = stat.getJSONObject("arrival").getJSONObject("airport").getJSONObject("city").getString("name").split(",")[0];
            String depID = stat.getJSONObject("departure").getJSONObject("airport").getJSONObject("city").getString("id");
            String depName = stat.getJSONObject("departure").getJSONObject("airport").getJSONObject("city").getString("name").split(",")[0];
            String duration = "6:66";
            String aeroName = stat.getJSONObject("airline").getString("name");
            String depTime = stat.getJSONObject("departure").getString("scheduled_time").split(" ")[1];
            String arrTime = stat.getJSONObject("arrival").getString("scheduled_time").split(" ")[1];
            String depAirName = stat.getJSONObject("departure").getJSONObject("airport").getString("description").split(",")[0];
            String arrAirName = stat.getJSONObject("arrival").getJSONObject("airport").getString("description").split(",")[0];
            String airId = stat.getJSONObject("airline").getString("id");
            String flightNum = stat.getString("number");
            String fliDate = stat.getJSONObject("departure").getString("scheduled_time").split(" ")[0];
            String status = "-";
            switch (stat.getString("status")) {
                case "S":
                    status = "Programado";
                    break;
                case "A":
                    status = "Activo";
                    break;
                case "R":
                    status = "Desviado";
                    break;
                case "L":
                    status = "Aterrizado";
                    break;
                case "C":
                    status = "Cancelado";
                    break;
                default:
                    new RuntimeException("Problema en el servidor");
            }
            return new Flight(depID, arrID, depName, arrName, duration, aeroName, airId, depTime, depAirName, arrTime, arrAirName, airId + flightNum, status, fliDate);
        } catch (Exception exception) {
            exception.printStackTrace();
            //result = "No existe ese vuelo";
            return null;
        }
    }

    private class flyRetriever extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... params) {
            String json = new GetJSON("http://hci.it.itba.edu.ar/v1/api/status.groovy?method=getflightstatus&airline_id="+ flyCode.split("-")[0] +"&flight_number="+ flyCode.split("-")[1]).get();
            return json;
        }

        @Override
        protected void onPostExecute(String result) {
            if (result != null && JSONtoFly(result) != null) {
                String history = preferences.getString(DATA, "");
                if (!history.equals("")) {
                    history += "#";
                }
                history += result;
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString(DATA,history);
                editor.apply();
                showHistory();

            } else {
                CoordinatorLayout coordinatorLayout = (CoordinatorLayout) view.findViewById(R.id.coordinator_layout);
                if (coordinatorLayout != null) {
                    // Snackbar sin acción.
                    Snackbar.make(coordinatorLayout, "No se encuentra ese vuelo", Snackbar.LENGTH_SHORT).show();
                }
            }
        }
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception
        try {
            mCallback = (OnFlightSelectedListener) getActivity();
        } catch (ClassCastException e) {
            throw new ClassCastException(getActivity().toString()
                    + " must implement OnHeadlineSelectedListener");
        }
    }
}

