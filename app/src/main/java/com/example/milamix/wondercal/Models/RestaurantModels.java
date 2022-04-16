package com.example.milamix.wondercal.Models;

public class RestaurantModels {

    private int Restaurant_id;
    private String RestaurantName_en;
    private String RestaurantName_th;
    private String detail;
    private double latitude;
    private double longtitude;

    private String URL_img;

    private String RestaurantImage_URL;

    public RestaurantModels(
            int Restaurant_id,
            String RestaurantName_en,
            String RestaurantName_th,
            String detail,
            double latitude,
            double longtitude,
            String RestaurantImage_URL
    ) {
        this.Restaurant_id = Restaurant_id;
        this.RestaurantName_en = RestaurantName_en;
        this.RestaurantName_th = RestaurantName_th;
        this.detail = detail;
        this.latitude = latitude;
        this.longtitude = longtitude;
        this.RestaurantImage_URL = RestaurantImage_URL;
    }

    public int getRestaurant_id(){
        return this.Restaurant_id;
    }
    public String getRestaurantName_en(){
        return this.RestaurantName_en;
    }
    public String getRestaurantName_th(){
        return this.RestaurantName_th;
    }
    public String getDetail(){
        return this.detail;
    }
    public double getLatitude(){
        return this.latitude;
    }
    public double getLongtitude(){
        return this.longtitude;
    }
    public String getRestaurantImage(){
        return this.RestaurantImage_URL;
    }
}


