package com.example.milamix.wondercal.Page.MainPage;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.VolleyError;
import com.example.milamix.wondercal.Models.MealModels;
import com.example.milamix.wondercal.Models.ResponseErrorModels;
import com.example.milamix.wondercal.Models.ResponseModels;
import com.example.milamix.wondercal.Models.UserInfoModels;
import com.example.milamix.wondercal.Page.LoginPage.LoginActivity;
import com.example.milamix.wondercal.Page.MealActivity;
import com.example.milamix.wondercal.Page.UserinfoPage.LoadingUserInfoActivity;
import com.example.milamix.wondercal.Page.UserinfoPage.UserInfoActivity;
import com.example.milamix.wondercal.R;
import com.example.milamix.wondercal.Service.IResult;
import com.example.milamix.wondercal.Service.SharePref;
import com.example.milamix.wondercal.Service.VolleyService;
import com.example.milamix.wondercal.Utils.Utils;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;

import cn.pedant.SweetAlert.SweetAlertDialog;


public class MainFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private ImageView tvDate;
    private TextView tvDate1;

    private UserInfoModels users;
    private TextView UsersEmail;
    private ImageView breakfast;
    private ImageView lunch;
    private ImageView dinner;
    private ImageView img;

    private TextView txtW;
    private TextView txtH;
    private TextView txtB;
    private DatePickerDialog.OnDateSetListener setListener;

    Intent itn;

    IResult mResultCallback = null;
    VolleyService mVolleyService;

    IResult mResultCallback1 = null;
    VolleyService mVolleyService1;

    MealModels l;
    MealModels d;
    MealModels b;

    public MainFragment() {
        // Required empty public constructor
    }

    public static MainFragment newInstance(String param1, String param2) {
        MainFragment fragment = new MainFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);

        try {
            initLoad();
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (AuthFailureError authFailureError) {
            authFailureError.printStackTrace();
        }
        SweetAlertDialog pDialog = new SweetAlertDialog(getActivity(), SweetAlertDialog.PROGRESS_TYPE);
        pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        pDialog.setTitleText("Loading");
        pDialog.setCancelable(false);
        pDialog.show();

        Utils.delay(4, new Utils.DelayCallback() {
            @Override
            public void afterDelay() {
                pDialog.dismiss();
                setUp();
            }
        });
    }

    private void initLoad() throws JSONException, AuthFailureError {
        SharePref sharePref = new SharePref(getContext());
        String email = sharePref.getString("email");
        JSONObject obj = new JSONObject();
        obj.put("email", email);
        Utils.Log(obj.toString());
        initVolleyCallback();
        mVolleyService = new VolleyService(mResultCallback, getContext());
        mVolleyService.postDataVolleyWithToken("/usersInfo/get-users-info", obj);

        TextView selectDate = (TextView)getView().findViewById(R.id.date_select);
        JSONObject obj1 = new JSONObject();
        obj1.put("email", email);
        if(!selectDate.equals("Select Date") && !selectDate.equals("")){
            obj1.put("date", selectDate.getText().toString());
        }

        initVolleyCallback1();
        mVolleyService1 = new VolleyService(mResultCallback1, getContext());
        mVolleyService1.postDataVolleyWithToken("/usersLog/get-logFood", obj1);
    }

    void initVolleyCallback(){
        SharePref sharePref = new SharePref(getContext());
        mResultCallback = new IResult() {
            @Override
            public void notifySuccess(JSONObject response) {
                ResponseModels res = new ResponseModels(response);
                try {
                    if(res.getStatus().equalsIgnoreCase("success")) {
                        sharePref.saveObj("userInfo", res.getData());
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void notifyError(VolleyError error) {
                ResponseErrorModels err = new ResponseErrorModels(error);
                if(err.getStatusCode() == 401){
                    new SweetAlertDialog(getContext(),SweetAlertDialog.ERROR_TYPE)
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

    void initVolleyCallback1(){
        mResultCallback1 = new IResult() {
            @Override
            public void notifySuccess(JSONObject response) {
                ResponseModels res = new ResponseModels(response);
                try {
                    if(res.getStatus().equalsIgnoreCase("success")) {
                        Utils.Log("GetLogFood");
                        JSONObject data = res.getData();

                        JSONArray lunch = data.getJSONArray("lunch");
                        JSONArray dinner = data.getJSONArray("dinner");
                        JSONArray breakfast = data.getJSONArray("breakfast");

                        l = new MealModels(lunch);
                        b = new MealModels(breakfast);
                        d = new MealModels(dinner);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void notifyError(VolleyError error) {
                ResponseErrorModels err = new ResponseErrorModels(error);
                if(err.getStatusCode() == 401){
                    new SweetAlertDialog(getContext(),SweetAlertDialog.ERROR_TYPE)
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

    void setUp(){
        SharePref sharePref = new SharePref(getContext());
        try {
            JSONObject obj = sharePref.getObj("userInfo");
            Utils.Log("MainFragment");
            Utils.Log(obj.toString());
            users = new UserInfoModels(obj.getString("email"),
                    Integer.parseInt(obj.getString("height")),
                    Integer.parseInt(obj.getString("weight")),
                    Integer.parseInt(obj.getString("age")),
                    obj.getString("gender"),
                    obj.getDouble("activity"),
                    obj.getString("image"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        img = (ImageView)getView().findViewById(R.id.img_main_profile);
        txtW = (TextView)getView().findViewById(R.id.txtweight);
        txtH = (TextView)getView().findViewById(R.id.txtheight);
        txtB = (TextView)getView().findViewById(R.id.txtBMR);
        TextView totalCal = (TextView)getView().findViewById(R.id.totalCal);
        UsersEmail = (TextView) getView().findViewById(R.id.usersEmail);

        totalCal.setText(String.valueOf(l.getCal()+d.getCal()+b.getCal()));
        UsersEmail.setText(users.getEmail());
        txtH.setText(users.getHeight()+"");
        txtW.setText(users.getWeight()+"");
        txtB.setText(users.getBMR()+"");

        String url = users.getImg().equalsIgnoreCase("")?
                "https://ggsc.s3.amazonaws.com/images/uploads/The_Science-Backed_Benefits_of_Being_a_Dog_Owner.jpg":
                users.getImg();

        Picasso.get()
                .load(url)
                .placeholder(R.mipmap.ic_launcher).fit()
                .error(R.mipmap.ic_launcher)
                .into(img);

        Calendar c = Calendar.getInstance();
        int y = c.get(Calendar.YEAR);
        int m = c.get(Calendar.MONTH);
        int d = c.get(Calendar.DAY_OF_MONTH);

        tvDate = (ImageView) getView().findViewById(R.id.tv_text1);
        tvDate1 = (TextView) getView().findViewById(R.id.date_select);

        breakfast = getView().findViewById(R.id.breakfast);
        lunch = getView().findViewById(R.id.lunch);
        dinner = getView().findViewById(R.id.dinner);

        breakfast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                swapMeal("breakfast");
            }
        });

        lunch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                swapMeal("lunch");

            }
        });
        dinner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                swapMeal("dinner");
            }
        });

        tvDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), android.R.style.Theme_Holo_Light_Dialog, setListener, y, m, d);
                datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                datePickerDialog.show();
            }
        });

        setListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int y1, int m1, int d1) {
                String date = d1 + "/" + m1 + "/" + y1;
                tvDate1.setText(date);
            }
        };
    }

    void swapMeal(String meal){

        Intent itn = new Intent(getActivity(), MealActivity.class);
        Bundle bundle = new Bundle();
        itn.putExtras(bundle);
        itn.putExtra("Meal",meal);
        itn.putExtra("food_list",
                (meal=="dinner")?d.toString():
                        (meal=="lunch")?l.toString():b.toString());
        startActivity(itn);
    }

    void swapPage(String page){
        switch (page){
            case "Login":
                itn = new Intent(getContext(), LoginActivity.class);
                break;
            case "UsersInfo":
                itn = new Intent(getContext(), UserInfoActivity.class);
                break;
            case "Main":
                itn = new Intent(getContext(),MainActivity.class);
                break;
        }
        startActivity(itn);
        getActivity().finish();
    }
}




