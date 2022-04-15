package com.example.milamix.wondercal.MainPage.Restaurant;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.milamix.wondercal.LoginPage.LoginActivity;
import com.example.milamix.wondercal.R;
import com.example.milamix.wondercal.UserinfoPage.LoadingUserInfoActivity;
import com.example.milamix.wondercal.sharePref.SharePref;
import com.example.milamix.wondercal.util.Utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RestaurantFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RestaurantFragment extends Fragment implements AdapterView.OnItemClickListener{
    Intent itn;
    SharePref sharePref = new SharePref(getActivity());
    private List<RestaurantData> RestaurantDatas = new ArrayList<>();
    private ListView lv;
    RestaurantAdapter adapter;

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
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_restaurant, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        onfetchAPIs();

        Utils.delay(1, new Utils.DelayCallback() {
            @Override
            public void afterDelay() {
                setListView();
            }
        });
    }

    public void setListView(){
        lv = getView().findViewById(R.id.ListViewRestaurant);
        adapter = new RestaurantAdapter(getActivity(),RestaurantDatas);
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Intent itn = new Intent(getActivity(), RestaurantdetailActivity.class);
        itn.putExtra("id",RestaurantDatas.get(i).getRestaurant_id());
        itn.putExtra("name_en",RestaurantDatas.get(i).getRestaurantName_en());
        itn.putExtra("name_th",RestaurantDatas.get(i).getRestaurantName_th());
        itn.putExtra("latitude",RestaurantDatas.get(i).getLatitude());
        itn.putExtra("longtitude",RestaurantDatas.get(i).getLongtitude());
        itn.putExtra("detail",RestaurantDatas.get(i).getDetail());
        itn.putExtra("image",RestaurantDatas.get(i).getRestaurantImage());
        startActivity(itn);
    }

    public void onfetchAPIs(){
        String url =  getResources().getString(R.string.api_endpoint);
        JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.POST,
                url+"/restaurant/get-restaurant",null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        String[] datas;
                        try {
                            String status = response.getString("status");
                            Utils.Log(response.toString());
                            if(status.equalsIgnoreCase("success")) {
                                JSONArray data = response.getJSONArray("data");
                                int dataLength = data.length();
                                Map<String,String> JsonItem = new HashMap<String,String>();
                                for(int i = 0 ; i< dataLength ;i++){
                                    JSONObject obj = data.getJSONObject(i);
                                    int id = Integer.parseInt(obj.getString("id"));
                                    String name_th = obj.getString("name_th");
                                    String name_en = obj.getString("name_en");
                                    String detail = obj.getString("detail");
                                    String longitude = obj.getString("longitude");
                                    String latitude = obj.getString("latitude");
                                    String img = obj.getString("img");
                                    RestaurantDatas.add(
                                            new RestaurantData(
                                                    id,
                                                    name_en,
                                                    name_th,
                                                    detail,
                                                    latitude,
                                                    longitude,
                                                    R.drawable.ic_launcher_background
                                            ));
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        int code = error.networkResponse.statusCode;
                        if(code == 401){
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
                        Utils.Log(String.valueOf(code));
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
                //headers.put("authorization",sharePref.getString("token"));
                return headers;
            }
        };
        try {
            Utils.LogAPIs(stringRequest);
        } catch (AuthFailureError authFailureError) {
            authFailureError.printStackTrace();
        }
        RequestQueue queue = Volley.newRequestQueue(getActivity());
        queue.add(stringRequest);
    }

    private void swapToLoginPage(){
        itn = new Intent(getActivity(), LoginActivity.class);
        startActivity(itn);
        getActivity().finish();
    }
}