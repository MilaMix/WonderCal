package com.example.milamix.wondercal.LoginPage;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.android.volley.VolleyError;
import com.example.milamix.wondercal.ForgotPage.ForgotActivity;
import com.example.milamix.wondercal.Models.UserLoginModels;
import com.example.milamix.wondercal.R;
import com.example.milamix.wondercal.RegisterPage.RegisterActivity;
import com.example.milamix.wondercal.Service.IResult;
import com.example.milamix.wondercal.Models.ResponseErrorModels;
import com.example.milamix.wondercal.Models.ResponseModels;
import com.example.milamix.wondercal.Service.VolleyService;
import com.example.milamix.wondercal.UserinfoPage.LoadingUserInfoActivity;
import com.example.milamix.wondercal.Service.SharePref;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class LoginActivity extends AppCompatActivity {
    Intent itn;
    SharePref sharePref = new SharePref(this);
    IResult mResultCallback = null;
    VolleyService mVolleyService;
    UserLoginModels users;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    public void btn_Login(View view) throws JSONException {
        EditText email_txt = (EditText) findViewById(R.id.email);
        EditText password_txt = (EditText) findViewById(R.id.password);

        users = new UserLoginModels(
                email_txt.getText().toString(),
                password_txt.getText().toString());

        initVolleyCallback();
        mVolleyService = new VolleyService(mResultCallback, this);
        mVolleyService.postDataVolley("/users/login", users.getJSONObject());
    }

    void initVolleyCallback(){
        mResultCallback = new IResult() {
            @Override
            public void notifySuccess(JSONObject response) {
                try {
                    ResponseModels res = new ResponseModels(response);
                    sharePref.saveLoginPref(
                            res.getToken(),
                            res.getTime(),
                            users.getEmail());

                    new SweetAlertDialog(LoginActivity.this,SweetAlertDialog.SUCCESS_TYPE)
                            .setContentText(res.getMessage())
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
            @Override
            public void notifyError(VolleyError error) {
                    try {
                        ResponseErrorModels err = new ResponseErrorModels(error);
                        new SweetAlertDialog(LoginActivity.this,SweetAlertDialog.ERROR_TYPE)
                                .setContentText(err.getError())
                                .setConfirmButton("OK", new SweetAlertDialog.OnSweetClickListener() {
                                    @Override
                                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                                        sweetAlertDialog.dismiss();
                                    }
                                }).show();
                    } catch (UnsupportedEncodingException | JSONException e) {
                        e.printStackTrace();
                    }
            }
        };
    }

    public void btn_Register(View view) {
        itn = new Intent(this, RegisterActivity.class);
        startActivity(itn);
    }

    public void btn_Forgotpass(View view) {
        itn = new Intent(this, ForgotActivity.class);
        startActivity(itn);
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