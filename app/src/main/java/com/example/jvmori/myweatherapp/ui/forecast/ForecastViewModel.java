package com.example.jvmori.myweatherapp.ui.forecast;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.jvmori.myweatherapp.data.forecast.ForecastRepository;
import com.example.jvmori.myweatherapp.data.forecast.Forecasts;
import com.example.jvmori.myweatherapp.ui.Resource;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class ForecastViewModel extends ViewModel {
    private ForecastRepository repository;
    private CompositeDisposable disposable = new CompositeDisposable();
    private MutableLiveData<Resource<Forecasts>> _forecast = new MutableLiveData<>();
    public LiveData<Resource<Forecasts>> getForecast = _forecast;

    @Inject
    public ForecastViewModel(ForecastRepository repository) {
        this.repository = repository;
    }

    public void fetchForecast(String cityName) {
        _forecast.setValue(Resource.loading(null));
        disposable.add(
                repository.getForecast(cityName)
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeOn(Schedulers.io())
                        .subscribe(
                                success -> _forecast.setValue(Resource.success(success)),
                                error -> _forecast.setValue(Resource.error(error.getMessage(), null))
                        )
        );
    }
}
