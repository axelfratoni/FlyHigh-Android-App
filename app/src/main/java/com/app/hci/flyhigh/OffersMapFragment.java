package com.app.hci.flyhigh;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

/**
 * Created by leo on 23/06/17.
 */

public class OffersMapFragment extends Fragment implements OnMapReadyCallback {

    private GoogleMap mMap;

    SupportMapFragment mapFragment;

    View view;

    //List<Offer> offers;

    public OffersMapFragment() {

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

        addMarkers();

        mMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(-34.6, -58.38)));  //bsas

    }

    private void addMarkers() {

        List<Offer> offers = ((OffersFragment)OffersMapFragment.this.getParentFragment()).getOffers();

        for(int i = 0 ; i < offers.size() ; i++) {

            String name = offers.get(i).getName();
            double longitude = offers.get(i).getLongitude();
            double latitude = offers.get(i).getLatitude();
            double price = offers.get(i).getPrice();

            MarkerOptions m = new MarkerOptions();

            LatLng latlng = new LatLng(latitude, longitude);

            BitmapDescriptor icon = null;

            if(price < 500.0){

                icon = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN);

            }else if (price > 500.00 && price < 1000.00){

                icon = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW);

            }else{

                icon = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE);

            }

            m.position(latlng);

            m.icon(icon);

            m.title(name.split(",")[0] + " $" + price);

            mMap.addMarker(m);

        }

    }

}
