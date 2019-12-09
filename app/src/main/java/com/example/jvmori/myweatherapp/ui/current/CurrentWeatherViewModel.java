package com.example.jvmori.myweatherapp.ui.current;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.jvmori.myweatherapp.data.current.CurrentWeatherRepository;
import com.example.jvmori.myweatherapp.data.current.CurrentWeatherUI;
import com.example.jvmori.myweatherapp.ui.Resource;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

public class CurrentWeatherViewModel extends ViewModel {

    private CurrentWeatherRepository repository;
    private CompositeDisposable disposable = new CompositeDisposable();

    private MutableLiveData<Resource<CurrentWeatherUI>> _weather = new MutableLiveData<>();
    public LiveData<Resource<CurrentWeatherUI>> getCurrentWeather() {
        return _weather;
    }

    @Inject
    public CurrentWeatherViewModel(CurrentWeatherRepository repository){
        this.repository = repository;
    }

    public void fetchCurrentWeather(String city){
        _weather.setValue(Resource.loading(null));
        disposable.add(
                repository.getCurrentWeatherByCity(city)
                        .subscribe(
                                success ->
                                        _weather.setValue(Resource.success(success)),
                                error ->
                                        _weather.setValue(Resource.error(error.getMessage(), null))
                        )
        );
    }



    @Override
    protected void onCleared() {
        super.onCleared();
        repository.cleanup();
        disposable.clear();
    }
}
