package com.example.milamix.wondercal.Page.ForgotPage;

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
import com.example.milamix.wondercal.Page.LoginPage.LoginActivity;
import com.example.milamix.wondercal.R;
import com.example.milamix.wondercal.Utils.Utils;

import org.json.JSONException;
import org.json.JSONObject;

public class ForgotActivity extends AppCompatActivity {
    Intent itn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot);
    }

    public void btn_submit_forgotpass(View view) throws JSONException {
        EditText emailTxt = (EditText)findViewById(R.id.email_forgot) ;
        EditText passwordTxt = (EditText)findViewById(R.id.passwordlast);
        EditText new_passwordTxt = (EditText)findViewById(R.id.newpassword);

        String password = passwordTxt.getText().toString();
        String newPassword = new_passwordTxt.getText().toString();
        String email = emailTxt.getText().toString();

        JSONObject obj = new JSONObject();
        obj.put("email", email);
        obj.put("password", password);
        obj.put("new_password", newPassword);

        String url =  getResources().getString(R.string.api_endpoint);
        JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.POST,url+"/users/reset-password",obj,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Utils.Log(response.toString());
                        swapToLoginPage();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Utils.Log(error.toString());
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

    private void swapToLoginPage(){
        itn = new Intent(this, LoginActivity.class);
        startActivity(itn);
        finish();
    }
}