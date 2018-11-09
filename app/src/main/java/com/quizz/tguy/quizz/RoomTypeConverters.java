package com.quizz.tguy.quizz;

import android.arch.persistence.room.TypeConverter;
import android.arch.persistence.room.TypeConverters;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.Collections;
import java.util.List;

// RoomTypeConverters : allows conversion of the lists for the Room using JSON
public class RoomTypeConverters {
    static Gson gson = new Gson();

    @TypeConverter
    public static List<String> stringToListString(String data) {
        if (data == null) {
            return Collections.emptyList();
        }

        Type listType = new TypeToken<List<String>>() {}.getType();

        return gson.fromJson(data, listType);
    }

    @TypeConverter
    public static String listStringToString(List<String> listString) {
        return gson.toJson(listString);
    }

    @TypeConverter
    public static List<List<String>> stringToListListString(String data) {
        if (data == null) {
            return Collections.emptyList();
        }

        Type listType = new TypeToken<List<List<String>>>() {}.getType();

        return gson.fromJson(data, listType);
    }

    @TypeConverter
    public static String listListStringToString(List<List<String>> listListString) {
        return gson.toJson(listListString);
    }

    @TypeConverter
    public static List<Integer> stringToListInteger(String data) {
        if (data == null) {
            return Collections.emptyList();
        }

        Type listType = new TypeToken<List<Integer>>() {}.getType();

        return gson.fromJson(data, listType);
    }

    @TypeConverter
    public static String listIntegerToString(List<Integer> listInteger) {
        return gson.toJson(listInteger);
    }
}
