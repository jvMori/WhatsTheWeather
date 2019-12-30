package com.example.jvmori.myweatherapp.util;

import android.icu.util.Calendar;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateConverter {
    public static Date convertStringToDate(String dateTxt) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        return formatter.parse(dateTxt);
    }

    public static String getDateCurrentTimeZone(long timestamp) {
        Calendar cal = Calendar.getInstance(Locale.getDefault());
        cal.setTimeInMillis(timestamp);
        android.icu.text.SimpleDateFormat simpleDateFormat = new android.icu.text.SimpleDateFormat("EEEE,  d MMM yyyy HH:mm:ss");
        Date currentTimeZone = cal.getTime();
        return simpleDateFormat.format(currentTimeZone);
    }

    public static String getDayOfWeek(String timeTxt) {
        Date date = new Date();
        if (timeTxt != null) {
            try {
                date = DateConverter.convertStringToDate(timeTxt);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return new SimpleDateFormat("EEEE", Locale.getDefault()).format(date);
    }
}
