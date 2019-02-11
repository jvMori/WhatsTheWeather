package com.example.jvmori.myweatherapp.architectureComponents.data;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;

import com.example.jvmori.myweatherapp.architectureComponents.AppExecutors;
import com.example.jvmori.myweatherapp.architectureComponents.data.db.entity.CurrentWeather;
import com.example.jvmori.myweatherapp.architectureComponents.data.network.WeatherNetworkDataSource;
import com.example.jvmori.myweatherapp.architectureComponents.data.network.WeatherNetworkDataSourceImpl;
import com.example.jvmori.myweatherapp.architectureComponents.data.network.response.CurrentWeatherResponse;
import com.example.jvmori.myweatherapp.model.CurrentLocation;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WeatherRepository {
    private static final Object LOCK = new Object();
    private static WeatherRepository instance;
    private WeatherDao weatherDao;
    private WeatherNetworkDataSourceImpl weatherNetworkDataSource;
    private AppExecutors executors;
    private MutableLiveData<CurrentWeather> currentWeatherLiveData;

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

    private void persistFetchedCurrentWeather(final CurrentWeather currentWeather) {
        executors.diskIO().execute(() -> weatherDao.insert(currentWeather));
    }

    public LiveData<List<CurrentWeather>> getAllWeather(){
        return weatherDao.getWeather();
    }

    public LiveData<CurrentWeather> initWeatherData(WeatherParameters weatherParameters) {
        if (isFetchCurrentNeeded(ZonedDateTime.now().minusMinutes(60)) || isDeviceLocationChanged()){
            weatherNetworkDataSource.fetchWeather(weatherParameters).enqueue(new Callback<CurrentWeatherResponse>() {
                @Override
                public void onResponse(Call<CurrentWeatherResponse> call, Response<CurrentWeatherResponse> response) {
                    if(!response.isSuccessful()){
                        Log.i("Fail", "Response is not successful");
                        return;
                    }
                    if (response.body() != null){
                        CurrentWeather currentWeather = response.body().getCurrent();
                        String cityName = response.body().getLocation().getName();
                        currentWeather.setLocation(cityName);
                        currentWeatherLiveData.postValue(currentWeather);
                        persistFetchedCurrentWeather(currentWeather);
                    }
                }

                @Override
                public void onFailure(Call<CurrentWeatherResponse> call, Throwable t) {
                    Log.i("Fail", "Failed to fetch current weather" + t.toString());
                }
            });
        }
        else {
            return weatherDao.getWeatherForLocation(weatherParameters.getLocation());
        }
        return currentWeatherLiveData;
    }

    private boolean isFetchCurrentNeeded(ZonedDateTime lastFetchedTime) {
        ZonedDateTime thirtyMinutesAgo = ZonedDateTime.now().minusMinutes(30);
        return lastFetchedTime.isBefore(thirtyMinutesAgo);
    }

    private boolean isDeviceLocationChanged(){
        String location = "50.08,18.09";
        String[] locations =  location.split(",");
        List<Double> latLon = new ArrayList<>();
        for (String loc: locations) {
            latLon.add(Double.parseDouble(loc));
        }
        return false;
    }
}
