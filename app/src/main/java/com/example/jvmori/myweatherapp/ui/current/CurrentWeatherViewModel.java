package com.example.jvmori.myweatherapp.ui.current;

import android.location.Location;
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

import io.reactivex.Observer;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public class CurrentWeatherViewModel extends ViewModel {

    private CurrentWeatherRepository repository;
    private static ILoadImage imageLoader;
    private CompositeDisposable disposable = new CompositeDisposable();

    private MutableLiveData<Resource<CurrentWeatherUI>> _weather = new MutableLiveData<>();

    public LiveData<Resource<CurrentWeatherUI>> getCurrentWeather() {
        return _weather;
    }

    @Inject
    public CurrentWeatherViewModel(CurrentWeatherRepository repository, ILoadImage imageLoader) {
        this.repository = repository;
        CurrentWeatherViewModel.imageLoader = imageLoader;
    }

    public void fetchCurrentWeather(String city) {
        //TODO: check if location has changed
        repository.getCurrentWeatherByCity(city)
                .toObservable()
                .subscribe(currentWeatherUIObserver);
    }

    public void fetchCurrentWeatherByGeographic(Location location) {
        repository.getCurrentWeatherByGeographic(location)
                .toObservable()
                .subscribe(currentWeatherUIObserver);
    }

    private Observer<CurrentWeatherUI> currentWeatherUIObserver = new Observer<CurrentWeatherUI>() {
        @Override
        public void onSubscribe(Disposable d) {
            disposable.add(d);
            _weather.setValue(Resource.loading(null));
        }

        @Override
        public void onNext(CurrentWeatherUI currentWeatherUI) {
            _weather.setValue(Resource.success(currentWeatherUI));
        }

        @Override
        public void onError(Throwable e) {
            _weather.setValue(Resource.error(e.getMessage(), null));
        }

        @Override
        public void onComplete() {

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
