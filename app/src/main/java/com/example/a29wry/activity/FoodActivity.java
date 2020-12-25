package com.example.a29wry.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.a29wry.R;
import com.example.a29wry.bean.Food;

public class FoodActivity extends AppCompatActivity {

    private ImageView ivFoodPic;
    private TextView  tvFoodName;
    private TextView  tvSaleNum;
    private TextView  tvPrice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food);
        initView();
        Intent intent = getIntent();
        Food food = (Food) intent.getSerializableExtra("food");
        tvFoodName.setText(food.getFoodName());
        tvSaleNum.setText("已售" + food.getSaleNum());
        tvPrice.setText("¥" + food.getPrice());
        Glide.with(this).load(food.getFoodPic()).into(ivFoodPic);
    }

    private void initView() {
        ivFoodPic = (ImageView) findViewById(R.id.iv_food_pic);
        tvFoodName = (TextView) findViewById(R.id.tv_food_name);
        tvSaleNum = (TextView) findViewById(R.id.tv_sale_num);
        tvPrice = (TextView) findViewById(R.id.tv_price);
    }
}