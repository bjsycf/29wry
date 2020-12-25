package com.example.a29wry.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.a29wry.R;
import com.example.a29wry.activity.FoodActivity;
import com.example.a29wry.activity.ShopDetailActivity;
import com.example.a29wry.bean.Food;
import com.example.a29wry.bean.Shop;

import java.util.List;

public class FoodAdapter extends RecyclerView.Adapter<FoodAdapter.FoodViewholder> {
    //添加成员变量
    List<Food> foodList;
    Context    context;

    //添加构造方法
    public FoodAdapter(List<Food> foodList, Context context) {
        this.foodList = foodList;
        this.context = context;
    }

    @NonNull
    @Override
    public FoodViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.food_item_layout, parent, false);
        FoodViewholder viewholder = new FoodViewholder(view);
        //点击列表项
        viewholder.ivFoodPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //获取点击的food对应的food对象
                Food food = foodList.get(viewholder.getAdapterPosition());
                Intent intent = new Intent(context, FoodActivity.class);
                intent.putExtra("food", food);
                context.startActivity(intent);
            }
        });
        return viewholder;
    }

    @Override
    public void onBindViewHolder(@NonNull FoodViewholder holder, int position) {
        Food food = foodList.get(position);
        holder.tvFoodName.setText(food.getFoodName());
        holder.tvFoodDesc.setText(food.getTaste());
        holder.tvSaleNum.setText("月售" + food.getSaleNum());
        holder.tvFoodPrice.setText("¥价格" + String.format("%.2f", food.getPrice()));
        //记载图片
        Glide.with(context).load(food.getFoodPic()).into(holder.ivFoodPic);
        //加入购物车的点击事件
        holder.tvAddCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //点击的是哪个食品的添加按钮
                //获取消息对象
                Message message = ((ShopDetailActivity) context).handler.obtainMessage();
                message.obj = food;
                //编号为1
                message.what = 1;
                ((ShopDetailActivity) context).handler.sendMessage(message);
            }
        });
    }

    @Override
    public int getItemCount() {
        return foodList.size();
    }

    class FoodViewholder extends RecyclerView.ViewHolder {
        private ImageView ivFoodPic;
        private TextView  tvFoodName;
        private TextView  tvFoodDesc;
        private TextView  tvSaleNum;
        private TextView  tvFoodPrice;
        private TextView  tvAddCart;

        public FoodViewholder(@NonNull View itemView) {
            super(itemView);
            ivFoodPic = (ImageView) itemView.findViewById(R.id.iv_food_pic);
            tvFoodName = (TextView) itemView.findViewById(R.id.tv_food_name);
            tvFoodDesc = (TextView) itemView.findViewById(R.id.tv_food_desc);
            tvSaleNum = (TextView) itemView.findViewById(R.id.tv_sale_num);
            tvFoodPrice = (TextView) itemView.findViewById(R.id.tv_food_price);
            tvAddCart = (TextView) itemView.findViewById(R.id.tv_add_cart);
        }
    }
}
