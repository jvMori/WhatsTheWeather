
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
    @SerializedName("feelslike_f")
    public Double mFeelslikeF;
    @SerializedName("humidity")
    public Long mHumidity;
    @SerializedName("is_day")
    public Long mIsDay;
    @SerializedName("last_updated")
    public String mLastUpdated;
    @SerializedName("last_updated_epoch")
    public Double mLastUpdatedEpoch;
    @SerializedName("precip_in")
    public Double mPrecipIn;
    @SerializedName("precip_mm")
    public Double mPrecipMm;
    @SerializedName("pressure_in")
    public Double mPressureIn;
    @SerializedName("pressure_mb")
    public Double mPressureMb;
    @SerializedName("temp_c")
    public Double mTempC;
    @SerializedName("temp_f")
    public Double mTempF;
    @SerializedName("uv")
    public Long mUv;
    @SerializedName("vis_km")
    public Double mVisKm;
    @SerializedName("vis_miles")
    public Double mVisMiles;
    @SerializedName("wind_degree")
    public Double mWindDegree;
    @SerializedName("wind_dir")
    public String mWindDir;
    @SerializedName("wind_kph")
    public Double mWindKph;
    @SerializedName("wind_mph")
    public Double mWindMph;

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
