package com.example.jvmori.myweatherapp.ui.view.fragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavDirections;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.jvmori.myweatherapp.R;
import com.example.jvmori.myweatherapp.data.network.response.Search;
import com.example.jvmori.myweatherapp.ui.view.adapters.SearchResultsAdapter;
import com.example.jvmori.myweatherapp.ui.viewModel.SearchViewModel;
import com.example.jvmori.myweatherapp.ui.viewModel.ViewModelProviderFactory;
import com.example.jvmori.myweatherapp.ui.viewModel.WeatherViewModel;
import com.example.jvmori.myweatherapp.util.Const;
import com.example.jvmori.myweatherapp.util.WeatherParameters;

import java.util.List;

import javax.inject.Inject;

import dagger.android.support.DaggerFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class SearchFragment extends DaggerFragment implements SearchResultsAdapter.IOnItemClicked {

    private RecyclerView cities, locations;
    private SearchViewModel searchViewModel;
    private WeatherViewModel weatherViewModel;
    @Inject
    ViewModelProviderFactory viewModelProviderFactory;

    public SearchFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        searchViewModel = ViewModelProviders.of(this, viewModelProviderFactory).get(SearchViewModel.class);
        return inflater.inflate(R.layout.fragment_search, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (getActivity() != null){
            weatherViewModel = ViewModelProviders.of(getActivity(), viewModelProviderFactory).get(WeatherViewModel.class);
        }
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SearchView searchView = view.findViewById(R.id.searchField);
        cities = view.findViewById(R.id.cities);
        locations = view.findViewById(R.id.locations);
        searchView.setOnCloseListener(() -> {
            cities.setVisibility(View.GONE);
            locations.setVisibility(View.VISIBLE);
            return false;
        });
        searchViewModel.search(searchView);
        searchViewModel.cities().observe(this, result -> showSugestions(result));
    }

    private void showSugestions(List<Search> searchList) {
        cities.setVisibility(View.VISIBLE);
        locations.setVisibility(View.GONE);
        createCitiesAdapter(searchList);
    }

    private void createCitiesAdapter(List<Search> searchList) {
        SearchResultsAdapter adapter = new SearchResultsAdapter();
        adapter.setSearchedResult(searchList, this);
        cities.setLayoutManager(new LinearLayoutManager(this.getContext(), RecyclerView.VERTICAL, false));
        cities.setAdapter(adapter);
    }


    @Override
    public void onClick(Search item) {
        fetchWeather(item);
        navigateToWeatherFragment();
    }

    private void fetchWeather(Search item) {
        WeatherParameters weatherParameters = new WeatherParameters(
                item.getName(),
                false,
                Const.FORECAST_DAYS
        );
        weatherViewModel.fetchRemote(weatherParameters);
    }

    private void navigateToWeatherFragment(){
        NavDirections directions = SearchFragmentDirections.actionSearchFragmentToWeatherFragment();
        NavHostFragment.findNavController(this).navigate(directions);
    }
}
