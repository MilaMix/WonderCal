package com.example.milamix.wondercal.Models;

import org.json.JSONException;
import org.json.JSONObject;

public class UserInfoModels {

    private String email;
    private int weight;
    private int height;
    private int age;
    private String gender;
    private double activity;
    private String img;

    public UserInfoModels(String email,
                          int height,
                          int weight,
                          int age,
                          String gender,
                          double activity,
                          String img){
        this.email = email;
        this.height = height;
        this.weight = weight;
        this.age = age;
        this.gender = gender;
        this.activity = activity;
        this.img = img;
    }

    public UserInfoModels(){
        this.email = "";
        this.height = 0;
        this.weight = 0;
        this.age = 0;
        this.gender = "male";
        this.activity = 1.2;
        this.img = "";
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setActivity(double activity) {
        this.activity = activity;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public void setImg(String img){ this.img = img;}

    public String getImg(){return this.img;}

    public String getEmail() {
        return this.email;
    }

    public int getAge() {
        return age;
    }

    public int getHeight() {
        return height;
    }

    public int getWeight() {
        return weight;
    }

    public JSONObject getJSONObj() throws JSONException {
        JSONObject obj = new JSONObject();
        obj.put("email", this.email);
        obj.put("weight", this.weight);
        obj.put("height", this.height);
        obj.put("age", this.age);
        obj.put("gender", this.gender);
        obj.put("activity", this.activity);
        obj.put("image", this.img);
        return obj;
    }
}
