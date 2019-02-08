package com.example.jvmori.myweatherapp.architectureComponents.data;

import com.example.jvmori.myweatherapp.architectureComponents.data.network.WeatherNetworkDataSource;
import com.example.jvmori.myweatherapp.architectureComponents.data.network.response.CurrentWeatherResponse;

import java.time.ZonedDateTime;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import io.reactivex.Completable;
import io.reactivex.CompletableObserver;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.schedulers.Schedulers;


public class WeatherRepository
{
    private MutableLiveData<CurrentWeatherResponse> getCurrentWeather;
    private static final Object LOCK = new Object();
    private static WeatherRepository instance;
    private WeatherDao weatherDao;
    private WeatherNetworkDataSource weatherNetworkDataSource;

    private WeatherRepository (WeatherNetworkDataSource weatherNetworkDataSource,
                               WeatherDao weatherDao){
        this.weatherNetworkDataSource = weatherNetworkDataSource;
        this.weatherDao = weatherDao;
        weatherNetworkDataSource.currentWeather.observeForever(new Observer<CurrentWeatherResponse>() {
            @Override
            public void onChanged(CurrentWeatherResponse newCurrentWeatherResponse) {
                    if (newCurrentWeatherResponse != null)
                        persistFetchedCurrentWeather(newCurrentWeatherResponse);
            }
        });
    }

//    public Observable<LiveData<CurrentWeatherResponse>> getCurrentWeather(String localization, String lang){
//        return weatherNetworkDataSource.fetchWeather(localization, lang);
//    }



    public synchronized static WeatherRepository getInstance(WeatherNetworkDataSource weatherNetworkDataSource, WeatherDao weatherDao){
        if (instance == null){
            synchronized (LOCK){
                instance = new WeatherRepository(weatherNetworkDataSource, weatherDao);
            }
        }
        return instance;
    }

    private void persistFetchedCurrentWeather(final CurrentWeatherResponse currentWeatherResponse){
        Completable.fromAction(new Action() {
            @Override
            public void run() throws Exception {
                weatherDao.insert(currentWeatherResponse.getCurrent());
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new CompletableObserver() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onComplete() {

            }

            @Override
            public void onError(Throwable e) {

            }
        });
    }


   private void initWeatherData(){
        if (isFetchCurrentNeeded(ZonedDateTime.now().minusHours(1)))
             weatherNetworkDataSource.fetchWeather("London", "en");
   }

    private boolean isFetchCurrentNeeded(ZonedDateTime lastFetchedTime){
        ZonedDateTime thirtyMinutesAgo = ZonedDateTime.now().minusMinutes(30);
        return lastFetchedTime.isBefore(thirtyMinutesAgo);
    }
}
