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
    private String departureHour;
    private String departureAirport;
    private String arrivalHour;
    private String arrivalAirport;
    private String status;
    private String flightDate;
    private boolean[] days;

    public Flight(String airline, String departureAirport, String arrivalAirport, String departureHour, String arrivalHour,  String flightCode, String flightDate, String status){
        this.airline = airline;
        this.flightCode = flightCode;
        this.status = status;
        this.flightDate = flightDate;
        this.departureHour = departureHour;
        this.arrivalHour = arrivalHour;
        this.departureAirport = departureAirport;
        this.arrivalAirport = arrivalAirport;
    }

    public String getAirline() {
        return airline;
    }

    public String getDepartureHour(){ return departureHour; }

    public String getDepartureAirport(){ return departureAirport; }

    public String getArrivalHour(){ return arrivalHour; }

    public String getArrivalAirport(){ return arrivalAirport; }

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
