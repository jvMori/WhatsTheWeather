package com.example.jvmori.myweatherapp.ui.view.customViews;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.jvmori.myweatherapp.R;

public class ConditionInfo extends ConstraintLayout {

    public ConditionInfo(Context context, AttributeSet attrs) {
        super(context, attrs);
        inflate(context, R.layout.condition_info, this);

        ImageView imageView = findViewById(R.id.icon);
        TextView desc = findViewById(R.id.description);
        TextView val = findViewById(R.id.value);

        TypedArray attributes = context.obtainStyledAttributes(attrs, R.styleable.ConditionInfo);
        imageView.setImageDrawable(attributes.getDrawable(R.styleable.ConditionInfo_icon));
        desc.setText(attributes.getText(R.styleable.ConditionInfo_desc));
        val.setText(attributes.getText(R.styleable.ConditionInfo_value));
        attributes.recycle();
    }
}
