package com.example.jvmori.myweatherapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.jvmori.myweatherapp.data.WeatherData;
import com.example.jvmori.myweatherapp.model.Locations;
import com.example.jvmori.myweatherapp.utils.WeatherAsyncResponse;
import com.example.jvmori.myweatherapp.view.LocationAdapter;

public class SearchActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    RecyclerView.Adapter myAdapter;
    ImageView backBtn;
    SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        backBtn = findViewById(R.id.backBtn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BackToMainActivity();
            }
        });
        searchView = findViewById(R.id.searchField);
        String hint = (String) getText(R.string.search_hint);
        searchView.setQueryHint(hint);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                Toast.makeText(SearchActivity.this, searchView.getQuery().toString().trim(), Toast.LENGTH_SHORT).show();

                searchWeather(searchView.getQuery().toString().trim());

                searchView.setQuery("", false);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });

        recyclerView = findViewById(R.id.rvSearch);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        myAdapter = new LocationAdapter(MainActivity.locations, this);
        recyclerView.setAdapter(myAdapter);
    }

    void BackToMainActivity(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    void searchWeather(String location){
        WeatherData weatherData = new WeatherData();
        weatherData.getResponse(new WeatherAsyncResponse() {
            @Override
            public void processFinished(Locations locationData) {
                MainActivity.locations.add(locationData);
                myAdapter.notifyDataSetChanged();
                BackToMainActivity();
            }
        }, location);

    }


}
