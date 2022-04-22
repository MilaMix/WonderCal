package com.example.milamix.wondercal.Models;

import org.json.JSONException;
import org.json.JSONObject;

public class Food {
    private int id;
    private String name_th;
    private String name_en;
    private int cal = 0;

    public Food(int id,String name,int cal){
        this.id = id;
        String[] nameFood = name.split(",");
        this.name_en = nameFood[0];
        this.name_th = nameFood[1];
        this.cal = cal;
    }

    public int getCal() {
        return cal;
    }

    public String getName_th() {
        return name_th;
    }

    public String getName_en() {
        return name_en;
    }

    public int getId(){
        return id;
    }

    public JSONObject getJSONObj() throws JSONException {
        JSONObject obj = new JSONObject();
        obj.put("id",id);
        return obj;
    }
}
