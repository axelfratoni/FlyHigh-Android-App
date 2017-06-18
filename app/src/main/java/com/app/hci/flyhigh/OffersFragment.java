package com.app.hci.flyhigh;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

/**
 * Created by Gaston on 15/06/2017.
 */

public class OffersFragment extends ListFragment {
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Flight[] values = new Flight[] {
                new Flight("EZE", "JFK", "Buenos Aires", "Nueva York", "14h10m", "Aerolineas Argentinas", "AA", "21.00", "Aeropuerto internacional de ezeiza", "07.30", "Aeropuerto internacional John F. Kennedy", "AR1005", "12/06", "Activo")
        };
        FlightArrayAdapter adapter = new FlightArrayAdapter(getActivity(), values);
        setListAdapter(adapter);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        //startActivity(prepareIntent(position));
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.offers_layout,null);

    }
}
