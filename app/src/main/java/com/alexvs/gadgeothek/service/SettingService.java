package com.alexvs.gadgeothek.service;

import android.content.Context;
import android.content.SharedPreferences;

import static android.content.Context.MODE_PRIVATE;

public class SettingService {
    private static String PREFERENCE_KEY = "settings";

    public static void setString(Context ctx, String key, String value) {
        SharedPreferences preferences  = ctx.getSharedPreferences(PREFERENCE_KEY, MODE_PRIVATE);
        SharedPreferences.Editor edit= preferences.edit();
        edit.putString(key, value);
        edit.commit();
    }

    public static void setBool(Context ctx, String key, boolean value) {
        SharedPreferences preferences  = ctx.getSharedPreferences(PREFERENCE_KEY, MODE_PRIVATE);
        SharedPreferences.Editor edit= preferences.edit();
        edit.putBoolean(key, value);
        edit.commit();
    }

    public static String getString(Context ctx, String key, String defaultValue) {
        SharedPreferences preferences  = ctx.getSharedPreferences(PREFERENCE_KEY, MODE_PRIVATE);
        return preferences.getString(key, defaultValue);
    }

    public static boolean getBool(Context ctx, String key, boolean defaultValue) {
        SharedPreferences preferences  = ctx.getSharedPreferences(PREFERENCE_KEY, MODE_PRIVATE);
        return preferences.getBoolean(key, defaultValue);
    }
}
