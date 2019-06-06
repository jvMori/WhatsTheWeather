package com.example.jvmori.myweatherapp.ui.viewModel;

import android.util.Log;
import com.example.jvmori.myweatherapp.data.network.response.Search;
import com.example.jvmori.myweatherapp.data.repository.WeatherRepository;
import java.util.List;
import java.util.concurrent.TimeUnit;
import androidx.appcompat.widget.SearchView;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import javax.inject.Inject;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.PublishSubject;

public class SearchViewModel extends ViewModel {

    private WeatherRepository weatherRepository;
    private MutableLiveData<List<Search>> _cities = new MutableLiveData<>();
    private CompositeDisposable disposable = new CompositeDisposable();
    public LiveData<List<Search>> cities() {return _cities;}

    @Inject
    public SearchViewModel(WeatherRepository weatherRepository){
        this.weatherRepository = weatherRepository;
    }

    private Observable<String> fromView(SearchView searchView) {
        final PublishSubject<String> subject = PublishSubject.create();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                subject.onComplete();
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
