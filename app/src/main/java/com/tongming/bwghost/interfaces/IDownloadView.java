package com.tongming.bwghost.interfaces;

/**
 * Created by Tongming on 2016/7/23.
 */
public interface IDownloadView {
    void onDownloading(int progress);

    void onComplete();

    void onNetworkError(int code);
}
