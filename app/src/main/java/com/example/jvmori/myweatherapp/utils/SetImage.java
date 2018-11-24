package com.example.jvmori.myweatherapp.utils;

import android.content.Context;
import android.widget.ImageView;

import com.example.jvmori.myweatherapp.R;

public class SetImage
{
    public static void setImageView(Context ctx, String code, ImageView ivIcon){
        int resourceId = ctx.getResources().getIdentifier("ic_"+code, "drawable", ctx.getPackageName());
        if (resourceId == 0)
            ivIcon.setImageResource(R.drawable.ic_default);
        else
            ivIcon.setImageResource(resourceId);
    }
}
