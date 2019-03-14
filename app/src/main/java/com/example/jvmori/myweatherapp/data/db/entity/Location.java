
package com.example.jvmori.myweatherapp.data.db.entity;

import com.google.gson.annotations.SerializedName;

import androidx.annotation.NonNull;


public class Location {
    @SerializedName("country")
    private String mCountry;
    @SerializedName("lat")
    private Double mLat;
    @SerializedName("localtime")
    private String mLocaltime;
    @SerializedName("localtime_epoch")
    private Long mLocaltimeEpoch;
    @SerializedName("lon")
    private Double mLon;
    @SerializedName("name")
    @NonNull
    public String mCityName ="";
    @SerializedName("region")
    private String mRegion;
    @SerializedName("tz_id")
    private String mTzId;

    public String getCountry() {
        return mCountry;
    }

    public void setCountry(String country) {
        mCountry = country;
    }

    public Double getLat() {
        return mLat;
    }

    public void setLat(Double lat) {
        mLat = lat;
    }

    public String getLocaltime() {
        return mLocaltime;
    }

    public void setLocaltime(String localtime) {
        mLocaltime = localtime;
    }

    public Long getLocaltimeEpoch() {
        return mLocaltimeEpoch;
    }

    public void setLocaltimeEpoch(Long localtimeEpoch) {
        mLocaltimeEpoch = localtimeEpoch;
    }

    public Double getLon() {
        return mLon;
    }

    public void setLon(Double lon) {
        mLon = lon;
    }

    public String getName() {
        return mCityName;
    }

    public void setName(String name) {
        mCityName = name;
    }

    public String getRegion() {
        return mRegion;
    }

    public void setRegion(String region) {
        mRegion = region;
    }

    public String getTzId() {
        return mTzId;
    }

    public void setTzId(String tzId) {
        mTzId = tzId;
    }

}
