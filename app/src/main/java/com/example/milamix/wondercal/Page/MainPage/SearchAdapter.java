package com.example.milamix.wondercal.Page.MainPage;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.milamix.wondercal.R;

import java.util.List;

public class SearchAdapter extends BaseAdapter {
    private List<Data> mDatas;
    private LayoutInflater mLayoutInflater;

    public SearchAdapter(Context context, List<Data> aList) {
        mDatas = aList;
        mLayoutInflater = LayoutInflater.from(context);
    }


    static class ViewHolder {
        TextView tvFoodname;
        TextView tvCal;
        TextView tvP;
        ImageView imgFood;
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
            view = mLayoutInflater.inflate(R.layout.list_food, viewGroup, false);
            holder = new ViewHolder();
            holder.tvFoodname = (TextView) view.findViewById(R.id.EngFoodName);
            holder.tvCal = (TextView) view.findViewById(R.id.Cal);
            holder.tvP = (TextView) view.findViewById(R.id.TfoodName);


            holder.imgFood = (ImageView) view.findViewById(R.id.imgFood);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        String title = mDatas.get(position).getMtxt1();
        holder.tvFoodname.setText(title);
        holder.tvP.setText(mDatas.get(position).getMtxt2());
        holder.tvCal.setText(mDatas.get(position).getMtxt3());
        holder.imgFood.setImageResource(mDatas.get(position).getmIcon());

        return view;
    }
}
