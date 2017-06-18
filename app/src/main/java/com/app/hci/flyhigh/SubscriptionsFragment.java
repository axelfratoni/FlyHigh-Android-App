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
import android.widget.TextView;
import android.widget.Toast;

import static android.R.id.list;

/**
 * Created by Gaston on 15/06/2017.
 */

public class SubscriptionsFragment extends ListFragment {

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
        startActivity(prepareIntent(position));
    }

    private Intent prepareIntent(int position){
        Intent i = new Intent(getActivity().getApplicationContext(), FlightActivity.class);
        Flight flight = (Flight) getListAdapter().getItem(position);
        String[] ids = getResources().getStringArray(R.array.flight_intent);
        i.putExtra(ids[0],flight.getDepartureAirport());
        i.putExtra(ids[1],flight.getArrivalAirport());
        i.putExtra(ids[2],flight.getDepartureCity());
        i.putExtra(ids[3],flight.getArrivalCity());
        i.putExtra(ids[4],flight.getDuration());
        i.putExtra(ids[6],flight.getAirlineName());
        i.putExtra(ids[7],flight.getWeekDays());
        i.putExtra(ids[8],flight.getDepartureHour());
        i.putExtra(ids[9],flight.getDepartureAirportName());
        i.putExtra(ids[10],flight.getArrivalHour());
        i.putExtra(ids[11],flight.getArrivalAirportName());
        i.putExtra(ids[12],flight.getFlightNumber());
        i.putExtra(ids[13],flight.getStatus());
        return i;
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.subscriptions_layout,null);
    }

}
