package com.app.hci.flyhigh;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import static com.app.hci.flyhigh.R.id.flight_date;

/**
 * Created by Gaston on 16/06/2017.
 */

public class FlightActivity extends AppCompatActivity{
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flight_info);
        Bundle extras = getIntent().getExtras();
        String[] ids = getResources().getStringArray(R.array.flight_intent);
        ((TextView) findViewById(R.id.flight_info_airline_id)).setText(extras.getString(ids[0]));
        ((TextView) findViewById(R.id.flight_info_departureAirport)).setText(extras.getString(ids[1]));
        ((TextView) findViewById(R.id.flight_info_departureAirportInfo)).setText(extras.getString(ids[2]));
        ((TextView) findViewById(R.id.flight_info_departureHour)).setText("Parte: " + extras.getString(ids[3]));
        ((TextView) findViewById(R.id.flight_info_arrivalAirport)).setText(extras.getString(ids[4]));
        ((TextView) findViewById(R.id.flight_info_arrivalAirportInfo)).setText(extras.getString(ids[5]));
        ((TextView) findViewById(R.id.flight_info_arrivalHour)).setText("Llega: " + extras.getString(ids[6]));
        ((TextView) findViewById(R.id.flight_info_number)).setText(extras.getString(ids[7]));
        ((TextView) findViewById(R.id.flight_info_status)).setText(extras.getString(ids[8]));

    }
}
