package com.example.milamix.wondercal.LoginPage;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.android.volley.AuthFailureError;
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
import com.example.milamix.wondercal.UserinfoPage.LoadingUserInfoActivity;
import com.example.milamix.wondercal.UserinfoPage.UserInfoActivity;
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

    
    public void btn_Login(View view) throws JSONException, AuthFailureError {
        EditText email_txt = (EditText)findViewById(R.id.email);
        EditText password_txt = (EditText)findViewById(R.id.password);

        String email = email_txt.getText().toString();
        String password = password_txt.getText().toString();

        JSONObject obj = new JSONObject();
        obj.put("email", email);
        obj.put("password", password);

        Utils.Log("request : "+obj.toString());
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

                            sharePref.saveLoginPref(token,time,email);

                            new SweetAlertDialog(LoginActivity.this,SweetAlertDialog.SUCCESS_TYPE)
                                    .setContentText(message)
                                    .setConfirmButton("OK", new SweetAlertDialog.OnSweetClickListener() {
                                        @Override
                                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                                            sweetAlertDialog.dismiss();
                                            swapToLoadingUserInfoPage();
                                        }
                                    }).show();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        int code = error.networkResponse.statusCode;

                        Utils.Log("Response : "+error.toString());
                        Utils.Log("responseCode : "+String.valueOf(code));
                        if(error.networkResponse.data!=null) {
                            try {
                                String body = new String(error.networkResponse.data,"UTF-8");
                                JSONObject bodyError = new JSONObject(body.toString());

                                Utils.Log(bodyError.getString("error"));
                                String message = bodyError.getString("error");

                                new SweetAlertDialog(LoginActivity.this,SweetAlertDialog.ERROR_TYPE)
                                        .setContentText(message)
                                        .setConfirmButton("OK", new SweetAlertDialog.OnSweetClickListener() {
                                            @Override
                                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                                sweetAlertDialog.dismiss();
                                            }
                                        }).show();
                            } catch (UnsupportedEncodingException | JSONException e) {
                                e.printStackTrace();
                            }

                        }else return;
                    }
                }){
            @Override
            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }
        };
        Utils.LogAPIs(stringRequest);
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

    private void swapToLoadingUserInfoPage(){
        itn = new Intent(this, LoadingUserInfoActivity.class);
        startActivity(itn);
        finish();
    }

    @Override
    public void onBackPressed() {
        new SweetAlertDialog(LoginActivity.this,SweetAlertDialog.WARNING_TYPE)
                .setContentText("Are you sure to close App")
                .setConfirmButton("OK", new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        sweetAlertDialog.dismiss();
                        finish();
                    }
                })
                .setCancelButton("Cancel", new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        sweetAlertDialog.dismiss();
                    }
                }).show();
    }
}