package com.example.milamix.wondercal.MainPage.Restaurant;

public class RestaurantData {
    private String RestaurantName;
    private String RestaurantDesc;

    private int RestaurantImage;

    public RestaurantData(String RestaurantName, String RestaurantDesc, int RestaurantImage) {
        this.RestaurantName = RestaurantName;
        this.RestaurantDesc = RestaurantDesc;
        this.RestaurantImage = RestaurantImage;
    }


    public String getRestaurantName() {
        return RestaurantName;
    }
    public void setRestaurantName(String RestaurantName) {
        this.RestaurantName = RestaurantName;
    }

    public String getRestaurantDesc() {
            return RestaurantDesc;
    }
    public void setRestaurantDesc(String RestaurantDesc) {
        this.RestaurantDesc = RestaurantDesc;
    }

    public int getRestaurantImage() {
        return RestaurantImage;
    }
    public void setRestaurantImage(int RestaurantImage) {
        this.RestaurantImage = RestaurantImage;
    }
}


