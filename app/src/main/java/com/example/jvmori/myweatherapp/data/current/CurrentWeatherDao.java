package com.example.jvmori.myweatherapp.data.current;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import io.reactivex.Flowable;

@Dao
public interface CurrentWeatherDao {

    @Query("select * from current_weather where city_name like :city")
    Flowable<CurrentWeatherUI> getCurrentWeatherByCity(String city);

    @Insert (onConflict = OnConflictStrategy.REPLACE)
    void insert(CurrentWeatherUI currentWeatherResponse);

}
