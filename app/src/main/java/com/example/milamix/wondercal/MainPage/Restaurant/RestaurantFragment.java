package com.example.milamix.wondercal.MainPage.Restaurant;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.android.volley.VolleyError;
import com.example.milamix.wondercal.LoginPage.LoginActivity;
import com.example.milamix.wondercal.Models.RestaurantModels;
import com.example.milamix.wondercal.R;
import com.example.milamix.wondercal.Service.IResult;
import com.example.milamix.wondercal.Models.ResponseErrorModels;
import com.example.milamix.wondercal.Models.ResponseModels;
import com.example.milamix.wondercal.Service.VolleyService;
import com.example.milamix.wondercal.util.Utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RestaurantFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RestaurantFragment extends Fragment implements AdapterView.OnItemClickListener{
    Intent itn;

    private List<RestaurantModels> restaurantModels = new ArrayList<>();
    private ListView lv;
    RestaurantAdapter adapter;
    IResult mResultCallback = null;
    VolleyService mVolleyService;

    public static RestaurantFragment newInstance(String param1, String param2) {
        RestaurantFragment fragment = new RestaurantFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_restaurant, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        SweetAlertDialog pDialog = new SweetAlertDialog(getActivity(), SweetAlertDialog.PROGRESS_TYPE);
        pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        pDialog.setTitleText("Loading");
        pDialog.setCancelable(false);
        pDialog.show();

        initVolleyCallback();
        mVolleyService = new VolleyService(mResultCallback, getContext());
        mVolleyService.postDataVolleyWithToken("/restaurant/get-restaurant", null);

        Utils.delay(1, new Utils.DelayCallback() {
            @Override
            public void afterDelay() {
                setListView();
                pDialog.dismiss();
            }
        });
    }

    public void setListView(){
        lv = getView().findViewById(R.id.ListViewRestaurant);
        adapter = new RestaurantAdapter(getActivity(), restaurantModels);
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Intent itn = new Intent(getActivity(), RestaurantdetailActivity.class);
        itn.putExtra("id", restaurantModels.get(i).getRestaurant_id());
        itn.putExtra("name_en", restaurantModels.get(i).getRestaurantName_en());
        itn.putExtra("name_th", restaurantModels.get(i).getRestaurantName_th());
        itn.putExtra("latitude", restaurantModels.get(i).getLatitude());
        itn.putExtra("longtitude", restaurantModels.get(i).getLongtitude());
        itn.putExtra("detail", restaurantModels.get(i).getDetail());
        itn.putExtra("image", restaurantModels.get(i).getRestaurantImage());
        startActivity(itn);
    }

    void initVolleyCallback(){
        mResultCallback = new IResult() {
            @Override
            public void notifySuccess(JSONObject response) {
                ResponseModels res = new ResponseModels(response);
                try {
                    if(res.getStatus().equalsIgnoreCase("success")) {
                        JSONArray data = res.getDataJSONArr();
                        for(int i = 0 ; i< data.length() ;i++){
                            JSONObject obj = data.getJSONObject(i);
                            String img = obj.getString("img");
                            restaurantModels.add(
                                    new RestaurantModels(
                                            Integer.parseInt(obj.getString("id")),
                                            obj.getString("name_en"),
                                            obj.getString("name_th"),
                                            obj.getString("detail"),
                                            obj.getString("latitude"),
                                            obj.getString("longitude"),
                                            R.drawable.ic_launcher_background
                                    ));
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void notifyError(VolleyError error) {
                ResponseErrorModels err = new ResponseErrorModels(error);
                if(err.getStatusCode() == 401){
                    new SweetAlertDialog(getActivity(),SweetAlertDialog.ERROR_TYPE)
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
        itn = new Intent(getActivity(), LoginActivity.class);
        startActivity(itn);
        getActivity().finish();
    }
}