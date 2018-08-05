package com.yseleshi.earthquake;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

public class EarthquakeAlarmReceiver extends BroadcastReceiver {


    public final static String TAG = "EarthquakeAlarmReceiver";

    public static final String ACTION_UPDATE_EARTHQUAKE_ALARM = "com.yseleshi.earthquake.ACTION_UPDATE_EARTHQUAKE_ALARM";

    public EarthquakeAlarmReceiver() {     }


    @Override
    public void onReceive(Context context, Intent intent) {


        // TODO: This method is called when the BroadcastReceiver is receiving
        SharedPreferences prefs =  PreferenceManager.getDefaultSharedPreferences(context);
        int minimumMagnitude = Integer.parseInt(prefs.getString(Constants.PREF_MIN_MAG, "3"));
        Log.d(TAG, "onReceive minimumMagnitude: " + minimumMagnitude);
        long lastQuakeTime = prefs.getLong(Constants.PREF_MOST_RECENT_QUAKE, 0);
        EarthquakeService.startRefreshEarthquake(context, minimumMagnitude, lastQuakeTime);


    }
}
