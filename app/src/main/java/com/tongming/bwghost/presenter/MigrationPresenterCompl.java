package com.tongming.bwghost.presenter;

import android.os.Handler;
import android.os.Looper;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.tongming.bwghost.interfaces.IMigrationView;
import com.tongming.bwghost.interfaces.Api;
import com.tongming.bwghost.bean.Location;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.Call;

/**
 * Created by Tongming on 2016/7/21.
 */
public class MigrationPresenterCompl {
    private IMigrationView migrationView;
    private Handler mHandler;

    public MigrationPresenterCompl(IMigrationView migrationView) {
        this.migrationView = migrationView;
        mHandler = new Handler(Looper.getMainLooper());
    }

    public void getLocations(String veid, String key) {
        OkHttpUtils.get()
                .url(Api.MIGRATE.GET_LOCATIONS)
                .addParams(Api.VEID, veid)
                .addParams(Api.KEY, key)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        final Location location = new Gson().fromJson(response, new TypeToken<Location>() {
                        }.getType());
                        mHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                migrationView.onGetLocation(location);
                            }
                        });
                    }
                });
    }

    public void migration(String veid, String key, String location) {
        OkHttpUtils.get()
                .url(Api.MIGRATE.START)
                .addParams(Api.VEID, veid)
                .addParams(Api.KEY, key)
                .addParams(Api.PARAMS.LOCATION, location)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        try {
                            JSONObject object = new JSONObject(response);
                            if (object.getInt("error") == 0) {
                                mHandler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        migrationView.onMigration();
                                    }
                                });
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }
}
