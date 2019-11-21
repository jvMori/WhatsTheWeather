package com.example.jvmori.myweatherapp.data.current;

import androidx.room.Dao;
import androidx.room.Query;

import com.example.jvmori.myweatherapp.data.current.response.CurrentWeatherResponse;

import io.reactivex.Maybe;

@Dao
public interface CurrentWeatherDao {

    @Query("select * from current_weather where mCityName like :city")
    public abstract Maybe<CurrentWeatherResponse> getCurrentWeatherByCity(String city);

}
