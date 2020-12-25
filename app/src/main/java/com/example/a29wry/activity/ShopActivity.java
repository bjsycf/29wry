package com.example.a29wry.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.a29wry.R;
import com.example.a29wry.adapter.ShopAdapter;
import com.example.a29wry.bean.Shop;
import com.example.a29wry.utils.NetUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class ShopActivity extends AppCompatActivity {

    private TextView     tvTitleText;
    private ImageView    ivBack;
    private RecyclerView rvShop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop);
        initView();
        NetUtils.doGet(new Callback() {
            private List<Shop> shopList;

            @Override
            public void onFailure(Call call, IOException e) {
                System.out.println("请求失败,网络错误或服务器错误");
            }


            @Override
            public void onResponse(Call call, Response response) throws IOException {
                //获取返回的数据字符串
                String data = response.body().string();
                //将字符串转换为json对象
                // try {
                //     JSONArray jsonArray = new JSONArray(data);
                //创建gson对象，准备解析Json字符串
                Gson gson = new Gson();
                //构建解析的类型对象
                Type type = new TypeToken<List<Shop>>() {
                }.getType();
                //类成员属性
                shopList = gson.fromJson(data, type);
                //在子线程中更新uri，可以使用runonui的方法
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        //为rv设置布局管理器
                        rvShop.setLayoutManager(new LinearLayoutManager(ShopActivity.this));
                        //创建适配器
                        ShopAdapter adapter = new ShopAdapter(shopList, ShopActivity.this);
                        rvShop.setAdapter(adapter);
                    }
                });

                // } catch (JSONException e) {
                //     e.printStackTrace();
                // }
            }
        });
    }

    private void initView() {
        tvTitleText = (TextView) findViewById(R.id.tv_title_text);
        ivBack = (ImageView) findViewById(R.id.iv_back);
        rvShop = (RecyclerView) findViewById(R.id.rv_shop);
        tvTitleText.setBackgroundColor(Color.WHITE);
        tvTitleText.setText("店铺");
        ivBack.setVisibility(View.GONE);
    }

    //定义一个默认的时间
    private long exitTime;//定义一个第一次点击的时间

    //添加手机返回按钮的点击监听
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        /**
         * 两个条件
         * 1.必须是返回键返回值
         * 2.按下操作
         */
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            //获取系统时间,毫秒
            long time = System.currentTimeMillis();
            Log.d("tag", "onKeyDown:time=" + time);
            if (time - exitTime > 2000) {
                Toast.makeText(this, "再按一次退出应用", Toast.LENGTH_SHORT).show();
                //更新点击的时间
                exitTime = time;
            } else {
                //退出应用
                finish();
                System.exit(0);
            }
            //返回true表示当前的方法已经处理的全部事件，不需要系统在进行处理
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}