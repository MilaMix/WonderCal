package com.example.milamix.wondercal.Models;

import org.json.JSONException;
import org.json.JSONObject;

public class FoodModels {
    private int id;
    private String name_en;
    private String name_th;
    private String url_img;
    private int cal;
    public FoodModels(int id,String name_en,String name_th,String url,int cal){
        this.id = id;
        this.name_en = name_en;
        this.name_th = name_th;
        this.url_img = url;
        this.cal = cal;
    }
    public int getCal() {
        return cal;
    }
    public void setId(int id) {
        this.id = id;
    }
    public int getId() {
        return id;
    }
    public String getName_en() {
        return name_en;
    }
    public String getName_th() {
        return name_th;
    }
    public String getUrl_img() {
        return url_img;
    }
    public void setName_th(String name_th) {
        this.name_th = name_th;
    }
    public JSONObject getJSONObject() throws JSONException {
        JSONObject obj = new JSONObject();
        return obj;
    }

}
