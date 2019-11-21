package com.example.jvmori.myweatherapp.data.current;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.jvmori.myweatherapp.data.current.response.CurrentWeatherResponse;

import io.reactivex.Flowable;

@Dao
public interface CurrentWeatherDao {

    @Query("select * from current_weather where mCityName like :city")
    Flowable<CurrentWeatherResponse> getCurrentWeatherByCity(String city);

    @Insert (onConflict = OnConflictStrategy.REPLACE)
    void insert(CurrentWeatherResponse currentWeatherResponse);

}
