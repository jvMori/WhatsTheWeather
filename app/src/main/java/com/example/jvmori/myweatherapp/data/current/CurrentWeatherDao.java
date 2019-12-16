package com.example.jvmori.myweatherapp.data.current;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.function.DoubleUnaryOperator;

import io.reactivex.Flowable;
import io.reactivex.Maybe;

@Dao
public interface CurrentWeatherDao {

    @Query("select * from current_weather where city_name like :city")
    Flowable<CurrentWeatherUI> getCurrentWeatherByCity(String city);

    @Query("select * from current_weather where lon like :lon AND lat like :lat")
    Maybe<CurrentWeatherUI> getCurrentWeatherByGeographic(String lon, String lat);

    @Insert (onConflict = OnConflictStrategy.REPLACE)
    void insert(CurrentWeatherUI currentWeatherResponse);


}
