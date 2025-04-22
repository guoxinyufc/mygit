package com.example.finalwork.util;

import android.content.Context;
import android.content.SharedPreferences;
//SharedPreferences是android中一个轻量存储类，可进行数据共享
public class SPUtils {//SharedPreferences工具类
    private static final String NAME = "config";
    //boolean
    public static void putBoolean(String key, boolean value, Context context) {//保持数据
        SharedPreferences sp = context.getSharedPreferences(NAME,
                Context.MODE_PRIVATE);//MODE_PRIVATE私有模式，只能被应用本身访问，覆盖写
        sp.edit().putBoolean(key, value).commit();
    }
    public static boolean getBoolean(String key, boolean defValue, Context context) {//读取数据
        SharedPreferences sp = context.getSharedPreferences(NAME,
                Context.MODE_PRIVATE);
        return sp.getBoolean(key, defValue);
    }
    //string
    public static void putString(String key, String value, Context context) {
        SharedPreferences sp = context.getSharedPreferences(NAME,
                Context.MODE_PRIVATE);
        sp.edit().putString(key, value).commit();
    }
    public static String getString(String key, String defValue, Context context) {
        if (context != null) {
            SharedPreferences sp = context.getSharedPreferences(NAME,
                    Context.MODE_PRIVATE);
            return sp.getString(key, defValue);
        }
        return "";

    }
    //int
    public static void putInt(String key, int value, Context context) {
        SharedPreferences sp = context.getSharedPreferences(NAME,
                Context.MODE_PRIVATE);
        sp.edit().putInt(key, value).commit();
    }
    public static int getInt(String key, int defValue, Context context) {
        SharedPreferences sp = context.getSharedPreferences(NAME,
                Context.MODE_PRIVATE);
        return sp.getInt(key, defValue);
    }
    //long
    public static void putLong(String key, long value, Context context) {
        SharedPreferences sp = context.getSharedPreferences(NAME,
                Context.MODE_PRIVATE);
        sp.edit().putLong(key, value).commit();
    }
    public static long getLong(String key, long defValue, Context context) {
        SharedPreferences sp = context.getSharedPreferences(NAME,
                Context.MODE_PRIVATE);
        return sp.getLong(key, defValue);
    }

    public static void remove(String key, Context context) {//删除该文件
        SharedPreferences sp = context.getSharedPreferences(NAME,
                Context.MODE_PRIVATE);
        sp.edit().remove(key).commit();
    }

}
