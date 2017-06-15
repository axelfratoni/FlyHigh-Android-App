package com.app.hci.flyhigh;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

/**
 * Created by Gaston on 15/06/2017.
 */

public class SuscriptionsFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        Flight[] valuse = new Flight[] {
                new Flight("Aerolineas Argentinas", "AR1005", 1534.5),
                new Flight("Emirates", "AR12346", 12345.54),
                new Flight("LAN", "AR923002", 789001.4)
        };
        FlightArrayAdapter adapter = new FlightArrayAdapter(getActivity(), valuse);

        ListView listView = (ListView)getActivity().findViewById(R.id.list_view);
        if(listView != null){
            listView.setAdapter(adapter);
        }
        return inflater.inflate(R.layout.suscriptions_layout,null);
    }

}
