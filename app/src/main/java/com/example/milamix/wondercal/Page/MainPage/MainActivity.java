package com.example.milamix.wondercal.Page.MainPage;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import com.example.milamix.wondercal.Page.MainPage.Restaurant.RestaurantFragment;
import com.example.milamix.wondercal.Page.MainPage.Setting.SettingsFragment;
import com.example.milamix.wondercal.R;
import com.example.milamix.wondercal.databinding.ActivityMainBinding;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        replaceFragment(new MainFragment());

        binding.bottomNavigationView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()){
                case R.id.main:
                    replaceFragment(new MainFragment());
                    break;
                case R.id.restaurant:
                    replaceFragment(new RestaurantFragment());
                    break;

                case R.id.search:
                    replaceFragment((new SearchFragment()));
                    break;

                case R.id.settings:
                    replaceFragment(new SettingsFragment());
                    break;
            }
            return true;
        });
    }

    private void replaceFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.FrameLayout,fragment);
        fragmentTransaction.commit();
    }

    @Override
    public void onBackPressed() {
        new SweetAlertDialog(MainActivity.this,SweetAlertDialog.WARNING_TYPE)
                .setContentText("Are you sure to close App")
                .setConfirmButton("OK", new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        sweetAlertDialog.dismiss();
                        finish();
                    }
                })
                .setCancelButton("Cancel", new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        sweetAlertDialog.dismiss();
                    }
                }).show();
    }

}