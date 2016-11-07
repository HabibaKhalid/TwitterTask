package com.eventtus.task.helpers;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class StringPreference {
    protected final String key;

    public StringPreference(String key) {
        this.key = key;
    }

    public SharedPreferences getPreference(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context);
    }

    public void savePreferences(Context context, String value) {
        SharedPreferences sp = getPreference(context);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(key, value);
        editor.commit();
    }

    public String readPreferences(Context context, String defaultValue) {
        SharedPreferences sp = getPreference(context);
        return sp.getString(key, defaultValue);
    }

    public void deletePreferences(Context context) {
        SharedPreferences sp = getPreference(context);
        SharedPreferences.Editor editor = sp.edit();
        editor.remove(key);
        editor.apply();
    }

    public String getKey() {
        return key;
    }
}