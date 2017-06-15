package com.app.hci.flyhigh;


import android.app.Activity;
import android.drm.ProcessedData;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

/**
 * Created by Gaston on 15/06/2017.
 */

public class FlightArrayAdapter extends ArrayAdapter<Flight> {
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
            holder.airlineTextView = (TextView) convertView.findViewById(R.id.list_airline);
            holder.flightCodeTextView = (TextView) convertView.findViewById(R.id.list_flightCode);
            holder.priceTextView = (TextView) convertView.findViewById(R.id.list_price);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }
        Flight flight = getItem(position);
        holder.airlineTextView.setText(flight.getAirline());
        holder.flightCodeTextView.setText(flight.getFlightCode());
        holder.priceTextView.setText(flight.getPrice().toString());

        return convertView;
    }

    public class ViewHolder{
        public TextView airlineTextView;
        public TextView flightCodeTextView;
        public TextView priceTextView;
    }
}
