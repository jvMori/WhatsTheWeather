package com.example.jvmori.myweatherapp.util;


import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.Nullable;

public class WeatherParameters implements Parcelable
{
    private String location;
    private String cityName;
    private boolean isDeviceLocation;
    private String lang;
    private String days;

    public WeatherParameters(String location, String cityName, boolean isDeviceLocation, String lang, String days) {
        this.location = location;
        this.cityName = cityName;
        this.isDeviceLocation = isDeviceLocation;
        this.lang = lang;
        this.days = days;
    }
    public WeatherParameters(String location, String cityName, boolean isDeviceLocation, String days) {
        this.location = location;
        this.cityName = cityName;
        this.isDeviceLocation = isDeviceLocation;
        this.lang = "en";
        this.days = days;
    }

    protected WeatherParameters(Parcel in) {
        location = in.readString();
        cityName = in.readString();
        isDeviceLocation = in.readByte() != 0;
        lang = in.readString();
        days = in.readString();
    }

    public static final Creator<WeatherParameters> CREATOR = new Creator<WeatherParameters>() {
        @Override
        public WeatherParameters createFromParcel(Parcel in) {
            return new WeatherParameters(in);
        }

        @Override
        public WeatherParameters[] newArray(int size) {
            return new WeatherParameters[size];
        }
    };

    @Override
    public boolean equals(@Nullable Object obj) {
        if(obj == this)
            return true;
        if(!(obj instanceof WeatherParameters))
            return false;
        WeatherParameters param = (WeatherParameters) obj;
        return param.location.equals(this.location) &&
                param.isDeviceLocation == this.isDeviceLocation &&
                param.days.equals(this.days) &&
                param.lang.equals(this.lang);
    }

    public String getLocation() {
        return location;
    }
    public String getCityName() { return cityName;}
    public void setCityName(String cityName){this.cityName = cityName;}

    public void setLocation(String location) {
        this.location = location;
    }

    public boolean isDeviceLocation() {
        return isDeviceLocation;
    }

    public String getLang() {
        return lang;
    }

    public String getDays() {
        return days;
    }

    public void setDays(String days) {
        this.days = days;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(location);
        dest.writeString(cityName);
        dest.writeByte((byte) (isDeviceLocation ? 1 : 0));
        dest.writeString(lang);
        dest.writeString(days);
    }
}
