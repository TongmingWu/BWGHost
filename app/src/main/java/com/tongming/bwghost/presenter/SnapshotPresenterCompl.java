package com.tongming.bwghost.presenter;

import android.os.Handler;
import android.os.Looper;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.orhanobut.logger.Logger;
import com.tongming.bwghost.bean.SnapshotToken;
import com.tongming.bwghost.interfaces.ISnapshotActionView;
import com.tongming.bwghost.interfaces.ISnapshotView;
import com.tongming.bwghost.interfaces.Api;
import com.tongming.bwghost.bean.SnapshotList;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.Call;

/**
 * Created by Tongming on 2016/7/23.
 */
public class SnapshotPresenterCompl {
    private ISnapshotView snapshotView;
    private ISnapshotActionView snapshotActionView;
    private Handler mHandler;
    private int DELETE_ACTION = 0;
    private int RESTORE_ACTION = 1;


    public SnapshotPresenterCompl(ISnapshotView snapshotView) {
        this.snapshotView = snapshotView;
        mHandler = new Handler(Looper.getMainLooper());
    }

    public SnapshotPresenterCompl(ISnapshotActionView snapshotActionView) {
        this.snapshotActionView = snapshotActionView;
        mHandler = new Handler(Looper.getMainLooper());
    }

    public void getSnapshotList(String veid, String key) {
        OkHttpUtils.get()
                .url(Api.SNAPSHOT.LIST)
                .addParams(Api.VEID, veid)
                .addParams(Api.KEY, key)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        try {
                            final SnapshotList list = new Gson().fromJson(response, new TypeToken<SnapshotList>() {
                            }.getType());
                            mHandler.post(new Runnable() {
                                @Override
                                public void run() {
                                    snapshotView.onGetList(list);
                                }
                            });
                        } catch (JsonSyntaxException e) {

                        }
                    }
                });
    }

    public void deleteSnapshot(String veid, String key, String snapshot) {
        OkHttpUtils.get()
                .url(Api.SNAPSHOT.DELETE)
                .addParams(Api.VEID, veid)
                .addParams(Api.KEY, key)
                .addParams(Api.PARAMS.SNAPSHOT, snapshot)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, final int id) {
                        Logger.e(e.getMessage());
                        mHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                snapshotView.onFail(id);
                            }
                        });
                    }

                    @Override
                    public void onResponse(String response, final int id) {
                        mHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                snapshotActionView.onDelete(DELETE_ACTION);
                            }
                        });
                    }
                });
    }

    public void restoreSnapshot(String veid, String key, String snapshot) {
        OkHttpUtils.get()
                .url(Api.SNAPSHOT.RESTORE)
                .addParams(Api.VEID, veid)
                .addParams(Api.KEY, key)
                .addParams(Api.PARAMS.SNAPSHOT, snapshot)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Logger.e(e.getMessage());
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        mHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                snapshotActionView.onRestore(RESTORE_ACTION);
                            }
                        });
                    }
                });
    }

    public void export(String veid, String key, final String snapshot) {
        OkHttpUtils.get()
                .url(Api.SNAPSHOT.EXPORT)
                .addParams(Api.VEID, veid)
                .addParams(Api.KEY, key)
                .addParams(Api.PARAMS.SNAPSHOT, snapshot)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        final SnapshotToken token = new Gson().fromJson(response, new TypeToken<SnapshotToken>() {
                        }.getType());
                        mHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                snapshotActionView.onExport(token);
                            }
                        });
                    }
                });
    }

    public void createSnapshot(String veid, String key, String description) {
        OkHttpUtils.get()
                .url(Api.SNAPSHOT.CREATE)
                .addParams(Api.VEID, veid)
                .addParams(Api.KEY, key)
                .addParams(Api.PARAMS.DESCRIPTION, description)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        try {
                            final JSONObject object = new JSONObject(response);
                            final String email = object.getString("notificationEmail");
                            if (object.getInt("error") == 0) {
                                mHandler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        snapshotView.onCreateSnapshot(email);
                                    }
                                });
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    public void importSnapshot(String veid, String key, String ohterVeid, String token) {
        OkHttpUtils.get()
                .url(Api.SNAPSHOT.IMPORT)
                .addParams(Api.VEID, veid)
                .addParams(Api.KEY, key)
                .addParams(Api.PARAMS.SOURCE_VEID, ohterVeid)
                .addParams(Api.PARAMS.SOURCE_TOKEN, token)
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
                                        snapshotView.onImportSnapshot();
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
