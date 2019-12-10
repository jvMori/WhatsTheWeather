package com.example.jvmori.myweatherapp.ui.current;

import android.widget.ImageView;

import androidx.databinding.BindingAdapter;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.jvmori.myweatherapp.data.current.CurrentWeatherRepository;
import com.example.jvmori.myweatherapp.data.current.CurrentWeatherUI;
import com.example.jvmori.myweatherapp.ui.Resource;
import com.example.jvmori.myweatherapp.util.images.ILoadImage;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

public class CurrentWeatherViewModel extends ViewModel {

    private CurrentWeatherRepository repository;
    private static ILoadImage imageLoader;
    private CompositeDisposable disposable = new CompositeDisposable();

    private MutableLiveData<Resource<CurrentWeatherUI>> _weather = new MutableLiveData<>();
    public LiveData<Resource<CurrentWeatherUI>> getCurrentWeather() {
        return _weather;
    }

    @Inject
    public CurrentWeatherViewModel(CurrentWeatherRepository repository, ILoadImage imageLoader){
        this.repository = repository;
        CurrentWeatherViewModel.imageLoader = imageLoader;
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
