package com.example.a29wry.adapter;

import android.content.Context;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.a29wry.R;
import com.example.a29wry.activity.ShopDetailActivity;
import com.example.a29wry.bean.Food;
import com.example.a29wry.bean.Shop;

import java.util.List;
import java.util.logging.Handler;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewholder> {
    //购物车商品列表
    List<Food> cartfoodList;
    Context    context;

    public CartAdapter(Context context, List<Food> cartfoodList) {
        this.cartfoodList = cartfoodList;
        this.context = context;
    }

    @NonNull
    @Override
    public CartViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_item_layout, parent, false);
        CartViewholder viewholder = new CartViewholder(view);
        return viewholder;
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewholder holder, int position) {
        if (cartfoodList != null) {
            Food food = cartfoodList.get(position);
            holder.tvCartFood.setText(food.getFoodName());
            holder.tvCartNum.setText(String.valueOf(food.getCount()));
            //holder.tvCartNum.setText("" + (food.getCount()));
            //%.2f保留两位小数
            holder.tvCartPrice.setText("¥" + String.format("%.2f", food.getPrice() * food.getCount()));

            //添加+的点击事件
            holder.ivAdd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //获取handle对象，准备消息对象
                    Message message = ((ShopDetailActivity) context).handler.obtainMessage();
                    message.obj = food;
                    message.what = 3;
                    ((ShopDetailActivity) context).handler.sendMessage(message);
                }
            });
            //添加-的点击事件
            holder.ivSub.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //获取handle对象，准备消息对象
                    Message message = ((ShopDetailActivity) context).handler.obtainMessage();
                    message.obj = food;
                    message.what = 4;
                    //发送编号为4的消息,表示要点击了图标
                    ((ShopDetailActivity) context).handler.sendMessage(message);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        //如果列表为空，返回0，否则返回列表
        return cartfoodList == null ? 0 : cartfoodList.size();
    }

    class CartViewholder extends RecyclerView.ViewHolder {
        private TextView  tvCartFood;
        private TextView  tvCartPrice;
        private ImageView ivAdd;
        private TextView  tvCartNum;
        private ImageView ivSub;

        public CartViewholder(@NonNull View itemView) {
            super(itemView);
            tvCartFood = (TextView) itemView.findViewById(R.id.tv_cart_food);
            tvCartPrice = (TextView) itemView.findViewById(R.id.tv_cart_price);
            ivAdd = (ImageView) itemView.findViewById(R.id.iv_add);
            tvCartNum = (TextView) itemView.findViewById(R.id.tv_cart_num);
            ivSub = (ImageView) itemView.findViewById(R.id.iv_sub);
        }
    }
}
