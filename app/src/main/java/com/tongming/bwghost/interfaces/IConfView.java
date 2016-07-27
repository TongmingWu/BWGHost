package com.tongming.bwghost.interfaces;

import com.tongming.bwghost.bean.ServiceInfo;

/**
 * Created by Tongming on 2016/7/17.
 */
public interface IConfView {
    void onGetInfo(ServiceInfo info);

    void onFail(int code);

}
