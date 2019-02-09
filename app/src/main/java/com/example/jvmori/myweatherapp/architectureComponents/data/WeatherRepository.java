package com.example.jvmori.myweatherapp.architectureComponents.data;

import android.app.Application;
import android.os.AsyncTask;

import com.example.jvmori.myweatherapp.architectureComponents.AppExecutors;
import com.example.jvmori.myweatherapp.architectureComponents.data.db.entity.CurrentWeather;
import com.example.jvmori.myweatherapp.architectureComponents.data.network.WeatherNetworkDataSource;
import com.example.jvmori.myweatherapp.architectureComponents.data.network.WeatherNetworkDataSourceImpl;
import com.example.jvmori.myweatherapp.architectureComponents.data.network.response.CurrentWeatherResponse;

import java.time.ZonedDateTime;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

public class WeatherRepository
{
    private static final Object LOCK = new Object();
    private static WeatherRepository instance;
    private WeatherDao weatherDao;
    private WeatherNetworkDataSourceImpl weatherNetworkDataSource;
    private AppExecutors executors;

    private WeatherRepository (Application application, AppExecutors executors){
        this.executors = executors;
        this.weatherNetworkDataSource = new WeatherNetworkDataSourceImpl();
        this.weatherDao = WeatherDatabase.getInstance(application.getApplicationContext()).weatherDao();
        weatherNetworkDataSource.currentWeather().observeForever(this::persistFetchedCurrentWeather);
    }

public LiveData<CurrentWeatherResponse> getCurrentWeather() {
   return weatherNetworkDataSource.fetchWeather("London", "en");
    //return weatherDao.getWeather();
}
    public synchronized static WeatherRepository getInstance(Application context, AppExecutors executors){
        if (instance == null){
            synchronized (LOCK){
                instance = new WeatherRepository(context, executors);
            }
        }
        return instance;
    }

    private void persistFetchedCurrentWeather(final CurrentWeatherResponse currentWeatherResponse){
        executors.diskIO().execute(() -> weatherDao.insert(currentWeatherResponse.getCurrent()));
    }

   private synchronized void initWeatherData(){
        if (isFetchCurrentNeeded(ZonedDateTime.now().minusHours(1)))
             weatherNetworkDataSource.fetchWeather("London", "en");
   }

    private boolean isFetchCurrentNeeded(ZonedDateTime lastFetchedTime){
        ZonedDateTime thirtyMinutesAgo = ZonedDateTime.now().minusMinutes(30);
        return lastFetchedTime.isBefore(thirtyMinutesAgo);
    }
}
