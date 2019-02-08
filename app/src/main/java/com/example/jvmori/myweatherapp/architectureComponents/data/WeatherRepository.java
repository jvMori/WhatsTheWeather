package com.example.jvmori.myweatherapp.architectureComponents.data;

import com.example.jvmori.myweatherapp.architectureComponents.data.network.WeatherNetworkDataSource;
import com.example.jvmori.myweatherapp.architectureComponents.data.network.response.CurrentWeatherResponse;

import java.time.ZonedDateTime;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.functions.Action;



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

//    public LiveData<CurrentWeather> getCurrentWeather(String localization, String lang){
//        final MutableLiveData<CurrentWeather> data = new MutableLiveData<>();
//       if (initWeatherData() != null)
//           initWeatherData().subscribe(new io.reactivex.Observer<CurrentWeatherResponse>() {
//               @Override
//               public void onSubscribe(Disposable d) {
//
//               }
//
//               @Override
//               public void onNext(CurrentWeatherResponse currentWeatherResponse) {
//                   data.postValue(currentWeatherResponse.getCurrent());
//               }
//
//               @Override
//               public void onError(Throwable e) {
//
//               }
//
//               @Override
//               public void onComplete() {
//
//               }
//           })
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
}
