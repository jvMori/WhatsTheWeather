
package com.example.jvmori.myweatherapp.architectureComponents.data.db.entity;

import com.example.jvmori.myweatherapp.architectureComponents.data.util.ZoneDateTypeConverter;
import com.google.gson.annotations.SerializedName;

import java.time.ZonedDateTime;

import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverter;
import androidx.room.TypeConverters;

@Entity(tableName="current_weather", indices = {@Index(value = {"location", "isDeviceLocation"}, unique = true)})
public class CurrentWeather {
    @PrimaryKey (autoGenerate = true)
    public int id;
    private String location;
    private boolean isDeviceLocation;
    @TypeConverters(ZoneDateTypeConverter.class)
    private ZonedDateTime fetchTime;
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
    public boolean isDeviceLocation() { return isDeviceLocation; }
    public void setDeviceLocation(boolean deviceLocation) { isDeviceLocation = deviceLocation; }
    public ZonedDateTime getFetchTime() {
        return fetchTime;
    }
    public void setFetchTime(ZonedDateTime fetchTime) {
        this.fetchTime = fetchTime;
    }
}
