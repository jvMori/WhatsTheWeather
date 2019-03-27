package com.example.jvmori.myweatherapp.data.db;

import com.example.jvmori.myweatherapp.data.db.entity.forecast.ForecastEntry;

import java.util.List;
import java.util.Observable;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;
import io.reactivex.Maybe;
import io.reactivex.Single;

@Dao
public abstract class ForecastDao
{
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract void insert(ForecastEntry forecastEntry);

    @Query("delete from forecast where isDeviceLocation = 1")
    public abstract void deleteOldDeviceLocWeather();

    @Transaction
    public void insertAndDeleteOldLocation(ForecastEntry forecastEntry){
        deleteOldDeviceLocWeather();
        insert(forecastEntry);
    }

    @Query("delete from forecast where mCityName like :cityName")
    public abstract void deleteForecast(String cityName);

    @Query("select * from forecast order by isDeviceLocation desc")
    public abstract io.reactivex.Observable<List<ForecastEntry>> getAllWeather();

    @Query("select * from forecast where mCityName like :location")
    public abstract Maybe<ForecastEntry> getWeather(String location);

}
