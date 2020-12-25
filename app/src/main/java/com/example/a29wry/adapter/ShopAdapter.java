package com.example.a29wry.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.a29wry.R;
import com.example.a29wry.activity.ShopDetailActivity;
import com.example.a29wry.bean.Shop;

import java.util.List;

public class ShopAdapter extends RecyclerView.Adapter<ShopAdapter.ShopViewholder> {
    //为适配器添加所绑定的数据集合
    List<Shop> shopList;
    //上下文,也就是列表控件所在的活动对象
    Context    context;

    public ShopAdapter(List<Shop> shopList, Context context) {
        this.shopList = shopList;
        this.context = context;
    }

    @NonNull
    @Override
    public ShopViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.shop_item_layout, parent, false);
        ShopViewholder viewholder = new ShopViewholder(view);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Shop shop = shopList.get(viewholder.getAdapterPosition());
                //Toast.makeText(context, "店铺的名称:" + shop.getShopName(), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(context, ShopDetailActivity.class);
                intent.putExtra("shop", shop);
                context.startActivity(intent);
            }
        });
        return viewholder;
    }

    @Override
    public void onBindViewHolder(@NonNull ShopViewholder holder, int position) {
        //根据position获取shop对象
        Shop shop = shopList.get(position);
        holder.tvShopName.setText(shop.getShopName());
        holder.tvShopSell.setText(shop.getSaleNum() + "");
        holder.tvDelivery.setText("起送:￥" + shop.getOfferPrice() + "配送:￥" + shop.getDistributionCost());
        holder.tvFlTxt.setText(shop.getWelfare());
        holder.tvDeliverTime.setText(shop.getTime());
        //glide插件实现图片加载
        Glide.with(context).load(shop.getShopPic()).into(holder.ivShopLogo);
    }

    @Override
    public int getItemCount() {
        return shopList.size();
    }

    class ShopViewholder extends RecyclerView.ViewHolder {
        private ImageView ivShopLogo;
        private TextView  tvShopName;
        private TextView  tvShopSell;
        private TextView  tvDelivery;
        private TextView  tvFuli;
        private TextView  tvFlTxt;
        private TextView  tvDeliverTime;

        public ShopViewholder(@NonNull View itemView) {
            super(itemView);
            ivShopLogo = (ImageView) itemView.findViewById(R.id.iv_shop_logo);
            tvShopName = (TextView) itemView.findViewById(R.id.tv_shop_name);
            tvShopSell = (TextView) itemView.findViewById(R.id.tv_shop_sell);
            tvDelivery = (TextView) itemView.findViewById(R.id.tv_delivery);
            tvFuli = (TextView) itemView.findViewById(R.id.tv_fuli);
            tvFlTxt = (TextView) itemView.findViewById(R.id.tv_fl_txt);
            tvDeliverTime = (TextView) itemView.findViewById(R.id.tv_deliver_time);
        }
    }
}
