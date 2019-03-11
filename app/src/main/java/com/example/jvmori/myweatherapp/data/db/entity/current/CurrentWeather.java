
package com.example.jvmori.myweatherapp.data.db.entity.current;


import com.google.gson.annotations.SerializedName;
import androidx.room.Embedded;

public class CurrentWeather {
    @SerializedName("cloud")
    public Long mCloud;
    @SerializedName("condition") @Embedded(prefix = "condition_")
    public Condition mCondition;
    @SerializedName("feelslike_c")
    public Double mFeelslikeC;
    @SerializedName("humidity")
    public Long mHumidity;
    @SerializedName("is_day")
    public Long mIsDay;
    @SerializedName("precip_mm")
    public Double mPrecipMm;
    @SerializedName("pressure_mb")
    public Double mPressureMb;
    @SerializedName("temp_c")
    public Double mTempC;
    @SerializedName("uv")
    public Long mUv;
    @SerializedName("vis_km")
    public Double mVisKm;
    @SerializedName("wind_degree")
    public Double mWindDegree;
    @SerializedName("wind_dir")
    public String mWindDir;
    @SerializedName("wind_kph")
    public Double mWindKph;

}
