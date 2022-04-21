package com.example.milamix.wondercal.Page.MainPage.Search;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.milamix.wondercal.Models.FoodModels;
import com.example.milamix.wondercal.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class SearchAdapter extends BaseAdapter {
    private List<FoodModels> food_list;
    private LayoutInflater mLayoutInflater;

    public SearchAdapter(Context context, List<FoodModels> food_list) {
        this.food_list = food_list;
        this.mLayoutInflater = LayoutInflater.from(context);
    }


    static class ViewHolder {
        TextView tvFoodname;
        TextView tvCal;
        TextView tvP;
        ImageView imgFood;
    }

    @Override
    public int getCount() {
        return food_list.size();
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
            view = mLayoutInflater.inflate(R.layout.list_food, viewGroup, false);
            holder = new ViewHolder();
            holder.tvFoodname = (TextView) view.findViewById(R.id.EngFoodName);
            holder.tvCal = (TextView) view.findViewById(R.id.Calorie);
            holder.tvP = (TextView) view.findViewById(R.id.TfoodName);
            holder.imgFood = (ImageView) view.findViewById(R.id.imgFood);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        holder.tvCal.setText(String.valueOf(food_list.get(position).getCal()) + " Cal");
        holder.tvFoodname.setText(food_list.get(position).getName_en());
        holder.tvP.setText(food_list.get(position).getName_th());

        Picasso.get()
                .load(food_list.get(position).getUrl_img())
                .placeholder(R.mipmap.ic_launcher).fit()
                .error(R.mipmap.ic_launcher)
                .into(holder.imgFood);

        return view;
    }
}
