package com.example.milamix.wondercal.Page.MainPage;

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

import com.example.milamix.wondercal.Models.RestaurantModels;
import com.example.milamix.wondercal.Page.MainPage.Restaurant.RestaurantAdapter;
import com.example.milamix.wondercal.R;
import com.example.milamix.wondercal.Service.VolleyService;
import com.example.milamix.wondercal.Utils.Utils;

import java.util.ArrayList;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SearchFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SearchFragment extends Fragment implements AdapterView.OnItemClickListener{
    Intent itn;
    private ListView lv;
    private List<Data> DataS = new ArrayList<>();
    SearchAdapter adapterS;



    public SearchFragment() {
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SearchFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SearchFragment newInstance(String param1, String param2) {
        SearchFragment fragment = new SearchFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        lv = getView().findViewById(R.id.lvFood);
        adapterS = new SearchAdapter(getActivity(), DataS);
        lv.setAdapter(adapterS);
        lv.setOnItemClickListener(this);
        DataS.add(new Data("Dawn tvPtvPtvPtvPtvPtvPtvPtvPtvPtvPtvPtvPtvPtvPtFish maw soup","The Weeknd","55.12 Cal",R.drawable.ic_launcher_background));
        DataS.add(new Data("Starboy","The Weeknd","55.12 Cal",R.drawable.ic_launcher_background));
        DataS.add(new Data("My Dear Malencholy,","The Weeknd","52.2 Cal",R.drawable.ic_launcher_background));
        DataS.add(new Data("Save Your Tear Remix","The Weeknd, Ariana Grande","2.12 Cal",R.drawable.ic_launcher_background));
        DataS.add(new Data("Monopoly","Ariana Grande","2.12 Cal",R.drawable.ic_launcher_background));
        DataS.add(new Data("Position","Ariana Grande","54.5 Cal",R.drawable.ic_launcher_background));
        DataS.add(new Data("Cold Heart (PNU Remix)","Elton John, Dua Lipa","2.75 Cal",R.drawable.ic_launcher_background));
        DataS.add(new Data("Hard Written","Shawn Mendes","54.5 Cal",R.drawable.ic_launcher_background));
        DataS.add(new Data("Sweetener","Shawn Mendes","54.5 Cal",R.drawable.ic_launcher_background));
        DataS.add(new Data("Justice","Justin Bieber","54.5 Cal",R.drawable.ic_launcher_background));
        DataS.add(new Data("Stay","Justin Bieber","2.3 Cal",R.drawable.ic_launcher_background));
        DataS.add(new Data("Save Your Tear Remix","The Weeknd, Ariana Grande","2.12 Cal",R.drawable.ic_launcher_background));
        DataS.add(new Data("Save Your Tear Remix","The Weeknd, Ariana Grande","2.12 Cal",R.drawable.ic_launcher_background));
        DataS.add(new Data("Save Your Tear Remix","The Weeknd, Ariana Grande","2.12 Cal",R.drawable.ic_launcher_background));
        DataS.add(new Data("Save Your Tear Remix","The Weeknd, Ariana Grande","2.12 Cal",R.drawable.ic_launcher_background));
        DataS.add(new Data("Save Your Tear Remix","The Weeknd, Ariana Grande","2.12 Cal",R.drawable.ic_launcher_background));
        DataS.add(new Data("Save Your Tear Remix","The Weeknd, Ariana Grande","2.12 Cal",R.drawable.ic_launcher_background));


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
}
