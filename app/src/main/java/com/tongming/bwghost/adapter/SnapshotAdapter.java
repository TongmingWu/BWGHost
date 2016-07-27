package com.tongming.bwghost.adapter;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.orhanobut.logger.Logger;
import com.tongming.bwghost.R;
import com.tongming.bwghost.bean.SnapshotList;
import com.tongming.bwghost.bean.SnapshotToken;
import com.tongming.bwghost.interfaces.ISnapshotActionView;
import com.tongming.bwghost.presenter.SnapshotPresenterCompl;
import com.tongming.bwghost.service.DownloadService;
import com.tongming.bwghost.utils.CommonUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Tongming on 2016/7/25.
 */
public class SnapshotAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements ISnapshotActionView {

    private List<SnapshotList.SnapshotsBean> list;
    private Activity mContext;
    private SharedPreferences sp;
    private SnapshotPresenterCompl compl;
    private ProgressDialog progressDialog;

    public SnapshotAdapter(List<SnapshotList.SnapshotsBean> list, Activity mContext) {
        this.list = list;
        this.mContext = mContext;
        sp = mContext.getSharedPreferences("config", Context.MODE_PRIVATE);
        compl = new SnapshotPresenterCompl(this);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mContext.getLayoutInflater().inflate(R.layout.item_snapshot, null);
        SnapshotHolder holder = new SnapshotHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof RecyclerView.ViewHolder) {
            SnapshotList.SnapshotsBean bean = list.get(position);
            ((SnapshotHolder) holder).tvOs.setText(bean.getOs());
            ((SnapshotHolder) holder).tvSize.setText(Math.round(Integer.parseInt(bean.getSize()) / 1024 / 1024.0) + "MB");
            ((SnapshotHolder) holder).tvDesc.setText("Description : " + bean.getDescription());
            ((SnapshotHolder) holder).tvMd5.setText("Md5 : " + bean.getMd5());
            ((SnapshotHolder) holder).btnDownload.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                }
            });
            ((SnapshotHolder) holder).btnDownload.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    boolean isAllow = checkPermission();
                    if (isAllow) {
                        new AlertDialog.Builder(mContext)
                                .setTitle("Prompting")
                                .setMessage("It will download the snapshot file")
                                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        String url = list.get(position).getDownloadLink();
                                        String fileName = list.get(position).getFileName();
                                        startService(url, fileName);
                                    }
                                }).setNegativeButton("Cancel", null).show();
                    } else {
                        Toast.makeText(mContext, "没有存取权限,请设置", Toast.LENGTH_SHORT).show();
                    }
                }
            });
            ((SnapshotHolder) holder).btnRestore.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new AlertDialog.Builder(mContext)
                            .setTitle("Warning")
                            .setMessage("Are you sure to restore ?")
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    progressDialog = ProgressDialog.show(mContext, null, "Restoring...");
                                    String[] config = CommonUtil.getConfig(mContext);
                                    compl.restoreSnapshot(config[0], config[1], list.get(position).getFileName());
                                }
                            }).setNegativeButton("Cancel", null).show();
                }
            });
            ((SnapshotHolder) holder).btnDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new AlertDialog.Builder(mContext)
                            .setTitle("Warning")
                            .setMessage("Are you sure to delete ?")
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    progressDialog = ProgressDialog.show(mContext, null, "Deleting...");
                                    String[] config = CommonUtil.getConfig(mContext);
                                    compl.deleteSnapshot(config[0], config[1], list.get(position).getFileName());
                                }
                            }).setNegativeButton("Cancel", null).show();
                }
            });
            ((SnapshotHolder) holder).btnExport.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    progressDialog = ProgressDialog.show(mContext, null, "Exporting");
                    String[] config = CommonUtil.getConfig(mContext);
                    compl.export(config[0], config[1], list.get(position).getFileName());
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    private void startService(String downloadUrl, String fileName) {
        Intent intent = new Intent(mContext, DownloadService.class);
        intent.putExtra("url", downloadUrl);
        intent.putExtra("file", fileName);
        Logger.d("StartService");
        mContext.startService(intent);
    }

    @TargetApi(Build.VERSION_CODES.M)
    private boolean checkPermission() {
        if ((mContext.checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)) {
            return true;
        }
        return false;
    }

    @Override
    public void onDelete(int code) {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }

    @Override
    public void onRestore(int code) {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }

    @Override
    public void onExport(SnapshotToken token) {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
        if (token == null) {
            return;
        }
        new AlertDialog.Builder(mContext)
                .setTitle("Snapshot Token")
                .setMessage(token.getToken())
                .setPositiveButton("OK", null)
                .show();
    }

    @Override
    public void onFail(int code) {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }

    class SnapshotHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_os)
        TextView tvOs;
        @BindView(R.id.tv_size)
        TextView tvSize;
        @BindView(R.id.tv_desc)
        TextView tvDesc;
        @BindView(R.id.tv_md5)
        TextView tvMd5;
        @BindView(R.id.btn_download)
        Button btnDownload;
        @BindView(R.id.btn_restore)
        Button btnRestore;
        @BindView(R.id.btn_delete)
        Button btnDelete;
        @BindView(R.id.btn_export)
        Button btnExport;

        SnapshotHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
