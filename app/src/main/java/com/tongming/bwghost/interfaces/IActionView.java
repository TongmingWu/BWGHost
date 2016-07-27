package com.tongming.bwghost.interfaces;

/**
 * Created by Tongming on 2016/7/19.
 */
public interface IActionView {
    void onAction(int code);

    void onChange(int code, String newHost);
}
