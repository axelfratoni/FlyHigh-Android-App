package com.app.hci.flyhigh;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

/**
 * Created by axel on 25/06/17.
 */

public class NewsAdapter extends ArrayAdapter<Flight> {
    public NewsAdapter(Activity context, Flight[] objects) {
        super(context, R.layout.news_layout, objects);
    }

    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.news_layout, parent, false);
        }
        TextView textView = (TextView) convertView.findViewById(R.id.info);
        Flight updatedFlight = getItem(position);
        Context context = getContext();
        String status = updatedFlight.getStatusBasic();
        String color = "black";
        switch(status){
            case "L":case "S": color = "green"; break;
            case "A": color = "blue"; break;
            case "R": color = "orange"; break;
            case "C": color = "red"; break;
        }
        textView.setText(Html.fromHtml(context.getResources().getString(R.string.notification_data1) +"<b>"+ updatedFlight.getAirlineId() + "-" + updatedFlight.getFlightNumber() +"</b>"+ context.getResources().getString(R.string.notification_data2) + "<font color="+color+">" + updatedFlight.getStatus(context) + "</font>"));
        return convertView;
    }
}
