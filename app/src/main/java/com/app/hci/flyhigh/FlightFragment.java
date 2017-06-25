package com.app.hci.flyhigh;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.support.v4.app.Fragment;

import org.json.JSONException;
import org.json.JSONObject;

import static android.graphics.Color.rgb;


/**
 * Created by Gaston on 19/06/2017.
 */

public class FlightFragment extends Fragment{
    int mCurrentPosition = -1;

    final static String ARG_POSITION = "position";
    private Flight flight;
    private static String[] ids = { "departure_airport",
        "arrival_airport",
        "departure_city",
        "arrival_city",
        "duration",
        "airline_logo",
        "airline",
        "week_days",
        "departure_hour",
        "departure_airport_name",
        "arrival_hour",
        "arrival_airport_name",
        "flight_number",
        "subscription_status",
        "departure_gate",
        "departure_terminal",
        "arrival_gate",
        "arrival_terminal",
        "json_representation"};


    public static FlightFragment newInstance(Context context, Flight flight) {
        Bundle args = prepareBundle(context, flight);
        FlightFragment fragment = new FlightFragment();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        try {
            this.flight = new Flight(new JSONObject(getArguments().getString(ids[18])));
        }catch(Exception e){
            e.printStackTrace();
        }
        super.onCreate(savedInstanceState);

    }

    private void switchSubscriptionStatus(boolean subscribing, View view){
        FloatingActionButton fab;
        if(view != null)
            fab = (FloatingActionButton)view.findViewById(R.id.flight_info_suscriptionButton);
        else
            fab = (FloatingActionButton) getView().findViewById(R.id.flight_info_suscriptionButton);
        if(subscribing){
            fab.setBackgroundTintList(ColorStateList.valueOf(rgb(196, 196, 196)));
            fab.setImageResource(R.drawable.ic_favorite_black_24dp);
        }else{
            fab.setBackgroundTintList(ColorStateList.valueOf(rgb(196, 196, 196)));
            fab.setImageResource(R.drawable.ic_favorite_empty_border_black_24dp);
        }

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // If activity recreated (such as from screen rotate), restore
        // the previous article selection set by onSaveInstanceState().
        // This is primarily necessary when in the two-pane layout.
        if (savedInstanceState != null) {
            mCurrentPosition = savedInstanceState.getInt(ARG_POSITION);
        }

        final View view =  inflater.inflate(R.layout.flight_fragment, container, false);
        FloatingActionButton button = (FloatingActionButton) view.findViewById(R.id.flight_info_suscriptionButton);
        if(DataManager.isSubscribed(getActivity(), flight))
            switchSubscriptionStatus(true, view);
        else
            switchSubscriptionStatus(false, view);
        button.setColorFilter(Color.rgb(255, 30, 41));

        if (button != null) {
            button.setOnClickListener(new Button.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(!DataManager.isSubscribed(getActivity(), flight)){
                        switchSubscriptionStatus(true, null);
                        DataManager.subscribeToFlight(getActivity(), flight);
                    }else{
                        switchSubscriptionStatus(false, null);
                        DataManager.unsubscribe(getActivity(), flight);
                    }
                }
            });
        }

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        // During startup, check if there are arguments passed to the fragment.
        // onStart is a good place to do this because the layout has already been
        // applied to the fragment at this point so we can safely call the method
        // below that sets the article text.
        Bundle args = getArguments();
        if (args != null) {
            // Set article based on argument passed in
            updateFlightFragment(args);
        } else if (mCurrentPosition != -1) {
            // Set article based on saved instance state defined during onCreateView
            updateFlightFragment(mCurrentPosition);
        }
    }
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        // Save the current article selection in case we need to recreate the fragment
        outState.putInt(ARG_POSITION, mCurrentPosition);
    }

    private void updateFlightFragment(int position){
        updateFlightFragment(FlightsHolder.subscriptionFlights.get(position));
        mCurrentPosition = position;
    }

    private void updateFlightFragment(Bundle args){
        ((TextView) getActivity().findViewById(R.id.flight_info_departureAirport)).setText(args.getString(ids[0]));
        ((TextView) getActivity().findViewById(R.id.flight_info_departureAirportBig)).setText(args.getString(ids[0]));
        ((TextView) getActivity().findViewById(R.id.flight_info_arrivalAirport)).setText(args.getString(ids[1]));
        ((TextView) getActivity().findViewById(R.id.flight_info_arrivalAirportBig)).setText(args.getString(ids[1]));
        ((TextView) getActivity().findViewById(R.id.flight_info_departureCity)).setText(args.getString(ids[2]));
        ((TextView) getActivity().findViewById(R.id.flight_info_arrivalCity)).setText(args.getString(ids[3]));
        //((ImageView) getActivity().findViewById(R.id.flight_info_airline_logo)).setImage(extras.getString(ids[5]));
        ((TextView) getActivity().findViewById(R.id.flight_info_airline)).setText(args.getString(ids[6]));
        ((TextView) getActivity().findViewById(R.id.flight_info_departureHour)).setText(args.getString(ids[8]));
        ((TextView) getActivity().findViewById(R.id.flight_info_departureCityAndAirport)).setText(getString(R.string.airport_city, args.getString(ids[2]), args.getString(ids[9])));
        ((TextView) getActivity().findViewById(R.id.flight_info_arrivalHour)).setText(args.getString(ids[10]));
        ((TextView) getActivity().findViewById(R.id.flight_info_arrivalCityAndAirport)).setText(getString(R.string.airport_city, args.getString(ids[3]) ,args.getString(ids[11])));
        ((TextView) getActivity().findViewById(R.id.flight_info_number)).setText(args.getString(ids[12]));
        ((TextView) getActivity().findViewById(R.id.flight_info_status)).setText(args.getString(ids[13]));
        ((TextView) getActivity().findViewById(R.id.flight_info_departure_gate)).setText(getString(R.string.gate, args.getString(ids[14])));
        ((TextView) getActivity().findViewById(R.id.flight_info_departure_terminal)).setText(getString(R.string.terminal, args.getString(ids[15])));
        ((TextView) getActivity().findViewById(R.id.flight_info_arrival_gate)).setText(getString(R.string.gate, args.getString(ids[16])));
        ((TextView) getActivity().findViewById(R.id.flight_info_arrival_terminal)).setText(getString(R.string.terminal, args.getString(ids[17])));
    }

    public void updateFlightFragment(Flight flight){
        ((TextView) getActivity().findViewById(R.id.flight_info_departureAirport)).setText(flight.getDepartureAirport());
        ((TextView) getActivity().findViewById(R.id.flight_info_departureAirportBig)).setText(flight.getDepartureAirport());
        ((TextView) getActivity().findViewById(R.id.flight_info_arrivalAirport)).setText(flight.getArrivalAirport());
        ((TextView) getActivity().findViewById(R.id.flight_info_arrivalAirportBig)).setText(flight.getArrivalAirport());
        ((TextView) getActivity().findViewById(R.id.flight_info_departureCity)).setText(flight.getDepartureCity());
        ((TextView) getActivity().findViewById(R.id.flight_info_arrivalCity)).setText(flight.getArrivalCity());
        ((TextView) getActivity().findViewById(R.id.flight_info_departure_gate)).setText(getString(R.string.gate, flight.getDepartureGate()));
        ((TextView) getActivity().findViewById(R.id.flight_info_arrival_gate)).setText(getString(R.string.gate, flight.getArrivalGate()));
        ((TextView) getActivity().findViewById(R.id.flight_info_departure_terminal)).setText(getString(R.string.terminal, flight.getDepartureTerminal()));
        ((TextView) getActivity().findViewById(R.id.flight_info_arrival_terminal)).setText(getString(R.string.terminal, flight.getArrivalTerminal()));
        //((ImageView) getActivity().findViewById(R.id.flight_info_airline_logo)).setImage(extras.getString(ids[5]));
        ((TextView) getActivity().findViewById(R.id.flight_info_airline)).setText(flight.getAirlineName());
        ((TextView) getActivity().findViewById(R.id.flight_info_departureHour)).setText(flight.getDepartureHour());
        ((TextView) getActivity().findViewById(R.id.flight_info_departureCityAndAirport)).setText(getString(R.string.airport_city, flight.getDepartureCity(), flight.getDepartureAirportName()));
        ((TextView) getActivity().findViewById(R.id.flight_info_arrivalHour)).setText(flight.getArrivalHour());
        ((TextView) getActivity().findViewById(R.id.flight_info_arrivalCityAndAirport)).setText(getString(R.string.airport_city, flight.getArrivalCity() , flight.getArrivalAirportName()));
        ((TextView) getActivity().findViewById(R.id.flight_info_number)).setText(flight.getFlightNumber());
        ((TextView) getActivity().findViewById(R.id.flight_info_status)).setText(flight.getStatus(getContext()));
    }

    private static Bundle prepareBundle(Context context, Flight flight){
        Bundle args = new Bundle();
        args.putString(ids[0],flight.getDepartureAirport());
        args.putString(ids[1],flight.getArrivalAirport());
        args.putString(ids[2],flight.getDepartureCity());
        args.putString(ids[3],flight.getArrivalCity());
//        args.putString(ids[4],flight.getDuration());
        args.putString(ids[6],flight.getAirlineName());
//        args.putString(ids[7],flight.getWeekDays());
        args.putString(ids[8],flight.getDepartureHour());
        args.putString(ids[9],flight.getDepartureAirportName());
        args.putString(ids[10],flight.getArrivalHour());
        args.putString(ids[11],flight.getArrivalAirportName());
        args.putString(ids[12],flight.getFlightNumber());
        args.putString(ids[13],flight.getStatus(context));
        args.putString(ids[14],flight.getDepartureGate());
        args.putString(ids[15],flight.getDepartureTerminal());
        args.putString(ids[16],flight.getArrivalGate());
        args.putString(ids[17],flight.getArrivalTerminal());
        args.putString(ids[18],flight.getJsonRepresentation());
        return args;
    }
}
