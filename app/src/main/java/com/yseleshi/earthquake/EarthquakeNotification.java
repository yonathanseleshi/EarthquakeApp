package com.yseleshi.earthquake;

import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class EarthquakeNotification extends AppCompatActivity {
    NotificationManager notificationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        Log.d("EarthquakeNotification","EarthquakeNotification (Intent Service");

        super.onCreate(savedInstanceState);

        String svcName = Context.NOTIFICATION_SERVICE;
        notificationManager = (NotificationManager)getSystemService(svcName);
        notificationManager.cancel(EarthquakeService.NOTIFICATION_ID);



        Intent intent = new Intent(this, EarthquakeList.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();

    }
}
