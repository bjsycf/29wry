package com.example.a29wry.utils;

import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;

public class NetUtils {
    //使用okhttp插件进行网络请求
    public static void doGet(Callback callback) {
        //创建okhttp客户端
        OkHttpClient client = new OkHttpClient();
        //创建请求对象,默认的请求模式get
        Request request = new Request.Builder().url(Constant.path).build();
        //发出异步请求
        client.newCall(request).enqueue(callback);
    }
}
