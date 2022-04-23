package com.example.milamix.wondercal.Service;

import android.content.Context;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class VolleyService {
    IResult mResultCallback = null;
    Context mContext;
    private String TAG = "HTTPD";
    private String host = "http://murphy.thddns.net:5150";

    public VolleyService(IResult resultCallback, Context context){
        mResultCallback = resultCallback;
        mContext = context;
    }

    public void postDataVolley(String url, JSONObject sendObj){
        try {
            Log.d(TAG,host+url);
            RequestQueue queue = Volley.newRequestQueue(mContext);
            JsonObjectRequest jsonObj = new JsonObjectRequest(
                    Request.Method.POST,
                    host+url,
                    sendObj,
                    new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    Log.d(TAG, "onResponse: "+response.toString());
                    if(mResultCallback != null)
                        mResultCallback.notifySuccess(response);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.d(TAG, "onResponse: "+error.toString());
                    if(mResultCallback != null)
                        mResultCallback.notifyError(error);
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
                    return headers;
                }
            };
            queue.add(jsonObj);
        }catch(Exception e){ }
    }

    public void postDataVolleyWithToken(String url, JSONObject sendObj){
        try {
            SharePref sharePref = new SharePref(mContext);
            Log.d(TAG,host+url);
            RequestQueue queue = Volley.newRequestQueue(mContext);
            JsonObjectRequest jsonObj = new JsonObjectRequest(
                    Request.Method.POST,
                    host+url,
                    sendObj,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            Log.d(TAG, "onResponse: "+response.toString());
                            if(mResultCallback != null)
                                mResultCallback.notifySuccess(response);
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.d(TAG, "onResponse: "+error.toString());
                    if(mResultCallback != null)
                        mResultCallback.notifyError(error);
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
            queue.add(jsonObj);
        }catch(Exception e){ }
    }
}
