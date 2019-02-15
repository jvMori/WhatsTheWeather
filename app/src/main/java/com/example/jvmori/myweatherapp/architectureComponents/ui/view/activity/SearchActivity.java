package com.example.jvmori.myweatherapp.architectureComponents.ui.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.jvmori.myweatherapp.MainActivity;
import com.example.jvmori.myweatherapp.R;
import com.example.jvmori.myweatherapp.architectureComponents.data.db.entity.forecast.ForecastEntry;
import com.example.jvmori.myweatherapp.architectureComponents.data.network.response.Search;
import com.example.jvmori.myweatherapp.architectureComponents.ui.view.adapters.SearchResultsAdapter;
import com.example.jvmori.myweatherapp.architectureComponents.ui.viewModel.CurrentWeatherViewModel;
import com.example.jvmori.myweatherapp.architectureComponents.ui.viewModel.SearchViewModel;
import com.example.jvmori.myweatherapp.data.WeatherData;
import com.example.jvmori.myweatherapp.model.Locations;
import com.example.jvmori.myweatherapp.utils.Contains;
import com.example.jvmori.myweatherapp.utils.ItemClicked;
import com.example.jvmori.myweatherapp.utils.OnErrorResponse;
import com.example.jvmori.myweatherapp.utils.SaveManager;
import com.example.jvmori.myweatherapp.utils.WeatherAsyncResponse;
import com.example.jvmori.myweatherapp.architectureComponents.ui.view.adapters.LocationAdapter;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


public class SearchActivity extends AppCompatActivity implements ItemClicked {

    private RecyclerView recyclerView, searchResltsRv;
    private RecyclerView.Adapter myAdapter;
    private ImageView backBtn;
    private SearchView searchView;
    private Context context;
    private SearchViewModel searchViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        setContentView(R.layout.activity_search);
        searchView = findViewById(R.id.searchField);
        searchResltsRv = findViewById(R.id.searchResults);
        String hint = (String) getText(R.string.search_hint);
        searchViewModel = ViewModelProviders.of(this).get(SearchViewModel.class);
        searchView.setQueryHint(hint);
        searchView.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                recyclerView.setVisibility(View.GONE);
                searchResltsRv.setVisibility(View.VISIBLE);
            } else {
                recyclerView.setVisibility(View.VISIBLE);
                searchResltsRv.setVisibility(View.GONE);
                searchView.setQuery("", false);
                searchView.clearFocus();
                searchView.setIconified(true);
            }
        });
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                searchView.setQuery("", false);
                searchView.clearFocus();
                searchView.setIconified(true);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                searchViewModel(query.trim());
                return false;
            }
        });
        createUi();
    }

    private void searchViewModel(String query) {
        searchViewModel.getResultsForCity(query).observe(this, (searchResponse -> {
            setSearchResultView(searchResponse);
        }));
    }

    private void createUi() {
        CurrentWeatherViewModel viewModel = ViewModelProviders.of(this).get(CurrentWeatherViewModel.class);
        viewModel.getAllForecast().observe(this, this::setupLocationAdapter);
    }

    private void setupLocationAdapter(List<ForecastEntry> responses) {
        recyclerView = findViewById(R.id.rvSearch);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        myAdapter = new LocationAdapter(responses, this);
        recyclerView.setAdapter(myAdapter);
    }

    void BackToMainActivity(int position) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("position", position);
        startActivity(intent);
    }

    void searchWeather(String location) {
        WeatherData weatherData = new WeatherData();
        weatherData.getResponse(locationData -> {
            if (Contains.containsName(MainActivity.locations, locationData.getId()) == -1) {
                MainActivity.locations.add(locationData);
                SaveManager.saveData(context, MainActivity.locations);
                myAdapter.notifyDataSetChanged();
            } else {
                Toast.makeText(SearchActivity.this, "City already exist!", Toast.LENGTH_SHORT).show();
            }
            BackToMainActivity(MainActivity.locations.size() - 1);
        }, message -> Toast.makeText(SearchActivity.this, message, Toast.LENGTH_SHORT).show(), location);

    }

    private void setSearchResultView(List<Search> results) {
        SearchResultsAdapter adapter = new SearchResultsAdapter(results);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onItemClicked(int index) {
        BackToMainActivity(index);
    }

    @Override
    public void onLongPress(int index) {
        MainActivity.locations.remove(index);
        SaveManager.saveData(context, MainActivity.locations);
        myAdapter.notifyDataSetChanged();
    }

}
