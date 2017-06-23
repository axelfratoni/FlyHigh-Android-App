package com.app.hci.flyhigh;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.support.v4.app.Fragment;

import org.json.JSONException;
import org.json.JSONObject;


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
        "json_representation"};


    public static FlightFragment newInstance(Flight flight) {
        Bundle args = prepareBundle(flight);
        FlightFragment fragment = new FlightFragment();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        try {
            this.flight = new Flight(new JSONObject(getArguments().getString(ids[14])));
        }catch(Exception e){
            e.printStackTrace();
        }
        DataManager.saveFlightInHistory(getActivity(), flight);
        super.onCreate(savedInstanceState);
//        Button button = (Button) getActivity().findViewById(R.id.flight_info_suscriptionButton);
//        button.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//                try {
//                    if (getArguments().getString(ids[13]).equals("Suscripto")) {
//                        switchSubscriptionStatus();
//                        DataManager.unsubscribe( getActivity(), flight);
//                    }else{
//                        switchSubscriptionStatus();
//                        DataManager.subscribeToFlight( getActivity(), flight);
//                    }
//                }catch (Exception e){
//                    e.printStackTrace();
//                }
//            }
//        });

    }

    private void switchSubscriptionStatus(){
        getArguments().putString(ids[13], "NoSuscripto");

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
        Button button = (Button) view.findViewById(R.id.flight_info_suscriptionButton);
        if (button != null) {
            button.setOnClickListener(new Button.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new DataManager().subscribeFlight(view.getContext(),flight);
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
        ((TextView) getActivity().findViewById(R.id.flight_info_duration)).setText(args.getString(ids[4]));
        //((ImageView) getActivity().findViewById(R.id.flight_info_airline_logo)).setImage(extras.getString(ids[5]));
        ((TextView) getActivity().findViewById(R.id.flight_info_airline)).setText(args.getString(ids[6]));
        ((TextView) getActivity().findViewById(R.id.flight_info_weekDays)).setText(args.getString(ids[7]));
        ((TextView) getActivity().findViewById(R.id.flight_info_departureHour)).setText(args.getString(ids[8]));
        ((TextView) getActivity().findViewById(R.id.flight_info_departureCityAndAirport)).setText(args.getString(ids[2])+ " - " + args.getString(ids[9]));
        ((TextView) getActivity().findViewById(R.id.flight_info_arrivalHour)).setText(args.getString(ids[10]));
        ((TextView) getActivity().findViewById(R.id.flight_info_arrivalCityAndAirport)).setText(args.getString(ids[3]) + " - " + args.getString(ids[11]));
        ((TextView) getActivity().findViewById(R.id.flight_info_number)).setText(args.getString(ids[12]));
        ((TextView) getActivity().findViewById(R.id.flight_info_status)).setText(args.getString(ids[13]));
    }

    public void updateFlightFragment(Flight flight){
        ((TextView) getActivity().findViewById(R.id.flight_info_departureAirport)).setText(flight.getDepartureAirport());
        ((TextView) getActivity().findViewById(R.id.flight_info_departureAirportBig)).setText(flight.getDepartureAirport());
        ((TextView) getActivity().findViewById(R.id.flight_info_arrivalAirport)).setText(flight.getArrivalAirport());
        ((TextView) getActivity().findViewById(R.id.flight_info_arrivalAirportBig)).setText(flight.getArrivalAirport());
        ((TextView) getActivity().findViewById(R.id.flight_info_departureCity)).setText(flight.getDepartureCity());
        ((TextView) getActivity().findViewById(R.id.flight_info_arrivalCity)).setText(flight.getArrivalCity());
        ((TextView) getActivity().findViewById(R.id.flight_info_duration)).setText(flight.getDuration());
        //((ImageView) getActivity().findViewById(R.id.flight_info_airline_logo)).setImage(extras.getString(ids[5]));
        ((TextView) getActivity().findViewById(R.id.flight_info_airline)).setText(flight.getAirlineName());
        ((TextView) getActivity().findViewById(R.id.flight_info_weekDays)).setText(flight.getWeekDays());
        ((TextView) getActivity().findViewById(R.id.flight_info_departureHour)).setText(flight.getDepartureHour());
        ((TextView) getActivity().findViewById(R.id.flight_info_departureCityAndAirport)).setText(flight.getDepartureCity()+ " - " + flight.getDepartureAirportName());
        ((TextView) getActivity().findViewById(R.id.flight_info_arrivalHour)).setText(flight.getArrivalHour());
        ((TextView) getActivity().findViewById(R.id.flight_info_arrivalCityAndAirport)).setText(flight.getArrivalCity() + " - " + flight.getArrivalAirportName());
        ((TextView) getActivity().findViewById(R.id.flight_info_number)).setText(flight.getFlightNumber());
        ((TextView) getActivity().findViewById(R.id.flight_info_status)).setText(flight.getStatus());
    }

    private static Bundle prepareBundle(Flight flight){
        Bundle args = new Bundle();
        args.putString(ids[0],flight.getDepartureAirport());
        args.putString(ids[1],flight.getArrivalAirport());
        args.putString(ids[2],flight.getDepartureCity());
        args.putString(ids[3],flight.getArrivalCity());
        args.putString(ids[4],flight.getDuration());
        args.putString(ids[6],flight.getAirlineName());
        args.putString(ids[7],flight.getWeekDays());
        args.putString(ids[8],flight.getDepartureHour());
        args.putString(ids[9],flight.getDepartureAirportName());
        args.putString(ids[10],flight.getArrivalHour());
        args.putString(ids[11],flight.getArrivalAirportName());
        args.putString(ids[12],flight.getFlightNumber());
        args.putString(ids[13],flight.getStatus());
        args.putString(ids[14],flight.getJsonRepresentation());
        return args;
    }
}
