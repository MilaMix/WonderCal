package com.example.milamix.wondercal.Page.MainPage.Search;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.android.volley.VolleyError;
import com.example.milamix.wondercal.Models.FoodModels;
import com.example.milamix.wondercal.Models.ResponseErrorModels;
import com.example.milamix.wondercal.Models.ResponseModels;
import com.example.milamix.wondercal.Models.RestaurantModels;
import com.example.milamix.wondercal.Page.LoginPage.LoginActivity;
import com.example.milamix.wondercal.Page.RegisterPage.RegisterActivity;
import com.example.milamix.wondercal.R;
import com.example.milamix.wondercal.Service.IResult;
import com.example.milamix.wondercal.Service.SharePref;
import com.example.milamix.wondercal.Service.VolleyService;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class AddMenuActivity extends AppCompatActivity {
    Intent itn;
    private FoodModels food;
    IResult mResultCallback = null;
    VolleyService mVolleyService;
    JSONObject obj;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_menu);
        
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        if(bundle != null){
            food = new FoodModels(
                    bundle.getInt("id"),
                    bundle.getString("name_en"),
                    bundle.getString("name_th"),
                    bundle.getString("img"),
                    bundle.getInt("cal")
            );
        }


    }

    @Override
    public void onBackPressed() {
        finish();
    }

    public void addMeal(String meal) throws JSONException {
        SharePref sharePref = new SharePref(this);

        obj.put("email",sharePref.getString("email"));
        obj.put("id_food",food.getId());
        obj.put("meal",meal);

        initVolleyCallback();
        mVolleyService = new VolleyService(mResultCallback, this);
        mVolleyService.postDataVolleyWithToken("/food/add-food", null);
    }

    void initVolleyCallback(){
        mResultCallback = new IResult() {
            @Override
            public void notifySuccess(JSONObject response) {
                ResponseModels res = new ResponseModels(response);
                try {
                    if(res.getStatus().equalsIgnoreCase("success")) {
                        new SweetAlertDialog(AddMenuActivity.this)
                                .setContentText("Add food success")
                                .setConfirmButton("OK", new SweetAlertDialog.OnSweetClickListener() {
                                    @Override
                                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                                        swapToLoginPage();
                                        sweetAlertDialog.dismiss();
                                    }
                                }).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void notifyError(VolleyError error) {
                ResponseErrorModels err = new ResponseErrorModels(error);
                if(err.getStatusCode() == 401){
                    new SweetAlertDialog(AddMenuActivity.this,SweetAlertDialog.ERROR_TYPE)
                            .setContentText("Session time out")
                            .setConfirmButton("OK", new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sweetAlertDialog) {
                                    sweetAlertDialog.dismiss();
                                    swapToLoginPage();
                                }
                            }).show();
                }
            }
        };
    }
    private void swapToLoginPage(){
        itn = new Intent(this, LoginActivity.class);
        startActivity(itn);
        finish();
    }
}