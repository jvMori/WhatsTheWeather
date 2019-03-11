package com.example.jvmori.myweatherapp.data.repository;

import android.app.Application;
import android.util.Log;

import com.example.jvmori.myweatherapp.AppExecutors;
import com.example.jvmori.myweatherapp.data.db.ForecastDao;
import com.example.jvmori.myweatherapp.data.db.WeatherDatabase;
import com.example.jvmori.myweatherapp.data.db.entity.forecast.ForecastEntry;
import com.example.jvmori.myweatherapp.data.network.WeatherNetworkDataSource;
import com.example.jvmori.myweatherapp.data.network.WeatherNetworkDataSourceImpl;
import com.example.jvmori.myweatherapp.data.network.response.Search;
import com.example.jvmori.myweatherapp.util.WeatherParameters;

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
    private ForecastDao forecastDao;
    private WeatherNetworkDataSource weatherNetworkDataSource;
    private AppExecutors executors;


    private WeatherRepository(Application application, AppExecutors executors) {
        this.executors = executors;
        this.weatherNetworkDataSource = new WeatherNetworkDataSourceImpl();
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

    public LiveData<List<ForecastEntry>> getAllForecast() {
        return forecastDao.getForecastsForAllLocations();
    }

    public LiveData<List<ForecastEntry>> allForecastsWithoutLoc() {
        return forecastDao.allForecastsExceptForDeviceLocation();
    }

    private void persistForecast(ForecastEntry newForecastEntry) {
        executors.diskIO().execute(() -> {
            forecastDao.deleteForecastForDeviceLocation();
            forecastDao.insert(newForecastEntry);
        });
    }

    public LiveData<List<Search>> getResultsForCity(String cityName) {
        return weatherNetworkDataSource.searchCity(cityName);
    }

    public LiveData<ForecastEntry> downloadWeather(WeatherParameters weatherParameters, OnFailure onFailure) {
        MutableLiveData<ForecastEntry> forecastEntryData = new MutableLiveData<>();
        if (isFetchCurrentNeeded(ZonedDateTime.now().minusMinutes(60))) {
            fetchWeather(weatherParameters, onFailure, forecastEntryData);
        } else {
            getWeatherFromDb(weatherParameters,forecastEntryData);
        }
        return forecastEntryData;
    }

    private void getWeatherFromDb(WeatherParameters weatherParameters,
                                  MutableLiveData<ForecastEntry> forecastEntryData) {
        executors.diskIO().execute(() -> {
            forecastEntryData.postValue(forecastDao.getForecastsForLocation(weatherParameters.getLocation()));
        });
    }

    private void fetchWeather(WeatherParameters weatherParameters,
                              OnFailure onFailure,
                              MutableLiveData<ForecastEntry> forecastEntryData) {
        weatherNetworkDataSource.fetchForecast(weatherParameters).enqueue(new Callback<ForecastEntry>() {
            @Override
            public void onResponse(Call<ForecastEntry> call, Response<ForecastEntry> response) {
                if (!response.isSuccessful()) {
                    Log.i("Fail", "Response is not successful");
                    onFailure.callback("Failed! Response is not successful");
                    return;
                }
                if (response.body() != null) {
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
    }

    public interface OnFailure {
        void callback(String message);
    }

    private boolean isFetchCurrentNeeded(ZonedDateTime lastFetchedTime) {
        ZonedDateTime thirtyMinutesAgo = ZonedDateTime.now().minusMinutes(30);
        return lastFetchedTime.isBefore(thirtyMinutesAgo);
    }
}
