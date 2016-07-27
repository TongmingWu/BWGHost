package com.tongming.bwghost.presenter;

import android.os.Handler;
import android.os.Looper;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.tongming.bwghost.bean.Os;
import com.tongming.bwghost.interfaces.Api;
import com.tongming.bwghost.interfaces.ISystemView;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.Call;

/**
 * Created by Tongming on 2016/7/20.
 */
public class OsPresenterCompl {
    private ISystemView systemView;
    private Handler mHandler;

    public OsPresenterCompl(ISystemView systemView) {
        this.systemView = systemView;
        mHandler = new Handler(Looper.getMainLooper());
    }

    public void reinstall(String veid, String key, String OS) {
        OkHttpUtils.get()
                .url(Api.Manager.REINSTALL_OS)
                .addParams(Api.VEID, veid)
                .addParams(Api.KEY, key)
                .addParams(Api.PARAMS.OS, OS)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(final String response, int id) {
                        try {
                            JSONObject object = new JSONObject(response);
                            if (object.getInt("error") == 0) {
                                mHandler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        systemView.onReinstall();
                                    }
                                });
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });

    }

    public void getAvaiableOS(String veid, String key) {
        OkHttpUtils.get()
                .url(Api.Manager.GET_AVAILABLE_OS)
                .addParams(Api.VEID, veid)
                .addParams(Api.KEY, key)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        final Os os = new Gson().fromJson(response, new TypeToken<Os>() {
                        }.getType());
                        mHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                systemView.onGetOS(os);
                            }
                        });
                    }
                });
    }
}
