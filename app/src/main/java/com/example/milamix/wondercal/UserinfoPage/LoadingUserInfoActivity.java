package com.example.milamix.wondercal.UserinfoPage;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.android.volley.AuthFailureError;
import com.android.volley.VolleyError;
import com.example.milamix.wondercal.LoginPage.LoginActivity;
import com.example.milamix.wondercal.MainPage.MainActivity;
import com.example.milamix.wondercal.R;
import com.example.milamix.wondercal.Service.IResult;
import com.example.milamix.wondercal.Models.ResponseErrorModels;
import com.example.milamix.wondercal.Models.ResponseModels;
import com.example.milamix.wondercal.Service.VolleyService;
import com.example.milamix.wondercal.Service.SharePref;
import com.example.milamix.wondercal.util.Utils;

import org.json.JSONException;
import org.json.JSONObject;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class LoadingUserInfoActivity extends AppCompatActivity {
    Intent itn;
    SharePref sharePref = new SharePref(this);
    IResult mResultCallback = null;
    VolleyService mVolleyService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading_user_info);
        try {
            initLoad();
        } catch (JSONException | AuthFailureError e) { }
    }

    private void initLoad() throws JSONException, AuthFailureError {
        String email = sharePref.getString("email");
        JSONObject obj = new JSONObject();
        obj.put("email", email);
        Utils.Log(obj.toString());
        initVolleyCallback();
        mVolleyService = new VolleyService(mResultCallback, this);
        mVolleyService.postDataVolleyWithToken("/usersInfo/get-users-info", obj);
    }

    void initVolleyCallback(){
        boolean isEditProfile = sharePref.getBoolean("isEditProfile");
        mResultCallback = new IResult() {
            @Override
            public void notifySuccess(JSONObject response) {
                ResponseModels res = new ResponseModels(response);
                try {
                    if(res.getStatus().equalsIgnoreCase("success")){
                        sharePref.saveObj("userInfo",res.getData());
                        sharePref.saveBoolean("isEditProfile",false);
                        if(isEditProfile) finish();
                        else swapPage("Main");
                    }else swapPage("UsersInfo");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void notifyError(VolleyError error) {
                ResponseErrorModels err = new ResponseErrorModels(error);
                if(err.getStatusCode() == 401){
                    new SweetAlertDialog(LoadingUserInfoActivity.this,SweetAlertDialog.ERROR_TYPE)
                            .setContentText("Session time out")
                            .setConfirmButton("OK", new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sweetAlertDialog) {
                                    sweetAlertDialog.dismiss();
                                    swapPage("Login");
                                }
                            }).show();
                }
            }
        };
    }

    void swapPage(String page){
        switch (page){
            case "Login":
                itn = new Intent(this,LoginActivity.class);
                break;
            case "UsersInfo":
                itn = new Intent(this,UserInfoActivity.class);
                break;
            case "Main":
                itn = new Intent(this,MainActivity.class);
                break;
        }
        startActivity(itn);
        finish();
    }
}