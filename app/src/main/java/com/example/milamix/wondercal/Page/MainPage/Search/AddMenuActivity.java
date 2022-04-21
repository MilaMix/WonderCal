package com.example.milamix.wondercal.Page.MainPage.Search;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.example.milamix.wondercal.Models.FoodModels;
import com.example.milamix.wondercal.Models.ResponseErrorModels;
import com.example.milamix.wondercal.Models.ResponseModels;
import com.example.milamix.wondercal.Models.RestaurantModels;
import com.example.milamix.wondercal.Page.LoginPage.LoginActivity;
import com.example.milamix.wondercal.Page.MainPage.MainActivity;
import com.example.milamix.wondercal.Page.RegisterPage.RegisterActivity;
import com.example.milamix.wondercal.Page.UserinfoPage.UserInfoActivity;
import com.example.milamix.wondercal.R;
import com.example.milamix.wondercal.Service.IResult;
import com.example.milamix.wondercal.Service.SharePref;
import com.example.milamix.wondercal.Service.VolleyService;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class AddMenuActivity extends AppCompatActivity {
    Intent itn;
    private FoodModels food;
    IResult mResultCallback = null;
    VolleyService mVolleyService;

    String[] meal_list = {"breakfast","lunch","dinner"};

    AutoCompleteTextView autoCompleteTxt;
    ArrayAdapter<String> adapterItems;
    private String meal = "";
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

        setup();
    }

    void setup(){
        TextView nameEn = (TextView)findViewById(R.id.addMenuFoodNameEng);
        TextView nameTh = (TextView)findViewById(R.id.addMenuFoodNameTh);
        ImageView img = (ImageView)findViewById(R.id.imageFood);

        nameEn.setText(food.getName_en());
        nameTh.setText(food.getName_th());
        Picasso.get()
                .load(food.getUrl_img())
                .placeholder(R.mipmap.ic_launcher).fit()
                .error(R.mipmap.ic_launcher)
                .into(img);

        autoCompleteTxt = findViewById(R.id.MealCompleteTextView);
        adapterItems = new ArrayAdapter<String>(this,R.layout.dropdown_ac_item,meal_list);
        autoCompleteTxt.setAdapter(adapterItems);
        autoCompleteTxt.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                meal = parent.getItemAtPosition(position).toString();
                Toast.makeText(getApplicationContext(), "Item: "+meal,Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    public void btn_add_food(View view) throws JSONException {

        SharePref sharePref = new SharePref(this);
        JSONObject obj = new JSONObject();
        obj.put("email",sharePref.getString("email"));
        obj.put("cal",food.getCal());
        obj.put("food",food.getName_en()+","+food.getName_th());
        obj.put("meal",meal);

        initVolleyCallback();
        mVolleyService = new VolleyService(mResultCallback, this);
        mVolleyService.postDataVolleyWithToken("/usersLog/add-food", obj);
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
                                        sweetAlertDialog.dismiss();
                                        finish();
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