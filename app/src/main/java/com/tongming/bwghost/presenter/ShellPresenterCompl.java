package com.tongming.bwghost.presenter;

import android.os.Handler;
import android.os.Looper;

import com.tongming.bwghost.interfaces.Api;
import com.tongming.bwghost.interfaces.IShellView;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.Call;

/**
 * Created by Tongming on 2016/7/27.
 */
public class ShellPresenterCompl {
    private IShellView shellView;
    private Handler mHandler;
    private int CD_ERROR = 722103;

    public ShellPresenterCompl(IShellView shellView) {
        this.shellView = shellView;
        mHandler = new Handler(Looper.getMainLooper());
    }

    public void exec(String veid, String key, String command) {
        OkHttpUtils.get()
                .url(Api.Manager.BASIC_SHELL.EXEC)
                .addParams(Api.VEID, veid)
                .addParams(Api.KEY, key)
                .addParams(Api.PARAMS.COMMAND, command)
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
                                try {
                                    JSONObject object = new JSONObject(response);
                                    shellView.onCommand(object.getString("message"));
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                    }
                });
    }

    public void cd(String veid, String key, String currentDir, String newDir) {
        boolean isResponse;
        OkHttpUtils.get()
                .url(Api.Manager.BASIC_SHELL.CD)
                .addParams(Api.VEID, veid)
                .addParams(Api.KEY, key)
                .addParams(Api.PARAMS.CURRENT_DIR, currentDir)
                .addParams(Api.PARAMS.NEW_DIR, newDir)
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
                                try {
                                    JSONObject object = new JSONObject(response);
                                    if (object.getInt("error") == 0) {
                                        //返回绝对路径
                                        shellView.onCd(object.getString("pwd"));
                                    } else {
                                        shellView.onFail(CD_ERROR, object.getString("message"));
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                    }
                });
    }
}
