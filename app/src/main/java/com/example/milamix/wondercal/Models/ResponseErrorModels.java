package com.example.milamix.wondercal.Models;

import com.android.volley.VolleyError;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

public class ResponseErrorModels {
    private VolleyError error;

    public ResponseErrorModels(VolleyError error){
        this.error = error;
    }

    public String getError() throws JSONException, UnsupportedEncodingException {
        String body = new String(error.networkResponse.data,"UTF-8");
        JSONObject bodyError = new JSONObject(body.toString());
        return bodyError.getString("error");
    }

    public int getStatusCode(){
        return error.networkResponse.statusCode;
    }
}
