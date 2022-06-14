package com.my.common.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.my.common.base.BaseApplication;

import java.util.Map;

/**
 * @author chenshaopeng
 * @date 2020/7/29 20:34
 */
public class SPUtil {
    private static final String FILE_NAME = "logistics_save";
    static SharedPreferences sp;

    public static void init (Context context) {
        sp = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
    }

    public static void put (String key, Object object) {
        if (object == null) {
            return;
        }
        if (sp == null) {
            sp = BaseApplication.getInstance().getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        }
        SharedPreferences.Editor editor = sp.edit();
        if (object instanceof String) {
            editor.putString(key, (String) object);
        } else if (object instanceof Integer) {
            editor.putInt(key, (Integer) object);
        } else if (object instanceof Boolean) {
            editor.putBoolean(key, (Boolean) object);
        } else if (object instanceof Float) {
            editor.putFloat(key, (Float) object);
        } else if (object instanceof Long) {
            editor.putLong(key, (Long) object);
        } else {
            editor.putString(key, object.toString());
        }
        Log.i("spUtil","spUtil put = "+key + ", value = "+ object);
        editor.commit();
    }

    public static Object get (String key, Object defaultObject) {
        if (sp == null) {
            sp = BaseApplication.getInstance().getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        }
        if (defaultObject instanceof String) {
            String result = sp.getString(key, (String) defaultObject);
            LogUtil.d("key:" + key + ",result:" + result);
            return result;
        } else if (defaultObject instanceof Integer) {
            int result = sp.getInt(key, (Integer) defaultObject);
            LogUtil.d("key:" + key + ",result:" + result);
            return result;
        } else if (defaultObject instanceof Boolean) {
            boolean result = sp.getBoolean(key, (Boolean) defaultObject);
            LogUtil.d("key:" + key + ",result:" + result);
            return result;
        } else if (defaultObject instanceof Float) {
            float result = sp.getFloat(key, (Float) defaultObject);
            LogUtil.d("key:" + key + ",result:" + result);
            return result;
        } else if (defaultObject instanceof Long) {
            long result = sp.getLong(key, (Long) defaultObject);
            LogUtil.d("key:" + key + ",result:" + result);
            return result;
        }
        return null;
    }

    public static void remove (String key) {
        if (sp == null) {
            sp = BaseApplication.getInstance().getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        }
        SharedPreferences.Editor editor = sp.edit();
        editor.remove(key);
        editor.commit();
    }

    public static void clear () {
        if (sp == null) {
            sp = BaseApplication.getInstance().getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        }
        SharedPreferences.Editor editor = sp.edit();
        editor.clear().commit();
    }

    public static Map<String, ?> getAll () {
        if (sp == null) {
            sp = BaseApplication.getInstance().getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        }
        return sp.getAll();
    }
}
