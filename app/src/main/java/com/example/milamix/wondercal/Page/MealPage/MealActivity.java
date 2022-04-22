package com.example.milamix.wondercal.Page.MealPage;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.milamix.wondercal.Models.MealModels;
import com.example.milamix.wondercal.Models.RestaurantModels;
import com.example.milamix.wondercal.R;
import com.example.milamix.wondercal.Utils.Utils;

import java.util.ArrayList;
import java.util.List;

public class MealActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private TextView Meal;
    private String meal;

    private List<MealModels> food_list_data = new ArrayList<>();
    private List<Data> datas = new ArrayList<>();
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
            if(food.length() > 1){
                food_list_data.add(
                        new MealModels(food));
            }
        }


        datas.add(new Data("1","1","1"));
        datas.add(new Data("1","1","1"));
        datas.add(new Data("1","1","1"));
        datas.add(new Data("1","1","1"));
        datas.add(new Data("1","1","1"));
        datas.add(new Data("1","1","1"));


        MealAdapter adapter = new MealAdapter(this,datas);
        ListView lv = findViewById(R.id.LVfood);
        lv.setOnItemClickListener(this);
        lv.setAdapter(adapter);
    }

    public void displayListview(){

    }

    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Log.d("TAG", String.valueOf(i));
        Intent itn = new Intent(this, MealActivity.class);
        itn.putExtra("recID", i);
        startActivity(itn);
    }

}