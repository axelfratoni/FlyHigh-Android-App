package com.app.hci.flyhigh;

import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Gaston on 15/06/2017.
 */

public class Flight {
    private String airline;
    private String flightCode;
    private Double price;
    public Flight(String airline, String flightCode, Double price){
        this.airline = airline;
        this.flightCode = flightCode;
        this.price = price;
    }

    public String getAirline() {
        return airline;
    }

    public String getFlightCode() {
        return flightCode;
    }

    public Double getPrice() {
        return price;
    }
}
