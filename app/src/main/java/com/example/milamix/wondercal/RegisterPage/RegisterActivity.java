package com.example.milamix.wondercal.RegisterPage;

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
import com.example.milamix.wondercal.LoginPage.LoginActivity;
import com.example.milamix.wondercal.R;
import com.example.milamix.wondercal.util.Utils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import cn.pedant.SweetAlert.SweetAlertDialog;

public class RegisterActivity extends AppCompatActivity {
    Intent itn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
    }

    public void btn_CreateAccount(View view) throws JSONException, AuthFailureError {
        EditText usernameTxt = (EditText)findViewById(R.id.editUsername);
        EditText emailTxt = (EditText)findViewById(R.id.editEmail);
        EditText passwordTxt = (EditText)findViewById(R.id.editPassword);
        EditText confirmPasswordTxt = (EditText)findViewById(R.id.editComfirmPassword);

        String password = passwordTxt.getText().toString();
        String confirmPassword = confirmPasswordTxt.getText().toString();

        JSONObject obj = new JSONObject();
        obj.put("username", usernameTxt.getText().toString());
        obj.put("email", emailTxt.getText().toString());
        obj.put("password", password);

        String url =  getResources().getString(R.string.api_endpoint);
        JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.POST,url+"/users/register",obj,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            String message = response.getString("message");
                            Utils.Log(response.toString());
                            new SweetAlertDialog(RegisterActivity.this)
                                    .setContentText("Register")
                                    .setConfirmButton("OK", new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sweetAlertDialog) {
                                    swapToLoginPage();
                                    sweetAlertDialog.dismiss();
                                }
                            }).show();
                        } catch (JSONException e) { }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        if(error.networkResponse.data!=null) {
                            try {
                                String body = new String(error.networkResponse.data,"UTF-8");
                                JSONObject bodyError = new JSONObject(body.toString());

                                Utils.Log("message => "+bodyError.getString("error"));
                                String message = bodyError.getString("error");

                                new SweetAlertDialog(RegisterActivity.this)
                                        .setContentText(message)
                                        .setConfirmButton("OK", new SweetAlertDialog.OnSweetClickListener() {
                                            @Override
                                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                                sweetAlertDialog.dismiss();
                                            }
                                        }).show();
                            } catch (UnsupportedEncodingException | JSONException e) { }
                        }else{
                            new SweetAlertDialog(RegisterActivity.this)
                                    .setContentText("Server Error")
                                    .setConfirmButton("OK", new SweetAlertDialog.OnSweetClickListener() {
                                        @Override
                                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                                            sweetAlertDialog.dismiss();
                                        }
                                    }).show();
                        }
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

    private void swapToLoginPage(){
        itn = new Intent(this, LoginActivity.class);
        startActivity(itn);
        finish();
    }
}