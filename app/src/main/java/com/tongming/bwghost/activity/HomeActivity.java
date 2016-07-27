package com.tongming.bwghost.activity;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.orhanobut.logger.Logger;
import com.tongming.bwghost.R;
import com.tongming.bwghost.base.BaseActivity;
import com.tongming.bwghost.fragment.MigrationFragment;
import com.tongming.bwghost.fragment.OSFragment;
import com.tongming.bwghost.fragment.ServiceFragment;
import com.tongming.bwghost.fragment.ShellFragment;
import com.tongming.bwghost.fragment.SnapshotFragment;

import java.lang.ref.WeakReference;

import butterknife.BindView;

public class HomeActivity extends BaseActivity {
    private static final int REQUEST_PERMISSION_CODE = 1;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.rl_content)
    RelativeLayout rlContent;
    @BindView(R.id.nav)
    NavigationView nav;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;
    private ServiceFragment serviceFragment;
    private OSFragment osFragment;
    private MigrationFragment migrationFragment;
    private ShellFragment shellFragment;
    private SnapshotFragment snapshotFragment;
    private static boolean isExit;
    private MyHandler handler = new MyHandler(this);


    private static class MyHandler extends Handler {
        private final WeakReference<HomeActivity> mActivity;

        private MyHandler(HomeActivity activity) {
            mActivity = new WeakReference<>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            HomeActivity activity = mActivity.get();
            if (activity != null) {
                isExit = false;
            }
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        checkPermission();
        setDefaultFragment();
    }

    @TargetApi(Build.VERSION_CODES.M)
    private boolean checkPermission() {
        if (!(checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)) {
            requestMultiplePermissions();
            return false;
        }
        return true;
    }

    @TargetApi(Build.VERSION_CODES.M)
    private void requestMultiplePermissions() {
        String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE};
        requestPermissions(permissions, REQUEST_PERMISSION_CODE);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_home;
    }


    @Override
    public void initViews() {
        //TODO bug:activity重建出现fragment重叠问题
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    drawerLayout.openDrawer(GravityCompat.START);
                }
            });
        }
        nav.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                FragmentManager manager = getFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();
                //机房迁移  更改系统  镜像操作  Shell命令
                switch (item.getItemId()) {
                    case R.id.menu_index:
                        hideFragment(transaction);
                        if (serviceFragment == null) {
                            serviceFragment = new ServiceFragment();
                            transaction.add(R.id.rl_content, serviceFragment, "service");
                        } else {
                            serviceFragment = (ServiceFragment) manager.findFragmentByTag("service");
                            transaction.show(serviceFragment);
                        }
                        break;
                    case R.id.menu_snapshot:
                        hideFragment(transaction);
                        if (snapshotFragment == null) {
                            snapshotFragment = new SnapshotFragment();
                            transaction.add(R.id.rl_content, snapshotFragment, "snapshot");
                        } else {
                            snapshotFragment = (SnapshotFragment) manager.findFragmentByTag("snapshot");
                            transaction.show(snapshotFragment);
                        }
                        break;
                    case R.id.menu_migration:
                        hideFragment(transaction);
                        if (migrationFragment == null) {
                            migrationFragment = new MigrationFragment();
                            transaction.add(R.id.rl_content, migrationFragment, "migration");
                        } else {
                            migrationFragment = (MigrationFragment) manager.findFragmentByTag("migration");
                            transaction.show(migrationFragment);
                        }
                        break;
                    case R.id.menu_reinstall:
                        hideFragment(transaction);
                        if (osFragment == null) {
                            osFragment = new OSFragment();
                            transaction.add(R.id.rl_content, osFragment, "os");
                        } else {
                            osFragment = (OSFragment) manager.findFragmentByTag("os");
                            transaction.show(osFragment);
                        }
                        break;
                    case R.id.menu_shell:
                        hideFragment(transaction);
                        if (shellFragment == null) {
                            shellFragment = new ShellFragment();
                            transaction.add(R.id.rl_content, shellFragment, "shell");
                        } else {
                            shellFragment = (ShellFragment) manager.findFragmentByTag("shell");
                            transaction.show(shellFragment);
                        }
                        break;
                }
                transaction.commit();
                drawerLayout.closeDrawer(Gravity.LEFT);
                return true;
            }
        });
    }

    private void setDefaultFragment() {
        FragmentManager fm = getFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        serviceFragment = new ServiceFragment();
        transaction.replace(R.id.rl_content, serviceFragment, "service");
        transaction.commit();
    }

    private void hideFragment(FragmentTransaction transition) {
        if (serviceFragment != null) transition.hide(serviceFragment);
        if (snapshotFragment != null) transition.hide(snapshotFragment);
        if (migrationFragment != null) transition.hide(migrationFragment);
        if (osFragment != null) transition.hide(osFragment);
        if (shellFragment != null) transition.hide(shellFragment);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            exit();
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void exit() {
        if (!isExit) {
            isExit = true;
            Toast.makeText(this, "It will exit if you touch again", Toast.LENGTH_SHORT).show();
            handler.sendEmptyMessageDelayed(0, 2000);
        } else {
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            startActivity(intent);
            //TODO 退出时提示正在下载中的任务
//            System.exit(0);
            finish();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Logger.d("home destroy");
    }
}
