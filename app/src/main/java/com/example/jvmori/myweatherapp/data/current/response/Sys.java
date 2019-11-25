
package com.example.jvmori.myweatherapp.data.current.response;

import androidx.room.ColumnInfo;

import com.google.gson.annotations.SerializedName;

public class Sys {

    @SerializedName("country")
    private String mCountry;
    @SerializedName("id")
    @ColumnInfo(name = "weather_id")
    private Long mId;
    @SerializedName("message")
    private Double mMessage;
    @SerializedName("sunrise")
    private Long mSunrise;
    @SerializedName("sunset")
    private Long mSunset;
    @SerializedName("type")
    @ColumnInfo(name = "weather_type")
    private Long mType;

    public String getCountry() {
        return mCountry;
    }

    public void setCountry(String country) {
        mCountry = country;
    }

    public Long getId() {
        return mId;
    }

    public void setId(Long id) {
        mId = id;
    }

    public Double getMessage() {
        return mMessage;
    }

    public void setMessage(Double message) {
        mMessage = message;
    }

    public Long getSunrise() {
        return mSunrise;
    }

    public void setSunrise(Long sunrise) {
        mSunrise = sunrise;
    }

    public Long getSunset() {
        return mSunset;
    }

    public void setSunset(Long sunset) {
        mSunset = sunset;
    }

    public Long getType() {
        return mType;
    }

    public void setType(Long type) {
        mType = type;
    }

}
