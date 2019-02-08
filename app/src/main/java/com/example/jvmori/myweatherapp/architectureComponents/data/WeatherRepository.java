package com.example.jvmori.myweatherapp.architectureComponents.data;

import android.content.Context;
import android.os.AsyncTask;

import com.example.jvmori.myweatherapp.architectureComponents.data.db.entity.CurrentWeather;
import com.example.jvmori.myweatherapp.architectureComponents.data.network.WeatherNetworkDataSource;
import com.example.jvmori.myweatherapp.architectureComponents.data.network.WeatherNetworkDataSourceImpl;
import com.example.jvmori.myweatherapp.architectureComponents.data.network.response.CurrentWeatherResponse;

import java.time.ZonedDateTime;
import java.util.concurrent.ExecutionException;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

public class WeatherRepository
{
    private MutableLiveData<CurrentWeatherResponse> getCurrentWeather;
    private static final Object LOCK = new Object();
    private static WeatherRepository instance;
    private WeatherDao weatherDao;
    private WeatherNetworkDataSource weatherNetworkDataSource;

    private WeatherRepository (Context context){
        this.weatherNetworkDataSource = new WeatherNetworkDataSourceImpl();
        this.weatherDao = WeatherDatabase.getInstance(context.getApplicationContext()).weatherDao();
        weatherNetworkDataSource.currentWeather.observeForever(new Observer<CurrentWeatherResponse>() {
            @Override
            public void onChanged(CurrentWeatherResponse newCurrentWeatherResponse) {
                    if (newCurrentWeatherResponse != null)
                        persistFetchedCurrentWeather(newCurrentWeatherResponse);
            }
        });
    }

public LiveData<CurrentWeather> currentWeatherLiveData() {
    try {
        return new  InitWeatherAsyncTask(this, weatherDao).execute().get();
    } catch (ExecutionException e) {
        e.printStackTrace();
    } catch (InterruptedException e) {
        e.printStackTrace();
    }
    return null;
}
    public synchronized static WeatherRepository getInstance(Context context){
        if (instance == null){
            synchronized (LOCK){
                instance = new WeatherRepository(context);
            }
        }
        return instance;
    }

    private void persistFetchedCurrentWeather(final CurrentWeatherResponse currentWeatherResponse){
        weatherDao.insert(currentWeatherResponse.getCurrent());
    }


   private void initWeatherData(){
        if (isFetchCurrentNeeded(ZonedDateTime.now().minusHours(1)))
             weatherNetworkDataSource.fetchWeather("London", "en");
   }

    private boolean isFetchCurrentNeeded(ZonedDateTime lastFetchedTime){
        ZonedDateTime thirtyMinutesAgo = ZonedDateTime.now().minusMinutes(30);
        return lastFetchedTime.isBefore(thirtyMinutesAgo);
    }

   public static class InitWeatherAsyncTask extends AsyncTask<Void, Void, LiveData<CurrentWeather>>{
        WeatherRepository weatherRepository;
        WeatherDao weatherDao;
        InitWeatherAsyncTask(WeatherRepository weatherRepository, WeatherDao weatherDao){
            this.weatherRepository = weatherRepository;
            this.weatherDao = weatherDao;
        }

       @Override
       protected LiveData<CurrentWeather> doInBackground(Void... voids) {
           weatherRepository.initWeatherData();
           return weatherDao.getWeather();
       }
   }
}
