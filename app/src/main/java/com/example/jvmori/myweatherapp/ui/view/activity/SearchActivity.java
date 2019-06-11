package com.example.jvmori.myweatherapp.ui.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.SearchView;

import com.example.jvmori.myweatherapp.MainActivity;
import com.example.jvmori.myweatherapp.R;
import com.example.jvmori.myweatherapp.data.db.entity.forecast.ForecastEntry;
import com.example.jvmori.myweatherapp.ui.view.adapters.SearchResultsAdapter;
import com.example.jvmori.myweatherapp.ui.viewModel.WeatherViewModel;
import com.example.jvmori.myweatherapp.ui.viewModel.SearchViewModel;
import com.example.jvmori.myweatherapp.util.Const;
import com.example.jvmori.myweatherapp.util.WeatherParameters;
import com.example.jvmori.myweatherapp.ui.view.adapters.LocationAdapter;


import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


public class SearchActivity extends AppCompatActivity implements LocationAdapter.IOnClickListener, LocationAdapter.ILongClickListener {

    private RecyclerView recyclerView, searchResultsRv;
    private LocationAdapter locationsAdapter;
    private SearchView searchView;
    private SearchViewModel searchViewModel;
    private SearchResultsAdapter searchAdapter;
    private WeatherViewModel weatherViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        searchView = findViewById(R.id.searchField);
        searchResultsRv = findViewById(R.id.searchResults);
        String hint = (String) getText(R.string.search_hint);
        searchViewModel = ViewModelProviders.of(this).get(SearchViewModel.class);
        currentWeatherViewModel();
        setSearchResultView();
        searchView.setQueryHint(hint);
        searchView.setOnQueryTextFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                recyclerView.setVisibility(View.GONE);
                searchResultsRv.setVisibility(View.VISIBLE);
            } else {
                recyclerView.setVisibility(View.VISIBLE);
                searchResultsRv.setVisibility(View.GONE);
            }
        });
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                getWeatherForCity(s.trim());
                searchView.setQuery("", false);
                searchView.clearFocus();
                searchView.setIconified(true);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String query) {

                return true;
            }
        });

        searchView.setOnCloseListener(() -> {
            searchView.clearFocus();
            return true;
        });
    }

    private void getWeatherForCity(String query) {
        if (query != null && query.length() > 3) {
           // weatherViewModel.fetchWeather(new WeatherParameters(query, false, Const.FORECAST_DAYS), null);
            weatherViewModel.getWeather().observe(this, result -> {
                if (result != null) {
                    locationsAdapter.addForecastAndNotifyAdapter(result);
                    recyclerView.setVisibility(View.VISIBLE);
                    searchResultsRv.setVisibility(View.GONE);
                }
            });
        }
    }

    private void currentWeatherViewModel() {
        weatherViewModel = ViewModelProviders.of(this).get(WeatherViewModel.class);
        weatherViewModel.allForecastsFromDb();
        weatherViewModel.getAllWeather().observe(this, this::setupLocationAdapter);
    }

    private void setupLocationAdapter(List<ForecastEntry> responses) {
        recyclerView = findViewById(R.id.rvSearch);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        locationsAdapter = new LocationAdapter(responses, this, this);
        recyclerView.setAdapter(locationsAdapter);
    }

    void BackToMainActivity(int position) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("position", position);
        startActivity(intent);
    }

    private void setSearchResultView() {
        searchAdapter = new SearchResultsAdapter();
        searchResultsRv.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        searchResultsRv.setHasFixedSize(true);
        searchResultsRv.setAdapter(searchAdapter);
    }

    @Override
    public void callback(int position) {
        BackToMainActivity(position);
    }

    @Override
    public void onLongClick(String location) {
        weatherViewModel.deleteWeather(location);
        locationsAdapter.notifyDataSetChanged();
    }

}
