package com.example.bakis;

import androidx.room.TypeConverter;

import com.google.gson.Gson;

import java.util.Date;

public class Converters {
    @TypeConverter
    public String fromDateToString(Date date){
        return new Gson().toJson(date);
    }

    @TypeConverter
    public Date fromGsonToDate(String string){
        return new Gson().fromJson(string, Date.class);
    }

    @TypeConverter
    public String fromIntArrayToString(int[] integer){
        return new Gson().toJson(integer);
    }

    @TypeConverter
    public int[] fromGsonToIntArray(String string){
        return new Gson().fromJson(string, int[].class);
    }

    @TypeConverter
    public String fromBooleanArrayToString(boolean[] booleans){
        return new Gson().toJson(booleans);
    }

    @TypeConverter
    public boolean[] fromGsonToBooleanArray(String string){
        return new Gson().fromJson(string, boolean[].class);
    }
}
