package com.app.hci.flyhigh;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;

/**
 * Created by axel on 15/06/17.
 */

public class SearchFragment extends Fragment {

    TextView resultTextView;
    View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        view =  inflater.inflate(R.layout.search_layout, container, false);
        resultTextView = (TextView) view.findViewById(R.id.textsearch);
        resultTextView.setText(getDeals());

        return view;
    }

    private String getDeals(){
        String result = "";
        try {
            String json = new HttpGetTask("http://hci.it.itba.edu.ar/v1/api/booking.groovy?method=getlastminuteflightdeals&from=BUE").execute().get();
            JSONObject obj = new JSONObject(json);
            JSONArray deals = obj.getJSONArray("deals");

            for (int i = 0; i < deals.length(); i++) {
                try {
                    JSONObject d = deals.getJSONObject(i);
                    JSONObject city = d.getJSONObject("city");
                    result += city.getString("name") + "\n";
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return  result;
    }
}
