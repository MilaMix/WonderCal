package com.example.milamix.wondercal.util;

import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;

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

    public static void Log(String str){
        Log.d("App","====> "+str);
    }
}