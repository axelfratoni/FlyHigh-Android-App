package com.app.hci.flyhigh;

import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONObject;

/**
 * Created by Gaston on 15/06/2017.
 */

public class Flight {
    private JSONObject jsonRepresentation;
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

    @Deprecated
    public Flight(String departureAirport, String arrivalAirport, String departureCity, String arrivalCity, String duration, String airlineName, String airlineId, String departureHour, String departureAirportName, String arrivalHour, String arrivalAirportName, String flightNumber, String status, String flightDate){
        setFlightData(departureAirport, arrivalAirport, departureCity, arrivalCity, duration, airlineName, airlineId, departureHour, departureAirportName, arrivalHour, arrivalAirportName, flightNumber, status, flightDate);
    }

    public Flight(JSONObject stat){
        try {
            String arrID = stat.getJSONObject("arrival").getJSONObject("airport").getJSONObject("city").getString("id");
            String arrName = stat.getJSONObject("arrival").getJSONObject("airport").getJSONObject("city").getString("name").split(",")[0];
            String depID = stat.getJSONObject("departure").getJSONObject("airport").getJSONObject("city").getString("id");
            String depName = stat.getJSONObject("departure").getJSONObject("airport").getJSONObject("city").getString("name").split(",")[0];
            String duration = "6:66";
            String aeroName = stat.getJSONObject("airline").getString("name");
            String depTime = stat.getJSONObject("departure").getString("scheduled_time").split(" ")[1];
            String arrTime = stat.getJSONObject("arrival").getString("scheduled_time").split(" ")[1];
            String depAirName = stat.getJSONObject("departure").getJSONObject("airport").getString("description").split(",")[0];
            String arrAirName = stat.getJSONObject("arrival").getJSONObject("airport").getString("description").split(",")[0];
            String airId = stat.getJSONObject("airline").getString("id");
            String flightNum = stat.getString("number");
            String fliDate = stat.getJSONObject("departure").getString("scheduled_time").split(" ")[0];
            String status = "-";
            switch (stat.getString("status")) {
                case "S":
                    status = "Programado";
                    break;
                case "A":
                    status = "Activo";
                    break;
                case "R":
                    status = "Desviado";
                    break;
                case "L":
                    status = "Aterrizado";
                    break;
                case "C":
                    status = "Cancelado";
                    break;
                default:
                    new RuntimeException("Problema en el servidor");
            }
            setFlightData(depID, arrID, depName, arrName, duration, aeroName, airId, depTime, depAirName, arrTime, arrAirName, flightNum, status, fliDate);
            jsonRepresentation = stat;
        }catch(Exception e){
            e.printStackTrace();
            //result = "No existe ese vuelo";
        }
    }

    private void setFlightData(String departureAirport, String arrivalAirport, String departureCity, String arrivalCity, String duration, String airlineName, String airlineId, String departureHour, String departureAirportName, String arrivalHour, String arrivalAirportName, String flightNumber, String status, String flightDate){
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

    public String getJsonRepresentation(){ return jsonRepresentation.toString(); }
}
