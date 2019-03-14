package com.example.jvmori.myweatherapp.data.db;

import com.example.jvmori.myweatherapp.data.db.entity.forecast.ForecastEntry;

import java.util.List;
import java.util.Observable;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import io.reactivex.Maybe;
import io.reactivex.Single;

@Dao
public interface ForecastDao
{
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(ForecastEntry forecastEntry);

    @Query("delete from forecast where isDeviceLocation = 1")
    void deleteOldDeviceLocWeather();

    @Query("delete from forecast where mCityName like :cityName")
    void deleteForecast(String cityName);

    @Query("select * from forecast order by isDeviceLocation desc")
    io.reactivex.Observable<List<ForecastEntry>> getAllWeather();

    @Query("select * from forecast where mCityName like :location")
    Maybe<ForecastEntry> getWeather(String location);

//    @Query("select * from forecast where isDeviceLocation = 0")
//    LiveData<List<ForecastEntry>> allForecastsExceptForDeviceLocation();

}
