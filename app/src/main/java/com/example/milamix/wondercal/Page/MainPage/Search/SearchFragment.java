package com.example.milamix.wondercal.Page.MainPage.Search;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import com.android.volley.VolleyError;
import com.example.milamix.wondercal.Models.FoodModels;
import com.example.milamix.wondercal.Models.ResponseErrorModels;
import com.example.milamix.wondercal.Models.ResponseModels;
import com.example.milamix.wondercal.Models.RestaurantModels;
import com.example.milamix.wondercal.Page.LoginPage.LoginActivity;
import com.example.milamix.wondercal.Page.MainPage.Data;
import com.example.milamix.wondercal.Page.MainPage.Restaurant.RestaurantAdapter;
import com.example.milamix.wondercal.R;
import com.example.milamix.wondercal.Service.IResult;
import com.example.milamix.wondercal.Service.VolleyService;
import com.example.milamix.wondercal.Utils.Utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class SearchFragment extends Fragment implements AdapterView.OnItemClickListener{
    Intent itn;
    private ListView lv;
    SearchAdapter adapter;
    IResult mResultCallback = null;
    VolleyService mVolleyService;

    SearchView searchBar;
    private List<FoodModels> food_list = new ArrayList<>();
    private List<FoodModels> filter_food_list;
    public SearchFragment() {
    }

    public static SearchFragment newInstance(String param1, String param2) {
        SearchFragment fragment = new SearchFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        searchBar = (SearchView)getActivity().findViewById(R.id.search_bar);
        SweetAlertDialog pDialog = new SweetAlertDialog(getActivity(), SweetAlertDialog.PROGRESS_TYPE);
        pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        pDialog.setTitleText("Loading");
        pDialog.setCancelable(false);
        pDialog.show();


        initVolleyCallback();
        mVolleyService = new VolleyService(mResultCallback, getContext());
        mVolleyService.postDataVolleyWithToken("/food/get-food", null);

        Utils.delay(4, new Utils.DelayCallback() {
            @Override
            public void afterDelay() {
                setListView();
                pDialog.dismiss();
            }
        });

        searchBar.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public boolean onQueryTextChange(String newText) {
                callSearch(newText);
                return true;
            }

            @RequiresApi(api = Build.VERSION_CODES.N)
            public void callSearch(String query) {
                //Utils.Log("Listdefault : "+String.valueOf(food_list.size()));
                //Utils.Log("Listfilter : "+String.valueOf(filter_food_list.size()));
                //filter_food_list.removeIf(s -> !s.getName_en().contains(query));
                setFilterListView(filterFood(query));
            }
        });
    }

    public List<FoodModels> filterFood(String text){
        List <FoodModels> listClone = new ArrayList<>();
        for (FoodModels obj : food_list) {
            if(obj.getName_en().matches("(?i)(" +text+").*") ||
                    obj.getName_th().matches("(?i)(" +text+").*") )
                listClone.add(new FoodModels(
                        obj.getId(),
                        obj.getName_en(),
                        obj.getName_th(),
                        obj.getUrl_img(),
                        obj.getCal()
                ));
        }
        return listClone;
    }


    public void setFilterListView(List<FoodModels> filterFood){
        lv = getView().findViewById(R.id.ListViewFood);
        adapter = new SearchAdapter(getActivity(), filterFood);
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(this);
    }

    public void setListView(){
        lv = getView().findViewById(R.id.ListViewFood);
        adapter = new SearchAdapter(getActivity(), food_list);
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(this);
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
                            food_list.add(
                                    new FoodModels(
                                            Integer.parseInt(obj.getString("id")),
                                            obj.getString("name_en"),
                                            obj.getString("name_th"),
                                            obj.getString("image_url"),
                                            Integer.parseInt(obj.getString("calories"))
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


    @Override
    public void onCreate(Bundle savedInstanceState) { super.onCreate(savedInstanceState); }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_search, container, false);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

    }

    private void swapToLoginPage(){
        itn = new Intent(getActivity(), LoginActivity.class);
        startActivity(itn);
        getActivity().finish();
    }
}
