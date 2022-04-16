package com.example.milamix.wondercal.Page;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.example.milamix.wondercal.Models.RestaurantModels;
import com.example.milamix.wondercal.R;

public class MealActivity extends AppCompatActivity {

    TextView Meal;
    String meal;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meal);
        Meal = (TextView)findViewById(R.id.textMeal);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        if(bundle != null){
            meal = bundle.getString("Meal");
            Meal.setText(meal);
        }
    }
}