package com.example.milamix.wondercal.Page.MainPage.Restaurant;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.example.milamix.wondercal.Models.RestaurantModels;
import com.example.milamix.wondercal.R;
import com.example.milamix.wondercal.databinding.ActivityRestaurantdetailBinding;
import com.example.milamix.wondercal.Utils.Utils;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class RestaurantdetailActivity extends AppCompatActivity implements OnMapReadyCallback {
    private GoogleMap mMap;
    private ActivityRestaurantdetailBinding binding;
    RestaurantModels data;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        if(bundle != null){
            data = new RestaurantModels(
                    bundle.getInt("id"),
                    bundle.getString("name_en"),
                    bundle.getString("name_th"),
                    bundle.getString("detail"),
                    bundle.getDouble("latitude"),
                    bundle.getDouble("longtitude"),
                    bundle.getString("img")
            );
        }

        binding = ActivityRestaurantdetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        Utils.Log(data.getRestaurantName_en());
        Utils.Log(data.getRestaurantName_th());
        Utils.Log(data.getDetail());
        Utils.Log(String.valueOf(data.getLatitude()));
        Utils.Log(String.valueOf(data.getLongtitude()));

        TextView name = (TextView)findViewById(R.id.name_rest);
        TextView detail = (TextView)findViewById(R.id.detail_rest);

        name.setText(data.getRestaurantName_en());
        detail.setText(data.getDetail());
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        Utils.Log("Map" + data.getLatitude() +" | "+ data.getLongtitude());
        LatLng sydney = new LatLng(data.getLatitude(), data.getLongtitude());
        mMap.addMarker(new MarkerOptions().position(sydney).title(data.getRestaurantName_en()));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }
}