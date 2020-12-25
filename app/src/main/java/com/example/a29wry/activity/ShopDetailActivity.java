package com.example.a29wry.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.a29wry.R;
import com.example.a29wry.adapter.CartAdapter;
import com.example.a29wry.adapter.FoodAdapter;
import com.example.a29wry.bean.Food;
import com.example.a29wry.bean.Shop;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ShopDetailActivity extends AppCompatActivity {
    private TextView        tvTitleText;
    private ImageView       ivBack;
    private RecyclerView    rvFood;
    private TextView        tvShopName;
    private TextView        tvDeliverTime;
    private TextView        tvNotice;
    private CircleImageView ivShopLogo;
    private TextView        tvMoney;
    private TextView        tvDelivery;
    private TextView        tvDeliveryFee;
    private ImageView       ivCartPic;
    private Shop            shop;
    private RelativeLayout  rlCartList;
    private RecyclerView    rvCart;
    private TextView        tvClear;
    private TextView        tvCartNum2;
    //购物车的商品列表(没有商品的购物车对象)
    private List<Food>      cartList = new ArrayList<>();
    //消息处理框架，准备接受适配器发出的各种消息
    /**
     * 1.在当前活动对应的FoodAdapter中点击"+"按钮发出的消息
     * 这个消息的作用是向购物车中添加一种商品，或者在增加数量
     * 2.在CartListLayout中的清空购物车的按钮会发出消息,,清空购物车的全部内容
     * 3.在Cart_item_layout中点击+/-会发出消息,更新购物车中的商品信息
     * 添加购物车按钮   1
     * 清空            2
     * 加号            3
     * 减号            4
     */
    public  Handler         handler  = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            //表示消息的编号
            switch (msg.what) {
                //点击添加购物车按钮
                case 1:
                    Food food = (Food) msg.obj;//将object强转为food
                    //Toast.makeText(ShopDetailActivity.this, "你添加的商品是:" + food.getFoodName(), Toast.LENGTH_SHORT).show();
                    //遍历购物车，判断要添加的商品是否已经有了
                    // 如果有增加数量
                    // 如果没有，添加商品并且将数量设置为1
                    boolean isInCart = false;//定义isInCart为假
                    for (Food f : cartList) {//遍历购物车列表
                        if (f.getFoodName().equals(food.getFoodName())) {//判断要添加的商品是否存在
                            // f.setCount(f.getCount()+1);
                            int num = f.getCount();
                            num++;//增加商品的数量
                            f.setCount(num);//设置商品数量为num
                            isInCart = true;//定义isInCart为真
                            break;//跳出循环
                        }
                    }
                    if (!isInCart) {//如果没有商品
                        food.setCount(1);//数量设置为1
                        cartList.add(food);//将food添加到购物车列表
                    }
                    break;//跳出循环
                //点击清空按妞
                case 2:
                    break;
                //点击加号按钮增加数量
                case 3:
                    Food food3 = (Food) msg.obj;
                    for (Food f : cartList) {
                        if (f.getFoodName().equals(food3.getFoodName())) {
                            int num3 = f.getCount() + 1;
                            f.setCount(num3);
                            break;
                        }
                    }
                    break;
                //点击减号按钮减少数量
                case 4:
                    Food food2 = (Food) msg.obj;
                    for (Food f : cartList) {
                        if (f.getFoodName().equals(food2.getFoodName())) {
                            if (f.getCount() > 1) {
                                int num2 = f.getCount() - 1;
                                f.setCount(num2);
                            } else {
                                cartList.remove(f);
                                break;
                            }
                        }
                    }
                    break;
            }
            updateCart();
        }
    };
    private CartAdapter     cartAdapter;

    //更新购物车显示的业务逻辑
    public void updateCart() {
        if (cartList.size() == 0) {
            /**
             * 购物车没东西
             * 1.显示空购物车图片
             * 2.不显示角标
             * 3.显示"未加入购物车"的文字
             * 4.不显示商品总价
             * 5.显示还差xxx起送
             * 6.隐藏购物车列表
             */
            //显示空购物车图标
            ivCartPic.setImageResource(R.drawable.shop_car_empty);
            //不显示角标
            tvCartNum2.setVisibility(View.GONE);
            //显示"未加入购物车"文字
            tvMoney.setText("未加入购物车");
            //不显示另需配送费xx元
            tvDelivery.setVisibility(View.GONE);
            //显示还差xx起送
            tvDeliveryFee.setText("还差" + shop.getOfferPrice() + "起送");
            //隐藏购物车列表
            rlCartList.setVisibility(View.GONE);
        } else {
            /**
             *购物车有商品
             * 1.显示有色的购物车图片
             * 2.计算商品总数，显示角标
             * 3.显示商品总价
             * 4.判断是否够起送金额，如果够，去结算，不够，显示"还差xx起送"文字
             **/
            //显示有色商品
            ivCartPic.setImageResource(R.drawable.shop_car_empty);
            //设置角标数量和显示
            tvCartNum2.setText(calAllNum() + "");
            tvCartNum2.setVisibility(View.VISIBLE);
            //设置显示的总金额
            tvMoney.setText(String.format("%.2f", calAllMoney()));
            //显示另需配送费xx元
            tvDelivery.setVisibility(View.VISIBLE);
            tvDelivery.setText("另需配送费¥" + shop.getDistributionCost());
            //显示购物车列表
            //rlCartList.setVisibility(View.VISIBLE);
            //判断购物车金额是否超过起送金额
            if (calAllMoney() > shop.getOfferPrice()) {
                tvDeliveryFee.setBackgroundColor(Color.WHITE);
                tvDeliveryFee.setText("去结算");
            } else {
                tvDeliveryFee.setBackgroundColor(0x8888888);
                float dis = shop.getOfferPrice() - calAllMoney();
                tvDeliveryFee.setText("还差¥" + String.format("%.2f", dis) + "起送");
            }
        }
        //当数据变化后，通知recyclerview更新显示的数据
        cartAdapter.notifyDataSetChanged();
    }

    //计算购物车商品总数
    private int calAllNum() {
        int sum = 0;
        for (Food f : cartList) {
            sum += f.getCount();
        }
        return sum;
    }

    //计算购物车商品总价
    private float calAllMoney() {
        float sunMoney = 0;
        for (Food f : cartList) {
            sunMoney += f.getCount() * f.getPrice();
        }
        return sunMoney;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_detail);
        Intent intent = getIntent();
        //获取意图中传递的店铺对象
        shop = (Shop) intent.getSerializableExtra("shop");
        initView();
        initData();
    }

    //向列表添加数据
    private void initData() {
        //显示店铺中商品列表
        rvFood.setLayoutManager(new LinearLayoutManager(this));
        FoodAdapter adapter = new FoodAdapter(shop.getFoodList(), this);
        rvFood.setAdapter(adapter);
        //显示购物车中商品列表
        rvCart.setLayoutManager(new LinearLayoutManager(this));
        cartAdapter = new CartAdapter(this, cartList);
        rvCart.setAdapter(cartAdapter);
    }

    private void initView() {
        //找控件
        tvTitleText = (TextView) findViewById(R.id.tv_title_text);
        ivBack = (ImageView) findViewById(R.id.iv_back);
        rvFood = (RecyclerView) findViewById(R.id.rv_food);
        tvShopName = (TextView) findViewById(R.id.tv_shop_name);
        tvDeliverTime = (TextView) findViewById(R.id.tv_deliver_time);
        tvNotice = (TextView) findViewById(R.id.tv_notice);
        ivShopLogo = (CircleImageView) findViewById(R.id.iv_shop_logo);
        tvMoney = (TextView) findViewById(R.id.tv_money);
        tvDelivery = (TextView) findViewById(R.id.tv_delivery);
        tvDeliveryFee = (TextView) findViewById(R.id.tv_delivery_fee);
        ivCartPic = (ImageView) findViewById(R.id.iv_cart_pic);
        //购物车列表局部布局的控件
        rlCartList = (RelativeLayout) findViewById(R.id.rl_cart_list);
        rvCart = (RecyclerView) findViewById(R.id.rv_cart);
        tvClear = (TextView) findViewById(R.id.tv_clear);
        tvCartNum2 = (TextView) findViewById(R.id.tv_cart_num2);
        //隐藏购物车列表
        /**
         * View.GONE不显示
         * View.VISIBLE显示
         */
        rlCartList.setVisibility(View.GONE);
        //隐藏购物车角标
        tvCartNum2.setVisibility(View.GONE);
        //为控件添加出事的内容
        tvShopName.setText(shop.getShopName());
        tvDeliverTime.setText(shop.getTime());
        tvNotice.setText(shop.getShopNotice());
        Glide.with(this).load(shop.getShopPic()).into(ivShopLogo);
        //返回
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        //点击购物车图标会打开和隐藏购物车列表
        ivCartPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //如果购物车商品数量大于0，点击有效
                if (calAllNum() > 0) {
                    if (rlCartList.getVisibility() == View.GONE) {
                        rlCartList.setVisibility(View.VISIBLE);
                    } else {
                        rlCartList.setVisibility(View.GONE);
                    }
                }
            }
        });
        //点击清空购物车按钮
        tvClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //定义对话框
                AlertDialog dialog;
                //设置对话框参数
                AlertDialog.Builder builder = new AlertDialog.Builder(ShopDetailActivity.this)
                        //设置标题
                        .setTitle("是否确定清空购物车")
                        //设置确定按钮
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //清空购物车
                                cartList.clear();
                                //更新购物车
                                updateCart();
                            }
                            //设置取消按钮
                        }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //关闭对话框
                                dialog.dismiss();
                            }
                        });
                //创建对话框
                dialog = builder.create();
                //显示对话框
                dialog.show();
                //Toast.makeText(ShopDetailActivity.this, "清空购物车", Toast.LENGTH_SHORT).show();
            }
        });
        //点击结算事件
        tvDeliveryFee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(ShopDetailActivity.this, "去结算", Toast.LENGTH_SHORT).show();
                if (calAllMoney() - shop.getOfferPrice() > 0) {
                    Intent intent = new Intent(ShopDetailActivity.this, OrderActivity.class);
                    intent.putExtra("cartList", (Serializable) cartList);
                    intent.putExtra("shop", shop);
                    startActivity(intent);
                } else {
                    Toast.makeText(ShopDetailActivity.this, "还差" + String.format("%.2f", shop.getOfferPrice() - calAllMoney()) + "起送",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
        //点击任意地方回收列表
        rlCartList.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (rlCartList.getVisibility() == View.GONE) {
                    rlCartList.setVisibility(View.VISIBLE);
                }
                return false;
            }
        });
        tvTitleText.setText(shop.getShopName());
    }
}