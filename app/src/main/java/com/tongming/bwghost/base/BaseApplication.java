package com.tongming.bwghost.base;

import android.app.Application;

import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.log.LoggerInterceptor;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

/**
 * Created by Tongming on 2016/7/17.
 */
public class BaseApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(new LoggerInterceptor("okhttp"))
                .connectTimeout(10000L, TimeUnit.MILLISECONDS)
                .readTimeout(10000L, TimeUnit.MILLISECONDS)
                .build();
        OkHttpUtils.initClient(client);
    }
}
