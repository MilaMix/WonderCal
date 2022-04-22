package com.example.milamix.wondercal.Page.MealPage;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.milamix.wondercal.R;

import java.util.List;

public class MealAdapter extends BaseAdapter {
    private List<Data> mDatas;
    private LayoutInflater mLayoutInflater;

    public MealAdapter(Context context, List<Data> aList) {
        mDatas = aList;
        mLayoutInflater = LayoutInflater.from(context);
    }


    static class ViewHolder {
        TextView ThFood;
        TextView EngFood;
        TextView Cal;
    }

    @Override
    public int getCount() {
        return mDatas.size();
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
            view.setTag(holder);
        } else {
            holder = (ViewHolder)view.getTag();
        }

        return view;
    }
}
