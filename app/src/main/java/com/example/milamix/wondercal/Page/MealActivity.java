package com.example.milamix.wondercal.Page;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.widget.TextView;

import com.example.milamix.wondercal.Models.MealModels;
import com.example.milamix.wondercal.Models.RestaurantModels;
import com.example.milamix.wondercal.R;
import com.example.milamix.wondercal.Utils.Utils;

import java.util.List;

public class MealActivity extends AppCompatActivity {

    TextView Meal;
    String meal;
    MealModels food_list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meal);
        Meal = (TextView)findViewById(R.id.textMeal);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        if(bundle != null){
            String food = bundle.getString("food_list");
            food_list = new MealModels(food);
            String meal = bundle.getString("Meal");
            food_list.toPrintString();
        }
    }
}