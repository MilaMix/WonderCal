package com.example.milamix.wondercal.MainPage.Restaurant;

public class RestaurantData {

    private int Restaurant_id;
    private String RestaurantName_en;
    private String RestaurantName_th;
    private String detail;
    private String latitude;
    private String longtitude;

    private String URL_img;

    private int RestaurantImage;

    public RestaurantData(
            int Restaurant_id,
            String RestaurantName_en,
            String RestaurantName_th,
            String detail,
            String latitude,
            String longtitude,
            int RestaurantImage
    ) {
        this.Restaurant_id = Restaurant_id;
        this.RestaurantName_en = RestaurantName_en;
        this.RestaurantName_th = RestaurantName_th;
        this.detail = detail;
        this.latitude = latitude;
        this.longtitude = longtitude;
        this.RestaurantImage = RestaurantImage;
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
    public String getLatitude(){
        return this.latitude;
    }
    public String getLongtitude(){
        return this.longtitude;
    }
    public int getRestaurantImage(){
        return this.RestaurantImage;
    }
}


