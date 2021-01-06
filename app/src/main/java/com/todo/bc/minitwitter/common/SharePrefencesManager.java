package com.todo.bc.minitwitter.common;

import android.content.Context;
import android.content.SharedPreferences;

public class SharePrefencesManager {


    private static  final String APP_SETTING_FILE = "APP_SETTING";

    private SharePrefencesManager() {

    }

    private static SharedPreferences getSharePreference() {
        return  MyApp.getContext().getSharedPreferences(APP_SETTING_FILE, Context.MODE_PRIVATE);
    }


    public static  void setSomeStringValue(String dataLabel, String dataValue) {
        SharedPreferences.Editor editor = getSharePreference().edit();
        editor.putString(dataLabel,dataValue);
        editor.commit();
    }


    public static  void setSomeBooleanValue(String dataLabel, boolean dataValue) {
        SharedPreferences.Editor editor = getSharePreference().edit();
        editor.putBoolean(dataLabel,dataValue);
        editor.commit();
    }


    public static String getSharePreferenceString(String dataLabel) {
        return getSharePreference().getString(dataLabel, null);
    }


    public static boolean getSharePreferenceBoolean(String dataLabel) {
        return getSharePreference().getBoolean(dataLabel, false);
    }
}
