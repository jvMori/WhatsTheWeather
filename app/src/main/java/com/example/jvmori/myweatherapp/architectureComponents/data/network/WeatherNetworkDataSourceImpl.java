package com.example.jvmori.myweatherapp.architectureComponents.data.network;

import com.example.jvmori.myweatherapp.architectureComponents.data.network.response.CurrentWeatherResponse;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import io.reactivex.Observer;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class WeatherNetworkDataSourceImpl implements WeatherNetworkDataSource{
    private MutableLiveData<CurrentWeatherResponse> _currentWeather =  new MutableLiveData<>();

    public LiveData<CurrentWeatherResponse> currentWeather(){
        return _currentWeather;
    }

    public void fetchWeather(String location, String lang){
        ApixuApi service = ApixuApiCall.init();
        service.getCurrentWeather(location, lang)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<CurrentWeatherResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(CurrentWeatherResponse currentWeatherResponse) {
                        _currentWeather.postValue(currentWeatherResponse);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }
                });
    }
}
