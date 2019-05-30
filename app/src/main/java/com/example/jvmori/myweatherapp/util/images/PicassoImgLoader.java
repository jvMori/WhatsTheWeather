package com.example.jvmori.myweatherapp.util.images;

import android.widget.ImageView;

import com.example.jvmori.myweatherapp.R;
import com.squareup.picasso.Picasso;

import javax.inject.Inject;

public class PicassoImgLoader implements ILoadImage {
    private  Picasso picasso;

    @Inject
    public PicassoImgLoader(Picasso picasso){
        this.picasso = picasso;
    }

    @Override
    public void loadImage(String url, ImageView icon) {
        picasso.load(url)
                .error(R.drawable.ic_11)
                .into(icon);
    }
}
