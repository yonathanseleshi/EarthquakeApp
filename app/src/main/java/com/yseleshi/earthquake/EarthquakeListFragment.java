package com.yseleshi.earthquake;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * A placeholder fragment containing a simple view.
 */
public class EarthquakeListFragment extends Fragment implements VolleyClassString.OnInfoListener<String>, EarthquakeRecyclerViewAdapter.OnAdapterItemInteraction {


   EarthquakeRecyclerViewAdapter earthquakeAdapter;
    public static ArrayList<Earthquake> mEarthquakeData = new ArrayList<>();
    private final static String TAG = "EarthquakeList Fragment";
    private final static int EARTHQUAKE_PREFERENCES = 1;
    public int minimumMagnitude = 0;
    public String queryString = "https://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/2.5_day.geojson";

    public boolean autoUpdateChecked = false;
    public int updateFreq = 0;
    public long lastQuakeTime = 0;
    EarthquakeAlarm earthquakeAlarm;



    private VolleyClassString volleyClassString;

    public EarthquakeListFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_earthquake_list, container, false);
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {

        super.onActivityCreated(savedInstanceState);


        RecyclerView recyclerView = getActivity().findViewById(R.id.earthquakeListRecView);


        getNetworkInfo();
        earthquakeAdapter = new EarthquakeRecyclerViewAdapter(mEarthquakeData, this);
        recyclerView.setAdapter(earthquakeAdapter);


        final LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, true);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_settings:

                startActivityForResult(new Intent(getActivity(), SettingsActivity.class), EARTHQUAKE_PREFERENCES);
                return true;
            case R.id.action_refresh:
                Log.d(TAG, "onOptionsItemSelected: action_refresh");
                updateFromPreferences();
                EarthquakeService.startRefreshEarthquake(getActivity(), minimumMagnitude, lastQuakeTime);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onActivityResult(int reqCode, int resCode, Intent data) {
        super.onActivityResult(reqCode, resCode, data);
        Log.d(TAG, "onActivityResult reqCode:  " + reqCode + " resCode: " + resCode); switch(reqCode) {
            case EARTHQUAKE_PREFERENCES: {
                refreshEarthquakes();
                break;
            }
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_earthquake_list, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }



    private void updateFromPreferences() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());

        minimumMagnitude = Integer.parseInt(prefs.getString(Constants.PREF_MIN_MAG, "3"));



        updateFreq = Integer.parseInt(prefs.getString(Constants.PREF_UPDATE_FREQ, "60"));
        autoUpdateChecked = prefs.getBoolean(Constants.PREF_AUTO_UPDATE, false);
        lastQuakeTime = prefs.getLong(Constants.PREF_MOST_RECENT_QUAKE, 0);

        if (earthquakeAlarm == null) {
            earthquakeAlarm = new EarthquakeAlarm(getActivity());
            earthquakeAlarm.setUpAlarms();
        }
    }

    // Call the network to get the earthquake list.
    public void refreshEarthquakes() {
        mEarthquakeData.clear();
        updateFromPreferences();
        String quakeFeed = queryString;
        if (volleyClassString == null){
            volleyClassString = new VolleyClassString(getActivity(), this);
        }
        volleyClassString.makeNetworkRequests(quakeFeed);
    }

    @Override
    public void onInfoAvailable(String responseString) {
        ArrayList<Earthquake> earthquakes;


        if(responseString != null) {


            ParseJsonInfo parseJsonInfo = new ParseJsonInfo();



            earthquakes = parseJsonInfo.decodeMessage(responseString, new ParseJsonInfo.earthquakeCallback() {

                @Override
                public void onEarthquake(Earthquake quake) {
                    if (quake.getMag() > minimumMagnitude) {

                        if (quake.getMilliseconds() > lastQuakeTime) {
                            Constants.savePrefs(getActivity(),  Constants.PREF_MOST_RECENT_QUAKE, quake.getMilliseconds());
                            lastQuakeTime = quake.getMilliseconds();
                        }
                       mEarthquakeData.add(quake);


                    }
                }
            });
            //earthquakes = parseJsonInfo.decodeMessage(responseString);


            mEarthquakeData.clear();
            mEarthquakeData.addAll(earthquakes);
            earthquakeAdapter.notifyDataSetChanged();
        }
    }


    @Override
    public void onItemSelected(EarthquakeRecyclerViewAdapter.ViewHolder holder, Integer position, ArrayList<Earthquake> earthquakeList) {


        //setupDialog(position, earthquakeList);
/*
           Earthquake selectedEarthquake = earthquakeList.get(position);

        TextView diaAddress = (TextView) getActivity().findViewById(R.id.addressDialog);
        TextView diaDate = (TextView)getActivity().findViewById(R.id.dateDialog);
        TextView diaMag = (TextView) getActivity().findViewById(R.id.magDialog);
        TextView diaLat = (TextView) getActivity().findViewById(R.id.latDialog);
        TextView diaLong = (TextView) getActivity().findViewById(R.id.longDialog);

        String address = selectedEarthquake.getLocation();
        Log.i(TAG, address);
        Log.i(TAG, selectedEarthquake.getDateTimeOf().toString());

        diaAddress.setText(address);
        diaDate.setText(selectedEarthquake.getDateTimeOf().toString());
        diaMag.setText(String.valueOf(selectedEarthquake.getMagnitude()));
        diaLat.setText(String.valueOf(selectedEarthquake.getLatitude()));
        diaLong.setText(String.valueOf(selectedEarthquake.getLongitude()));
        */

        ((EarthquakeList)getActivity()).showDialog(position, earthquakeList);


    }



    public void getNetworkInfo() {





        Log.d( TAG, ": getNetworkInfo url: " + queryString);

        if (volleyClassString == null){
            volleyClassString = new VolleyClassString(getActivity(), this);
        }
        volleyClassString.makeNetworkRequests(queryString);

    }


    private void setupDialog(int position, ArrayList<Earthquake> earthquakesList){

        Earthquake selectedEarthquake = earthquakesList.get(position);
        TextView diaAddress = (TextView) getActivity().findViewById(R.id.addressDialog);
        TextView diaDate = (TextView)getActivity().findViewById(R.id.dateDialog);
        TextView diaMag = (TextView) getActivity().findViewById(R.id.magDialog);
        TextView diaCoord = (TextView) getActivity().findViewById(R.id.coordinates);

        String address = selectedEarthquake.getPlace();
        Log.i(TAG, address);
        Log.i(TAG, selectedEarthquake.getTime().toString());

        diaAddress.setText(address);
        diaDate.setText(selectedEarthquake.getTime().toString());
        diaMag.setText(String.valueOf(selectedEarthquake.getMag()));
        diaCoord.setText(String.valueOf(selectedEarthquake.getCoordinates()));


    }


}
