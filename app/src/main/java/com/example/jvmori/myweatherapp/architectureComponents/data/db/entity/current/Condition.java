
package com.example.jvmori.myweatherapp.architectureComponents.data.db.entity.current;

import com.google.gson.annotations.SerializedName;

public class Condition {

    @SerializedName("code")
    private Long mCode;
    @SerializedName("icon")
    private String mIcon;
    @SerializedName("text")
    private String mText;

    public Long getCode() {
        return mCode;
    }

    public void setCode(Long code) {
        mCode = code;
    }

    public String getIcon() {
        return mIcon;
    }

    public void setIcon(String icon) {
        mIcon = icon;
    }

    public String getText() {
        return mText;
    }

    public void setText(String text) {
        mText = text;
    }

}
