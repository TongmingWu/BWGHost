package com.tongming.bwghost.interfaces;

import com.tongming.bwghost.bean.Os;

/**
 * Created by Tongming on 2016/7/20.
 */
public interface ISystemView {
    void onGetOS(Os os);

    void onReinstall();

    void onFaild(int code);
}
