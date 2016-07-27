package com.tongming.bwghost.presenter;

import android.os.Handler;
import android.os.Looper;

import com.tongming.bwghost.interfaces.IActionView;
import com.tongming.bwghost.interfaces.Api;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import okhttp3.Call;

/**
 * Created by Tongming on 2016/7/19.
 */
public class ActionPresenterCompl {
    private IActionView actionView;
    private Handler mHandler;
    public static String REQUEST_TAG = "action";

    public ActionPresenterCompl(IActionView actionView) {
        this.actionView = actionView;
        mHandler = new Handler(Looper.getMainLooper());
    }

    public void doAction(String veid, String key, String url) {
        OkHttpUtils.get()
                .url(url)
                .addParams(Api.VEID, veid)
                .addParams(Api.KEY, key)
                .tag(REQUEST_TAG)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(final String response, int id) {
                        mHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                Pattern pattern = Pattern.compile("\\d+");
                                Matcher matcher = pattern.matcher(response);
                                if (matcher.find()) {
                                    if (matcher.group(0).equals("0")) {
                                        actionView.onAction(Integer.parseInt(matcher.group(0)));
                                    } else {

                                    }
                                }
                            }
                        });
                    }
                });
    }

    public void doChange(String veid, String key, final String newHost) {
        OkHttpUtils.get()
                .url(Api.Manager.SET_HOSTNAME)
                .addParams(Api.VEID, veid)
                .addParams(Api.KEY, key)
                .addParams(Api.PARAMS.NEW_HOSTNAME, newHost)
                .build()
                .execute(new StringCallback() {
                             @Override
                             public void onError(Call call, Exception e, int id) {

                             }

                             @Override
                             public void onResponse(String response, int id) {
                                 Pattern pattern = Pattern.compile("\\d+");
                                 Matcher matcher = pattern.matcher(response);
                                 if (matcher.find()) {
                                     if (matcher.group(0).equals("0")) {
                                         actionView.onChange(Integer.parseInt(matcher.group(0)), newHost);
                                     } else {

                                     }
                                 }
                             }
                         }
                );
    }
}

