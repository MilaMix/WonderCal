package com.example.milamix.wondercal.sharePref;
import android.content.Context;
import android.content.SharedPreferences;

public class SharePref {
    private static final String PREF_NAME = "MyPref";
    private Context context;

    SharedPreferences sharedPref = context.getSharedPreferences(PREF_NAME,Context.MODE_PRIVATE);
    SharedPreferences.Editor editor = sharedPref.edit();

    public SharePref(Context context){
        this.context = context;
    }

    public void saveInt(String key, int value) {
        editor.putInt(key, value);
        editor.commit();
    }

    public int getInt(String key) {
        SharedPreferences sharedPref = context.getSharedPreferences(PREF_NAME,Context.MODE_PRIVATE);
        return sharedPref.getInt(key, 0);
    }

    public void saveString( String key, String value) {
        editor.putString(key, value);
        editor.commit();
    }

    public String getString(String key) {
        SharedPreferences sharedPref = context.getSharedPreferences(PREF_NAME,Context.MODE_PRIVATE);
        return sharedPref.getString(key, "");
    }

    public void saveBoolean(String key, Boolean value) {
        editor.putBoolean(key, value);
        editor.commit();
    }

    public Boolean getBoolean(String key) {
        SharedPreferences sharedPref = context.getSharedPreferences(PREF_NAME,Context.MODE_PRIVATE);
        return sharedPref.getBoolean(key, false);
    }
}
