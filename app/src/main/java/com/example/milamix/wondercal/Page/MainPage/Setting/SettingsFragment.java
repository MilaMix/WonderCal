package com.example.milamix.wondercal.Page.MainPage.Setting;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.milamix.wondercal.Page.LoginPage.LoginActivity;
import com.example.milamix.wondercal.Page.MainPage.MainFragment;
import com.example.milamix.wondercal.R;
import com.example.milamix.wondercal.Page.UserinfoPage.UserInfoActivity;
import com.example.milamix.wondercal.Service.SharePref;
import com.example.milamix.wondercal.Utils.Utils;

public class SettingsFragment<contact> extends Fragment {
    Intent itn;

    private TextView profile;
    private TextView contact;
    private ImageView logout;
    private Object Bundle;

    public SettingsFragment() { }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_settings, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        SharePref sharePref = new SharePref(getContext());
        contact = getView().findViewById(R.id.Contact);
        logout = getView().findViewById(R.id.logout);
        profile = getView().findViewById(R.id.Profile);
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sharePref.saveBoolean("isEditProfile",true);
                Utils.delay(1, new Utils.DelayCallback() {
                    @Override
                    public void afterDelay() {
                        itn = new Intent(getActivity(), UserInfoActivity.class);
                        startActivity(itn);
                        replaceFragment(new MainFragment());
                    }
                });
            }
        });

        contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                itn = new Intent(getActivity(), ContactActivity.class);
                startActivity(itn);
            }
        });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                itn = new Intent(getActivity(), LoginActivity.class);
                startActivity(itn);
                sharePref.saveLogout();
                getActivity().finish();
            }
        });

    }

    private void replaceFragment(Fragment fragment){
        FragmentManager fragmentManager = getParentFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.FrameLayout,fragment);
        fragmentTransaction.commit();
    }

}







