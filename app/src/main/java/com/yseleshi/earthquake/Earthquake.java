package com.yseleshi.earthquake;

import java.util.Date;



public class Earthquake {



    private String earthquakePlace = "";
    private String earthquakeDate = "";
    private String earthquakeMag = "";
    private String earthquakeLong = "";
    private String earthquakeLat = "";
    private String earthquakeDetail = "";
    private String airportDelayReason = "";
    public static final String TAG_PLACE = "place";
    public static final String TAG_TIME = "time";
    public static final String TAG_MAG = "mag";
    public static final String TAG_PROPERTIES = "properties";
    public static final String TAG_GEOMETRY = "geometry";
    public static final String TAG_COORDINATES = "coordinates";
    public static final String TAG_DETAIL = "detail";
    public static final String TAG_COUNT = "count";
    public static final String TAG_METADATA = "metadata";
    public static final String TAG_FEATURES = "features";

    public static final String TAG_STATUS_REASON = "reason";

    public String place;
    public Long time;
    public double mag;


    private long milliseconds;
    public String coordinates;
    public String latitude;
    public String longitude;
    public String detail;

    public Earthquake(){


    }

    public Earthquake(String place, Long time, long milliseconds, double mag, String latitude, String longitude, String detail) {


        this.place = place;
        this.time = time;
        this.mag = mag;
        this.latitude = latitude;
        this.longitude = longitude;
        this.detail = detail;
        this.milliseconds = milliseconds;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public long getMilliseconds() {
        return milliseconds;
    }

    public void setMilliseconds(long milliseconds) {
        this.milliseconds = milliseconds;
    }

    public Long getTime() {




        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public double getMag() {
        return mag;
    }

    public void setMag(double mag) {
        this.mag = mag;
    }

    public String getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(String coordinates) {
        this.coordinates = coordinates;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }



    public String logQuakeData(){

        return "Date: " + getTime() + " Details: " + getPlace()
                + " Latitude: " + getLatitude()
                + " Longitude: " + getLongitude()
                + " magnitude: " + getMag()
                ;

    }



}
