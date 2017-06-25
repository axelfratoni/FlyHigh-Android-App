package com.app.hci.flyhigh;

import android.app.Application;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.ContextThemeWrapper;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.Locale;

/**
 * Created by axel on 23/06/17.
 */

public class SettingsActivity extends AppCompatActivity {
    boolean isFirstCall = true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_activity);

        final Spinner timerSpinner = (Spinner) findViewById(R.id.timer_Spiner);
        ArrayAdapter<CharSequence> staticAdapter = ArrayAdapter
                .createFromResource(this, R.array.notification_timer,
                        android.R.layout.simple_spinner_item);
        staticAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        timerSpinner.setAdapter(staticAdapter);
        timerSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                //String option = timerSpinner.getSelectedItem().toString();
                System.out.println(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub

            }
        });

        final Spinner languageSpinner = (Spinner) findViewById(R.id.language_spiner);
        ArrayAdapter<CharSequence> langAdapter = ArrayAdapter
                .createFromResource(this, R.array.language_options,
                        android.R.layout.simple_spinner_item);
        langAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        languageSpinner.setAdapter(langAdapter);
        switch (getString(R.string.actual_lang)) {
            case "es": languageSpinner.setSelection(0); break;
            case "en": languageSpinner.setSelection(1); break;
        }
        languageSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                switch (position) {
                    case 0: setLocale("es"); break;
                    case 1: setLocale("en"); break;
                    default:;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub

            }
        });

    }

    public void setLocale(String lang) {
        /*LocaleUtils.setLocale(new Locale(lang));
        LocaleUtils.updateConfig(this, getBaseContext().getResources().getConfiguration());*/
        System.out.println(isFirstCall);
        if (!isFirstCall) {
            Locale myLocale = new Locale(lang);
            Resources res = getResources();
            DisplayMetrics dm = res.getDisplayMetrics();
            Configuration conf = res.getConfiguration();
            conf.locale = myLocale;
            res.updateConfiguration(conf, dm);
            Intent refresh = new Intent(this, MainActivity.class);
            startActivity(refresh);
        }
        isFirstCall = false;
    }
}
