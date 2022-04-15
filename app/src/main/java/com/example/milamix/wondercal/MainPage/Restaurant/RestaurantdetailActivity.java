package com.example.milamix.wondercal.MainPage.Restaurant;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;

import com.example.milamix.wondercal.R;
import com.example.milamix.wondercal.databinding.ActivityRestaurantdetailBinding;
import com.example.milamix.wondercal.util.Utils;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

public class RestaurantdetailActivity extends AppCompatActivity implements OnMapReadyCallback {
    private GoogleMap mMap;
    private ActivityRestaurantdetailBinding binding;
    RestaurantData data;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        if(bundle != null){
            data = new RestaurantData(
                    bundle.getInt("id"),
                    bundle.getString("name_en"),
                    bundle.getString("name_th"),
                    bundle.getString("detail"),
                    bundle.getString("latitude"),
                    bundle.getString("longtitude"),
                    bundle.getInt("image")
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
        Utils.Log(data.getLatitude());
        Utils.Log(data.getLongtitude());
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        LatLng sydney = new LatLng(13.746875348997376, 100.49320581796165);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Krung Thep"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }
}