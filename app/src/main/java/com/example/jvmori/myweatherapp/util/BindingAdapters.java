package com.example.jvmori.myweatherapp.util;

import android.view.View;

import androidx.databinding.BindingAdapter;

import com.example.jvmori.myweatherapp.ui.Resource;

public class BindingAdapters {

    @BindingAdapter("app:showView")
    public static void showSuccessView (View view, Resource.Status status) {
        int visibility = View.GONE;
        switch (status){
            case SUCCESS:
                visibility = View.VISIBLE;
                break;
            case ERROR:
                visibility = View.GONE;
                break;
            case LOADING:
                visibility = View.GONE;
                break;
        }
        view.setVisibility(visibility);
    }
    @BindingAdapter("app:showLoadingView")
    public static void showLoadingView (View view, Resource.Status status) {
        int visibility = View.GONE;
        switch (status){
            case SUCCESS:
                visibility = View.GONE;
                break;
            case ERROR:
                visibility = View.GONE;
                break;
            case LOADING:
                visibility = View.VISIBLE;
                break;
        }
        view.setVisibility(visibility);
    }

    @BindingAdapter("app:showErrorView")
    public static void showErrorView (View view, Resource.Status status) {
        int visibility = View.GONE;
        switch (status){
            case SUCCESS:
                visibility = View.GONE;
                break;
            case ERROR:
                visibility = View.VISIBLE;
                break;
            case LOADING:
                visibility = View.GONE;
                break;
        }
        view.setVisibility(visibility);
    }
}
