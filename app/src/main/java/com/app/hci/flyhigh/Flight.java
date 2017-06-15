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
    private String originDate;
    private String originAirport;
    private String destinationDate;
    private String destinationAirport;
    private String status;
    private boolean[] days;

    public Flight(String airline, String flightCode, String originAirport, String destinationAirport, Double price){
        this.airline = airline;
        this.flightCode = flightCode;
        this.price = price;
        this.originAirport = originAirport;
        this.destinationAirport = destinationAirport;
    }

    public String getAirline() {
        return airline;
    }

    public String getOriginDate() {
        return originDate;
    }

    public String getOriginAirport() {
        return originAirport;
    }

    public String getDestinationDate() {
        return destinationDate;
    }

    public String getDestinationAirport() {
        return destinationAirport;
    }

    public String getStatus() {
        return status;
    }

    public boolean[] getDays() {
        return days;
    }

    public String getFlightCode() {
        return flightCode;
    }

    public Double getPrice() {
        return price;
    }
}
