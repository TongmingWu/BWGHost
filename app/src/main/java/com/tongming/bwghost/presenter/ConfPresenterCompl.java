package com.tongming.bwghost.presenter;

import android.os.Handler;
import android.os.Looper;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.orhanobut.logger.Logger;
import com.tongming.bwghost.interfaces.IConfView;
import com.tongming.bwghost.interfaces.Api;
import com.tongming.bwghost.bean.ServiceInfo;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import okhttp3.Call;

/**
 * Created by Tongming on 2016/7/17.
 */
public class ConfPresenterCompl {
    private IConfView confView;
    private Handler mHanlder;
    public static String REQUEST_TAG = "action";

    public ConfPresenterCompl(IConfView confView) {
        this.confView = confView;
        mHanlder = new Handler(Looper.getMainLooper());
    }

    public void getServiceInfo(String veid, String key) {
        OkHttpUtils.get()
                .url(Api.Manager.GET_ALL_INFO)
                .addParams(Api.VEID, veid)
                .addParams(Api.KEY, key)
                .tag(REQUEST_TAG)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, final int id) {
                        mHanlder.post(new Runnable() {
                            @Override
                            public void run() {
                                confView.onFail(id);
                            }
                        });
                    }

                    @Override
                    public void onResponse(final String response, int id) {
                        try {
                            final ServiceInfo info = new Gson().fromJson(
                                    response, new TypeToken<ServiceInfo>() {
                                    }.getType()
                            );
                            if (info != null) {
                                mHanlder.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        confView.onGetInfo(info);
                                    }
                                });
                            }
                        } catch (JsonSyntaxException e) {
                            Logger.e("Gson解析失败");
                            mHanlder.post(new Runnable() {
                                @Override
                                public void run() {
                                    confView.onFail(403);
                                }
                            });
                        }
                    }
                });
    }
}
