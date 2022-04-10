package com.example.milamix.wondercal.UserinfoPage;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.milamix.wondercal.MainPage.MainActivity;
import com.example.milamix.wondercal.R;
import com.example.milamix.wondercal.sharePref.SharePref;
import com.example.milamix.wondercal.util.Utils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public class LoadingUserInfoActivity extends AppCompatActivity {
    Intent itn;
    SharePref sharePref = new SharePref(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading_user_info);
        try {
            initLoad();
        } catch (JSONException | AuthFailureError e) { }
    }

    private void initLoad() throws JSONException, AuthFailureError {
        String email = sharePref.getString("email");

        JSONObject obj = new JSONObject();
        obj.put("email", email);

        Utils.Log(obj.toString());

        String url =  getResources().getString(R.string.api_endpoint);
        JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.POST,url+"/usersInfo/get-users-info",obj,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            String status = response.getString("status");
                            Utils.Log(response.toString());
                            if(status.equalsIgnoreCase("success")){
                                JSONObject data = response.getJSONObject("data");
                                sharePref.saveObj("userInfo",data);
                                swapToMainPage();
                            }else{
                                swapToUserInfoPage();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        int code = error.networkResponse.statusCode;
                        Utils.Log(String.valueOf(code));
                    }
                }){
            @Override
            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json");
                headers.put("authorization",sharePref.getString("token"));
                return headers;
            }
        };
        Utils.LogAPIs(stringRequest);
        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(stringRequest);
    }
    private void swapToMainPage(){
        itn = new Intent(this, MainActivity.class);
        startActivity(itn);
        finish();
    }
    private void swapToUserInfoPage(){
        itn = new Intent(this, UserInfoActivity.class);
        startActivity(itn);
        finish();
    }
}