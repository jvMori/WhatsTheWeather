package com.example.jvmori.myweatherapp;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutCompat;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.LinearLayout;


public class MainActivity extends AppCompatActivity {

    private SlidePagerAdapter slidePagerAdapter;
    private ViewPager viewPager;
    LinearLayout layoutDots;
    int mDotCount;
    ImageView[] dots;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewPager = findViewById(R.id.ViewPager);
        layoutDots = findViewById(R.id.layoutDots);
        SetData();
    }

    private void SetData(){
        slidePagerAdapter = new SlidePagerAdapter(this, getSupportFragmentManager(), ForecastList.locList);
        viewPager.setAdapter(slidePagerAdapter);
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
        dots[0].setImageResource(R.drawable.dotactive);

        viewPager.addOnPageChangeListener(changeListener);
    }

    ViewPager.OnPageChangeListener changeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int i, float v, int i1) {

        }

        @Override
        public void onPageSelected(int i) {
            changeActiveDot(i);
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
}
