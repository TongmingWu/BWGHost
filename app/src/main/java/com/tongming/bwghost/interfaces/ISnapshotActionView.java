package com.tongming.bwghost.interfaces;

import com.tongming.bwghost.bean.SnapshotToken;

/**
 * Created by Tongming on 2016/7/26.
 */
public interface ISnapshotActionView {
    void onDelete(int code);

    void onRestore(int code);

    void onFail(int code);

    void onExport(SnapshotToken token);
}
