package com.example.milamix.wondercal;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.example.milamix.wondercal.databinding.ActivityMainBinding;
import com.example.milamix.wondercal.sharePref.SharePref;
import com.example.milamix.wondercal.util.Utils;

public class LandingActivity extends AppCompatActivity {
    Intent itn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing);
        int secs = 2; // Delay in seconds
        Utils.delay(secs, new Utils.DelayCallback() {
            @Override
            public void afterDelay() {
                initLoading();
            }
        });
    }

    private void initLoading(){
        SharePref sharePref = new SharePref(this);
        Boolean isLogin = sharePref.getBoolean("isLogin");

        Utils.Log(isLogin.toString());

        if (isLogin){
            itn = new Intent(this, MainActivity.class);
        }else{
            itn = new Intent(this, LoginActivity.class);
        }
        startActivity(itn);
        finish();
    }
}