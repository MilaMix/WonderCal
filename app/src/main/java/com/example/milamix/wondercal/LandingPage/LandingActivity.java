package com.example.milamix.wondercal.LandingPage;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.milamix.wondercal.LoginPage.LoginActivity;
import com.example.milamix.wondercal.MainPage.MainActivity;
import com.example.milamix.wondercal.R;
import com.example.milamix.wondercal.UserinfoPage.LoadingUserInfoActivity;
import com.example.milamix.wondercal.sharePref.SharePref;
import com.example.milamix.wondercal.util.Utils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class LandingActivity extends AppCompatActivity {
    Intent itn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing);

        String url =  getResources().getString(R.string.api_endpoint);
        JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.GET,url,null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                            Utils.Log(response.toString());
                            initLoading();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                    }
                }){
            @Override
            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }
        };
        try {
            Utils.LogAPIs(stringRequest);
        } catch (AuthFailureError authFailureError) {
            authFailureError.printStackTrace();
        }
        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(stringRequest);
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