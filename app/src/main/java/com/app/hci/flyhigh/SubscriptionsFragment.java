package com.app.hci.flyhigh;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.ListFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import static android.R.id.list;

/**
 * Created by Gaston on 15/06/2017.
 */

public class SubscriptionsFragment extends ListFragment {
    OnFlightSelectedListener mCallback;
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Flight[] values = new Flight[] {
                new Flight("EZE", "JFK", "Buenos Aires", "Nueva York", "14h10m", "Aerolineas Argentinas", "AA", "21.00", "Aeropuerto internacional de ezeiza", "07.30", "Aeropuerto internacional John F. Kennedy", "AR1005", "Activo", "12/06"  ),
                new Flight("EZE", "MIA", "Buenos Aires", "Miami", "10h5m", "LATAM Airlines", "LAN", "21.00", "Aeropuerto internacional de ezeiza", "07.30", "Aeropuerto internacional de Miami", "159101", "Demorado", "12/06"),
                new Flight("EZE", "JFK", "Buenos Aires", "Nueva York", "14h10m", "Aerolineas Argentinas", "AA", "21.00", "Aeropuerto internacional de ezeiza", "07.30", "Aeropuerto internacional John F. Kennedy", "AR1005",  "Cancelado", "12/06"),
        };
        FlightArrayAdapter adapter = new FlightArrayAdapter(getActivity(), values);

        setListAdapter(adapter);
    }
    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        mCallback.onFlightSelected(position);
    }



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.subscriptions_layout,null);
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
