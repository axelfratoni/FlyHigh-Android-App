package com.app.hci.flyhigh;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.Switch;
import android.widget.CompoundButton;

import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Gaston on 15/06/2017.
 */

public class OffersFragment extends Fragment {

    Fragment mapFragment = new OffersMapFragment();
    Fragment listFragment = new OffersListFragment();

    List<Offer> offers;

    View rootView;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        if (rootView != null) {
            ViewGroup parent = (ViewGroup) rootView.getParent();
            if (parent != null) {
                parent.removeView(rootView);
            }

        }

        rootView = inflater.inflate(R.layout.offers_layout, container, false);

        setHasOptionsMenu(true);

        requestOffers();

        return rootView;
    }

    public void start() {

        getChildFragmentManager().beginTransaction()
                .replace(R.id.offers_frame, mapFragment)
                .commit();

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        inflater.inflate(R.menu.map_menu, menu);

        MenuItem itemSwitch = menu.findItem(R.id.mySwitch);

        itemSwitch.setActionView(R.layout.switch_layout);

        final Switch sw = (Switch) menu.findItem(R.id.mySwitch).getActionView().findViewById(R.id.action_switch);

        sw.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    //FragmentManager fragmentManager = getFragmentManager();
                    getChildFragmentManager().beginTransaction()
                            .replace(R.id.offers_frame, mapFragment)
                            .commit();
                } else {
                    //FragmentManager fragmentManager = getFragmentManager();
                    getChildFragmentManager().beginTransaction()
                            .replace(R.id.offers_frame, listFragment)
                            .commit();
                }
            }
        });

        sw.setChecked(true);

        super.onCreateOptionsMenu(menu, inflater);
    }

    public void requestOffers() {

        offers = new ArrayList<Offer>();

        String origin = "BUE";

        String  url = "http://hci.it.itba.edu.ar/v1/api/booking.groovy?method=getflightdeals&from=" + origin;

        JsonObjectRequest jsObjRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {

                try {

                    for(int i=0 ; i < response.getJSONArray("deals").length() ; i++) {

                        String name = (response.getJSONArray("deals").getJSONObject(i)).getJSONObject("city").getString("name");
                        double longitude = (response.getJSONArray("deals").getJSONObject(i)).getJSONObject("city").getDouble("longitude");
                        double latitude = (response.getJSONArray("deals").getJSONObject(i)).getJSONObject("city").getDouble("latitude");
                        double price = (response.getJSONArray("deals").getJSONObject(i)).getDouble("price");

                        offers.add(new Offer(name, price, latitude, longitude));

                    }

                } catch (JSONException e) {

                    Log.e("Error", e.toString());

                }

                start();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Log.e("Error", "");

            }
        });

        VolleyRequester.getInstance(getActivity()).addToRequestQueue(jsObjRequest);

    }

    public List<Offer> getOffers() {
        return offers;
    }
}

