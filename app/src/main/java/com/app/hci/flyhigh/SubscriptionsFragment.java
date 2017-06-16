package com.app.hci.flyhigh;

import android.support.v4.app.ListFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

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

        ListView listView = getListView();
        if(listView != null){
            setListAdapter(adapter);
        }
        setListAdapter(adapter);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.subscriptions_layout,null);
    }

}
