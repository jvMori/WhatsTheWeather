
package com.example.jvmori.myweatherapp.data.db.entity.forecast;

import com.example.jvmori.myweatherapp.data.db.entity.current.Condition;
import com.google.gson.annotations.SerializedName;

import androidx.room.Embedded;

public class Day {

    @SerializedName("avghumidity")
    private Long mAvghumidity;
    @SerializedName("avgtemp_c")
    private Double mAvgtempC;
    @SerializedName("avgvis_km")
    private Double mAvgvisKm;
    @SerializedName("condition")
    @Embedded(prefix = "condition_")
    private Condition mCondition;
    @SerializedName("maxtemp_c")
    private Double mMaxtempC;
    @SerializedName("maxwind_kph")
    private Double mMaxwindKph;
    @SerializedName("mintemp_c")
    private Double mMintempC;
    @SerializedName("totalprecip_mm")
    private Double mTotalprecipMm;
    @SerializedName("uv")
    private Double mUv;

    public Long getAvghumidity() {
        return mAvghumidity;
    }

    public void setAvghumidity(Long avghumidity) {
        mAvghumidity = avghumidity;
    }

    public Double getAvgtempC() {
        return mAvgtempC;
    }

    public void setAvgtempC(Double avgtempC) {
        mAvgtempC = avgtempC;
    }

    public Double getAvgvisKm() {
        return mAvgvisKm;
    }

    public void setAvgvisKm(Double avgvisKm) {
        mAvgvisKm = avgvisKm;
    }

    public Condition getCondition() {
        return mCondition;
    }

    public void setCondition(Condition condition) {
        mCondition = condition;
    }

    public Double getMaxtempC() {
        return mMaxtempC;
    }

    public void setMaxtempC(Double maxtempC) {
        mMaxtempC = maxtempC;
    }

    public Double getMaxwindKph() {
        return mMaxwindKph;
    }

    public void setMaxwindKph(Double maxwindKph) {
        mMaxwindKph = maxwindKph;
    }

    public Double getMintempC() {
        return mMintempC;
    }

    public void setMintempC(Double mintempC) {
        mMintempC = mintempC;
    }

    public Double getTotalprecipMm() {
        return mTotalprecipMm;
    }

    public void setTotalprecipMm(Double totalprecipMm) {
        mTotalprecipMm = totalprecipMm;
    }

    public Double getUv() {
        return mUv;
    }

    public void setUv(Double uv) {
        mUv = uv;
    }

}
