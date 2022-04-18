package com.example.milamix.wondercal.Page.UserinfoPage;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.example.milamix.wondercal.Page.LoginPage.LoginActivity;
import com.example.milamix.wondercal.Models.UserInfoModels;
import com.example.milamix.wondercal.Page.MainPage.MainActivity;
import com.example.milamix.wondercal.R;
import com.example.milamix.wondercal.Service.IResult;
import com.example.milamix.wondercal.Models.ResponseErrorModels;
import com.example.milamix.wondercal.Models.ResponseModels;
import com.example.milamix.wondercal.Service.VolleyService;
import com.example.milamix.wondercal.Service.SharePref;
import com.example.milamix.wondercal.Utils.Utils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class UserInfoActivity extends AppCompatActivity {
    SharePref sharePref = new SharePref(this);
    Intent itn;
    UserInfoModels users = new UserInfoModels();
    IResult mResultCallback = null;
    VolleyService mVolleyService;

    String[] items = {"Little or no exercise","Exercise 1-2 times/week","Exercise2-3 times/week","Exercise 4-5 times/week","Exercise 6-7 times/week","Professional athlete"};
    String[] genders = {"Male","Female"};

    double[] itemsValue = {1.2,1.4,1.6,1.75,2.0,2.3};
    int index = 0;
    int index_gender = 0;

    private TextView emailView;

    AutoCompleteTextView autoCompleteTxt,autoCompleteTxt_gender;
    ArrayAdapter<String> adapterItems,adapterItems_gender;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);
        boolean isEditProfile = sharePref.getBoolean("isEditProfile");
        Utils.Log(String.valueOf(isEditProfile));

        if(isEditProfile){
            try {
                onEditProfile();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }else{
            onEditFirstTime();
        }
    }

    public void onEditProfile() throws JSONException {
        JSONObject obj = sharePref.getObj("userInfo");
        Utils.Log(obj.toString());
        EditText weight = findViewById(R.id.Weight);
        EditText height = findViewById(R.id.Height);
        EditText age = findViewById(R.id.Age);

        users.setAge(Integer.parseInt(obj.getString("age")));
        users.setHeight(Integer.parseInt(obj.getString("height")));
        users.setWeight(Integer.parseInt(obj.getString("weight")));
        weight.setText(String.valueOf(users.getWeight()));
        height.setText(String.valueOf(users.getHeight()));
        age.setText(String.valueOf(users.getAge()));
        select();
    }

    public void onEditFirstTime(){
        emailView = (TextView) findViewById(R.id.info_show_email);
        users.setEmail(sharePref.getString("email"));
        emailView.setText(users.getEmail());
        select();
    }

    void select(){
        autoCompleteTxt = findViewById(R.id.autoCompleteTextView);
        adapterItems = new ArrayAdapter<String>(this,R.layout.dropdown_ac_item,items);
        autoCompleteTxt.setAdapter(adapterItems);
        autoCompleteTxt.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                index = position;
                String item = parent.getItemAtPosition(position).toString();
                Toast.makeText(getApplicationContext(), "Item: "+item,Toast.LENGTH_SHORT).show();
            }
        });

        autoCompleteTxt_gender = findViewById(R.id.GenderCompleteTextView);
        adapterItems_gender = new ArrayAdapter<String>(this,R.layout.dropdown_ac_item,genders);
        autoCompleteTxt_gender.setAdapter(adapterItems_gender);
        autoCompleteTxt_gender.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                index_gender = position;
                String item = parent.getItemAtPosition(position).toString();
                Toast.makeText(getApplicationContext(), "Item: "+item,Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void btn_submit_userInfo(View view) throws JSONException {
        boolean isEditProfile = sharePref.getBoolean("isEditProfile");
        EditText weight = findViewById(R.id.Weight);
        EditText height = findViewById(R.id.Height);
        EditText age = findViewById(R.id.Age);

        users.setActivity(itemsValue[index]);
        users.setWeight(Integer.parseInt(weight.getText().toString()));
        users.setHeight(Integer.parseInt(height.getText().toString()));
        users.setEmail(sharePref.getString("email"));
        users.setGender(genders[index_gender]);
        users.setAge(Integer.parseInt(age.getText().toString()));
        users.setImg("");
        Utils.Log(users.getJSONObj().toString());
        initVolleyCallback();
        mVolleyService = new VolleyService(mResultCallback, this);
        mVolleyService.postDataVolleyWithToken(
                isEditProfile? "/usersInfo/update-users-info":"/usersInfo/create-users-info",
                users.getJSONObj());
    }

    void initVolleyCallback(){
        boolean isEditProfile = sharePref.getBoolean("isEditProfile");
        mResultCallback = new IResult() {
            @Override
            public void notifySuccess(JSONObject response) {
                ResponseModels res = new ResponseModels(response);
                try {
                    new SweetAlertDialog(UserInfoActivity.this,SweetAlertDialog.SUCCESS_TYPE)
                            .setContentText(res.getMessage())
                            .setConfirmButton("OK", new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sweetAlertDialog) {
                                    sweetAlertDialog.dismiss();
                                    if(isEditProfile) swapToLoadingUserInfoPage();
                                    else swapToUploadImagePage();
                                }
                            }).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void notifyError(VolleyError error) {
                ResponseErrorModels err = new ResponseErrorModels(error);
                if(err.getStatusCode() == 401){
                    new SweetAlertDialog(UserInfoActivity.this,SweetAlertDialog.ERROR_TYPE)
                            .setContentText("Session time out")
                            .setConfirmButton("OK", new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sweetAlertDialog) {
                                    sweetAlertDialog.dismiss();
                                    swapToLoginPage();
                                }
                            }).show();
                }else{

                    try {
                        new SweetAlertDialog(UserInfoActivity.this,SweetAlertDialog.ERROR_TYPE)
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
            }
        };
    }

    private void swapToUploadImagePage(){
        itn = new Intent(this, UploadImageActivity.class);
        itn.putExtra("email",users.getEmail());
        startActivity(itn);
        finish();
    }

    private void swapToLoadingUserInfoPage(){
        itn = new Intent(this, LoadingUserInfoActivity.class);
        startActivity(itn);
        finish();
    }

    private void swapToLoginPage(){
        itn = new Intent(this, LoginActivity.class);
        startActivity(itn);
        finish();
    }
}
