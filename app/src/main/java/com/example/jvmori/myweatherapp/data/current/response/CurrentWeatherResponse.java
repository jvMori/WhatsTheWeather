
package com.example.jvmori.myweatherapp.data.current.response;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.TypeConverters;

import java.util.List;

import com.example.jvmori.myweatherapp.util.DescriptionTypeConverter;
import com.google.gson.annotations.SerializedName;

@Entity(tableName = "current_weather", primaryKeys = {"city_name"})
public class CurrentWeatherResponse {

    @SerializedName("base")
    private String mBase;
    @SerializedName("clouds")
    @Embedded
    private Clouds mClouds;
    @SerializedName("cod")
    private Long mCod;
    @SerializedName("coord")
    @Embedded
    private Coord mCoord;
    @SerializedName("dt")
    private Long mDt;
    @SerializedName("id")
    private Long mId;
    @SerializedName("main")
    @Embedded
    private Main mMain;
    @SerializedName("name")
    @ColumnInfo(name = "city_name")
    @NonNull
    private String mCityName = "";
    @SerializedName("sys")
    @Embedded
    private Sys mSys;
    @SerializedName("visibility")
    private Long mVisibility;
    @SerializedName("weather")
    @TypeConverters(DescriptionTypeConverter.class)
    private List<Description> mWeather;
    @SerializedName("wind")
    @Embedded
    private Wind mWind;

    public String getBase() {
        return mBase;
    }

    public void setBase(String base) {
        mBase = base;
    }

    public Clouds getClouds() {
        return mClouds;
    }

    public void setClouds(Clouds clouds) {
        mClouds = clouds;
    }

    public Long getCod() {
        return mCod;
    }

    public void setCod(Long cod) {
        mCod = cod;
    }

    public Coord getCoord() {
        return mCoord;
    }

    public void setCoord(Coord coord) {
        mCoord = coord;
    }

    public Long getDt() {
        return mDt;
    }

    public void setDt(Long dt) {
        mDt = dt;
    }

    public Long getId() {
        return mId;
    }

    public void setId(Long id) {
        mId = id;
    }

    public Main getMain() {
        return mMain;
    }

    public void setMain(Main main) {
        mMain = main;
    }

    public String getCityName() {
        return mCityName;
    }

    public void setCityName(String name) {
        mCityName = name;
    }

    public Sys getSys() {
        return mSys;
    }

    public void setSys(Sys sys) {
        mSys = sys;
    }

    public Long getVisibility() {
        return mVisibility;
    }

    public void setVisibility(Long visibility) {
        mVisibility = visibility;
    }

    public List<Description> getWeather() {
        return mWeather;
    }

    public void setWeather(List<Description> weather) {
        mWeather = weather;
    }

    public Wind getWind() {
        return mWind;
    }

    public void setWind(Wind wind) {
        mWind = wind;
    }

}
