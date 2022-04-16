package com.example.milamix.wondercal.Service;
import android.content.Context;
import android.content.SharedPreferences;

import org.json.JSONException;
import org.json.JSONObject;

public class SharePref {

    private static final String PREF_NAME = "MyPref";
    private Context context;

    public SharePref(Context context){
        this.context = context;
    }

    public void saveLoginPref(String token,String time,String email){
        saveString("token",token);
        saveBoolean("isLogin",true);
        saveString("email",email);
        saveString("lastLogin",time);
    }
    public void saveLogout(){
        saveString("token","");
        saveString("email","");
        saveBoolean("isLogin",false);
        saveString("lastLogin","");
    }

    public void saveObj(String key, JSONObject obj){
        SharedPreferences sharedPref = context.getSharedPreferences(PREF_NAME,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(key, obj.toString());
        editor.commit();
    }

    public JSONObject getObj(String key) throws JSONException {
        SharedPreferences sharedPref = context.getSharedPreferences(PREF_NAME,Context.MODE_PRIVATE);
        String value = sharedPref.getString(key, "");
        JSONObject jsonObject = new JSONObject(value);
        return jsonObject;
    }

    public void saveInt(String key, int value) {
        SharedPreferences sharedPref = context.getSharedPreferences(PREF_NAME,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt(key, value);
        editor.commit();
    }

    public int getInt(String key) {
        SharedPreferences sharedPref = context.getSharedPreferences(PREF_NAME,Context.MODE_PRIVATE);
        return sharedPref.getInt(key, 0);
    }

    public void saveString( String key, String value) {
        SharedPreferences sharedPref = context.getSharedPreferences(PREF_NAME,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(key, value);
        editor.commit();
    }

    public String getString(String key) {
        SharedPreferences sharedPref = context.getSharedPreferences(PREF_NAME,Context.MODE_PRIVATE);
        return sharedPref.getString(key, "");
    }

    public void saveBoolean(String key, boolean value) {
        SharedPreferences sharedPref = context.getSharedPreferences(PREF_NAME,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putBoolean(key, value);
        editor.commit();
    }

    public Boolean getBoolean(String key) {
        SharedPreferences sharedPref = context.getSharedPreferences(PREF_NAME,Context.MODE_PRIVATE);
        return sharedPref.getBoolean(key, false);
    }
}
