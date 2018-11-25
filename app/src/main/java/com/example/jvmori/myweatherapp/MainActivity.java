package com.example.jvmori.myweatherapp;

import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutCompat;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jvmori.myweatherapp.data.WeatherData;
import com.example.jvmori.myweatherapp.model.Locations;
import com.example.jvmori.myweatherapp.utils.OnErrorResponse;
import com.example.jvmori.myweatherapp.utils.WeatherAsyncResponse;
import com.example.jvmori.myweatherapp.view.SlidePagerAdapter;

import org.json.JSONArray;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {
    public static ArrayList<Locations> locations;

    private SlidePagerAdapter slidePagerAdapter;
    private ViewPager viewPager;
    LinearLayout layoutDots;
    int mDotCount;
    ImageView[] dots;
    ImageView ivSearch, ivMarker;
    TextView tvLocalization;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //load saved locations

        tvLocalization = findViewById(R.id.tvLocalization);
        viewPager = findViewById(R.id.ViewPager);
        layoutDots = findViewById(R.id.layoutDots);
        ivSearch = findViewById(R.id.ivSearch);
        ivMarker = findViewById(R.id.ivMarker);
        ivSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SearchActivity(view);
            }
        });

        if (locations == null || locations.isEmpty()){
            locations = new ArrayList<>(); //create new one or load from prefs
            WeatherData weatherData = new WeatherData();
            weatherData.getResponse(new WeatherAsyncResponse() {
                @Override
                public void processFinished(Locations locationData) {
                    locations.add(locationData); //add new one if already doesn't exist
                    SetData(locations);
                    }}, new OnErrorResponse() {
                @Override
                public void displayErrorMessage(String message) {
                    Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
                    }}, "Cracow");
        } else {
            SetData(locations);
        }

    }

    private void SearchActivity(View view){
        Intent intent = new Intent(this, SearchActivity.class);
        startActivity(intent);
    }

    private void SetData(ArrayList<Locations> data){
        slidePagerAdapter = new SlidePagerAdapter(this, getSupportFragmentManager(), data);
        viewPager.setAdapter(slidePagerAdapter);
        viewPager.setCurrentItem(getIntent().getIntExtra("position",0));
        setUiViewPager();
    }

    void setUiViewPager(){
        mDotCount = slidePagerAdapter.getCount();
        dots = new ImageView[mDotCount];

        for (int i = 0; i < mDotCount; i++) {
            dots[i] = new ImageView(this);
            dots[i].setImageResource(R.drawable.dotinactive);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayoutCompat.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);
            dots[i].setLayoutParams(layoutParams);
            layoutParams.setMargins(5,0,5,0);
            layoutParams.gravity = Gravity.CENTER;

            layoutDots.addView(dots[i], layoutParams);
        }
        dots[viewPager.getCurrentItem()].setImageResource(R.drawable.dotactive);
        changeActiveCityName(viewPager.getCurrentItem());
        geoLocMarkerVisibility(viewPager.getCurrentItem());
        viewPager.addOnPageChangeListener(changeListener);
    }

    ViewPager.OnPageChangeListener changeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int i, float v, int i1) {

        }

        @Override
        public void onPageSelected(int i) {
            changeActiveDot(i);
            changeActiveCityName(i);
            geoLocMarkerVisibility(i);
        }

        @Override
        public void onPageScrollStateChanged(int i) {

        }
    };

    private void changeActiveDot(int position){
        for (int i = 0; i < mDotCount; i++) {
            dots[i].setImageResource(R.drawable.dotinactive);
        }
        dots[position].setImageResource(R.drawable.dotactive);
    }

    private void changeActiveCityName(int position){
        tvLocalization.setText(locations.get(position).getId());
    }

    private void geoLocMarkerVisibility(int position){
        if (position == 0)
            ivMarker.setVisibility(View.VISIBLE);
        else{
            ivMarker.setVisibility(View.INVISIBLE);
        }
    }
}
