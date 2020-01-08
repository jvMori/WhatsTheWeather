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

import java.io.Flushable;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.PublishSubject;
import io.reactivex.subjects.Subject;

public class CurrentWeatherViewModel extends ViewModel {

    private CurrentWeatherRepository repository;
    private ForecastRepository forecastRepository;
    private static ILoadImage imageLoader;
    private CompositeDisposable disposable = new CompositeDisposable();

    private MutableLiveData<Resource<WeatherUI>> _weather = new MutableLiveData<>();
    private MutableLiveData<Resource<List<CurrentWeatherUI>>> _allWeather = new MutableLiveData<>();
    private Subject<String> citySubject = PublishSubject.create();

    public LiveData<Resource<WeatherUI>> getCurrentWeather() {
        return _weather;
    }

    public LiveData<Resource<List<CurrentWeatherUI>>> getAllWeather() {
        return _allWeather;
    }

    @Inject
    public CurrentWeatherViewModel(CurrentWeatherRepository repository,
                                   ForecastRepository forecastRepository,
                                   ILoadImage imageLoader) {
        this.repository = repository;
        this.forecastRepository = forecastRepository;
        CurrentWeatherViewModel.imageLoader = imageLoader;
    }

    public void searchCity(String query) {
        citySubject.onNext(query);
    }

    public void observeSearchCityResults() {
        citySubject
                .filter(query -> !query.isEmpty())
                .switchMap(this::getObservableWeatherForCity)
                .doOnNext(result ->
                        Log.i("data", result.toString()))
                .subscribe(currentWeatherUIObserver);
    }

    public void fetchAllWeather() {
        repository.getAllWeather().subscribe(
                allWeatherObserver
        );
    }

    private Observer<List<CurrentWeatherUI>> allWeatherObserver = new Observer<List<CurrentWeatherUI>>() {
        @Override
        public void onSubscribe(Disposable d) {
            disposable.add(d);
            _allWeather.setValue(Resource.loading(null));
        }

        @Override
        public void onNext(List<CurrentWeatherUI> listResource) {
            _allWeather.setValue(Resource.success(listResource));
        }

        @Override
        public void onError(Throwable e) {
            _allWeather.setValue(Resource.error(e.getLocalizedMessage(), null));
        }

        @Override
        public void onComplete() {

        }
    };

    private Observable<WeatherUI> getWeatherUIObservable(Flowable<CurrentWeatherUI> currentWeatherByCity, Flowable<Forecasts> forecast2) {
        return Flowable.zip(
                currentWeatherByCity,
                forecast2,
                (current, forecast) -> createWeather(current, forecast.getForecastList()))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .toObservable();
    }

    private Observable<WeatherUI> getObservableWeatherForCity(String city) {
        return getWeatherUIObservable(
                repository.getCurrentWeatherByCity(city),
                forecastRepository.getForecast(city))
                .doOnError(error -> Log.i("error", error.getLocalizedMessage()))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io());
    }

    public void fetchWeatherForCity(String city) {
        getObservableWeatherForCity(city).subscribe(currentWeatherUIObserver);
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
