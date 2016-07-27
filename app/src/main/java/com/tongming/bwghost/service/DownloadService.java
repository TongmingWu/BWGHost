package com.tongming.bwghost.service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.widget.RemoteViews;

import com.tongming.bwghost.R;
import com.tongming.bwghost.interfaces.IDownloadView;
import com.tongming.bwghost.presenter.DownloadPresenterCompl;

/**
 * Created by Tongming on 2016/7/23.
 */
public class DownloadService extends Service implements IDownloadView {
    private NotificationManager manager;
    private Notification notification;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notification = new Notification.Builder(this)
                .setSmallIcon(R.drawable.ic_file_download_white_36dp)
                .setOngoing(true)
                .build();
        notification.tickerText = "Preparing";
        notification.flags = Notification.FLAG_NO_CLEAR;    //不能够清除
        RemoteViews rv = new RemoteViews(getPackageName(), R.layout.notification_download);
        rv.setProgressBar(R.id.pb_download, 100, 0, false);
        rv.setTextViewText(R.id.tv_status, "Start");
        notification.contentView = rv;
        manager.notify(0, notification);
        DownloadPresenterCompl compl = new DownloadPresenterCompl(this);
        compl.download(intent.getStringExtra("url"), intent.getStringExtra("file"));
//        compl.download("http://downloads.jianshu.io/apps/haruki/JianShu-1.11.1.apk", "jianshu.apk");
        return super.onStartCommand(intent, flags, startId);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDownloading(int progress) {
        //更改通知布局UI
        RemoteViews rv = new RemoteViews(getPackageName(), R.layout.notification_download);
        rv.setTextViewText(R.id.tv_status, "Downloading...");
        rv.setTextViewText(R.id.tv_content, progress + "%");
        rv.setProgressBar(R.id.pb_download, 100, progress, false);
        notification.contentView = rv;
        manager.notify(0, notification);
    }

    @Override
    public void onComplete() {
        RemoteViews rv = new RemoteViews(getPackageName(), R.layout.notification_download);
        rv.setTextViewText(R.id.tv_status, "Download Completed");
        rv.setTextViewText(R.id.tv_content, "100%");
        rv.setProgressBar(R.id.pb_download, 100, 100, false);
        notification.contentView = rv;
        notification.flags = Notification.FLAG_AUTO_CANCEL;
        manager.notify(0, notification);
    }

    @Override
    public void onNetworkError(int code) {

    }
}
