package com.app.hci.flyhigh;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;


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
        ((TextView) findViewById(R.id.flight_info_departureAirport)).setText(extras.getString(ids[0]));
        ((TextView) findViewById(R.id.flight_info_departureAirportBig)).setText(extras.getString(ids[0]));
        ((TextView) findViewById(R.id.flight_info_arrivalAirport)).setText(extras.getString(ids[1]));
        ((TextView) findViewById(R.id.flight_info_arrivalAirportBig)).setText(extras.getString(ids[1]));
        ((TextView) findViewById(R.id.flight_info_departureCity)).setText(extras.getString(ids[2]));
        ((TextView) findViewById(R.id.flight_info_arrivalCity)).setText(extras.getString(ids[3]));
        ((TextView) findViewById(R.id.flight_info_duration)).setText(extras.getString(ids[4]));
        //((ImageView) findViewById(R.id.flight_info_airline_logo)).setImage(extras.getString(ids[5]));
        ((TextView) findViewById(R.id.flight_info_airline)).setText(extras.getString(ids[6]));
        ((TextView) findViewById(R.id.flight_info_weekDays)).setText(extras.getString(ids[7]));
        ((TextView) findViewById(R.id.flight_info_departureHour)).setText(extras.getString(ids[8]));
        ((TextView) findViewById(R.id.flight_info_departureCityAndAirport)).setText(extras.getString(ids[2]) + " - " + extras.getString(ids[9]));
        ((TextView) findViewById(R.id.flight_info_arrivalHour)).setText(extras.getString(ids[10]));
        ((TextView) findViewById(R.id.flight_info_arrivalCityAndAirport)).setText(extras.getString(ids[1]) + " - " + extras.getString(ids[11]));
        ((TextView) findViewById(R.id.flight_info_number)).setText(extras.getString(ids[12]));
        ((TextView) findViewById(R.id.flight_info_status)).setText(extras.getString(ids[13]));
    }
}
