package com.yseleshi.earthquake;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by dell laptop on 8/30/2017.
 */

public class Constants {

    public static final String PREF_AUTO_UPDATE = "PREF_AUTO_UPDATE";
    public static final String PREF_MIN_MAG = "PREF_MIN_MAG";
    public static final String PREF_UPDATE_FREQ = "PREF_UPDATE_FREQ";
    public static final String PREF_MOST_RECENT_QUAKE = "PREF_MOST_RECENT_QUAKE";

    public static void savePrefs(Context context, String key, long value) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor ed = sp.edit();
        ed.putLong(key, value);
        ed.apply();
    }
}
