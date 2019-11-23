package com.example.jvmori.myweatherapp.data.current;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.jvmori.myweatherapp.data.current.response.CurrentWeatherResponse;

import io.reactivex.Maybe;

@Dao
public interface CurrentWeatherDao {

    @Query("select * from current_weather where city_name like :city")
    Maybe<CurrentWeatherResponse> getCurrentWeatherByCity(String city);

    @Insert (onConflict = OnConflictStrategy.REPLACE)
    void insert(CurrentWeatherResponse currentWeatherResponse);

}
