package com.grocery.hr.lalajikidukan.preferences;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.grocery.hr.lalajikidukan.utils.Utils;

import java.util.List;

/**
 * Created by vipul on 25/3/17.
 */

public class AppSharedPreference{

        private static final String PREFERENCE_NAME = "grocery";

        private static SharedPreferences getSharedPreferences(Context context) {
            return context.getSharedPreferences(PREFERENCE_NAME,
                    Context.MODE_PRIVATE);
        }

        public static void remove(Context context, String key) {
            if (context != null) {
                getSharedPreferences(context).edit().remove(key).commit();
            }
        }

        public static void putInt(Context context, String key, int value) {
            if (context != null) {
                getSharedPreferences(context).edit().putInt(key, value).commit();
            }
        }

        public static void putLong(Context context, String key, long value) {
            if (context != null) {
                getSharedPreferences(context).edit().putLong(key, value).commit();
            }
        }

        public static void putString(Context context, String key, String value) {
            if (context != null) {
                SharedPreferences.Editor edit = getSharedPreferences(context).edit();
                if (TextUtils.isEmpty(value)) {
                    edit.remove(key);
                } else {
                    edit.putString(key, value);
                }
                edit.commit();
            }
        }

        public static void putBoolean(Context context, String key, boolean value) {
            if (context != null) {
                getSharedPreferences(context).edit().putBoolean(key, value)
                        .commit();
            }
        }

        public static String getString(Context context, String key) {
            if (context != null) {
                return getSharedPreferences(context).getString(key, "");
            } else {
                return getString(context, key, "");
            }
        }

        public static String getString(Context context, String key, String def) {
            if (context != null) {
                return getSharedPreferences(context).getString(key, def);
            } else {
                return def;
            }
        }



    public static int getInt(Context context, String key, int def) {
            if (context != null) {
                return getSharedPreferences(context).getInt(key, def);
            } else {
                return 0;
            }
        }

        public static long getLong(Context context, String key) {
            if (context != null) {
                return getSharedPreferences(context).getLong(key, 0);
            } else {
                return 0;
            }
        }

        public static boolean getBoolean(Context context, String key) {

            return context != null && getBoolean(context, key, false);

        }

        public static boolean getBoolean(Context context, String key, boolean def) {
            return context != null
                    && getSharedPreferences(context).getBoolean(key, def);
        }

        /*public static <T> void setList(Context context, String key, List<T> list) {
            String json = Utils.toJson(list);
            putString(context, key, json);
        }*/

}
