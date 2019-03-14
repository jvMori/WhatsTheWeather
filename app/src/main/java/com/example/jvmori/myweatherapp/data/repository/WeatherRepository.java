package com.example.jvmori.myweatherapp.data.repository;

import android.app.Application;
import com.example.jvmori.myweatherapp.AppExecutors;
import com.example.jvmori.myweatherapp.data.db.ForecastDao;
import com.example.jvmori.myweatherapp.data.db.WeatherDatabase;
import com.example.jvmori.myweatherapp.data.db.entity.forecast.ForecastEntry;
import com.example.jvmori.myweatherapp.data.network.WeatherNetworkDataSource;
import com.example.jvmori.myweatherapp.data.network.WeatherNetworkDataSourceImpl;
import com.example.jvmori.myweatherapp.data.network.response.Search;
import com.example.jvmori.myweatherapp.util.Const;
import com.example.jvmori.myweatherapp.util.WeatherParameters;
import io.reactivex.Maybe;
import io.reactivex.Observable;
import java.util.List;
import androidx.lifecycle.LiveData;
import io.reactivex.schedulers.Schedulers;

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

    public Observable<ForecastEntry> getWeather(String location, boolean isDeviceLoc, String days) {
        return Maybe.concat(getWeatherFromDb(location), getWeatherRemote(location, isDeviceLoc, days))
                .filter(it -> isUpToDate(it.getTimestamp()))
                .toObservable();
    }

    private Maybe<ForecastEntry> getWeatherFromDb(String location) {
        return forecastDao.getWeather(location)
                .subscribeOn(Schedulers.io());
    }

    private Maybe<ForecastEntry> getWeatherRemote(String location, boolean isDeviceLoc, String days) {
        return weatherNetworkDataSource.fetchWeather(
                new WeatherParameters(location, isDeviceLoc, days))
                .subscribeOn(Schedulers.io())
                .doOnSuccess(
                        it -> {
                            it.isDeviceLocation = isDeviceLoc;
                            persistForecast(it);
                        }
                );
    }

    public LiveData<List<ForecastEntry>> getAllForecast() {
        return forecastDao.getAllWeather();
    }

    public LiveData<List<ForecastEntry>> allForecastsWithoutLoc() {
        return forecastDao.allForecastsExceptForDeviceLocation();
    }

    private void persistForecast(ForecastEntry newForecastEntry) {
        newForecastEntry.setTimestamp(System.currentTimeMillis());
//TODO: delete old device location
        forecastDao.insert(newForecastEntry);
    }

    public void deleteWeather(String location) {
        executors.diskIO().execute(() -> {
            forecastDao.deleteForecast(location);
        });
    }

    public LiveData<List<Search>> getResultsForCity(String cityName) {
        return weatherNetworkDataSource.searchCity(cityName);
    }

    private boolean isUpToDate(Long lastUpdate) {
        return System.currentTimeMillis() - lastUpdate < Const.STALE_MS;
    }
}
