package com.ehook.dy.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.evan.socket.JsonUtil;

import java.lang.reflect.Type;

import a.c.e.f.AESUtil;


/**
 * SharedPreferences 工具类
 *
 * @author cxcui
 */
public class SharedUtil {
    public final static String AES_KEY = "asdfadsfdsf";
    private static SharedPreferences preferences;
    private static SharedUtil sharedUtil = null;

    private SharedUtil(Context context) {
        if (preferences == null)
            preferences = context.getSharedPreferences("dy_" + "v2", Context.MODE_PRIVATE);
    }

    public static SharedUtil instance(Context context) {
        if (sharedUtil == null)
            sharedUtil = new SharedUtil(context);

        return sharedUtil;
    }

    public String getString(String key) {
        return getString(key, "");
    }

    public String getString(String key, String defaultValue) {
        String value = preferences.getString(key, defaultValue);
        if (!TextUtils.isEmpty(value)) {
            value = AESUtil.decrypt(value, AES_KEY);
        }
        return value;
    }

    public void saveString(String key, String value) {
        if (!TextUtils.isEmpty(value)) {
            value = AESUtil.encrypt(value, AES_KEY);
        }
        preferences.edit().putString(key, value).commit();
    }

    public int getInt(String key) {
        return preferences.getInt(key, -1);
    }

    public int getInt(String key, int defaultValue) {
        return preferences.getInt(key, defaultValue);
    }

    public void saveInt(String key, int value) {
        preferences.edit().putInt(key, value).commit();
    }

    public boolean getBoolean(String key) {
        return preferences.getBoolean(key, false);
    }

    public boolean getBoolean(String key, boolean defaultValue) {
        return preferences.getBoolean(key, defaultValue);
    }

    public void saveBoolean(String key, boolean value) {
        preferences.edit().putBoolean(key, value).commit();
    }

    public float getFloat(String key) {
        return preferences.getFloat(key, 0f);
    }

    public void saveFloat(String key, float value) {
        preferences.edit().putFloat(key, value).commit();
    }

    public void saveLong(String key, long value) {
        preferences.edit().putLong(key, value);
    }

    public long getLong(String key, long defaultValue) {
        return preferences.getLong(key, defaultValue);
    }

    public void saveObject(String key, Object obj) {
        saveString(key, JsonUtil.toJson(obj));
    }

    public <T> T getObject(String cacheKey, Class<T> classOfT) {
        String string = getString(cacheKey);
        if (!TextUtils.isEmpty(string)) {
            try {
                return JsonUtil.fromJson(getString(cacheKey), classOfT);
            } catch (Exception e) {
                return null;
            }
        }
        return null;
    }


    public <T> T getObject(String cacheKey, Type type) {
        String string = getString(cacheKey);
        if (!TextUtils.isEmpty(string)) {
            try {
                return JsonUtil.fromJson(getString(cacheKey), type);
            } catch (Exception e) {
                return null;
            }
        }
        return null;
    }

    public void remove(String key) {
        preferences.edit().remove(key).commit();
    }

}
