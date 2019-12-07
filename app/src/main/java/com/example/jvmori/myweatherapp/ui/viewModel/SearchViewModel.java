package com.example.jvmori.myweatherapp.ui.viewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.jvmori.myweatherapp.data.network.response.Search;
import com.example.jvmori.myweatherapp.ui.Resource;

import java.util.List;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.subjects.PublishSubject;

public class SearchViewModel extends ViewModel {

    private MutableLiveData<Resource<List<Search>>> _cities = new MutableLiveData<>();
    private CompositeDisposable disposable = new CompositeDisposable();
    public LiveData<Resource<List<Search>>> cities() {return _cities;}
    final PublishSubject<String> subject = PublishSubject.create();

    public void subjectOnNext(String query){
        subject.onNext(query);
    }

    public void listenForSearchChanges(){
//        disposable.add(
//                subject.debounce(300, TimeUnit.MILLISECONDS)
//                        .filter( result -> !result.isEmpty())
//                        .distinctUntilChanged()
//                        .switchMap(query ->
//                                weatherRepository.getResultsForCity(query)
//                        )
//                        .subscribeOn(Schedulers.io())
//                        .observeOn(AndroidSchedulers.mainThread())
//                        .subscribe(
//                                searches -> {
//                                    if (searches == null)  _cities.setValue(Resource.loading(null));
//                                    _cities.setValue(Resource.success(searches));
//                                },
//                                throwable -> {
//                                    _cities.setValue(Resource.error(throwable.getMessage(), null));
//                                    Log.i("Error", "Something went wrong");
//                                }
//                        )
//        );
    }

    @Override
    protected void onCleared() {
        disposable.clear();
    }
}
