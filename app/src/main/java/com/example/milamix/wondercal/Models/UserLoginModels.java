package com.example.milamix.wondercal.Models;

import org.json.JSONException;
import org.json.JSONObject;

public class UserLoginModels {
    private String email;
    private String password;

    public UserLoginModels(String email,String password){
        this.email = email;
        this.password = password;
    }

    public String getEmail(){
        return this.email;
    }

    public String getPassword(){
        return this.password;
    }

    public JSONObject getJSONObject() throws JSONException {
        JSONObject obj = new JSONObject();
        obj.put("email", this.email);
        obj.put("password", this.password);
        return obj;
    }
}
