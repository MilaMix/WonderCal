package com.example.milamix.wondercal.Page.MainPage;

public class Data {
    private String mtxt1;
    private String mtxt2;
    private String mtxt3;


    private int mIcon;

    //------------------------------------------constructor------------------------
    public Data(String mtxt1, String mtxt2, String mtxt3, int mIcon) {
        this.mtxt1 = mtxt1;
        this.mtxt2 = mtxt2;
        this.mtxt3 = mtxt3;
        this.mIcon = mIcon;

    }

    //------------------------------------------------txt1------------------------
    public String getMtxt1() {
        return mtxt1;
    }
    public void setMtxt1(String mtxt1) {
        this.mtxt1 = mtxt1;
    }

    //------------------------------------------------txt2------------------------
    public String getMtxt2() {
        return mtxt2;
    }
    public void setMtxt2(String mtxt2) {
        this.mtxt2 = mtxt2;
    }

    public String getMtxt3() {
        return mtxt3;
    }
    public void setMtxt3(String mtxt3) {
        this.mtxt3 = mtxt3;
    }
    //------------------------------------------------icon------------------------
    public int getmIcon() {
        return mIcon;
    }
    public void setmIcon(int mIcon) {
        this.mIcon = mIcon;
    }



}