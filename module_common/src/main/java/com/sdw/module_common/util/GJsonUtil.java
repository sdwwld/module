package com.sdw.module_common.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public final class GJsonUtil {

    public static Gson gson = new GsonBuilder()
            .setDateFormat("yyyy-MM-dd HH:mm:ss")
            .create();

    public static <T> T toObject(String jsonString, Class<T> cls) {
        return gson.fromJson(jsonString, cls);
    }

    public static String toJson(Object object) {
        return gson.toJson(object);
    }

//    public static <T> List<T> jsonToList(String gsonString, Class<T> cls) {
//        return gson.fromJson(gsonString, new TypeToken<List<T>>() {
//        }.getType());
//    }

    public static <T> List<T> toList(String json, Class<T> cls) {
        List<T> list = new ArrayList<T>();
        JsonArray array = new JsonParser().parse(json).getAsJsonArray();
        for (final JsonElement elem : array) {
            list.add(gson.fromJson(elem, cls));
        }
        return list;
    }

    public static <T> List<Map<String, T>> toListMaps(String jsonString) {
        return gson.fromJson(jsonString, new TypeToken<List<Map<String, T>>>() {
        }.getType());
    }


    public static <T> Map<String, T> toMaps(String jsonString) {
        return gson.fromJson(jsonString, new TypeToken<Map<String, T>>() {
        }.getType());
    }

}
