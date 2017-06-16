package com.app.hci.flyhigh;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.app.ListFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import static android.R.id.list;

/**
 * Created by Gaston on 15/06/2017.
 */

public class SubscriptionsFragment extends ListFragment {

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Flight[] values = new Flight[] {
                new Flight("AA", "EZE", "JFK", "21.00", "07.30", "AR1005", "12/06", "Activo"),
                new Flight("LAN", "EZE", "OLA", "10.00", "20.00", "AR12346", "13/06", "Demorado"),
                new Flight("TAM", "EZE", "ASS", "11.15", "15.30", "AR923002", "14/06", "Volando")
        };
        FlightArrayAdapter adapter = new FlightArrayAdapter(getActivity(), values);

        setListAdapter(adapter);
    }
    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        startActivity(prepareIntent(position));
    }

    private Intent prepareIntent(int position){
        Intent i = new Intent(getActivity().getApplicationContext(), FlightActivity.class);
        Flight flight = (Flight) getListAdapter().getItem(position);
        String[] ids = getResources().getStringArray(R.array.flight_intent);
        i.putExtra(ids[0],flight.getAirline());
        i.putExtra(ids[1],flight.getDepartureAirport());
        i.putExtra(ids[2],flight.getDepartureAirport());
        i.putExtra(ids[3],flight.getDepartureHour());
        i.putExtra(ids[4],flight.getArrivalAirport());
        i.putExtra(ids[5],flight.getArrivalAirport());
        i.putExtra(ids[6],flight.getArrivalHour());
        i.putExtra(ids[7],flight.getFlightCode());
        i.putExtra(ids[8],flight.getStatus());
        return i;
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.subscriptions_layout,null);
    }

}
