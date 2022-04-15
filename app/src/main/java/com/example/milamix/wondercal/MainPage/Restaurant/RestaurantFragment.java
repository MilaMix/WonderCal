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

import com.example.milamix.wondercal.R;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RestaurantFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RestaurantFragment extends Fragment implements AdapterView.OnItemClickListener{
    private List<RestaurantData> RestaurantDatas = new ArrayList<>();

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

        RestaurantDatas.add(new RestaurantData("Dawn FM","The Weeknd",R.drawable.ic_launcher_background));
        RestaurantDatas.add(new RestaurantData("Starboy","The Weeknd",R.drawable.ic_launcher_background));
        RestaurantDatas.add(new RestaurantData("My Dear Malencholy,","The Weeknd",R.drawable.ic_launcher_background));
        RestaurantDatas.add(new RestaurantData("Save Your Tear Remix","The Weeknd, Ariana Grande",R.drawable.ic_launcher_background));
        RestaurantDatas.add(new RestaurantData("Monopoly","Ariana Grande",R.drawable.ic_launcher_background));
        RestaurantDatas.add(new RestaurantData("Position","Ariana Grande",R.drawable.ic_launcher_background));
        RestaurantDatas.add(new RestaurantData("Cold Heart (PNU Remix)","Elton John, Dua Lipa",R.drawable.ic_launcher_background));
        RestaurantDatas.add(new RestaurantData("Hard Written","Shawn Mendes",R.drawable.ic_launcher_background));
        RestaurantDatas.add(new RestaurantData("Sweetener","Shawn Mendes",R.drawable.ic_launcher_background));
        RestaurantDatas.add(new RestaurantData("Justice","Justin Bieber",R.drawable.ic_launcher_background));
        RestaurantDatas.add(new RestaurantData("Stay","Justin Bieber",R.drawable.ic_launcher_background));

        ListView lv = getView().findViewById(R.id.ListViewRestaurant);
        RestaurantAdapter adapter = new RestaurantAdapter(getActivity(),RestaurantDatas);
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Intent itn = new Intent(getActivity(), RestaurantdetailActivity.class);
        itn.putExtra("id",i);
        itn.putExtra("ProductName",RestaurantDatas.get(i).getRestaurantName());
        itn.putExtra("img",RestaurantDatas.get(i).getRestaurantImage());
        startActivity(itn);
    }
}