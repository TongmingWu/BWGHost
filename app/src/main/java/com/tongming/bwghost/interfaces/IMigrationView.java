package com.tongming.bwghost.interfaces;

import com.tongming.bwghost.bean.Location;

/**
 * Created by Tongming on 2016/7/21.
 */
public interface IMigrationView {
    void onGetLocation(Location location);

    void onMigration();

    void onFaild(int code);
}
