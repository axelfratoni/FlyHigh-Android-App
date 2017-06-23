package com.app.hci.flyhigh;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

/**
 * Created by leo on 23/06/17.
 */

public class OffersMapFragment extends Fragment implements OnMapReadyCallback {

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
        /* map is already there, just return view as it is */
        }

        return view;

        /*View rootView = inflater.inflate(R.layout.offersmapfragment_layout, container, false);

        mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        return rootView;*/
    }

    @Override
    public void onMapReady(final GoogleMap map) {

        final String url = "http://hci.it.itba.edu.ar/v1/api/booking.groovy?method=getflightdeals&from=BUE";

        LatLng bsas = new LatLng(-34.603, -58.381);
        map.addMarker(new MarkerOptions().position(bsas)
                .title("Buenos Aires"));
        map.moveCamera(CameraUpdateFactory.newLatLng(bsas));
    }

}
