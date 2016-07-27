package com.tongming.bwghost.presenter;

import android.os.Environment;
import android.os.Handler;
import android.os.Looper;

import com.orhanobut.logger.Logger;
import com.tongming.bwghost.interfaces.IDownloadView;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.FileCallBack;

import java.io.File;

import okhttp3.Call;

/**
 * Created by Tongming on 2016/7/23.
 */
public class DownloadPresenterCompl {
    private IDownloadView downloadView;
    private Handler mHandler;

    public DownloadPresenterCompl(IDownloadView downloadView) {
        this.downloadView = downloadView;
        mHandler = new Handler(Looper.getMainLooper());
    }

    public void download(String url, String fileName) {
        OkHttpUtils.get()
                .url(url)
                .build()
                .execute(new FileCallBack(Environment.getExternalStorageDirectory().getAbsolutePath(), fileName) {
                    @Override
                    public void inProgress(final float progress, long total, int id) {
                        mHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                downloadView.onDownloading((int) (100 * progress));
                            }
                        });
                    }

                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Logger.e(e.getMessage());
                    }

                    @Override
                    public void onResponse(File response, int id) {
                        Logger.d("complete");
                        mHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                downloadView.onComplete();
                            }
                        });
                    }
                });
    }
}
