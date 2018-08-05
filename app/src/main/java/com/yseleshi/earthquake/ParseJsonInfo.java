package com.yseleshi.earthquake;


import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

public class ParseJsonInfo {

    private final static  String TAG = "quakeInfo_HttpURLConn";

    Earthquake earthquake;
    ArrayList<Earthquake> equakes = new ArrayList<>();
    public int minimumMagnitude = 0;
    long lastQuakeTime = 0;

    // constructors
    public ParseJsonInfo() {

    }

    @Override
    public String toString() {
        return super.toString() + earthquake.toString();

    }

    public interface earthquakeCallback{

        void onEarthquake(Earthquake quake);
    }

    public ArrayList<Earthquake> decodeMessage(String message, ParseJsonInfo.earthquakeCallback callback) {




        try {



            Log.d(TAG, "Parsing: " + message);

            JSONObject jObject;
            JSONObject jPropObj;
            JSONObject jGeoObj;
            JSONObject jFeatureObject;
            JSONObject jStatusObj;
            jObject = new JSONObject(message);



            JSONObject jObjectMetaData = jObject.getJSONObject(Earthquake.TAG_METADATA);

            int numEarthquakes = jObjectMetaData.getInt(Earthquake.TAG_COUNT);

            JSONArray jArrayFeatures = jObject.getJSONArray(Earthquake.TAG_FEATURES);

            for (int i = 0; i < jArrayFeatures.length(); i++) {

                earthquake = new Earthquake();
                jFeatureObject = jArrayFeatures.getJSONObject(i);


                jPropObj = jFeatureObject.getJSONObject(Earthquake.TAG_PROPERTIES);
                earthquake.setPlace(jPropObj.getString(Earthquake.TAG_PLACE));
                earthquake.setMag(jPropObj.getDouble(Earthquake.TAG_MAG));
                earthquake.setTime(jPropObj.getLong(String.valueOf(Earthquake.TAG_TIME)));
                earthquake.setDetail(jPropObj.getString(Earthquake.TAG_DETAIL));
                jGeoObj = jFeatureObject.getJSONObject(Earthquake.TAG_GEOMETRY);
                earthquake.setCoordinates(jGeoObj.getString(Earthquake.TAG_COORDINATES));
               if (earthquake.getMag() > minimumMagnitude) {
                   equakes.add(earthquake);

               }

                if (callback != null) {
                    callback.onEarthquake(earthquake);
                }
            }
        }
        catch (Exception e) {
            Log.e(TAG, "decodeMessage: exception during parsing");
            e.printStackTrace();
            return null;



        }

        return equakes;

    }

}
