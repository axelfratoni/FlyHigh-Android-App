package com.app.hci.flyhigh;


import android.app.Activity;
import android.drm.ProcessedData;
import android.graphics.Color;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

import static android.graphics.Color.rgb;

/**
 * Created by Gaston on 15/06/2017.
 */

public class FlightArrayAdapter extends ArrayAdapter<Flight> {
    // Hay que ver como mover estos colores al archivo de colors.xml,
    // pero si subis un hexa al xml, se guarda como int y al parsecolor de Color
    // le tenes que pasar un string.
    private String ORANGE = "#FF8811";
    private String LIGHTBLUE = "#4EB2ED";
    private String GREEN = "#01A001";

    public FlightArrayAdapter(Activity context, Flight[] objects){
        super(context, R.layout.list_view_flight, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder holder;
        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_view_flight, parent, false);
            holder = new ViewHolder();
            holder.airlineIdTextView = (TextView) convertView.findViewById(R.id.flight_airline_id);
            holder.flightNumberTextView = (TextView) convertView.findViewById(R.id.flight_number);
            holder.departureTextView = (TextView) convertView.findViewById(R.id.flight_departure);
            holder.arrivalTextView = (TextView) convertView.findViewById(R.id.flight_arrival);
            holder.dateTextView = (TextView) convertView.findViewById(R.id.flight_date);
            holder.statusTextView = (TextView) convertView.findViewById(R.id.flight_status);
            holder.airportsTextView = (TextView) convertView.findViewById(R.id.flight_airports);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }
        setHolder(holder, position);
        return convertView;
    }

    private void setHolder(ViewHolder holder, int position) {
        Flight flight = getItem(position);
        holder.airlineIdTextView.setText(flight.getAirline());
        holder.airportsTextView.setText(flight.getDepartureAirport() + " -> " + flight.getArrivalAirport());
        holder.departureTextView.setText("Salida: " + flight.getDepartureHour() + "hs");
        holder.arrivalTextView.setText("Llegada: " + flight.getArrivalHour() + "hs");
        holder.flightNumberTextView.setText(flight.getFlightCode());
        holder.dateTextView.setText("12/06");
        String status = flight.getStatus();
        switch(status){
            case "Activo":
                holder.statusTextView.setTextColor(Color.parseColor(GREEN));
                break;
            case "Demorado":
                holder.statusTextView.setTextColor(Color.parseColor(ORANGE));
                break;
            case "Volando":
                holder.statusTextView.setTextColor(Color.parseColor(LIGHTBLUE));
                break;
            case "Cancelado":
                holder.statusTextView.setTextColor(Color.RED);
                break;
        }
        holder.statusTextView.setText(status);
    }

    public class ViewHolder{
        public TextView airlineIdTextView;
        public TextView airportsTextView;
        public TextView departureTextView;
        public TextView arrivalTextView;
        public TextView flightNumberTextView;
        public TextView dateTextView;
        public TextView statusTextView;
    }
}
