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
import com.example.jvmori.myweatherapp.utils.ItemClicked;
import com.example.jvmori.myweatherapp.utils.OnErrorResponse;
import com.example.jvmori.myweatherapp.utils.WeatherAsyncResponse;
import com.example.jvmori.myweatherapp.view.LocationAdapter;

import java.util.List;

public class SearchActivity extends AppCompatActivity implements ItemClicked{

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
                BackToMainActivity(MainActivity.locations.size() - 1);
            }
        });
        searchView = findViewById(R.id.searchField);
        String hint = (String) getText(R.string.search_hint);
        searchView.setQueryHint(hint);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                searchWeather(searchView.getQuery().toString().trim());
                searchView.setQuery("", false);
                searchView.clearFocus();
                searchView.setIconified(true);
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

    void BackToMainActivity(int position){
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("position",position);
        startActivity(intent);
    }

    void searchWeather(String location){
        WeatherData weatherData = new WeatherData();
        weatherData.getResponse(new WeatherAsyncResponse() {
            @Override
            public void processFinished(Locations locationData) {
                if (containsName(MainActivity.locations, locationData.getId()) == -1){
                    MainActivity.locations.add(locationData);
                    myAdapter.notifyDataSetChanged();
                }else{
                    Toast.makeText(SearchActivity.this, "City already exist!", Toast.LENGTH_SHORT).show();
                }
                BackToMainActivity(MainActivity.locations.size() -1);
            }
        }, new OnErrorResponse() {
            @Override
            public void displayErrorMessage(String message) {
                Toast.makeText(SearchActivity.this, message, Toast.LENGTH_SHORT).show();
            }
        }, location);

    }

    public int containsName(final List<Locations> list, final String name){
        if (list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
                if (list.get(i) != null && list.get(i).getId().equals(name))
                    return i;
            }
        }
        return -1;
    }

    @Override
    public void onItemClicked(int index) {
        BackToMainActivity(index);
    }
}
