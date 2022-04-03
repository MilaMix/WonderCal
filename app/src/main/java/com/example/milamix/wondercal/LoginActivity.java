package com.example.milamix.wondercal;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    public void btn_Login(View view) throws JSONException {
        EditText email_txt = (EditText)findViewById(R.id.email);
        EditText password_txt = (EditText)findViewById(R.id.password);

        String email = email_txt.getText().toString();
        String password = password_txt.getText().toString();

        JSONObject obj = new JSONObject();
        obj.put("email", email);
        obj.put("password", password);

        String url =  getResources().getString(R.string.api_endpoint);
        JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.POST,url+"/users/login",obj,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {  // if OK
                        Log.d("HTTPResponse",response.toString());

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("HTTPResponse", error.toString());
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

    public void btn_Register(View view) {

    }

    public void btn_Forgotpass(View view) {
        Intent itn = new Intent(this, ForgotActivity.class);
        startActivity(itn);

    }
}