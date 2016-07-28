package com.tongming.bwghost.interfaces;

/**
 * Created by Tongming on 2016/7/27.
 */
public interface IShellView {
    void onCommand(String message);

    void onCd(String cd);

    void onFail(int code, String message);
}
