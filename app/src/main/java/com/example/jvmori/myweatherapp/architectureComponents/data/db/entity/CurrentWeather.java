
package com.example.jvmori.myweatherapp.architectureComponents.data.db.entity;

import com.google.gson.annotations.SerializedName;

public class CurrentWeather {

    @SerializedName("cloud")
    private Long mCloud;
    @SerializedName("condition")
    private Condition mCondition;
    @SerializedName("feelslike_c")
    private Double mFeelslikeC;
    @SerializedName("feelslike_f")
    private Long mFeelslikeF;
    @SerializedName("humidity")
    private Long mHumidity;
    @SerializedName("is_day")
    private Long mIsDay;
    @SerializedName("last_updated")
    private String mLastUpdated;
    @SerializedName("last_updated_epoch")
    private Long mLastUpdatedEpoch;
    @SerializedName("precip_in")
    private Long mPrecipIn;
    @SerializedName("precip_mm")
    private Long mPrecipMm;
    @SerializedName("pressure_in")
    private Double mPressureIn;
    @SerializedName("pressure_mb")
    private Long mPressureMb;
    @SerializedName("temp_c")
    private Long mTempC;
    @SerializedName("temp_f")
    private Double mTempF;
    @SerializedName("uv")
    private Long mUv;
    @SerializedName("vis_km")
    private Long mVisKm;
    @SerializedName("vis_miles")
    private Long mVisMiles;
    @SerializedName("wind_degree")
    private Long mWindDegree;
    @SerializedName("wind_dir")
    private String mWindDir;
    @SerializedName("wind_kph")
    private Double mWindKph;
    @SerializedName("wind_mph")
    private Long mWindMph;

    public Long getCloud() {
        return mCloud;
    }

    public void setCloud(Long cloud) {
        mCloud = cloud;
    }

    public Condition getCondition() {
        return mCondition;
    }

    public void setCondition(Condition condition) {
        mCondition = condition;
    }

    public Double getFeelslikeC() {
        return mFeelslikeC;
    }

    public void setFeelslikeC(Double feelslikeC) {
        mFeelslikeC = feelslikeC;
    }

    public Long getFeelslikeF() {
        return mFeelslikeF;
    }

    public void setFeelslikeF(Long feelslikeF) {
        mFeelslikeF = feelslikeF;
    }

    public Long getHumidity() {
        return mHumidity;
    }

    public void setHumidity(Long humidity) {
        mHumidity = humidity;
    }

    public Long getIsDay() {
        return mIsDay;
    }

    public void setIsDay(Long isDay) {
        mIsDay = isDay;
    }

    public String getLastUpdated() {
        return mLastUpdated;
    }

    public void setLastUpdated(String lastUpdated) {
        mLastUpdated = lastUpdated;
    }

    public Long getLastUpdatedEpoch() {
        return mLastUpdatedEpoch;
    }

    public void setLastUpdatedEpoch(Long lastUpdatedEpoch) {
        mLastUpdatedEpoch = lastUpdatedEpoch;
    }

    public Long getPrecipIn() {
        return mPrecipIn;
    }

    public void setPrecipIn(Long precipIn) {
        mPrecipIn = precipIn;
    }

    public Long getPrecipMm() {
        return mPrecipMm;
    }

    public void setPrecipMm(Long precipMm) {
        mPrecipMm = precipMm;
    }

    public Double getPressureIn() {
        return mPressureIn;
    }

    public void setPressureIn(Double pressureIn) {
        mPressureIn = pressureIn;
    }

    public Long getPressureMb() {
        return mPressureMb;
    }

    public void setPressureMb(Long pressureMb) {
        mPressureMb = pressureMb;
    }

    public Long getTempC() {
        return mTempC;
    }

    public void setTempC(Long tempC) {
        mTempC = tempC;
    }

    public Double getTempF() {
        return mTempF;
    }

    public void setTempF(Double tempF) {
        mTempF = tempF;
    }

    public Long getUv() {
        return mUv;
    }

    public void setUv(Long uv) {
        mUv = uv;
    }

    public Long getVisKm() {
        return mVisKm;
    }

    public void setVisKm(Long visKm) {
        mVisKm = visKm;
    }

    public Long getVisMiles() {
        return mVisMiles;
    }

    public void setVisMiles(Long visMiles) {
        mVisMiles = visMiles;
    }

    public Long getWindDegree() {
        return mWindDegree;
    }

    public void setWindDegree(Long windDegree) {
        mWindDegree = windDegree;
    }

    public String getWindDir() {
        return mWindDir;
    }

    public void setWindDir(String windDir) {
        mWindDir = windDir;
    }

    public Double getWindKph() {
        return mWindKph;
    }

    public void setWindKph(Double windKph) {
        mWindKph = windKph;
    }

    public Long getWindMph() {
        return mWindMph;
    }

    public void setWindMph(Long windMph) {
        mWindMph = windMph;
    }

}
