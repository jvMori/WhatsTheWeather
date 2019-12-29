package com.example.jvmori.myweatherapp.data.current;

import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;

import com.example.jvmori.myweatherapp.util.Const;

import java.util.Date;
import java.util.Locale;

@Entity(tableName = "current_weather", primaryKeys = {"city_name", "longitude", "latitude"})
public class CurrentWeatherUI {
    @ColumnInfo(name = "city_name")
    @NonNull
    private String city = "";
    @NonNull
    private String longitude;
    @NonNull
    private String latitude;
    private String country;
    private String mainDescription;
    private String detailedDescription;
    private String iconUrl;
    private double mainTemp;
    private String pressure;
    private String humidity;
    private String cloudness;
    private String windSpeed;
    private Long timestamp;

    public CurrentWeatherUI(Long timestamp, String city, String longitude, String latitude, String country, String mainDescription, String detailedDescription, String iconUrl, double mainTemp, String pressure, String humidity, String cloudness, String windSpeed) {
        this.timestamp = timestamp;
        this.city = city;
        this.longitude = longitude;
        this.latitude = latitude;
        this.country = country;
        this.mainDescription = mainDescription;
        this.detailedDescription = detailedDescription;
        this.iconUrl = iconUrl;
        this.mainTemp = mainTemp;
        this.pressure = pressure;
        this.humidity = humidity;
        this.cloudness = cloudness;
        this.windSpeed = windSpeed;
    }

    public String getDate(){
        try {
            return getDateCurrentTimeZone(timestamp);
        }
        catch (Exception e){
            Log.i("Weather", e.getMessage());
        }
        return "";
    }

    public String getDateCurrentTimeZone(long timestamp) {
        Calendar cal = Calendar.getInstance(Locale.getDefault());
        cal.setTimeInMillis(timestamp);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEEE,  d MMM yyyy HH:mm:ss");
        Date currentTimeZone = cal.getTime();
        return simpleDateFormat.format(currentTimeZone);
    }
    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getMainDescription() {
        return mainDescription;
    }

    public void setMainDescription(String mainDescription) {
        this.mainDescription = mainDescription;
    }

    public String getDetailedDescription() {
        return detailedDescription;
    }

    public void setDetailedDescription(String detailedDescription) {
        this.detailedDescription = detailedDescription;
    }

    public String getIconUrl() {
        return  iconUrl;
    }

    public String getFullIconUrl(){
        return Const.baseIconUrl + iconUrl + "@2x.png";
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }

    public String getMainTempTxt() {
        return Integer.toString((int) Math.floor(mainTemp));
    }

    public double getMainTemp() {
        return mainTemp;
    }

    public void setMainTemp(double mainTemp) {
        this.mainTemp = mainTemp;
    }

    public String getPressure() {
        return pressure;
    }

    public void setPressure(String pressure) {
        this.pressure = pressure;
    }

    public String getHumidity() {
        return humidity;
    }

    public void setHumidity(String humidity) {
        this.humidity = humidity;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public String getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(String windSpeed) {
        this.windSpeed = windSpeed;
    }

    public String getCloudness() {
        return cloudness;
    }

    public void setCloudness(String cloudness) {
        this.cloudness = cloudness;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }
}
