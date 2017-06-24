package com.app.hci.flyhigh;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.SearchView;
import android.util.Log;
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
    String flyCode;
    View view;
    private boolean called;
    OnFlightSearchedListener mCallback;
    OnFlightSelectedListener mCallback2;

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
                    DataManager.wipeHistory(getActivity());
                    showHistory();
                }
            });
        }

        return view;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

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
                    CoordinatorLayout coordinatorLayout = (CoordinatorLayout) view.findViewById(R.id.coordinator_layout);
                    if (coordinatorLayout != null) {
                        Snackbar.make(coordinatorLayout, "No es un codigo de vuelo", Snackbar.LENGTH_SHORT).show();
                    }
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
        Flight[] values = DataManager.retrieveHistoryFlights(getActivity());
        FlightArrayAdapter adapter = new FlightArrayAdapter(getActivity(), values);
        setListAdapter(adapter);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception
        try {
            mCallback = (OnFlightSearchedListener) getActivity();
            mCallback2 = (OnFlightSelectedListener) getActivity();
        } catch (ClassCastException e) {
            throw new ClassCastException(getActivity().toString()
                    + " must implement OnHeadlineSelectedListener");
        }
    }

    public void onStart() {
        super.onStart();

        // When in two-pane layout, set the listview to highlight the selected list item
        // (We do this during onStart because at the point the listview is available.)
        if (getFragmentManager().findFragmentById(R.id.flight_fragment) != null) {
            getListView().setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        }
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        System.out.println("Position: " + position);
        mCallback2.onFlightSelected(position);
        getListView().setItemChecked(position, true);
    }

    public JSONObject getStatusObject(String json) {
        try {
            JSONObject obj = new JSONObject(json);
            JSONObject stat = obj.getJSONObject("status");
            return stat;
        } catch (Exception exception) {
            exception.printStackTrace();
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
            Log.d("Vuelo buscado: ", result);
            JSONObject stat = getStatusObject(result);
            if (result != null && stat != null) {
                Flight f = new Flight(stat);
                DataManager.saveFlightInHistory(getActivity(), f);
                showHistory();
                mCallback.onFlightSearch(f);
            } else {
                CoordinatorLayout coordinatorLayout = (CoordinatorLayout) view.findViewById(R.id.coordinator_layout);
                if (coordinatorLayout != null) {
                    Snackbar.make(coordinatorLayout, "No se encuentra ese vuelo", Snackbar.LENGTH_SHORT).show();
                }
            }
        }
    }
}

