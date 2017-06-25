package com.app.hci.flyhigh;

import android.app.Application;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
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
    boolean isFirstCallTime = true;
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
        switch (PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getInt("time", 1000)) {
            case 1000: timerSpinner.setSelection(0); break;
            case 1000 * 60 * 5: timerSpinner.setSelection(1); break;
            case 1000 * 60 * 10: timerSpinner.setSelection(2); break;
            case 1000 * 60 * 30: timerSpinner.setSelection(3); break;
            case 1000 * 60 * 60: timerSpinner.setSelection(4); break;
            case 0: timerSpinner.setSelection(5); break;
        }
        timerSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                if (!isFirstCallTime) {
                    SharedPreferences desired = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                    SharedPreferences.Editor desiredEditor = desired.edit();
                    switch (position) {
                        case 0:
                            desiredEditor.putInt("time", 1000);
                            break;
                        case 1:
                            desiredEditor.putInt("time", 1000 * 60 * 5);
                            break;
                        case 2:
                            desiredEditor.putInt("time", 1000 * 60 * 10);
                            break;
                        case 3:
                            desiredEditor.putInt("time", 1000 * 60 * 30);
                            break;
                        case 4:
                            desiredEditor.putInt("time", 1000 * 60 * 60);
                            break;
                        case 5:
                            desiredEditor.putInt("time", 0);
                            break;
                    }
                    desiredEditor.apply();
                    Intent refresh = new Intent(SettingsActivity.this, MainActivity.class);
                    startActivity(refresh);
                }
                isFirstCallTime = false;
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
