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

import org.json.JSONObject;

import static android.R.attr.fragment;
import static android.R.attr.switchMinWidth;
import static com.app.hci.flyhigh.R.array.fragment_names;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, OnFlightSelectedListener, OnFlightSearchedListener {
    FragmentManager mFragmentManager;
    String fragmentName;
    String mainFragmentName;
    NotificationDealer notificationDealer;
    Fragment fragment;
    Fragment mainFragment;
    String[] fragmentNames = {
            "homeFragment",
            "searchFragment",
            "subscriptionFragment",
            "offersFragment",
            "flightFragment"
    };

    static boolean fromNotification;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Bundle extras = getIntent().getExtras();
        if( extras!= null){
            try{
                fromNotification = true;
                onFlightSearch(new Flight(new JSONObject(getIntent().getExtras().getString("flight", "null"))));
            }catch(Exception e){
            }
        }else{
            fromNotification = false;
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.mainFrame, new HomeFragment());
            //transaction.addToBackStack(null);
            transaction.commit();
        }


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
            if( fragmentName.equals(fragmentNames[4])){
                if(fromNotification){
                    fromNotification = false;
                    FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                    Fragment fragment = new SubscriptionsFragment();
                    mainFragment = fragment;
                    fragmentName = fragmentNames[2];
                    mainFragmentName = fragmentNames[2];
                    transaction.add(fragment, fragmentName);
                    transaction.replace(R.id.mainFrame, fragment);
                    transaction.addToBackStack(null);
                    transaction.commit();
                    setTitle(getResources().getStringArray(R.array.fragment_names)[2]);
                }else{
                    if(fragment instanceof SubscriptionsFragment){
                        setTitle(getResources().getStringArray(R.array.fragment_names)[2]);
                        fragmentName = fragmentNames[2];
                        mainFragmentName = fragmentNames[2];
                    }else if(fragment instanceof SearchFragment){
                        setTitle(getResources().getStringArray(R.array.fragment_names)[1]);
                        fragmentName = fragmentNames[1];
                        mainFragmentName = fragmentNames[1];
                    }else if(fragment instanceof OffersFragment){
                        setTitle(getResources().getStringArray(R.array.fragment_names)[3]);
                        fragmentName = fragmentNames[3];
                        mainFragmentName = fragmentNames[3];
                    }
                    super.onBackPressed();
                }
            }else if(fragmentName.equals(fragmentNames[1]) || fragmentName.equals(fragmentNames[2]) || fragmentName.equals(fragmentNames[3]) || fragmentName.equals(fragmentNames[4])){
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                Fragment fragment = new HomeFragment();
                mainFragment = fragment;
                fragmentName = "homeFragment";
                mainFragmentName = "homeFragment";
                transaction.add(fragment, fragmentName);
                transaction.replace(R.id.mainFrame, fragment);
                transaction.addToBackStack(null);
                transaction.commit();
                setTitle(getResources().getStringArray(R.array.fragment_names)[0]);
            }else if(fragmentName.equals(fragmentNames[0])){
                finish();
            }


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
        fragment = null;
        mainFragment = null;
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if (id == R.id.nav_home) {
            fragment = new HomeFragment();
            mainFragment = fragment;
            fragmentName = fragmentNames[0];
            mainFragmentName = fragmentNames[0];
            transaction.add(fragment, fragmentName);
            setTitle(getResources().getStringArray(R.array.fragment_names)[0]);
        } else if (id == R.id.nav_suscriptions) {
            fragment = new SubscriptionsFragment();
            mainFragment = fragment;
            fragmentName = fragmentNames[2];
            mainFragmentName = fragmentNames[2];
            transaction.add(fragment, fragmentName);
            setTitle(getResources().getStringArray(R.array.fragment_names)[2]);
        } else if (id == R.id.nav_offers) {
            fragment = new OffersFragment();
            mainFragment = fragment;
            fragmentName = fragmentNames[3];
            mainFragmentName = fragmentNames[3];
            transaction.add(fragment, fragmentName);
            setTitle(getResources().getStringArray(R.array.fragment_names)[3]);
        } else if (id == R.id.nav_search) {
            fragment = new SearchFragment();
            mainFragment = fragment;
            fragmentName = fragmentNames[1];
            mainFragmentName = fragmentNames[1];
            transaction.add(fragment, fragmentName);
            setTitle(getResources().getStringArray(R.array.fragment_names)[1]);
        }
        transaction.replace(R.id.mainFrame, fragment);
        transaction.addToBackStack(null);
        transaction.commit();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void onFlightSearch(Flight f){
        FlightFragment flightFrag = (FlightFragment)
                getSupportFragmentManager().findFragmentById(R.id.flight_fragment);

        if (flightFrag != null) {
            Log.d("Hola","Hola");
            flightFrag.updateFlightFragment(f);
        } else {
            Log.d("Chau","Chau");
            FlightFragment detailsFragment = FlightFragment.newInstance(this, f);
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            String detailsFragmentName = "flightFragment";
            transaction.add(detailsFragment, detailsFragmentName);
            // Replace whatever is in the fragment_container view with this fragment,
            // and add the transaction to the back stack so the user can navigate back
            if(((DualPane)mainFragment).isDualPane()) {
                ((DualPane)mainFragment).addDetails(detailsFragment);
            } else {
                setTitle(getString(R.string.flight_title, f.getFlightNumber()));
                fragmentName = fragmentNames[4];
                transaction.replace(R.id.mainFrame, detailsFragment);
            }
            transaction.addToBackStack(null);
            // Commit the transaction
            transaction.commit();
        }
    }


    public void onFlightSelected(int position) {
        onFlightSearch((Flight) ((ListFragment) getSupportFragmentManager().findFragmentByTag(fragmentName)).getListAdapter().getItem(position));
    }

    public NotificationDealer getNotificationDealer() {
        return notificationDealer;
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        if(mainFragmentName != null) {
            if (mainFragmentName.equals("subscriptionFragment") || mainFragmentName.equals("searchFragment")) {

                if (newConfig.screenWidthDp >= 600) {

                    FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

                    if (mainFragmentName.equals("searchFragment")) {
                        mainFragment = new SearchFragment();
                        transaction.add(mainFragment, mainFragmentName);
                    } else {
                        mainFragment = new SubscriptionsFragment();
                        transaction.add(mainFragment, mainFragmentName);
                    }

                    transaction.replace(R.id.mainFrame, mainFragment);
                    //transaction.addToBackStack(null);
                    transaction.commit();

                }

            }
        }

    }

}
