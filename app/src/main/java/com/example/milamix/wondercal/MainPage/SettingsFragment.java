package com.example.milamix.wondercal.MainPage;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.milamix.wondercal.ContactActivity;
import com.example.milamix.wondercal.ForgotPage.ForgotActivity;
import com.example.milamix.wondercal.LoginPage.LoginActivity;
import com.example.milamix.wondercal.R;
import com.example.milamix.wondercal.RegisterPage.RegisterActivity;
import com.example.milamix.wondercal.UserinfoPage.UserInfoActivity;
import com.example.milamix.wondercal.sharePref.SharePref;

public class SettingsFragment<contact> extends Fragment {
    Intent itn;

    SharePref sharePref = new SharePref(getActivity());
    private TextView profile;
    private TextView contact;
    private ImageView logout;
    private Object Bundle;

    public SettingsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_settings, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        contact = getView().findViewById(R.id.Contact);
        logout = getView().findViewById(R.id.logout);
        profile = getView().findViewById(R.id.Profile);
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                itn = new Intent(getActivity(), UserInfoActivity.class);
                startActivity(itn);
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
                       }
                   });

    }
}







