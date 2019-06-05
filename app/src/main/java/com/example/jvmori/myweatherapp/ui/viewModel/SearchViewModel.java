package com.example.jvmori.myweatherapp.ui.viewModel;

import android.app.Application;
import android.util.Log;
import android.view.View;

import com.example.jvmori.myweatherapp.application.WeatherApplication;
import com.example.jvmori.myweatherapp.data.network.response.Search;
import com.example.jvmori.myweatherapp.data.repository.WeatherRepository;

import java.util.List;
import java.util.concurrent.TimeUnit;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.recyclerview.widget.RecyclerView;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.PublishSubject;

public class SearchViewModel extends AndroidViewModel {
    private WeatherRepository weatherRepository;
    private MutableLiveData<List<Search>> _cities = new MutableLiveData<>();
    private CompositeDisposable disposable = new CompositeDisposable();

    public LiveData<List<Search>> cities() {return _cities;}

    public SearchViewModel(@NonNull Application application) {
        super(application);
        WeatherApplication weatherApplication = (WeatherApplication) application;
        weatherRepository = weatherApplication.weatherRepository();
    }

    private Observable<String> fromView(SearchView searchView) {
        final PublishSubject<String> subject = PublishSubject.create();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                subject.onComplete();
                searchView.setQuery("", false);
                searchView.setIconifiedByDefault(true);
                searchView.clearFocus();
                return true;
            }
            @Override
            public boolean onQueryTextChange(String text) {
                subject.onNext(text);
                return true;
            }
        });
        return subject;
    }

    public void search(SearchView searchView){
        disposable.add(
                fromView(searchView)
                        .debounce(300, TimeUnit.MILLISECONDS)
                        .filter( result -> !result.isEmpty())
                        .distinctUntilChanged()
                        .switchMap(query ->
                                weatherRepository.getResultsForCity(query)
                        )
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                searches -> _cities.setValue(searches),
                                throwable -> Log.i("Error", "Something went wrong")
                        )
        );
    }

    @Override
    protected void onCleared() {
        disposable.clear();
    }
}
