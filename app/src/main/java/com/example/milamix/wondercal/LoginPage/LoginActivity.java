package com.example.milamix.wondercal.LoginPage;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.milamix.wondercal.ForgotPage.ForgotActivity;
import com.example.milamix.wondercal.MainPage.MainActivity;
import com.example.milamix.wondercal.R;
import com.example.milamix.wondercal.RegisterPage.RegisterActivity;
import com.example.milamix.wondercal.TestData.Test;
import com.example.milamix.wondercal.sharePref.SharePref;
import com.example.milamix.wondercal.util.Utils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class LoginActivity extends AppCompatActivity {
    Intent itn;
    SharePref sharePref = new SharePref(this);

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

        Test t1 = new Test();
        if(t1.LoginTest(email,password)){
            swapToMainPage();
            return;
        }

        JSONObject obj = new JSONObject();
        obj.put("email", email);
        obj.put("password", password);

        String url =  getResources().getString(R.string.api_endpoint);
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

                                new SweetAlertDialog(LoginActivity.this, SweetAlertDialog.ERROR_TYPE)
                                        .setTitleText("Oops...")
                                        .setContentText(message)
                                        .show();
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

    public void btn_Register(View view) {
        itn = new Intent(this, RegisterActivity.class);
        startActivity(itn);
    }

    public void btn_Forgotpass(View view) {
        itn = new Intent(this, ForgotActivity.class);
        startActivity(itn);
    }

    private void swapToMainPage(){
        itn = new Intent(this, MainActivity.class);
        startActivity(itn);
        finish();
    }
}