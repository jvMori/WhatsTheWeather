
package com.example.jvmori.myweatherapp.data.current.response;

import com.google.gson.annotations.SerializedName;

public class Coord {

    @SerializedName("lat")
    private Long mLat;
    @SerializedName("lon")
    private Long mLon;

    public Long getLat() {
        return mLat;
    }

    public void setLat(Long lat) {
        mLat = lat;
    }

    public Long getLon() {
        return mLon;
    }

    public void setLon(Long lon) {
        mLon = lon;
    }

}
