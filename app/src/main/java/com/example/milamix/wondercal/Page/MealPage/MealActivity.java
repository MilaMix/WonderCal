package com.example.milamix.wondercal.Page.MealPage;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.milamix.wondercal.Models.Food;
import com.example.milamix.wondercal.Models.MealModels;
import com.example.milamix.wondercal.R;

import java.util.ArrayList;
import java.util.List;

public class MealActivity extends AppCompatActivity  {

    private TextView Meal;
    private String meal;

    private List<Food> food_list_data = new ArrayList<>();
    private String food;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meal);
        Meal = (TextView)findViewById(R.id.textMeal);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        if(bundle != null){
            food = bundle.getString("food_list");
            String meal = bundle.getString("Meal");
            Meal.setText(meal);
        }

        if(food.length() > 1){
            MealModels m = new MealModels(food);
            food_list_data = m.getFoodModels();
            setListView();
        }
    }

    void setListView(){
        MealAdapter adapter = new MealAdapter(this,food_list_data);
        ListView lv = findViewById(R.id.LVfood);
        lv.setAdapter(adapter);
    }

}