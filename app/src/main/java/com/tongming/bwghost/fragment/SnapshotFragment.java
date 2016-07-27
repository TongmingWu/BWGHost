package com.tongming.bwghost.fragment;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.tongming.bwghost.R;
import com.tongming.bwghost.adapter.SnapshotAdapter;
import com.tongming.bwghost.base.BaseFragment;
import com.tongming.bwghost.bean.SnapshotList;
import com.tongming.bwghost.interfaces.ISnapshotView;
import com.tongming.bwghost.presenter.SnapshotPresenterCompl;
import com.tongming.bwghost.utils.CommonUtil;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Tongming on 2016/7/19.
 */
public class SnapshotFragment extends BaseFragment implements ISnapshotView {

    private static final int REQUEST_PERMISSION_CODE = 1;
    /*@BindView(R.id.tv_os)
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
    @BindView(R.id.rl_current_snapshot)
    RelativeLayout rlCurrentSnapshot;*/
    @BindView(R.id.btn_create)
    Button btnCreate;
    @BindView(R.id.btn_import)
    Button btnImport;
    @BindView(R.id.tv_introduce)
    TextView introduce;
    @BindView(R.id.rv_snapshot)
    RecyclerView rvSnapshot;
    private SnapshotList list;
    private SnapshotPresenterCompl compl;
    private String fileName;
    private String downloadUrl;
    private SharedPreferences sp;
    private ProgressDialog progressDialog;

    @Override
    protected void initViews() {
        compl = new SnapshotPresenterCompl(this);
        String[] config = CommonUtil.getConfig(getActivity());
        compl.getSnapshotList(config[0], config[1]);
        rvSnapshot.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_snapshot;
    }

    @Override
    protected void afterCreate(Bundle saveInstanceState) {

    }

    @OnClick({R.id.btn_create, R.id.btn_import})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_create:
                LinearLayout linearLayout = new LinearLayout(getActivity());
                linearLayout.setOrientation(LinearLayout.VERTICAL);
                linearLayout.setPadding(10, 10, 10, 10);
                TextView tv = new TextView(getActivity());
                tv.setText("Input your snapshot:");
                tv.setTextColor(Color.BLACK);
                final EditText et = new EditText(getActivity());

                linearLayout.addView(tv);
                linearLayout.addView(et);
                AlertDialog dialog = new AlertDialog.Builder(getActivity())
                        .setTitle("Create")
                        .setPositiveButton("create", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if (!"".equals(et.getText().toString()) && et.getText().toString() != null) {
                                    String snapshot = et.getText().toString();
                                    String[] config = CommonUtil.getConfig(getActivity());
                                    compl.createSnapshot(config[0], config[1], snapshot);
                                    progressDialog = ProgressDialog.show(getActivity(), null, "Creating...");
                                }
                            }
                        }).setNegativeButton("Cancel", null)
                        .create();

                dialog.setView(linearLayout);
                dialog.show();
                break;
            case R.id.btn_import:
                LinearLayout ll = new LinearLayout(getActivity());
                ll.setOrientation(LinearLayout.VERTICAL);
                ll.setPadding(10, 10, 10, 10);
                TextView tvVeid = new TextView(getActivity());
                tvVeid.setText("Input your source veid:");
                tvVeid.setTextColor(Color.BLACK);
                final EditText etVeid = new EditText(getActivity());

                TextView tvToken = new TextView(getActivity());
                tvToken.setText("Input your source token:");
                tvToken.setTextColor(Color.BLACK);
                final EditText etToken = new EditText(getActivity());

                ll.addView(tvVeid);
                ll.addView(etVeid);
                ll.addView(tvToken);
                ll.addView(etToken);

                new AlertDialog.Builder(getActivity())
                        .setView(ll)
                        .setTitle("Import")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String veid = etVeid.getText().toString();
                                String token = etToken.getText().toString();
                                if (!"".equals(veid) && veid != null && !"".equals(token) && token != null) {
                                    String[] config = CommonUtil.getConfig(getActivity());
                                    compl.importSnapshot(config[0], config[1], veid, token);
                                    progressDialog = ProgressDialog.show(getActivity(), null, "Importing...");
                                }
                            }
                        })
                        .setNegativeButton("Cancel", null)
                        .show();
                break;
        }
    }

    @Override
    public void onGetList(SnapshotList list) {
        if (list != null) {
            this.list = list;
            if (list.getSnapshots().size() > 0) {
                introduce.setVisibility(View.VISIBLE);
                rvSnapshot.setAdapter(new SnapshotAdapter(list.getSnapshots(), getActivity()));
            }
        }
    }

    @Override
    public void onCreateSnapshot(String email) {
        //创建完成之后刷新界面
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
        Toast.makeText(getActivity(), "Please wait a few minutes", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onImportSnapshot() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
        Toast.makeText(getActivity(), "Please wait a few minutes", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onFail(int code) {

    }
}
