package com.app.hci.flyhigh;

import android.os.AsyncTask;
import android.widget.TextView;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by axel on 16/06/17.
 */

public class GetJSON {
    String targerURL;

    public GetJSON(String url) {
        this.targerURL = url;
    }

    protected String get() {

        HttpURLConnection urlConnection = null;

        try {
            URL url = new URL(targerURL);
            urlConnection = (HttpURLConnection) url.openConnection();
            InputStream in = new BufferedInputStream(urlConnection.getInputStream());
            return readStream(in);
        } catch (Exception exception) {
            exception.printStackTrace();
            return "lmao";
        } finally {
            if (urlConnection != null)
                urlConnection.disconnect();
        }
    }

    private String readStream(InputStream inputStream) {
        try {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            int i = inputStream.read();
            while (i != -1) {
                outputStream.write(i);
                i = inputStream.read();
            }
            return outputStream.toString();
        } catch (IOException e) {
            return "";
        }
    }
}
