package com.app.hci.flyhigh;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.view.Menu;
import android.view.MenuItem;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;

import static android.R.attr.fragment;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, OnFlightSelectedListener {
    FragmentManager mFragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.mainFrame, new HomeFragment());
        transaction.addToBackStack(null);
        transaction.commit();




        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        Fragment fragment = null;
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if (id == R.id.nav_home) {
            fragment = new HomeFragment();
            transaction.add(fragment, "homeFragment");
        } else if (id == R.id.nav_suscriptions) {
            fragment = new SubscriptionsFragment();
            transaction.add(fragment, "subscriptionFragment");
        } else if (id == R.id.nav_offers) {
            fragment = new OffersFragment();
            transaction.add(fragment, "offersFragment");
        } else if (id == R.id.nav_history) {
            fragment = new HistoryFragment();
            transaction.add(fragment, "historyFragment");
        } else if (id == R.id.nav_search) {
            fragment = new SearchFragment();
            transaction.add(fragment, "searchFragment");
        }
        transaction.replace(R.id.mainFrame, fragment);
        transaction.addToBackStack(null);
        transaction.commit();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void onFlightSelected(int position){
        int screenSize = getResources().getConfiguration().screenLayout &
                Configuration.SCREENLAYOUT_SIZE_MASK;
        if((screenSize == Configuration.SCREENLAYOUT_SIZE_LARGE || screenSize == Configuration.SCREENLAYOUT_SIZE_XLARGE)&& getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            System.out.print("XDDDDDDD");
            prepareIntent(position);
            transaction.replace(R.id.detailFragment, new FlightFragment());
            transaction.addToBackStack(null);
            transaction.commit();
        }else{
            startActivity(prepareIntent(position));
        }
    }

    private Intent prepareIntent(int position){
        Intent i = new Intent(getApplicationContext(), FlightActivity.class);
        Flight flight = (Flight) ((ListFragment)getSupportFragmentManager().findFragmentByTag("subscriptionFragment")).getListAdapter().getItem(position);
        String[] ids = getResources().getStringArray(R.array.flight_intent);
        i.putExtra(ids[0],flight.getDepartureAirport());
        i.putExtra(ids[1],flight.getArrivalAirport());
        i.putExtra(ids[2],flight.getDepartureCity());
        i.putExtra(ids[3],flight.getArrivalCity());
        i.putExtra(ids[4],flight.getDuration());
        i.putExtra(ids[6],flight.getAirlineName());
        i.putExtra(ids[7],flight.getWeekDays());
        i.putExtra(ids[8],flight.getDepartureHour());
        i.putExtra(ids[9],flight.getDepartureAirportName());
        i.putExtra(ids[10],flight.getArrivalHour());
        i.putExtra(ids[11],flight.getArrivalAirportName());
        i.putExtra(ids[12],flight.getFlightNumber());
        i.putExtra(ids[13],flight.getStatus());
        return i;
    }
}
