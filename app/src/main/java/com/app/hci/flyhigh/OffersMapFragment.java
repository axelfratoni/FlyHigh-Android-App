package com.app.hci.flyhigh;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by leo on 23/06/17.
 */

public class OffersMapFragment extends Fragment implements OnMapReadyCallback {

    private GoogleMap mMap;

    SupportMapFragment mapFragment;

    private static View view;

    public OffersMapFragment() {

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {

        super.onActivityCreated(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        if (view != null) {
            ViewGroup parent = (ViewGroup) view.getParent();
            if (parent != null)
                parent.removeView(view);
        }
        try {
            view = inflater.inflate(R.layout.offersmapfragment_layout, container, false);

            mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
            mapFragment.getMapAsync(this);

        } catch (InflateException e) {

        }

        return view;

    }

    @Override
    public void onMapReady(final GoogleMap gmap) {

        mMap = gmap;

        mMap.getUiSettings().setZoomControlsEnabled(true);

        addMarkersFrom("BUE");

        mMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(-34.6, -58.38)));  //bsas

    }

    private void addMarkersFrom(String origin) {

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

                    MarkerOptions m = new MarkerOptions();

                    LatLng position = new LatLng(latitude, longitude);

                    BitmapDescriptor icon = null;

                    if(price < 500.0){

                        icon = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN);

                    }else if (price > 500.00 && price < 1000.00){

                        icon = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW);

                    }else{

                        icon = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE);

                    }

                    m.position(position);

                    m.icon(icon);

                    m.title(name.split(",")[0] + " $" + price);

                    mMap.addMarker(m);

                }

            } catch (JSONException e) {

                Log.e("Error", e.toString());

            }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Log.e("Error", "");

            }
        });

        VolleyRequester.getInstance(getActivity()).addToRequestQueue(jsObjRequest);

    }

}
