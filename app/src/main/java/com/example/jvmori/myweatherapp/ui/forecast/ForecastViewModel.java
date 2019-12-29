package com.example.jvmori.myweatherapp.ui.forecast;

import android.location.Location;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.jvmori.myweatherapp.data.forecast.ForecastEntity;
import com.example.jvmori.myweatherapp.data.forecast.ForecastRepository;
import com.example.jvmori.myweatherapp.data.forecast.Forecasts;
import com.example.jvmori.myweatherapp.ui.Resource;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class ForecastViewModel extends ViewModel {
    private ForecastRepository repository;
    private CompositeDisposable disposable = new CompositeDisposable();
    private MutableLiveData<Resource<List<ForecastEntity>>> _forecast = new MutableLiveData<>();
    public LiveData<Resource<List<ForecastEntity>>> getForecast = _forecast;

    @Inject
    public ForecastViewModel(ForecastRepository repository) {
        this.repository = repository;
    }

    public void fetchForecast(String cityName) {
        repository.getForecast(cityName)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .map(Forecasts::getForecastList)
                .toObservable()
                .subscribe(
                        forecastObserver
                );
    }

    public void fetchForecastByGeo(Location location) {
        repository.getForecastByGeo(location)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .map(Forecasts::getForecastList)
                .toObservable()
                .subscribe(forecastObserver);
    }

    private Observer<List<ForecastEntity>> forecastObserver = new Observer<List<ForecastEntity>>() {

        @Override
        public void onSubscribe(Disposable d) {
            _forecast.setValue(Resource.loading(null));
            disposable.add(d);
        }

        @Override
        public void onNext(List<ForecastEntity> forecastEntities) {
            _forecast.setValue(Resource.success(forecastEntities));
        }

        @Override
        public void onError(Throwable e) {
            _forecast.setValue(Resource.error(e.getLocalizedMessage(), null));
        }

        @Override
        public void onComplete() {

        }
    };

}
