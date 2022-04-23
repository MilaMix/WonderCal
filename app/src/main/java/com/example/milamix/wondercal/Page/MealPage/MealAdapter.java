package com.example.milamix.wondercal.Page.MealPage;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.android.volley.VolleyError;
import com.example.milamix.wondercal.Models.Food;
import com.example.milamix.wondercal.Models.ResponseErrorModels;
import com.example.milamix.wondercal.Models.ResponseModels;
import com.example.milamix.wondercal.Page.LoginPage.LoginActivity;
import com.example.milamix.wondercal.Page.MainPage.MainActivity;
import com.example.milamix.wondercal.Page.UserinfoPage.UserInfoActivity;
import com.example.milamix.wondercal.R;
import com.example.milamix.wondercal.Service.IResult;
import com.example.milamix.wondercal.Service.SharePref;
import com.example.milamix.wondercal.Service.VolleyService;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class MealAdapter extends BaseAdapter {
    private List<Food> food_list_data;
    private LayoutInflater mLayoutInflater;
    private Context context;
    IResult mResultCallback = null;
    VolleyService mVolleyService;
    Intent itn;

    public MealAdapter(Context context, List<Food> food_list_data) {
        this.context = context;
        this.food_list_data = food_list_data;
        this.mLayoutInflater = LayoutInflater.from(context);
    }


    static class ViewHolder {
        TextView ThFood;
        TextView EngFood;
        TextView Cal;
        ImageView Trash;
    }

    @Override
    public int getCount() {
        return food_list_data.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }
    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        if (view == null) {
            view = mLayoutInflater.inflate(R.layout.food_name_meal,viewGroup,false);
            holder = new ViewHolder();
            holder.ThFood = (TextView)view.findViewById(R.id.ThFood);
            holder.EngFood = (TextView)view.findViewById(R.id.EngFood);
            holder.Cal = (TextView)view.findViewById(R.id.Cal);
            holder.Trash = (ImageView)view.findViewById(R.id.Trash);
            view.setTag(holder);
        } else {
            holder = (ViewHolder)view.getTag();
        }

        holder.ThFood.setText(food_list_data.get(position).getName_th());
        holder.EngFood.setText(food_list_data.get(position).getName_en());
        holder.Cal.setText(String.valueOf(food_list_data.get(position).getCal()));

        holder.Trash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    new SweetAlertDialog(context,SweetAlertDialog.ERROR_TYPE)
                            .setContentText("Delete?")
                            .setConfirmButton("OK", new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sweetAlertDialog) {
                                    initVolleyCallback();
                                    mVolleyService = new VolleyService(mResultCallback, context);
                                    try {
                                        mVolleyService.postDataVolleyWithToken("/usersInfo/get-users-info",
                                                food_list_data.get(position).getJSONObj());
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }).setCancelButton("Cancel", new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                            sweetAlertDialog.dismiss();
                        }
                    }).show();
            }
        });
        return view;
    }

    void initVolleyCallback(){
        mResultCallback = new IResult() {
            @Override
            public void notifySuccess(JSONObject response) {
                ResponseModels res = new ResponseModels(response);
                try {
                    if(res.getStatus().equalsIgnoreCase("success")) {
                        ((Activity)context).finish();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void notifyError(VolleyError error) {
                ResponseErrorModels err = new ResponseErrorModels(error);
                if(err.getStatusCode() == 401){
                    new SweetAlertDialog(context,SweetAlertDialog.ERROR_TYPE)
                            .setContentText("Session time out")
                            .setConfirmButton("OK", new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sweetAlertDialog) {
                                    sweetAlertDialog.dismiss();
                                    ((Activity)context).finish();
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
                itn = new Intent(context, LoginActivity.class);
                break;
            case "UsersInfo":
                itn = new Intent(context, UserInfoActivity.class);
                break;
            case "Main":
                itn = new Intent(context, MainActivity.class);
                break;
        }
        ((Activity)context).finish();
        context.startActivity(itn);
    }
}
