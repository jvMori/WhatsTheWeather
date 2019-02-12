
package com.example.jvmori.myweatherapp.architectureComponents.data.db.entity;

import com.example.jvmori.myweatherapp.architectureComponents.util.ZoneDateTypeConverter;
import com.google.gson.annotations.SerializedName;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;

import androidx.room.TypeConverters;

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
    public String mCityName;
    @SerializedName("region")
    private String mRegion;
    @SerializedName("tz_id")
    private String mTzId;
//    @TypeConverters(ZoneDateTypeConverter.class)
//    private ZonedDateTime zonedDateTime;

//    public Location(){
//        Instant instant = Instant.ofEpochSecond(getLocaltimeEpoch());
//        ZoneId zoneId = ZoneId.of(getTzId());
//        zonedDateTime = ZonedDateTime.ofInstant(instant, zoneId);
//    }

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

//    public ZonedDateTime getZonedDateTime() {
//        return zonedDateTime;
//    }
//
//    public void setZonedDateTime(ZonedDateTime zonedDateTime) {
//        this.zonedDateTime = zonedDateTime;
//    }
}
