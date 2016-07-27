package com.tongming.bwghost.interfaces;

import com.tongming.bwghost.bean.SnapshotList;

/**
 * Created by Tongming on 2016/7/23.
 */
public interface ISnapshotView {
    void onGetList(SnapshotList list);

    void onCreateSnapshot(String email);

    void onImportSnapshot();

    void onFail(int code);

}
