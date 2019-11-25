package com.example.jvmori.myweatherapp.data.forecast.response;

import androidx.room.ColumnInfo;

import com.example.jvmori.myweatherapp.data.current.response.Coord;
import com.google.gson.annotations.SerializedName;

public class City {

    public City(Long id, String cityName, Coord coord, String country) {
        this.id = id;
        this.cityName = cityName;
        this.coord = coord;
        this.country = country;
    }

    @SerializedName("id")
    @ColumnInfo(name="city_id")
    private Long id;

    @SerializedName("name")
    @ColumnInfo(name="city_name")
    private String cityName;

    @SerializedName("coord")
    private Coord coord;

    @SerializedName("country")
    private String country;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public Coord getCoord() {
        return coord;
    }

    public void setCoord(Coord coord) {
        this.coord = coord;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}
