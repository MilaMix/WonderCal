package com.example.milamix.wondercal.util;

import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;

import com.android.volley.AuthFailureError;
import com.android.volley.toolbox.JsonObjectRequest;

public class Utils {

    public interface DelayCallback{
        void afterDelay();
    }

    public static void delay(int secs, final DelayCallback delayCallback){
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                delayCallback.afterDelay();
            }
        }, secs * 1000); // afterDelay will be executed after (secs*1000) milliseconds.
    }

    public static boolean isValidEmail(CharSequence target) {
        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }

    public static void LogAPIs(JsonObjectRequest stringRequest) throws AuthFailureError {
        Log.d("HTTP_","=====================");
        Log.d("HTTP_",stringRequest.toString());
        Log.d("HTTP_",stringRequest.getUrl());
        Log.d("HTTP_",stringRequest.getHeaders().toString());
        Log.d("HTTP_",String.valueOf(stringRequest.getMethod()));
        Log.d("HTTP_","=====================");
    }
    public static void Log(String str){
        Log.d("App","====> "+str);
    }
}