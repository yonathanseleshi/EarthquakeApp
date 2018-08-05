package com.yseleshi.earthquake;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.Context;
import android.content.res.Resources;
import android.support.v7.app.NotificationCompat;
import android.util.Log;

import java.util.Date;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
public class EarthquakeService extends IntentService implements VolleyClassString.OnInfoListener<String> {
    // TODO: Rename actions, choose action names that describe tasks that this
    // IntentService can perform, e.g. ACTION_FETCH_NEW_ITEMS
    private static final String ACTION_REFRESH_EARTHQUAKE = "com.yseleshi.earthquake.action.REFRESH_EARTHQUAKE";
    private static final String ACTION_BAZ = "com.yseleshi.earthquake.action.BAZ";

    // TODO: Rename parameters
    private static final String PREF_MIN_MAG = "Constants.PREF_MIN_MAG";
    private static final String PREF_MOST_RECENT_QUAKE = "Constants.PREF_MOST_RECENT_QUAKE";

    private final String TAG = "EarthquakeService";
    VolleyClassString volleyClassString;
    ParseJsonInfo quakeParserJson;
    int minimumMagnitudeDefault = 3;
    int minimumMagnitude = 3;
    long lastQuakeTime = 0;
    public Notification earthquakeNotification;
    NotificationCompat.Builder builder;
    NotificationManager notificationManager;
    public static final int NOTIFICATION_ID = 1;

    public EarthquakeService() {
        super("EarthquakeService");
    }

    /**
     * Starts this service to perform action Foo with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */
    // TODO: Customize helper method
    public static void startRefreshEarthquake(Context context, int param1, long param2) {
        Intent intent = new Intent(context, EarthquakeService.class);
        intent.setAction(ACTION_REFRESH_EARTHQUAKE);
        intent.putExtra(PREF_MIN_MAG, param1);
        intent.putExtra(PREF_MOST_RECENT_QUAKE, param2);
        context.startService(intent);
    }

    /**
     * Starts this service to perform action Baz with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */
    // TODO: Customize helper method
    /*
    public static void startActionBaz(Context context, String param1, String param2) {
        Intent intent = new Intent(context, EarthquakeService.class);
        intent.setAction(ACTION_BAZ);
        intent.putExtra(PREF_MIN_MAG, param1);
        intent.putExtra(PREF_MOST_RECENT_QUAKE, param2);
        context.startService(intent);
    }*/

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_REFRESH_EARTHQUAKE.equals(action)) {
                final int param1 = intent.getIntExtra(PREF_MIN_MAG, minimumMagnitudeDefault);
                final long param2 = intent.getLongExtra(PREF_MOST_RECENT_QUAKE, 0);
                handleRefreshEarthquake(param1, param2);
            /*} else if (ACTION_BAZ.equals(action)) {
                final int param1 = intent.getStringExtra(PREF_MIN_MAG);
                final long param2 = intent.getStringExtra(PREF_MOST_RECENT_QUAKE);
                handleRefreshEarthquake(param1, param2);
            */}
        }
    }


    @Override
    public void onInfoAvailable(String responseString) {
        Log.v(TAG, "onInfoAvailable " + responseString);
        if (quakeParserJson == null) {
            quakeParserJson = new ParseJsonInfo();
        }
        quakeParserJson.decodeMessage(responseString, new ParseJsonInfo.earthquakeCallback(){
            @Override
            public void onEarthquake(Earthquake quake) {
                if (quake.getMag() > minimumMagnitude) {
                    // if (quake.getMilliseconds()>lastQuakeTime) {if (true) {
                     notificationManager.notify(NOTIFICATION_ID, earthquakeNotification);
                    Log.d(TAG, "onInfoAvailable new quake " + quake.logQuakeData());
                } else {
                    Log.d(TAG, "onInfoAvailable not new quake " + quake.logQuakeData());

                }
            }
        });
    }

    /**
     * Handle action Foo in the provided background thread with the provided
     * parameters.
     */

    private void makeNotification(){
        Log.d(TAG,"makeNotification entry");
        String svcName = Context.NOTIFICATION_SERVICE;
        notificationManager = (NotificationManager)getSystemService(svcName);
        Context context = getApplicationContext();
        Intent startActivityIntent = new Intent(EarthquakeService.this, EarthquakeNotification.class);
        PendingIntent launchIntent = PendingIntent.getActivity(context,0, startActivityIntent, 0);
        Resources res = context.getResources();

        builder = new NotificationCompat.Builder(context);
        int icon = R.mipmap.ic_launcher;

        builder.setSmallIcon(icon)
                .setTicker(res.getString(R.string.expandedTitle))
                .setWhen(java.lang.System.currentTimeMillis())
                .setContentTitle(res.getString(R.string.expandedTitle))
                .setContentText(res.getString(R.string.expandedText))
                .setWhen(java.lang.System.currentTimeMillis())
                .setContentIntent(launchIntent);

        earthquakeNotification = builder.build();

    }


    private void refreshEarthquakes (){
        String quakeFeed = getString(R.string.quakefeed);



        if (volleyClassString == null) {
            volleyClassString = new VolleyClassString(this, this);
        }
        volleyClassString.makeNetworkRequests(quakeFeed);
    }
    private void handleRefreshEarthquake(int param1, Long param2) {
        minimumMagnitude = param1;
        lastQuakeTime = param2;
        makeNotification();
        Date date = new Date(lastQuakeTime);
        Log.d(TAG, "onHandleIntent: minimumMagnitude " + minimumMagnitude
                + " lastQuakeTime: " + lastQuakeTime + " " + date);
        refreshEarthquakes();
    }

    /**
     * Handle action Baz in the provided background thread with the provided
     * parameters.
     */

    /*
    private void handleActionBaz(String param1, String param2) {
        // TODO: Handle action Baz
        throw new UnsupportedOperationException("Not yet implemented");
    }*/
}
