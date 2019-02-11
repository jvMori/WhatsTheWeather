package com.example.jvmori.myweatherapp.architectureComponents.ui.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.jvmori.myweatherapp.MainActivity;
import com.example.jvmori.myweatherapp.R;
import com.example.jvmori.myweatherapp.data.WeatherData;
import com.example.jvmori.myweatherapp.model.Locations;
import com.example.jvmori.myweatherapp.utils.Contains;
import com.example.jvmori.myweatherapp.utils.ItemClicked;
import com.example.jvmori.myweatherapp.utils.OnErrorResponse;
import com.example.jvmori.myweatherapp.utils.SaveManager;
import com.example.jvmori.myweatherapp.utils.WeatherAsyncResponse;
import com.example.jvmori.myweatherapp.architectureComponents.ui.view.adapters.LocationAdapter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


public class SearchActivity extends AppCompatActivity implements ItemClicked{

    RecyclerView recyclerView;
    RecyclerView.Adapter myAdapter;
    ImageView backBtn;
    SearchView searchView;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        setContentView(R.layout.activity_search);
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
        myAdapter = new LocationAdapter(MainActivity.weathers, this);
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
                if (Contains.containsName(MainActivity.locations, locationData.getId()) == -1){
                    MainActivity.locations.add(locationData);
                    SaveManager.saveData(context, MainActivity.locations);
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
