package com.example.milamix.wondercal.Models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ResponseModels {
    private JSONObject response;
    public ResponseModels(JSONObject response){
        this.response = response;
    }
    public String getMessage() throws JSONException {
        return response.getString("message");
    }
    public JSONObject getData() throws JSONException {
        return response.getJSONObject("data");
    }
    public String getDataString() throws JSONException {
        return response.getString("data");
    }
    public String getToken() throws JSONException {
        JSONObject data = response.getJSONObject("data");
        return data.getString("token");
    }
    public String getTime() throws JSONException {
        JSONObject data = response.getJSONObject("data");
        return data.getString("time");
    }
    public String getStatus() throws JSONException {
        return response.getString("status");
    }
    public JSONArray getDataJSONArr() throws JSONException {
        return response.getJSONArray("data");
    }
}
