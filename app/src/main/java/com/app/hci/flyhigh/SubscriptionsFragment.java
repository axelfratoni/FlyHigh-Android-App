package com.app.hci.flyhigh;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;
import android.support.v4.app.ListFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static android.content.Context.ALARM_SERVICE;


/**
 * Created by Gaston on 15/06/2017.
 */

public class SubscriptionsFragment extends ListFragment {
    OnFlightSelectedListener mCallback;
    View view;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*Flight f1 = new Flight("EZE", "JFK", "Buenos Aires", "Nueva York", "14h10m", "Aerolineas Argentinas", "AA", "21.00", "Aeropuerto internacional de ezeiza", "07.30", "Aeropuerto internacional John F. Kennedy", "AR1005", "Activo", "12/06"  );
        Flight f2 = new Flight("EZE", "MIA", "Buenos Aires", "Miami", "10h5m", "LATAM Airlines", "LAN", "21.00", "Aeropuerto internacional de ezeiza", "07.30", "Aeropuerto internacional de Miami", "159101", "Demorado", "12/06");
        Flight f3 = new Flight("EZE", "JFK", "Buenos Aires", "Nueva York", "14h10m", "Aerolineas Argentinas", "AA", "21.00", "Aeropuerto internacional de ezeiza", "07.30", "Aeropuerto internacional John F. Kennedy", "AR1005",  "Cancelado", "12/06");
        Flight[] valuesA = new Flight[] { f1, f2, f3 };
        if(FlightsHolder.subscriptionFlights == null){
               FlightsHolder.subscriptionFlights = new ArrayList<>();
           }
        FlightsHolder.subscriptionFlights.add(f1);
        FlightsHolder.subscriptionFlights.add(f2);
        FlightsHolder.subscriptionFlights.add(f3);*/
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
        view = inflater.inflate(R.layout.subscriptions_layout,null);

        Flight[] valuesA = null;
        try {
            //valuesA = new Flight[]{new Flight(new JSONObject("{\"meta\":{\"uuid\":\"a2be40ed-b9e5-4d22-a3b7-d65aceb2b29c\",\"time\":\"117.524935ms\"},\"status\":{\"id\":94588,\"number\":8700,\"airline\":{\"id\":\"8R\",\"name\":\"SOL\",\"logo\":\"http://hci.it.itba.edu.ar/v1/images/8R.png\"},\"status\":\"S\",\"departure\":{\"airport\":{\"id\":\"EZE\",\"description\":\"Aeropuerto Ezeiza Ministro Pistarini, Buenos Aires, Argentina\",\"time_zone\":\"-03:00\",\"latitude\":-34.8126,\"longitude\":-58.5397,\"city\":{\"id\":\"BUE\",\"name\":\"Buenos Aires, Ciudad de Buenos Aires\",\"latitude\":-34.8126,\"longitude\":-58.5397,\"country\":{\"id\":\"AR\",\"name\":\"Argentina\"}},\"terminal\":null,\"gate\":null},\"scheduled_time\":\"2017-06-23 05:50:00\",\"actual_time\":null,\"scheduled_gate_time\":\"2017-06-23 05:00:00\",\"actual_gate_time\":null,\"gate_delay\":null,\"estimate_runway_time\":\"2017-06-23 05:30:00\",\"actual_runway_time\":null,\"runway_delay\":null},\"arrival\":{\"airport\":{\"id\":\"TUC\",\"description\":\"Aeropuerto Benjamin Matienzo, San Miguel de Tucuman, Argentina\",\"time_zone\":\"-03:00\",\"latitude\":-26.8357,\"longitude\":-65.1083,\"city\":{\"id\":\"TUC\",\"name\":\"San Miguel de Tucuman, Tucuman\",\"latitude\":-26.8083,\"longitude\":-65.2176,\"country\":{\"id\":\"AR\",\"name\":\"Argentina\"}},\"terminal\":null,\"gate\":null,\"baggage\":null},\"scheduled_time\":\"2017-06-23 07:09:00\",\"actual_time\":null,\"scheduled_gate_time\":\"2017-06-23 07:59:00\",\"actual_gate_time\":null,\"gate_delay\":null,\"estimate_runway_time\":\"2017-06-23 07:29:00\",\"actual_runway_time\":null,\"runway_delay\":null}}}").getJSONObject("status"))};
            Flight testFLight = new Flight(new JSONObject("{\"meta\":{\"uuid\":\"a2be40ed-b9e5-4d22-a3b7-d65aceb2b29c\",\"time\":\"117.524935ms\"},\"status\":{\"id\":94588,\"number\":8700,\"airline\":{\"id\":\"8R\",\"name\":\"SOL\",\"logo\":\"http://hci.it.itba.edu.ar/v1/images/8R.png\"},\"status\":\"S\",\"departure\":{\"airport\":{\"id\":\"EZE\",\"description\":\"Aeropuerto Ezeiza Ministro Pistarini, Buenos Aires, Argentina\",\"time_zone\":\"-03:00\",\"latitude\":-34.8126,\"longitude\":-58.5397,\"city\":{\"id\":\"BUE\",\"name\":\"Buenos Aires, Ciudad de Buenos Aires\",\"latitude\":-34.8126,\"longitude\":-58.5397,\"country\":{\"id\":\"AR\",\"name\":\"Argentina\"}},\"terminal\":null,\"gate\":null},\"scheduled_time\":\"2017-06-23 05:50:00\",\"actual_time\":null,\"scheduled_gate_time\":\"2017-06-23 05:00:00\",\"actual_gate_time\":null,\"gate_delay\":null,\"estimate_runway_time\":\"2017-06-23 05:30:00\",\"actual_runway_time\":null,\"runway_delay\":null},\"arrival\":{\"airport\":{\"id\":\"TUC\",\"description\":\"Aeropuerto Benjamin Matienzo, San Miguel de Tucuman, Argentina\",\"time_zone\":\"-03:00\",\"latitude\":-26.8357,\"longitude\":-65.1083,\"city\":{\"id\":\"TUC\",\"name\":\"San Miguel de Tucuman, Tucuman\",\"latitude\":-26.8083,\"longitude\":-65.2176,\"country\":{\"id\":\"AR\",\"name\":\"Argentina\"}},\"terminal\":null,\"gate\":null,\"baggage\":null},\"scheduled_time\":\"2017-06-23 07:09:00\",\"actual_time\":null,\"scheduled_gate_time\":\"2017-06-23 07:59:00\",\"actual_gate_time\":null,\"gate_delay\":null,\"estimate_runway_time\":\"2017-06-23 07:29:00\",\"actual_runway_time\":null,\"runway_delay\":null}}}").getJSONObject("status"));
            //new DataManager().subscribeFlight(getActivity(),testFLight);
            valuesA = new DataManager().retrieveSubscriptionsDep(view.getContext());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (valuesA != null) {
            FlightArrayAdapter adapter = new FlightArrayAdapter(((MainActivity)getActivity()), valuesA);
            setListAdapter(adapter);

            //((MainActivity)getActivity()).getNotificationDealer().startNotifications();
        }
        return view;
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
