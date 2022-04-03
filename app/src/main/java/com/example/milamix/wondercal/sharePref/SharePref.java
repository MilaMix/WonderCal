package com.example.milamix.wondercal.sharePref;
import android.content.Context;
import android.content.SharedPreferences;

public class SharePref {
    private static final String PREF_NAME = "MyPref";
    private Context context;

    public SharePref(Context context){
        this.context = context;
    }

    public static void saveInt(Context context, String key, int value) {
        SharedPreferences sharedPref = context.getSharedPreferences(PREF_NAME,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt(key, value);
        editor.commit();
    }
}
