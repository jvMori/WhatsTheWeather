package com.example.jvmori.myweatherapp.ui.current;

import android.location.Location;
import android.util.Log;
import android.widget.ImageView;

import androidx.databinding.BindingAdapter;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.jvmori.myweatherapp.data.WeatherUI;
import com.example.jvmori.myweatherapp.data.current.CurrentWeatherRepository;
import com.example.jvmori.myweatherapp.data.current.CurrentWeatherUI;
import com.example.jvmori.myweatherapp.data.forecast.ForecastEntity;
import com.example.jvmori.myweatherapp.data.forecast.ForecastRepository;
import com.example.jvmori.myweatherapp.data.forecast.Forecasts;
import com.example.jvmori.myweatherapp.ui.Resource;
import com.example.jvmori.myweatherapp.util.images.ILoadImage;

import java.lang.reflect.Method;
import java.util.List;
import java.util.function.Function;

import javax.inject.Inject;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.BiFunction;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.PublishSubject;

public class CurrentWeatherViewModel extends ViewModel {

    private CurrentWeatherRepository repository;
    private ForecastRepository forecastRepository;
    private static ILoadImage imageLoader;
    private CompositeDisposable disposable = new CompositeDisposable();

    private MutableLiveData<Resource<WeatherUI>> _weather = new MutableLiveData<>();

    public LiveData<Resource<WeatherUI>> getCurrentWeather() {
        return _weather;
    }

    private Observable<WeatherUI> getWeatherUIObservable(Flowable<CurrentWeatherUI> currentWeatherByCity, Flowable<Forecasts> forecast2) {
        return Flowable.zip(
                currentWeatherByCity,
                forecast2,
                (current, forecast) -> createWeather(current, forecast.getForecastList()))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .toObservable();
    }


    @Inject
    public CurrentWeatherViewModel(CurrentWeatherRepository repository,
                                   ForecastRepository forecastRepository,
                                   ILoadImage imageLoader) {
        this.repository = repository;
        this.forecastRepository = forecastRepository;
        CurrentWeatherViewModel.imageLoader = imageLoader;
    }

    public void fetchCurrentWeather(String city) {
        getWeatherUIObservable(repository.getCurrentWeatherByCity(city), forecastRepository.getForecast(city))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(currentWeatherUIObserver);
    }

    public void fetchWeatherByGeographic(Location location) {
        getWeatherUIObservable(repository.getCurrentWeatherByGeographic(location), forecastRepository.getForecastByGeo(location))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(currentWeatherUIObserver);
    }

    private WeatherUI createWeather(CurrentWeatherUI currentWeatherUI, List<ForecastEntity> forecastEntityList) {
        forecastEntityList.remove(0);
        return new WeatherUI(currentWeatherUI, forecastEntityList);
    }

    private Observer<WeatherUI> currentWeatherUIObserver = new Observer<WeatherUI>() {
        @Override
        public void onSubscribe(Disposable d) {
            disposable.add(d);
            _weather.setValue(Resource.loading(null));
        }

        @Override
        public void onNext(WeatherUI currentWeatherUI) {
            _weather.setValue(Resource.success(currentWeatherUI));
        }

        @Override
        public void onError(Throwable e) {
            _weather.setValue(Resource.error(e.getMessage(), null));
        }

        @Override
        public void onComplete() {
            Log.i("Log", "Completed");
        }
    };

    @BindingAdapter("iconImage")
    public static void loadImage(ImageView view, String imageUrl) {
        imageLoader.loadImage(imageUrl, view);
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        repository.cleanup();
        disposable.clear();
    }
}
