package com.app.hci.flyhigh;

import android.content.Context;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONObject;

import static android.R.attr.duration;

/**
 * Created by Gaston on 15/06/2017.
 */

public class Flight {
    private JSONObject jsonRepresentation;
    private String departureAirport;
    private String arrivalAirport;
    private String departureCity;
    private String arrivalCity;
    private ImageView airlineLogo; /// No se si lo tendria que tener el vuelo
    private String airlineName;
    private String airlineId;
    private String departureHour;
    private String departureAirportName;
    private String arrivalHour;
    private String arrivalAirportName;
    private String flightNumber;
    private String status;
    private Double price;
    private String flightDate;
    private String departureGate;
    private String departureTerminal;
    private String arrivalGate;
    private String arrivalTerminal;

    public Flight(String departureAirport, String arrivalAirport, String departureCity, String arrivalCity, String airlineName, String airlineId, String departureHour, String departureAirportName, String arrivalHour, String arrivalAirportName, String flightNumber, String status, String flightDate, String departureGate,String departureTerminal, String arrivalGate, String arrivalTerminal){
        setFlightData(departureAirport, arrivalAirport, departureCity, arrivalCity, airlineName, airlineId, departureHour, departureAirportName, arrivalHour, arrivalAirportName, flightNumber, status, flightDate, departureGate,departureTerminal, arrivalGate, arrivalTerminal);
    }
    public Flight(JSONObject stat){
        try {
            String arrID = stat.getJSONObject("arrival").getJSONObject("airport").getJSONObject("city").getString("id");
            String arrName = stat.getJSONObject("arrival").getJSONObject("airport").getJSONObject("city").getString("name").split(",")[0];
            String depID = stat.getJSONObject("departure").getJSONObject("airport").getJSONObject("city").getString("id");
            String depName = stat.getJSONObject("departure").getJSONObject("airport").getJSONObject("city").getString("name").split(",")[0];
            String arrGate = checkIfNull(stat.getJSONObject("arrival").getJSONObject("airport").getString("gate"));
            String arrTerminal = checkIfNull(stat.getJSONObject("arrival").getJSONObject("airport").getString("terminal"));
            String depGate = checkIfNull(stat.getJSONObject("departure").getJSONObject("airport").getString("gate"));
            String depTerminal = checkIfNull(stat.getJSONObject("departure").getJSONObject("airport").getString("terminal"));
            String aeroName = stat.getJSONObject("airline").getString("name");
            String airId = stat.getJSONObject("airline").getString("id");
            String depTime = stat.getJSONObject("departure").getString("scheduled_time").split(" ")[1];
            String arrTime = stat.getJSONObject("arrival").getString("scheduled_time").split(" ")[1];
            String depAirName = stat.getJSONObject("departure").getJSONObject("airport").getString("description").split(",")[0];
            String arrAirName = stat.getJSONObject("arrival").getJSONObject("airport").getString("description").split(",")[0];
            String flightNum = stat.getString("number");
            String fliDate = stat.getJSONObject("departure").getString("scheduled_time").split(" ")[0];
            String status = stat.getString("status");
            setFlightData(depID, arrID, depName, arrName, aeroName, airId, depTime, depAirName, arrTime, arrAirName, flightNum, status, fliDate, arrGate, arrTerminal, depGate, depTerminal);
            jsonRepresentation = stat;
        }catch(Exception e){
            e.printStackTrace();
            //result = "No existe ese vuelo";
        }
    }
    private String checkIfNull(String s){
        if(s.equals("null")){
            return " -";
        }
        return s;
    }
    @Override
    public boolean equals(Object o) {
        if (o != null && Flight.class.isAssignableFrom(o.getClass())) {
            if (((Flight)o).getFlightNumber().equals(this.getFlightNumber()) && ((Flight)o).getAirlineId().equals(this.getAirlineId())) {
                return true;
            }
        }
        return  false;
    }

    private void setFlightData(String departureAirport, String arrivalAirport, String departureCity, String arrivalCity, String airlineName, String airlineId, String departureHour, String departureAirportName, String arrivalHour, String arrivalAirportName, String flightNumber, String status, String flightDate, String departureGate,String departureTerminal, String arrivalGate, String arrivalTerminal){
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
        this.departureAirportName = departureAirportName;
        this.arrivalAirportName = arrivalAirportName;
        this.departureGate = departureGate;
        this.departureTerminal = departureTerminal;
        this.arrivalGate = arrivalGate;
        this.arrivalTerminal = arrivalTerminal;
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

    public String getStatusBasic() { return status;}

    public String getStatus(Context context) {
        String stat = "";
        switch (status) {
            case "S":
                stat = context.getResources().getString(R.string.stat_s);
                break;
            case "A":
                stat = context.getResources().getString(R.string.stat_a);
                break;
            case "R":
                stat = context.getResources().getString(R.string.stat_r);
                break;
            case "L":
                stat = context.getResources().getString(R.string.stat_l);
                break;
            case "C":
                stat = context.getResources().getString(R.string.stat_c);
                break;
            default:
                new RuntimeException("Problema en el servidor");
        }
        return stat;
    }

    public String getFlightNumber() {
        return flightNumber;
    }

    public String getDepartureGate() { return departureGate; }

    public String getDepartureTerminal() { return departureTerminal; }

    public String getArrivalGate() { return arrivalGate; }

    public String getArrivalTerminal() { return arrivalTerminal; }

    public String getDepartureCity() { return departureCity; }

    public String getJsonRepresentation(){ return jsonRepresentation.toString(); }
}
