package com.app.hci.flyhigh;

import android.content.Context;
import android.support.v4.app.ListFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;


/**
 * Created by Gaston on 15/06/2017.
 */

public class SubscriptionsFragment extends ListFragment {
    OnFlightSelectedListener mCallback;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Flight f1 = new Flight("EZE", "JFK", "Buenos Aires", "Nueva York", "14h10m", "Aerolineas Argentinas", "AA", "21.00", "Aeropuerto internacional de ezeiza", "07.30", "Aeropuerto internacional John F. Kennedy", "AR1005", "Activo", "12/06"  );
        Flight f2 = new Flight("EZE", "MIA", "Buenos Aires", "Miami", "10h5m", "LATAM Airlines", "LAN", "21.00", "Aeropuerto internacional de ezeiza", "07.30", "Aeropuerto internacional de Miami", "159101", "Demorado", "12/06");
        Flight f3 = new Flight("EZE", "JFK", "Buenos Aires", "Nueva York", "14h10m", "Aerolineas Argentinas", "AA", "21.00", "Aeropuerto internacional de ezeiza", "07.30", "Aeropuerto internacional John F. Kennedy", "AR1005",  "Cancelado", "12/06");
        Flight[] valuesA = new Flight[] { f1, f2, f3 };
        if(FlightsHolder.subscriptionFlights == null){
               FlightsHolder.subscriptionFlights = new ArrayList<>();
           }
        FlightsHolder.subscriptionFlights.add(f1);
        FlightsHolder.subscriptionFlights.add(f2);
        FlightsHolder.subscriptionFlights.add(f3);
        FlightArrayAdapter adapter = new FlightArrayAdapter(getActivity(), valuesA);
        setListAdapter(adapter);
    }
    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        mCallback.onFlightSelected(position);
        getListView().setItemChecked(position, true);
    }

    public void onStart() {
        super.onStart();

        // When in two-pane layout, set the listview to highlight the selected list item
        // (We do this during onStart because at the point the listview is available.)
        if (getFragmentManager().findFragmentById(R.id.flight_fragment) != null) {
            getListView().setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        }
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
