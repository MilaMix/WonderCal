package com.example.milamix.wondercal.TestData;

import com.example.milamix.wondercal.sharePref.SharePref;
import com.example.milamix.wondercal.util.Utils;

public class Test {
    SharePref sharePref;
    public Test (){ }

    public boolean LoginTest(String email,String password){
        Utils.Log("testLogin");
        Utils.Log("email : "+email);
        if(email == "a@gmail.com" && password == "123123"){
            sharePref.saveString("token","token");
            sharePref.saveBoolean("isLogin",true);
            sharePref.saveString("lastLogin","0000:00:00");
            return true;
        }
        return false;
    }

    public boolean LogoutTest(){
        sharePref.saveString("token","");
        sharePref.saveBoolean("isLogin",false);
        return true;
    }
}
