package com.example.a29wry.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.a29wry.R;
import com.example.a29wry.bean.Food;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.OrderViewholder> {
    private List<Food> foodList;
    Context context;

    public OrderAdapter(List<Food> foodList, Context context) {
        this.foodList = foodList;
        this.context = context;
    }

    @NonNull
    @Override
    public OrderViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.order_item_layout, parent, false);
        OrderViewholder viewholder = new OrderViewholder(view);
        return viewholder;
    }

    @Override
    public void onBindViewHolder(@NonNull OrderViewholder holder, int position) {
        Food food = foodList.get(position);
        holder.tvName.setText(food.getFoodName());
        holder.tvPrice.setText(String.format("%.2f", food.getPrice()));
        holder.tvNum.setText("×" + food.getCount());
        Glide.with(context).load(food.getFoodPic()).into(holder.tvPic);
    }

    @Override
    public int getItemCount() {
        return foodList.size();
    }

    class OrderViewholder extends RecyclerView.ViewHolder {
        private CircleImageView tvPic;
        private TextView  tvName;
        private TextView  tvNum;
        private TextView  tvPrice;

        public OrderViewholder(@NonNull View itemView) {
            super(itemView);
            tvPic = (CircleImageView) itemView.findViewById(R.id.tv_pic);
            tvName = (TextView) itemView.findViewById(R.id.tv_name);
            tvNum = (TextView) itemView.findViewById(R.id.tv_num);
            tvPrice = (TextView) itemView.findViewById(R.id.tv_price);
        }
    }
}
