package com.app.hci.flyhigh;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.drm.ProcessedData;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import static android.graphics.Color.rgb;
import static com.app.hci.flyhigh.R.id.imageView;

/**
 * Created by Gaston on 15/06/2017.
 */

public class FlightArrayAdapter extends ArrayAdapter<Flight> {
    // Hay que ver como mover estos colores al archivo de colors.xml,
    // pero si subis un hexa al xml, se guarda como int y al parsecolor de Color
    // le tenes que pasar un string.

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
        new LoadImage(getItem(position).getLogURL(), (ImageView) convertView.findViewById(R.id.image_view)).execute();
        return convertView;
    }

    private void setHolder(ViewHolder holder, int position) {
        Flight flight = getItem(position);
        Log.d("xD", flight.toString());
        holder.airlineIdTextView.setText(flight.getAirlineId());
        holder.departureAirportTextView.setText(flight.getDepartureAirport());
        holder.arrivalAirportTextView.setText(flight.getArrivalAirport());
        holder.flightNumberTextView.setText(flight.getFlightNumber());
        holder.departureHourTextView.setText(flight.getDepartureHour());
        holder.arrivalHourTextView.setText(flight.getArrivalHour());
        holder.departureCityTextView.setText(parseCityText(flight.getDepartureCity()));
        holder.arrivalCityTextView.setText(parseCityText(flight.getArrivalCity()));
        holder.statusTextView.setText(flight.getStatus(getContext()));

        String status = flight.getStatusBasic();

        switch(status){
            case "L":case "S":
                //noinspection ResourceType
                holder.statusTextView.setTextColor(Color.parseColor(getContext().getResources().getString(R.color.green)));
                break;
            case "A":
                //noinspection ResourceType
                holder.statusTextView.setTextColor(Color.parseColor(getContext().getResources().getString(R.color.lightblue)));
                break;
            case "R":
                //noinspection ResourceType
                holder.statusTextView.setTextColor(Color.parseColor(getContext().getResources().getString(R.color.orange)));
                break;
            case "C":
                //noinspection ResourceType
                holder.statusTextView.setTextColor(Color.RED);
                break;
        }
        holder.statusTextView.setText(flight.getStatus(getContext()));
    }
    private String parseCityText(String city){
        if(city.length() > 12){
            return city.substring(0,9) + "...";
        }
        return city;
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

    public class LoadImage extends AsyncTask<String, Void, Bitmap> {
        private String targetURL;
        private ImageView imageView;

        public LoadImage (String url, ImageView imageView) {
            this.targetURL = url;
            this.imageView = imageView;
        }

        @Override
        protected Bitmap doInBackground(String... params) {
            try {
                HttpURLConnection urlConnection = null;
                URL url = new URL(targetURL);
                urlConnection = (HttpURLConnection) url.openConnection();
                InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                return BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(Bitmap result) {
            if (result != null) {
                imageView.setImageBitmap(result);
            }
        }
    }
}
