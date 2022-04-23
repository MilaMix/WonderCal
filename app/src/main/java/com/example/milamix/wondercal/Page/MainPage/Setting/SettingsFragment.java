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

import com.example.milamix.wondercal.Models.UserInfoModels;
import com.example.milamix.wondercal.Page.LoginPage.LoginActivity;
import com.example.milamix.wondercal.Page.MainPage.MainActivity;
import com.example.milamix.wondercal.Page.MainPage.MainFragment;
import com.example.milamix.wondercal.Page.UserinfoPage.UploadImageActivity;
import com.example.milamix.wondercal.R;
import com.example.milamix.wondercal.Page.UserinfoPage.UserInfoActivity;
import com.example.milamix.wondercal.Service.SharePref;
import com.example.milamix.wondercal.Utils.Utils;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class SettingsFragment<contact> extends Fragment {
    Intent itn;

    private TextView profile;
    private TextView contact;
    private ImageView logout;
    private TextView changeImage;
    private ImageView img_profile;
    private Object Bundle;

    UserInfoModels users = new UserInfoModels();

    public SettingsFragment() { }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_settings, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        contact = getView().findViewById(R.id.Contact);
        changeImage = getView().findViewById(R.id.change_image);
        logout = getView().findViewById(R.id.logout);
        profile = getView().findViewById(R.id.Profile);
        img_profile = getView().findViewById(R.id.img_profile);
        JSONObject obj;
        SharePref sharePref = new SharePref(getContext());
        try {
            obj = sharePref.getObj("userInfo");
            users.setImg(obj.getString("image"));
            Utils.Log(obj.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }


        String url_image = users.getImg().equalsIgnoreCase("")
                ? "https://dict.drkrok.com/wp-content/uploads/2021/11/Channelcatfish-300x225.jpg" : users.getImg();

        Picasso.get()
                .load(url_image)
                .placeholder(R.mipmap.ic_launcher).fit()
                .error(R.mipmap.ic_launcher)
                .into(img_profile);

        changeImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sharePref.saveBoolean("isEditImage",true);
                Utils.delay(1, new Utils.DelayCallback() {
                    @Override
                    public void afterDelay() {
                        itn = new Intent(getActivity(), UploadImageActivity.class);
                        startActivity(itn);
                        getActivity().finish();
                    }
                });
            }
        });
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
                new SweetAlertDialog(getActivity(),SweetAlertDialog.WARNING_TYPE)
                        .setContentText("Are you sure to close App")
                        .setConfirmButton("OK", new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                sweetAlertDialog.dismiss();
                                try {
                                    sharePref.saveLogout();
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                getActivity().finish();
                                itn = new Intent(getActivity(), LoginActivity.class);
                                startActivity(itn);
                            }
                        })
                        .setCancelButton("Cancel", new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                sweetAlertDialog.dismiss();
                            }
                        }).show();
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







