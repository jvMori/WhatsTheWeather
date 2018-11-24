package com.example.jvmori.myweatherapp.view;


import android.content.Context;
import android.widget.ImageView;

public class Icon
{
    public void setImage(Context ctx, ImageView imageView, String code){
        int resourceId = ctx.getResources().getIdentifier(code, "id", ctx.getPackageName());
        imageView.setImageResource(resourceId);
    }

}



