package com.example.jvmori.myweatherapp.utils;

import com.example.jvmori.myweatherapp.model.Locations;

import java.util.List;

public class Contains
{
    public static int containsName(final List<Locations> list, final String name){
        if (list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
                if (list.get(i) != null && list.get(i).getId().equals(name))
                    return i;
            }
        }
        return -1;
    }
}
