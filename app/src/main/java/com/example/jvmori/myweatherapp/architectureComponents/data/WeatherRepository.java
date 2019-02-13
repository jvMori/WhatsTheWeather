package com.example.jvmori.myweatherapp.architectureComponents.data;

import android.app.Application;
import android.util.Log;

import com.example.jvmori.myweatherapp.architectureComponents.AppExecutors;
import com.example.jvmori.myweatherapp.architectureComponents.data.network.WeatherNetworkDataSourceImpl;
import com.example.jvmori.myweatherapp.architectureComponents.data.db.entity.CurrentWeatherEntry;
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
    private WeatherDao weatherDao;
    private WeatherNetworkDataSourceImpl weatherNetworkDataSource;
    private AppExecutors executors;
    private MutableLiveData<CurrentWeatherEntry> currentWeatherLiveData;

    private WeatherRepository(Application application, AppExecutors executors) {
        this.executors = executors;
        currentWeatherLiveData = new MutableLiveData<>();
        this.weatherNetworkDataSource = new WeatherNetworkDataSourceImpl();
        this.weatherDao = WeatherDatabase.getInstance(application.getApplicationContext()).weatherDao();
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
        executors.diskIO().execute(() -> weatherDao.insert(currentWeather));
    }

    public LiveData<List<CurrentWeatherEntry>> getWeatherExceptDeviceLoc(){
        return weatherDao.getWeather();
    }

    public LiveData<List<CurrentWeatherEntry>> getAllWeather(){
        return weatherDao.getAllWeather();
    }

    public void deletePreviousDeviceLocation(){
        executors.diskIO().execute(() -> weatherDao.deleteLastDeviceLocation());
    }

    public LiveData<CurrentWeatherEntry> initWeatherData(WeatherParameters weatherParameters, OnFailure callbackOnFailure) {
        if (isFetchCurrentNeeded(ZonedDateTime.now().minusMinutes(60))){
            weatherNetworkDataSource.fetchWeather(weatherParameters).enqueue(new Callback<CurrentWeatherEntry>() {
                @Override
                public void onResponse(Call<CurrentWeatherEntry> call, Response<CurrentWeatherEntry> response) {
                    if(!response.isSuccessful()){
                        Log.i("Fail", "Response is not successful");
                        return;
                    }
                    if (response.body() != null){
                        response.body().setDeviceLocation(weatherParameters.isDeviceLocation());
                        currentWeatherLiveData.postValue(response.body());
                        if (weatherParameters.isDeviceLocation())
                            deletePreviousDeviceLocation();
                        persistFetchedCurrentWeather(response.body());
                    }
                }

                @Override
                public void onFailure(Call<CurrentWeatherEntry> call, Throwable t) {
                    Log.i("Fail", "Failed to fetch current weather" + t.toString());
                    callbackOnFailure.callback(t.toString());
                }
            });
        }
        else {
            return weatherDao.getWeatherForLocation(weatherParameters.getLocation());
        }
        return currentWeatherLiveData;
    }
    public interface OnFailure{
        void callback(String message);
    }

    private boolean isFetchCurrentNeeded(ZonedDateTime lastFetchedTime) {
        ZonedDateTime thirtyMinutesAgo = ZonedDateTime.now().minusMinutes(30);
        return lastFetchedTime.isBefore(thirtyMinutesAgo);
    }
}
