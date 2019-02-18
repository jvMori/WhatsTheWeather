package com.example.jvmori.myweatherapp.architectureComponents.ui.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.SearchView;

import com.example.jvmori.myweatherapp.MainActivity;
import com.example.jvmori.myweatherapp.R;
import com.example.jvmori.myweatherapp.architectureComponents.data.db.entity.forecast.ForecastEntry;
import com.example.jvmori.myweatherapp.architectureComponents.ui.view.adapters.SearchResultsAdapter;
import com.example.jvmori.myweatherapp.architectureComponents.ui.viewModel.CurrentWeatherViewModel;
import com.example.jvmori.myweatherapp.architectureComponents.ui.viewModel.SearchViewModel;
import com.example.jvmori.myweatherapp.utils.ItemClicked;
import com.example.jvmori.myweatherapp.architectureComponents.ui.view.adapters.LocationAdapter;

import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


public class SearchActivity extends AppCompatActivity implements ItemClicked {

    private RecyclerView recyclerView, searchResultsRv;
    private RecyclerView.Adapter myAdapter;
    private ImageView backBtn;
    private SearchView searchView;
    private Context context;
    private SearchViewModel searchViewModel;
    private SearchResultsAdapter searchAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        setContentView(R.layout.activity_search);
        searchView = findViewById(R.id.searchField);
        searchResultsRv = findViewById(R.id.searchResults);
        String hint = (String) getText(R.string.search_hint);
        searchViewModel = ViewModelProviders.of(this).get(SearchViewModel.class);
        setSearchResultView();
        searchView.setQueryHint(hint);
        searchView.setOnQueryTextFocusChangeListener((v, hasFocus) -> {
            if (hasFocus){
                recyclerView.setVisibility(View.GONE);
                searchResultsRv.setVisibility(View.VISIBLE);
            }
        });
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                searchViewModel(s.trim());
                searchView.setQuery("", false);
                searchView.clearFocus();
                searchView.setIconified(true);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                searchViewModel(query.trim());
                return true;
            }
        });

        searchView.setOnCloseListener(() -> {
            recyclerView.setVisibility(View.VISIBLE);
            searchResultsRv.setVisibility(View.GONE);
            searchView.setQuery("", false);
            searchView.clearFocus();
            searchView.setIconified(true);
            return false;
        });
        createUi();
    }

    private void searchViewModel(String query) {
        searchViewModel.getResultsForCity(query).observe(this, (searchResponse -> {
            searchAdapter.setSearchedResult(searchResponse);
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

    private void setSearchResultView() {
        searchAdapter = new SearchResultsAdapter();
        searchResultsRv.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        searchResultsRv.setHasFixedSize(true);
        searchResultsRv.setAdapter(searchAdapter);
    }

    @Override
    public void onItemClicked(int index) {
        BackToMainActivity(index);
    }

    @Override
    public void onLongPress(int index) {

    }

}
