package com.example.jvmori.myweatherapp.architectureComponents.data.repository;

import android.app.Application;
import android.util.Log;

import com.example.jvmori.myweatherapp.architectureComponents.AppExecutors;
import com.example.jvmori.myweatherapp.architectureComponents.data.db.CurrentWeatherDao;
import com.example.jvmori.myweatherapp.architectureComponents.data.db.ForecastDao;
import com.example.jvmori.myweatherapp.architectureComponents.data.db.WeatherDatabase;
import com.example.jvmori.myweatherapp.architectureComponents.data.db.entity.forecast.ForecastEntry;
import com.example.jvmori.myweatherapp.architectureComponents.data.network.WeatherNetworkDataSourceImpl;
import com.example.jvmori.myweatherapp.architectureComponents.data.db.entity.current.CurrentWeatherEntry;
import com.example.jvmori.myweatherapp.architectureComponents.util.WeatherParameters;

import java.time.ZonedDateTime;
import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WeatherRepository {
    private static final Object LOCK = new Object();
    private static WeatherRepository instance;
    private CurrentWeatherDao currentWeatherDao;
    private ForecastDao forecastDao;
    private WeatherNetworkDataSourceImpl weatherNetworkDataSource;
    private AppExecutors executors;
    private MutableLiveData<CurrentWeatherEntry> currentWeatherLiveData;
    private MutableLiveData<ForecastEntry> forecastEntryData;

    private WeatherRepository(Application application, AppExecutors executors) {
        this.executors = executors;
        currentWeatherLiveData = new MutableLiveData<>();
        forecastEntryData = new MutableLiveData<>();
        this.weatherNetworkDataSource = new WeatherNetworkDataSourceImpl();
        this.currentWeatherDao = WeatherDatabase.getInstance(application.getApplicationContext()).weatherDao();
        this.forecastDao = WeatherDatabase.getInstance(application.getApplicationContext()).forecastDao();
    }

    public synchronized static WeatherRepository getInstance(Application context, AppExecutors executors) {
        if (instance == null) {
            synchronized (LOCK) {
                instance = new WeatherRepository(context, executors);
            }
        }
        return instance;
    }

    private void persistFetchedCurrentWeather(final CurrentWeatherEntry currentWeather) {
        executors.diskIO().execute(() -> currentWeatherDao.insert(currentWeather));
    }

    public LiveData<List<CurrentWeatherEntry>> getWeatherExceptDeviceLoc(){
        return currentWeatherDao.getWeather();
    }

    public LiveData<List<CurrentWeatherEntry>> getAllWeather(){
        return currentWeatherDao.getAllWeather();
    }
    public LiveData<List<ForecastEntry>> getAllForecast(){
        return forecastDao.getForecastsForAllLocations();
    }

    public void deletePreviousDeviceLocation(){
        executors.diskIO().execute(() -> currentWeatherDao.deleteLastDeviceLocation());
    }

    private void persistForecast(ForecastEntry newForecastEntry){
        executors.diskIO().execute(()-> {
            forecastDao.deleteForecastForDeviceLocation();
            forecastDao.insert(newForecastEntry);
        });
    }

    public LiveData<ForecastEntry> getForecast(WeatherParameters weatherParameters, OnFailure onFailure){
        if (isFetchCurrentNeeded(ZonedDateTime.now().minusMinutes(60))){
            weatherNetworkDataSource.fetchForecast(weatherParameters).enqueue(new Callback<ForecastEntry>() {
                @Override
                public void onResponse(Call<ForecastEntry> call, Response<ForecastEntry> response) {
                    if(!response.isSuccessful()){
                        Log.i("Fail", "Response is not successful");
                        onFailure.callback("Failed! Response is not successful");
                        return;
                    }
                    if (response.body() != null){
                        response.body().isDeviceLocation = weatherParameters.isDeviceLocation();
                        persistForecast(response.body());
                        forecastEntryData.postValue(response.body());
                    }
                }

                @Override
                public void onFailure(Call<ForecastEntry> call, Throwable t) {
                    Log.i("Fail", "Failed to fetch current weather" + t.toString());
                    onFailure.callback(t.getMessage());
                }
            });
        } else
            return forecastDao.getForecastsForLocation(weatherParameters.getLocation());

        return forecastEntryData;
    }

    public LiveData<CurrentWeatherEntry> initWeatherData(WeatherParameters weatherParameters, OnFailure callbackOnFailure) {
        if (isFetchCurrentNeeded(ZonedDateTime.now().minusMinutes(60))){
           fetchCurrentWeather(weatherParameters, callbackOnFailure);
        }
        else {
            return currentWeatherDao.getWeatherForLocation(weatherParameters.getLocation());
        }
        return currentWeatherLiveData;
    }

    private void fetchCurrentWeather(WeatherParameters weatherParameters, OnFailure callbackOnFailure){
        weatherNetworkDataSource.fetchWeather(weatherParameters).enqueue(new Callback<CurrentWeatherEntry>() {
            @Override
            public void onResponse(Call<CurrentWeatherEntry> call, Response<CurrentWeatherEntry> response) {
                if(!response.isSuccessful()){
                    Log.i("Fail", "Response is not successful");
                    return;
                }
                if (response.body() != null){
                    response.body().setDeviceLocation(weatherParameters.isDeviceLocation());
                    if (weatherParameters.isDeviceLocation())
                        deletePreviousDeviceLocation();
                    persistFetchedCurrentWeather(response.body());
                    currentWeatherLiveData.postValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<CurrentWeatherEntry> call, Throwable t) {
                Log.i("Fail", "Failed to fetch current weather" + t.toString());
                callbackOnFailure.callback(t.toString());
            }
        });
    }

    public interface OnFailure{
        void callback(String message);
    }

    private boolean isFetchCurrentNeeded(ZonedDateTime lastFetchedTime) {
        ZonedDateTime thirtyMinutesAgo = ZonedDateTime.now().minusMinutes(30);
        return lastFetchedTime.isBefore(thirtyMinutesAgo);
    }
}
