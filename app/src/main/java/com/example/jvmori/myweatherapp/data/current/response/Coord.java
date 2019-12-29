
package com.example.jvmori.myweatherapp.data.current.response;

import com.google.gson.annotations.SerializedName;

public class Coord {

    @SerializedName("lat")
    private Double mLat;
    @SerializedName("lon")
    private Double mLon;

    public Double getLat() {
        return mLat;
    }

    public void setLat(Double lat) {
        mLat = lat;
    }

    public Double getLon() {
        return mLon;
    }

    public void setLon(Double lon) {
        mLon = lon;
    }

}
