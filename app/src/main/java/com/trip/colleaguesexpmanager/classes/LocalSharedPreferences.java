package com.trip.colleaguesexpmanager.classes;

import android.content.Context;
import android.content.SharedPreferences;

public class LocalSharedPreferences {
    private static final String strSharedPrefName = "AppPreference";
    private static final String isLogin = "isLogin";
    private static final String user_id = "user_id";
    private static final String user_name = "user_name";
    private static final String email = "email";
    private static final String mobile = "mobile";

    public static void saveIsLogin(Context context, boolean value) {
        SharedPreferences pref = context.getSharedPreferences(strSharedPrefName, context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putBoolean(isLogin, value);
        editor.commit();
    }

    public static boolean getIsLogin(Context context) {
        SharedPreferences pref = context.getSharedPreferences(strSharedPrefName, context.MODE_PRIVATE);
        boolean isLoginBoolean = pref.getBoolean(isLogin, false);
        return isLoginBoolean;
    }

    public static void saveUserId(Context context, String value) {
        SharedPreferences pref = context.getSharedPreferences(strSharedPrefName, context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString(user_id, value);
        editor.commit();
    }

    public static String getUserId(Context context) {
        SharedPreferences pref = context.getSharedPreferences(strSharedPrefName, context.MODE_PRIVATE);
        String value = pref.getString(user_id, "");
        return value;
    }

    public static void saveUserName(Context context, String value) {
        SharedPreferences pref = context.getSharedPreferences(strSharedPrefName, context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString(user_name, value);
        editor.commit();
    }

    public static String getUserName(Context context) {
        SharedPreferences pref = context.getSharedPreferences(strSharedPrefName, context.MODE_PRIVATE);
        String value = pref.getString(user_name, "");
        return value;
    }

    public static void saveEmail(Context context, String value) {
        SharedPreferences pref = context.getSharedPreferences(strSharedPrefName, context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString(email, value);
        editor.commit();
    }

    public static String getEmail(Context context) {
        SharedPreferences pref = context.getSharedPreferences(strSharedPrefName, context.MODE_PRIVATE);
        String value = pref.getString(email, "");
        return value;
    }

    public static void saveMobile(Context context, String value) {
        SharedPreferences pref = context.getSharedPreferences(strSharedPrefName, context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString(mobile, value);
        editor.commit();
    }

    public static String getMobile(Context context) {
        SharedPreferences pref = context.getSharedPreferences(strSharedPrefName, context.MODE_PRIVATE);
        String value = pref.getString(mobile, "");
        return value;
    }



}
