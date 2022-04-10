package com.example.milamix.wondercal.UserinfoPage;

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

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.milamix.wondercal.LoginPage.LoginActivity;
import com.example.milamix.wondercal.MainPage.MainActivity;
import com.example.milamix.wondercal.R;
import com.example.milamix.wondercal.RegisterPage.RegisterActivity;
import com.example.milamix.wondercal.sharePref.SharePref;
import com.example.milamix.wondercal.util.Utils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class UserInfoActivity extends AppCompatActivity {
    SharePref sharePref = new SharePref(this);
    Intent itn;

    String[] items = {"Little or no exercise","Exercise 1-2 times/week","Exercise2-3 times/week","Exercise 4-5 times/week","Exercise 6-7 times/week","Professional athlete"};
    String[] genders = {"Male","Female"};

    double[] itemsValue = {1.2,1.4,1.6,1.75,2.0,2.3};
    int index = 0;
    int index_gender = 0;

    private TextView emailView;

    AutoCompleteTextView autoCompleteTxt;
    ArrayAdapter<String> adapterItems;

    AutoCompleteTextView autoCompleteTxt_gender;
    ArrayAdapter<String> adapterItems_gender;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);
        emailView = (TextView) findViewById(R.id.info_show_email);
        emailView.setText(sharePref.getString("email"));

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

    public void btn_submit_userInfo(View view) throws JSONException, AuthFailureError {
        EditText weight = findViewById(R.id.Weight);
        EditText height = findViewById(R.id.Height);
        EditText age = findViewById(R.id.Age);

        JSONObject obj = new JSONObject();
        obj.put("email", sharePref.getString("email"));
        obj.put("weight", weight.getText().toString());
        obj.put("height", height.getText().toString());
        obj.put("age", age.getText().toString());
        obj.put("gender", genders[index_gender]);
        obj.put("activity", itemsValue[index]);

        Utils.Log("Body => "+obj.toString());

        String url =  getResources().getString(R.string.api_endpoint);
        JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.POST,url+"/usersInfo/create-users-info",obj,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            String message = response.getString("status");
                            Utils.Log(response.toString());

                            new SweetAlertDialog(UserInfoActivity.this)
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

                                new SweetAlertDialog(UserInfoActivity.this)
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
                        }
                    }
                }){
            @Override
            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json");
                headers.put("authorization",sharePref.getString("token"));
                return headers;
            }
        };

        Utils.LogAPIs(stringRequest);
        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(stringRequest);
    }

    private void swapToLoadingUserInfoPage(){
        itn = new Intent(this, LoadingUserInfoActivity.class);
        startActivity(itn);
        finish();
    }
}
