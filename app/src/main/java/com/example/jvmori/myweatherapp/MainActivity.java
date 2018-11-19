package com.example.jvmori.myweatherapp;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    private SlidePagerAdapter slidePagerAdapter;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewPager = findViewById(R.id.ViewPager);
        SetData();
    }

    private void SetData(){
        slidePagerAdapter = new SlidePagerAdapter(this, getSupportFragmentManager(), ForecastList.locList);
        viewPager.setAdapter(slidePagerAdapter);
    }
}
