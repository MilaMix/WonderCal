package com.example.milamix.wondercal.MainPage.Restaurant;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.milamix.wondercal.R;

import java.util.List;

public class RestaurantAdapter extends BaseAdapter {
    private List<RestaurantData> RestaurantDatas;
    private LayoutInflater RestaurantLayoutInflater;

    public RestaurantAdapter(Context context, List<RestaurantData> aList) {
        RestaurantDatas = aList;
        RestaurantLayoutInflater = LayoutInflater.from(context);
    }
    static class ViewHolder {
        TextView RestaurantName;
        TextView RestaurantDesc;
        ImageView RestaurantImage;
    }

    @Override
    public int getCount() {
        return RestaurantDatas.size();
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
            view = RestaurantLayoutInflater.inflate(R.layout.list_restaurant,viewGroup,false);
            holder = new ViewHolder();
            holder.RestaurantName = (TextView)view.findViewById(R.id.NameRestaurant);
            holder.RestaurantDesc = (TextView)view.findViewById(R.id.DescRestaurant);
            holder.RestaurantImage = (ImageView)view.findViewById(R.id.ImageRestaurant);
            view.setTag(holder);
        } else {
            holder = (ViewHolder)view.getTag();
        }

        String Name = RestaurantDatas.get(position).getRestaurantName_en();
        holder.RestaurantName.setText(Name);
        holder.RestaurantDesc.setText(RestaurantDatas.get(position).getRestaurantName_th());
        holder.RestaurantImage.setImageResource(RestaurantDatas.get(position).getRestaurantImage());
        return view;
    }
}
