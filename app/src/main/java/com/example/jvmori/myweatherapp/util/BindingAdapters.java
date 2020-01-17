package com.example.jvmori.myweatherapp.util;

import android.view.View;

import androidx.databinding.BindingAdapter;

import com.example.jvmori.myweatherapp.ui.Resource;

public class BindingAdapters {

    @BindingAdapter("app:isVisible")
        public static void setVisibility(View view, boolean isVisible){
        if (isVisible) view.setVisibility(View.VISIBLE);
        else view.setVisibility(View.GONE);
    }


    @BindingAdapter("app:showSuccessView")
    public static void showSuccessView(View view, Resource.Status status) {
        int visibility = View.GONE;
        if (status != null) {
            switch (status) {
                case SUCCESS:
                    visibility = View.VISIBLE;
                    break;
                case ERROR:
                case LOADING:
                    visibility = View.GONE;
                    break;
            }
        }
        view.setVisibility(visibility);
    }

    @BindingAdapter("app:showLoadingView")
    public static void showLoadingView(View view, Resource.Status status) {
        int visibility = View.GONE;
        if (status != null) {
            switch (status) {
                case SUCCESS:
                case ERROR:
                    visibility = View.GONE;
                    break;
                case LOADING:
                    visibility = View.VISIBLE;
                    break;
            }
        }
        view.setVisibility(visibility);
    }

    @BindingAdapter("app:showErrorView")
    public static void showErrorView(View view, Resource.Status status) {
        int visibility = View.GONE;
        if (status != null) {
            switch (status) {
                case SUCCESS:
                case ERROR:
                    visibility = View.VISIBLE;
                    break;
                case LOADING:
                    visibility = View.GONE;
                    break;
            }
        }
        view.setVisibility(visibility);
    }
}
