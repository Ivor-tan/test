package com.example.myTest.Utils.Json;

import org.json.JSONException;
import org.json.JSONObject;

public class JsonHelper {

    public static void put(JSONObject object, String key, Object value) {
        try {
            object.put(key, value);
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public static void put(JSONObject object, String key, int value) {
        try {
            object.put(key, value);
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public static void put(JSONObject object, String key, long value) {
        try {
            object.put(key, value);
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public static void put(JSONObject object, String key, double value) {
        try {
            object.put(key, value);
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public static void put(JSONObject object, String key, boolean value) {
        try {
            object.put(key, value);
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }


}
