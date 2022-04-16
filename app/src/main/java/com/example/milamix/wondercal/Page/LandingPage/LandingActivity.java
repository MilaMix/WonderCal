package com.example.milamix.wondercal.Page.LandingPage;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.android.volley.VolleyError;
import com.example.milamix.wondercal.Page.LoginPage.LoginActivity;
import com.example.milamix.wondercal.Service.IResult;
import com.example.milamix.wondercal.Service.VolleyService;
import com.example.milamix.wondercal.R;
import com.example.milamix.wondercal.Page.UserinfoPage.LoadingUserInfoActivity;
import com.example.milamix.wondercal.Service.SharePref;
import com.example.milamix.wondercal.Utils.Utils;

import org.json.JSONObject;

public class LandingActivity extends AppCompatActivity {
    Intent itn;
    IResult mResultCallback = null;
    VolleyService mVolleyService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing);
        Utils.Log("Landing Page");
        initVolleyCallback();
        mVolleyService = new VolleyService(mResultCallback,this);
        mVolleyService.postDataVolley("/", null);
    }

    void initVolleyCallback(){
        mResultCallback = new IResult() {
            @Override
            public void notifySuccess(JSONObject response) {
                initLoading();
            }
            @Override
            public void notifyError(VolleyError error) { }
        };
    }

    private void initLoading(){
        SharePref sharePref = new SharePref(this);
        Boolean isLogin = sharePref.getBoolean("isLogin");

        Utils.Log("isLogin => "+isLogin.toString());

        if (isLogin){
            itn = new Intent(this, LoadingUserInfoActivity.class);
        }else{
            itn = new Intent(this, LoginActivity.class);
        }
        startActivity(itn);
        finish();
    }
}