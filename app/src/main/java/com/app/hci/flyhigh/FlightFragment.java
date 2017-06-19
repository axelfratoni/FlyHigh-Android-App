package com.app.hci.flyhigh;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.support.v4.app.Fragment;

/**
 * Created by Gaston on 19/06/2017.
 */

public class FlightFragment extends Fragment{
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Bundle extras = getActivity().getIntent().getExtras();
        String[] ids = getResources().getStringArray(R.array.flight_intent);
        ((TextView) getActivity().findViewById(R.id.flight_info_departureAirport)).setText(extras.getString(ids[0]));
        ((TextView) getActivity().findViewById(R.id.flight_info_departureAirportBig)).setText(extras.getString(ids[0]));
        ((TextView) getActivity().findViewById(R.id.flight_info_arrivalAirport)).setText(extras.getString(ids[1]));
        ((TextView) getActivity().findViewById(R.id.flight_info_arrivalAirportBig)).setText(extras.getString(ids[1]));
        ((TextView) getActivity().findViewById(R.id.flight_info_departureCity)).setText(extras.getString(ids[2]));
        ((TextView) getActivity().findViewById(R.id.flight_info_arrivalCity)).setText(extras.getString(ids[3]));
        ((TextView) getActivity().findViewById(R.id.flight_info_duration)).setText(extras.getString(ids[4]));
        //((ImageView) getActivity().findViewById(R.id.flight_info_airline_logo)).setImage(extras.getString(ids[5]));
        ((TextView) getActivity().findViewById(R.id.flight_info_airline)).setText(extras.getString(ids[6]));
        ((TextView) getActivity().findViewById(R.id.flight_info_weekDays)).setText(extras.getString(ids[7]));
        ((TextView) getActivity().findViewById(R.id.flight_info_departureHour)).setText(extras.getString(ids[8]));
        ((TextView) getActivity().findViewById(R.id.flight_info_departureCityAndAirport)).setText(extras.getString(ids[2]) + " - " + extras.getString(ids[9]));
        ((TextView) getActivity().findViewById(R.id.flight_info_arrivalHour)).setText(extras.getString(ids[10]));
        ((TextView) getActivity().findViewById(R.id.flight_info_arrivalCityAndAirport)).setText(extras.getString(ids[1]) + " - " + extras.getString(ids[11]));
        ((TextView) getActivity().findViewById(R.id.flight_info_number)).setText(extras.getString(ids[12]));
        ((TextView) getActivity().findViewById(R.id.flight_info_status)).setText(extras.getString(ids[13]));
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.flight_fragment,null);
    }
}
