package com.app.hci.flyhigh;

import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Gaston on 15/06/2017.
 */

public class Flight {
    private String departureAirport;
    private String arrivalAirport;
    private String departureCity;
    private String arrivalCity;
    private String duration;
    private ImageView airlineLogo; /// No se si lo tendria que tener el vuelo
    private String airlineName;
    private String airlineId;
    private boolean[] days;
    private String departureHour;
    private String departureAirportName;
    private String arrivalHour;
    private String arrivalAirportName;
    private String flightNumber;
    private String status;
    private Double price;
    private String flightDate;

    public Flight(String departureAirport, String arrivalAirport, String departureCity, String arrivalCity, String duration, String airlineName, String airlineId, String departureHour, String departureAirportName, String arrivalHour, String arrivalAirportName, String flightNumber, String status, String flightDate){
        this.airlineName = airlineName;
        this.airlineId = airlineId;
        this.flightNumber= flightNumber;
        this.status = status;
        this.flightDate = flightDate;
        this.departureHour = departureHour;
        this.arrivalHour = arrivalHour;
        this.departureAirport = departureAirport;
        this.arrivalAirport = arrivalAirport;
        this.departureCity = departureCity;
        this.arrivalCity = arrivalCity;
        this.duration = duration;
        this.departureAirportName = departureAirportName;
        this.arrivalAirportName = arrivalAirportName;
    }

    public String getArrivalCity() { return arrivalCity; }

    public String getAirlineName() {
        return airlineName;
    }

    public String getAirlineId() {
        return airlineId;
    }

    public String getDepartureHour(){ return departureHour; }

    public String getDepartureAirport(){ return departureAirport; }

    public String getArrivalHour(){ return arrivalHour; }

    public String getDepartureAirportName() { return departureAirportName; }

    public String getArrivalAirportName() { return arrivalAirportName; }

    public String getArrivalAirport(){ return arrivalAirport; }

    public String getStatus() {
        return status;
    }

    public String getWeekDays() {
        return "Lunes, Martes y Miercoles";
    }

    public String getDate() { return flightDate; }

    public String getFlightNumber() {
        return flightNumber;
    }

    public Double getPrice() {
        return price;
    }

    public String getDuration() { return duration; }

    public String getDepartureCity() { return departureCity; }
}
