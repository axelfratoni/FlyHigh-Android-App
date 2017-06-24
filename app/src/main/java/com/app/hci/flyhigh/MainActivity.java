package com.app.hci.flyhigh;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.util.Log;
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
    String fragmentName;
    NotificationDealer notificationDealer;

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
        notificationDealer = new NotificationDealer(MainActivity.this);
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

/*    @Override
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
    }*/

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        if (id == R.id.nav_settings) {
            Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
            startActivity(intent);
            return true;
        }
        Fragment fragment = null;
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if (id == R.id.nav_home) {
            fragment = new HomeFragment();
            fragmentName = "homeFragment";
            transaction.add(fragment, fragmentName);
        } else if (id == R.id.nav_suscriptions) {
            fragment = new SubscriptionsFragment();
            fragmentName = "subscriptionFragment";
            transaction.add(fragment, fragmentName);
        } else if (id == R.id.nav_offers) {
            fragment = new OffersFragment();
            fragmentName = "offersFragment";
            transaction.add(fragment, fragmentName);
        } else if (id == R.id.nav_history) {
            fragment = new HistoryFragment();
            fragmentName = "historyFragment";
            transaction.add(fragment, fragmentName);
        } else if (id == R.id.nav_search) {
            fragment = new SearchFragment();
            fragmentName = "searchFragment";
            transaction.add(fragment, fragmentName);
        }
        transaction.replace(R.id.mainFrame, fragment);
        transaction.addToBackStack(null);
        transaction.commit();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void onFlightSelected(int position) {
        FlightFragment flightFrag = (FlightFragment)
                getSupportFragmentManager().findFragmentById(R.id.flight_fragment);

        if (flightFrag != null) {
            Log.d("Hola","Hola");
            flightFrag.updateFlightFragment((Flight) ((ListFragment) getSupportFragmentManager().findFragmentByTag(fragmentName)).getListAdapter().getItem(position));
        } else {
            Log.d("Chau","Chau");
            FlightFragment newFragment = FlightFragment.newInstance((Flight) ((ListFragment) getSupportFragmentManager().findFragmentByTag(fragmentName)).getListAdapter().getItem(position));
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            // Replace whatever is in the fragment_container view with this fragment,
            // and add the transaction to the back stack so the user can navigate back
            transaction.replace(R.id.mainFrame, newFragment);
            transaction.addToBackStack(null);

            // Commit the transaction
                transaction.commit();
        }
    }

    public NotificationDealer getNotificationDealer() {
        return notificationDealer;
    }

}
