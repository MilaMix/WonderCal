package com.example.milamix.wondercal.APIs;

import com.example.milamix.wondercal.LoginPage.LoginActivity;
import com.example.milamix.wondercal.R;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.milamix.wondercal.util.Utils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class serviceAPIs {
    //private String URL =
    public void LoginAPIs(JSONObject obj){
        JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.POST,url+"/users/login",obj,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        try {
                            String message = response.getString("message");
                            Utils.Log(response.toString());

                            JSONObject data = response.getJSONObject("data");
                            String token = data.getString("token");
                            String time = data.getString("time");

                            new SweetAlertDialog(LoginActivity.this)
                                    .setTitleText(message)
                                    .show();

                            sharePref.saveString("token",token);
                            sharePref.saveBoolean("isLogin",true);
                            sharePref.saveString("lastLogin",time);

                            swapToMainPage();
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
                        if(error.networkResponse.data!=null) {
                            try {
                                String body = new String(error.networkResponse.data,"UTF-8");
                                JSONObject bodyError = new JSONObject(body.toString());

                                Utils.Log(bodyError.getString("error"));
                                String message = bodyError.getString("error");
                                
                            } catch (UnsupportedEncodingException | JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }){
            @Override
            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }
        };
        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(stringRequest);
    }
}
