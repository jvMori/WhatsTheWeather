
package com.example.jvmori.myweatherapp.architectureComponents.data.db.entity;

import com.google.gson.annotations.SerializedName;
import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName="current_weather", indices = {@Index(value = {"location"}, unique = true)})
public class CurrentWeather {
    @PrimaryKey (autoGenerate = true)
    public int id;
    private String location;
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

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
