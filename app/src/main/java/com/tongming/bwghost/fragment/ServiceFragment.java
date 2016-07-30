package com.tongming.bwghost.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.orhanobut.logger.Logger;
import com.tongming.bwghost.R;
import com.tongming.bwghost.base.BaseFragment;
import com.tongming.bwghost.bean.ServiceInfo;
import com.tongming.bwghost.interfaces.Api;
import com.tongming.bwghost.interfaces.IActionView;
import com.tongming.bwghost.interfaces.IConfView;
import com.tongming.bwghost.presenter.ActionPresenterCompl;
import com.tongming.bwghost.presenter.ConfPresenterCompl;

import java.math.RoundingMode;
import java.text.DecimalFormat;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Tongming on 2016/7/19.
 */
public class ServiceFragment extends BaseFragment implements IConfView, IActionView {
    @BindView(R.id.tv_host_name)
    TextView tvHostName;
    @BindView(R.id.location)
    TextView location;
    @BindView(R.id.ip)
    TextView ip;
    @BindView(R.id.port)
    TextView port;
    @BindView(R.id.status)
    TextView status;
    @BindView(R.id.btn_start)
    Button btnStart;
    @BindView(R.id.btn_reboot)
    Button btnReboot;
    @BindView(R.id.btn_stop)
    Button btnStop;
    @BindView(R.id.btn_kill)
    Button btnKill;
    @BindView(R.id.pb_ram)
    ProgressBar pbRam;
    @BindView(R.id.ram)
    TextView ram;
    @BindView(R.id.pb_swap)
    ProgressBar pbSwap;
    @BindView(R.id.swap)
    TextView swap;
    @BindView(R.id.pb_disk)
    ProgressBar pbDisk;
    @BindView(R.id.disk)
    TextView disk;
    @BindView(R.id.pb_delivery)
    ProgressBar pbDelivery;
    @BindView(R.id.delivery)
    TextView delivery;
    @BindView(R.id.system)
    TextView system;
    @BindView(R.id.host)
    TextView host;
    @BindView(R.id.tv_change)
    TextView tvChange;
    @BindView(R.id.rl_content)
    RelativeLayout rlContent;
    @BindView(R.id.btn_refresh)
    ImageView refresh;

    private SharedPreferences sp;
    private ProgressDialog actionDialog;
    private ConfPresenterCompl compl;
    private ActionPresenterCompl actionCompl;
    private String veid;
    private String key;
    private boolean isConfig;

    /*private Handler handler = new Handler();
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            this.update();
            handler.postDelayed(runnable, 1000 * 180);
            Logger.d("刷新完成");
        }

        void update() {
            if (veid != null && key != null) {
                compl.getServiceInfo(veid, key);
            }
        }
    };*/
    private Animation animation;

    @Override
    protected void initViews() {
        Logger.d("service create");
        sp = getActivity().getSharedPreferences("config", Context.MODE_PRIVATE);
        isConfig = sp.getBoolean("isConfig", false);
        if (!isConfig) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setMessage("You has not VPS,please to configure now")
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            View config = getActivity().getLayoutInflater().inflate(R.layout.dialog_config, null);
                            final EditText et_veid = (EditText) config.findViewById(R.id.et_veid);
                            final EditText et_key = (EditText) config.findViewById(R.id.et_key);
                            new AlertDialog.Builder(getActivity())
                                    .setTitle("Configuration")
                                    .setView(config)
                                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            if (!"".equals(et_veid.getText().toString()) && !"".equals(et_key.getText().toString())) {
                                                veid = et_veid.getText().toString();
                                                key = et_key.getText().toString();
                                                doConfigure(veid, key);
                                                refresh.startAnimation(animation);
                                            }
                                        }
                                    })
                                    .setNegativeButton("Cancel", null)
                                    .show();
                            Logger.d("开始配置");
                        }
                    }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    System.exit(0);
                }
            }).show();
        } else {
            veid = sp.getString("veid", "");
            key = sp.getString("key", "");
            compl = new ConfPresenterCompl(this);
            compl.getServiceInfo(veid, key);
            actionCompl = new ActionPresenterCompl(this);
            animation = AnimationUtils.loadAnimation(getActivity(), R.anim.refresh);
            LinearInterpolator interpolator = new LinearInterpolator();
            animation.setInterpolator(interpolator);
            refresh.startAnimation(animation);
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_service;
    }

    @Override
    protected void afterCreate(Bundle saveInstanceState) {
//        handler.postDelayed(runnable, 1000 * 180);

    }

    @OnClick({R.id.btn_start, R.id.btn_reboot, R.id.btn_stop, R.id.btn_kill, R.id.tv_change, R.id.btn_refresh})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_start:
                Logger.d("Service is Starting");
                actionDialog = ProgressDialog.show(getActivity(), null, "Service is Starting");
                actionCompl.doAction(veid, key, Api.Manager.START);
                break;
            case R.id.btn_reboot:
                Logger.d("Service is Rebooting");
                actionDialog = ProgressDialog.show(getActivity(), null, "Service is Rebooting");
                actionCompl.doAction(veid, key, Api.Manager.RESTART);
                break;
            case R.id.btn_stop:
                Logger.d("Service is Stopping");
                actionDialog = ProgressDialog.show(getActivity(), null, "Service is Stopping");
                actionCompl.doAction(veid, key, Api.Manager.STOP);
                break;
            case R.id.btn_kill:
                Logger.d("Service is Killing");
                actionDialog = ProgressDialog.show(getActivity(), null, "Service is Killing");
                actionCompl.doAction(veid, key, Api.Manager.KILL);
                break;
            case R.id.tv_change:
                final EditText editText = new EditText(getActivity());
                new AlertDialog.Builder(getActivity()).setTitle("Please input a new host")
                        .setView(editText)
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if (!"".equals(editText.getText().toString()) && editText.getText().toString() != null) {
                                    String newHost = editText.getText().toString();
                                    actionCompl.doChange(veid, key, newHost);
                                }
                            }
                        })
                        .setNegativeButton("Cancel", null)
                        .show();
                break;
            case R.id.btn_refresh:
                refresh.startAnimation(animation);
                compl.getServiceInfo(veid, key);
        }
    }

    private void doConfigure(String veid, String key) {
        if (compl == null) {
            compl = new ConfPresenterCompl(this);
            compl.getServiceInfo(veid, key);
        }
    }

    private void initData(ServiceInfo info) {
        tvHostName.setText(info.getHostname() + "  [" + info.getPlan().toUpperCase() + "]");
        location.setText(info.getNode_location());
        ip.setText(info.getIp_addresses().get(0));
        port.setText(info.getSsh_port() + "");
        ServiceInfo.VzStatusBean vz_status = info.getVz_status();
        if (vz_status.getStatus().equals("stopped")) {
            status.setText(vz_status.getStatus());
        } else {
            status.setText(vz_status.getStatus().substring(0, 1).toUpperCase() + vz_status.getStatus().substring(1)
                    + "  (" + vz_status.getNproc() + " Processes; LA:" + vz_status.getLoad_average() + " )"
            );
        }
        DecimalFormat df = new DecimalFormat("0.00");
        df.setRoundingMode(RoundingMode.HALF_UP);
        final double progressRam;
        double progressSwap;
        if (vz_status.getStatus().equals("running")) {
            progressRam = Integer.parseInt(vz_status.getOomguarpages()) * 4 / 1024.0;
            progressSwap = Integer.parseInt(vz_status.getSwappages()) * 4 / 1024.0;
        } else {
            progressRam = 0;
            progressSwap = 0;
        }
        int maxRam = info.getPlan_ram() / 1024 / 1024;
        ram.setText(df.format(progressRam) + " / " + maxRam + " MB");
        pbRam.setMax(maxRam);
        setProgress(pbRam, (int) progressRam);
//        pbRam.setProgress((int) progressRam);

        int maxSwap = info.getPlan_swap() / 1024 / 1024;
        swap.setText(df.format(progressSwap) + " / " + maxSwap + " MB");
        pbSwap.setMax(maxSwap);
//        pbSwap.setProgress((int) progressSwap);
        setProgress(pbSwap, (int) progressSwap);

        double progressDisk = Integer.parseInt(info.getVz_quota().getOccupied_kb()) / 1024 / 1024.0;
        long maxDisk = info.getPlan_disk() / 1024 / 1024 / 1024;
        disk.setText(df.format(progressDisk) + " / " + maxDisk + " GB");
        pbDisk.setMax((int) maxDisk);
//        pbDisk.setProgress((int) progressDisk);
        setProgress(pbDisk, (int) progressDisk);

        double progressDelivery = info.getData_counter() / 1024 / 1024 / 1024.0;
        long maxDelivery = info.getPlan_monthly_data() / 1024 / 1024 / 1024;
        delivery.setText(df.format(progressDelivery) + " / " + maxDelivery + " GB");
        pbDelivery.setMax((int) maxDelivery);
//        pbDelivery.setProgress((int) progressDelivery);
        setProgress(pbDelivery, (int) progressDelivery);
        //TODO 实现progressbar和数字动态显示的效果
        system.setText(info.getOs());
        host.setText(info.getHostname());
        tvChange.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
    }

    private void setProgress(final ProgressBar progressBar, final int progress) {
        new Thread(new Runnable() {
            int progressStatus = 0;

            @Override
            public void run() {
                while (progressStatus < progress) {
                    new Handler(Looper.getMainLooper()).post(new Runnable() {
                        @Override
                        public void run() {
                            progressBar.setProgress(progressStatus);
                        }
                    });
                    try {
                        Thread.sleep(20);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    progressStatus++;
                }
            }
        }).start();
    }

    @Override
    public void onAction(int code) {
        if (actionDialog != null && actionDialog.isShowing()) {
            actionDialog.dismiss();
        }
        if (code == 0) {
            //操作成功刷新界面
            compl.getServiceInfo(veid, key);
        } else {
            Toast.makeText(getActivity(), "timeout", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onChange(int code, String newHost) {
        if (code == 0) {
            host.setText(newHost);
            Toast.makeText(getActivity(), "succeed", Toast.LENGTH_SHORT).show();
        } else {

        }
    }

    @Override
    public void onGetInfo(ServiceInfo info) {
        if (info != null) {

            if (!isConfig) {
                SharedPreferences.Editor edit = sp.edit();
                edit.putBoolean("isConfig", true)
                        .putString("veid", veid)
                        .putString("key", key)
                        .commit();

            }
            if (rlContent.getVisibility() == View.INVISIBLE) {
                rlContent.setVisibility(View.VISIBLE);
                RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                        ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT
                );
                params.addRule(RelativeLayout.BELOW, R.id.rl_content);
                params.addRule(RelativeLayout.CENTER_HORIZONTAL);
                refresh.setLayoutParams(params);
            }
            refresh.clearAnimation();
            if (actionDialog != null) {
                if (actionDialog.isShowing()) {
                    actionDialog.dismiss();
                    Toast.makeText(getActivity(), "succeed", Toast.LENGTH_SHORT).show();
                }
            }
            initData(info);
        }
    }

    @Override
    public void onDestroy() {
//        handler.removeCallbacks(runnable);
//        OkHttpUtils.getInstance().cancelTag(compl.REQUEST_TAG);
        Logger.d("service destroy");
        super.onDestroy();
    }

    @Override
    public void onFail(int code) {
        Toast.makeText(getActivity(), "failed", Toast.LENGTH_SHORT).show();
    }
}
