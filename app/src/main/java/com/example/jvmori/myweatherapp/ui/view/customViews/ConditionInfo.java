package com.example.jvmori.myweatherapp.ui.view.customViews;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.jvmori.myweatherapp.R;

public class ConditionInfo extends ConstraintLayout {

    ImageView imageView;
    TextView desc;
    TextView val;

    public ConditionInfo(Context context, AttributeSet attrs) {
        super(context, attrs);
        inflate(context, R.layout.condition_info, this);

        imageView = findViewById(R.id.icon);
        desc = findViewById(R.id.description);
        val = findViewById(R.id.value);

        TypedArray attributes = context.obtainStyledAttributes(attrs, R.styleable.ConditionInfo);
        imageView.setImageDrawable(attributes.getDrawable(R.styleable.ConditionInfo_icon));
        desc.setText(attributes.getText(R.styleable.ConditionInfo_desc));
        val.setText(attributes.getText(R.styleable.ConditionInfo_value));
        attributes.recycle();
    }

    public void setIcon(int value){
        imageView.setImageResource(value);
    }

    public void setValue(String value){
        val.setText(value);
    }
}
