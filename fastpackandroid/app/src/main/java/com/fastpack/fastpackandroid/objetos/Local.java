package com.fastpack.fastpackandroid.objetos;

import android.location.Location;

public class Local {

    public static final String LATITUDE = "latitude";
    public static final String LONGITUDE = "longitude";
    private double latitude, longitude;

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }


    public void setLocation(Location location) {
        this.latitude = location.getLatitude();
        this.longitude = location.getLongitude();
    }
}
