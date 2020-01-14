package com.example.jvmori.myweatherapp.data.current;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;

import java.util.List;
import java.util.function.DoubleUnaryOperator;

import io.reactivex.Flowable;
import io.reactivex.Maybe;

@Dao
public abstract class CurrentWeatherDao {

    @Query("select * from current_weather where city_name like :city")
    public abstract Maybe<CurrentWeatherUI> getCurrentWeatherByCity(String city);

    @Query("select * from current_weather where longitude like :lon AND latitude like :lat")
    public abstract Maybe<CurrentWeatherUI> getCurrentWeatherByGeographic(String lon, String lat);

    @Query("select * from current_weather")
    public abstract Maybe<List<CurrentWeatherUI>> getAllWeather();

    @Insert (onConflict = OnConflictStrategy.REPLACE)
    public abstract void insert(CurrentWeatherUI currentWeatherResponse);

    @Query("delete from current_weather where city_name like :cityName")
    abstract void deleteAll(String cityName);

    @Transaction
    void update(CurrentWeatherUI currentWeatherResponse){
        deleteAll(currentWeatherResponse.getCity());
        insert(currentWeatherResponse);
    }
}
