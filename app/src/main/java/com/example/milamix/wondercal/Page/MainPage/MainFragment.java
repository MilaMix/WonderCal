package com.example.milamix.wondercal.Page.MainPage;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.milamix.wondercal.Models.UserInfoModels;
import com.example.milamix.wondercal.Page.MealActivity;
import com.example.milamix.wondercal.R;
import com.example.milamix.wondercal.Service.SharePref;
import com.example.milamix.wondercal.Utils.Utils;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;


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
        SharePref sharePref = new SharePref(getContext());
        super.onViewCreated(view, savedInstanceState);
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
        UsersEmail = (TextView) getView().findViewById(R.id.usersEmail);

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
        tvDate1 = (TextView) getView().findViewById(R.id.tv_text);

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
        itn.putExtra("Meal",meal);
        startActivity(itn);
    }

}




