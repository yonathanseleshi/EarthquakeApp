package com.yseleshi.earthquake;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by dell laptop on 7/30/2017.
 */

public class Quake {
    public ArrayList<Earthquake> earthquakes = new ArrayList<Earthquake>();



    Date date = new Date();
    public Quake(){

        LoadEarthquakeData();

    }

    public void LoadEarthquakeData(){



    }

    public ArrayList<Earthquake> getEarthquakes(){

        return earthquakes;
    }

}
