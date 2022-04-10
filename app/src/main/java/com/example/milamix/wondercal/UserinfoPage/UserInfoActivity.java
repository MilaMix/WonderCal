package com.example.milamix.wondercal.UserinfoPage;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

import com.example.milamix.wondercal.R;

public class UserInfoActivity extends AppCompatActivity {
    String[] items = {"Little or no exercise","Exercise 1-2 times/week","Exercise2-3 times/week","Exercise 4-5 times/week","Exercise 6-7 times/week","Professional athlete"};
    AutoCompleteTextView autoCompleteTxt;
    ArrayAdapter<String> adapterItems;

    /* After  calculate  BMR, need to multiply with
    - Sedentary lifestyle (little or no exercise): 1.2
    - Slightly active lifestyle (light exercise or sports 1-2 days/week): 1.4
    - Moderately active lifestyle (moderate exercise or sports 2-3 days/week): 1.6
    - Very active lifestyle (hard exercise or sports 4-5 days/week): 1.75
    - Extra active lifestyle (very hard exercise, physical job or sports 6-7 days/week): 2.0
    - Professional athlete: 2.3
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);
        autoCompleteTxt = findViewById(R.id.autoCompleteTextView);
        adapterItems = new ArrayAdapter<String>(this,R.layout.dropdown_ac_item,items);
        autoCompleteTxt.setAdapter(adapterItems);
        autoCompleteTxt.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String item = parent.getItemAtPosition(position).toString();
                Toast.makeText(getApplicationContext(), "Item: "+item,Toast.LENGTH_SHORT).show();
            }
        });
    }
}
