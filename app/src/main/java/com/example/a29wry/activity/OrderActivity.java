package com.example.a29wry.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.a29wry.R;
import com.example.a29wry.adapter.OrderAdapter;
import com.example.a29wry.bean.Food;
import com.example.a29wry.bean.Shop;

import java.util.List;

public class OrderActivity extends AppCompatActivity {
    private List<Food>     orderlist;
    private Shop           shop;
    private TextView       tvTitleText;
    private ImageView      ivBack;
    private LinearLayout   LinearLayout;
    private EditText       editText;
    private RecyclerView   rvOrder;
    private RelativeLayout lvOrrder;
    private TextView       tvPrice;
    private TextView       tvSendPrice;
    private TextView       textView;
    private TextView       tvAllPrice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        Intent intent = getIntent();
        orderlist = (List<Food>) intent.getSerializableExtra("cartList");
        shop = (Shop) intent.getSerializableExtra("shop");
        Log.e("TAG", "onCreate: " + shop.getDistributionCost());
        initView();
    }

    private void initView() {
        tvTitleText = (TextView) findViewById(R.id.tv_title_text);
        ivBack = (ImageView) findViewById(R.id.iv_back);
        LinearLayout = (LinearLayout) findViewById(R.id.LinearLayout);
        editText = (EditText) findViewById(R.id.editText);
        rvOrder = (RecyclerView) findViewById(R.id.rv_order);
        lvOrrder = (RelativeLayout) findViewById(R.id.lv_orrder);
        tvPrice = (TextView) findViewById(R.id.tv_price);
        tvSendPrice = (TextView) findViewById(R.id.tv_send_price);
        textView = (TextView) findViewById(R.id.textView);
        tvAllPrice = (TextView) findViewById(R.id.tv_all_price);

        tvTitleText.setText("订单信息");
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(OrderActivity.this);
                builder.setView(R.layout.dialog_layout);
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });
        float sumMoney = 0;
        for (int i = 0; i < orderlist.size(); i++) {
            float price = orderlist.get(i).getPrice();
            sumMoney += price * orderlist.get(i).getCount();
        }
        tvTitleText.setBackgroundColor(0xff555555);
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        tvPrice.setText(String.format("%.2f", sumMoney));
        rvOrder.setLayoutManager(new LinearLayoutManager(this));
        OrderAdapter orderAdapter = new OrderAdapter(orderlist, this);
        rvOrder.setAdapter(orderAdapter);
        tvSendPrice.setText("配送费: " + shop.getDistributionCost());
        tvAllPrice.setText(String.format("%.2f", sumMoney + shop.getDistributionCost()));
    }
}