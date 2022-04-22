package com.example.milamix.wondercal.Models;

import com.example.milamix.wondercal.Utils.Utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MealModels{
    private List<Food> FoodModels = new ArrayList<>();

    public MealModels(JSONArray meal) throws JSONException {
        for(int i = 0 ; i< meal.length() ;i++){
            JSONObject obj = meal.getJSONObject(i);
            FoodModels.add(
                    new Food(
                            obj.getInt("id"),
                            obj.getString("food"),
                            obj.getInt("calories")
                    ));
        }
    }

    public List<Food> getFoodModels() {
        return FoodModels;
    }

    public MealModels(String meal){
        if(meal.length() ==0){
            FoodModels.add(
                    new Food(
                            0,
                            "2"+","+"3",
                            Integer.parseInt("asd")
                    ));
        }else{
            String[] list = meal.split(":");
            for(int i = 0 ; i<list.length;i++){
                String[] P = list[i].split(",");
                FoodModels.add(
                        new Food(
                                Integer.parseInt(P[0]),
                                P[1]+","+P[2],
                                Integer.parseInt(P[3])
                        ));
            }
        }
    }

    public int getCal(){
        int sum = 0;
        for(int i = 0 ; i< FoodModels.size() ;i++){
            sum += FoodModels.get(i).getCal();
        }
        return sum;
    }

    public String toString(){
        String str = "";
        for(int i = 0 ;i<this.FoodModels.size();i++){
            String id = String.valueOf(FoodModels.get(i).getId());
            String nameEn = FoodModels.get(i).getName_en();
            String nameTh = FoodModels.get(i).getName_th();
            String Cal = String.valueOf(FoodModels.get(i).getCal());
            if(i!=0) str += ":";
            str += id+","+nameEn+","+nameTh+","+Cal;
        }
        return str;
    }

    public void toPrintString(){
        for(int i = 0 ;i<this.FoodModels.size();i++){
            Utils.Log("====== Food =====");
            Utils.Log(FoodModels.get(i).getName_en());
            Utils.Log(FoodModels.get(i).getName_th());
            Utils.Log(String.valueOf(FoodModels.get(i).getCal()));
        }
    }
}

class Food{
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
}