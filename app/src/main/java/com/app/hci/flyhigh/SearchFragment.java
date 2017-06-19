package com.app.hci.flyhigh;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

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
        //resultTextView = (TextView) view.findViewById(R.id.textsearch);
        //new infoRetriever().execute();

        EditText searchInput = (EditText) view.findViewById(R.id.search_input);
        if (searchInput != null) {
            searchInput.setOnClickListener(new Button.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getActivity(), SearchActivity.class);
                    startActivity(intent);
                }
            });
        }
        return view;
    }

    public void setText(String text) {
        resultTextView.setText(text);
    }

    private class infoRetriever extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... params) {
            String result = "";
            try {
                String json = new GetJSON("http://hci.it.itba.edu.ar/v1/api/booking.groovy?method=getlastminuteflightdeals&from=BUE").get();
                JSONObject obj = new JSONObject(json);
                JSONArray deals = obj.getJSONArray("deals");

                for (int i = 0; i < deals.length(); i++) {
                    JSONObject d = deals.getJSONObject(i);
                    JSONObject city = d.getJSONObject("city");
                    result += city.getString("name") + "\n";
                }
            } catch (Exception exception) {
                exception.printStackTrace();
            }
            return  result;
        }

        @Override
        protected void onPostExecute(String result) {
            setText(result);
        }
    }
}
