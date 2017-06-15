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
                new Flight("Aerolineas Argentinas", "AR1005", 1534.5),
                new Flight("Emirates", "AR12346", 12345.54),
                new Flight("LAN", "AR923002", 789001.4)
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
