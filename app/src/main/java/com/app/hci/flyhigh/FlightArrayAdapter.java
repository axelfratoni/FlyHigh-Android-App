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
            holder.airlineIdTextView = (TextView) convertView.findViewById(R.id.flight_airlineId);
            holder.departureAirportTextView = (TextView) convertView.findViewById(R.id.flight_departureAirport);
            holder.arrivalAirportTextView = (TextView) convertView.findViewById(R.id.flight_arrivalAirport);
            holder.flightNumberTextView = (TextView) convertView.findViewById(R.id.flight_number);
            holder.departureHourTextView = (TextView) convertView.findViewById(R.id.flight_departureHour);
            holder.arrivalHourTextView = (TextView) convertView.findViewById(R.id.flight_arrivalHour);
            holder.departureCityTextView = (TextView) convertView.findViewById(R.id.flight_departureCity);
            holder.arrivalCityTextView = (TextView) convertView.findViewById(R.id.flight_arrivalCity);
            holder.statusTextView = (TextView) convertView.findViewById(R.id.flight_status);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }
        setHolder(holder, position);
        return convertView;
    }

    private void setHolder(ViewHolder holder, int position) {
        Flight flight = getItem(position);
        holder.airlineIdTextView.setText(flight.getAirlineId());
        holder.departureAirportTextView.setText(flight.getDepartureAirport());
        holder.arrivalAirportTextView.setText(flight.getArrivalAirport());
        holder.flightNumberTextView.setText(flight.getFlightNumber());
        holder.departureHourTextView.setText(flight.getDepartureHour());
        holder.arrivalHourTextView.setText(flight.getArrivalHour());
        holder.departureCityTextView.setText(flight.getDepartureCity());
        holder.arrivalCityTextView.setText(flight.getArrivalCity());
        holder.statusTextView.setText(flight.getStatus());

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
        public TextView departureAirportTextView;
        public TextView arrivalAirportTextView;
        public TextView flightNumberTextView;
        public TextView departureHourTextView;
        public TextView arrivalHourTextView;
        public TextView departureCityTextView;
        public TextView arrivalCityTextView;
        public TextView statusTextView;
    }
}
