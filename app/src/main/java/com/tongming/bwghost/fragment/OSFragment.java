package com.tongming.bwghost.fragment;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.AppCompatSpinner;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.orhanobut.logger.Logger;
import com.tongming.bwghost.R;
import com.tongming.bwghost.interfaces.ISystemView;
import com.tongming.bwghost.base.BaseFragment;
import com.tongming.bwghost.bean.Os;
import com.tongming.bwghost.presenter.OsPresenterCompl;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;

/**
 * Created by Tongming on 2016/7/19.
 */
public class OSFragment extends BaseFragment implements ISystemView {
    @BindView(R.id.sp_centos)
    AppCompatSpinner spCentos;
    @BindView(R.id.sp_debian)
    AppCompatSpinner spDebian;
    @BindView(R.id.sp_fedora)
    AppCompatSpinner spFedora;
    @BindView(R.id.sp_oracle)
    AppCompatSpinner spOracle;
    @BindView(R.id.sp_scientific)
    AppCompatSpinner spScientific;
    @BindView(R.id.sp_suse)
    AppCompatSpinner spSuse;
    @BindView(R.id.sp_ubuntu)
    AppCompatSpinner spUbuntu;
    @BindView(R.id.tv_selected_os)
    TextView selected;
    @BindView(R.id.btn_reload)
    Button reload;
    @BindView(R.id.progressbar)
    ProgressBar bar;
    @BindView(R.id.ll_content)
    LinearLayout content;
    private OsPresenterCompl compl;
    private SharedPreferences sp;

    @Override
    protected void initViews() {
        Logger.d("os create");
        spCentos.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selected.setText(parent.getItemAtPosition(position).toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spDebian.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selected.setText(parent.getItemAtPosition(position).toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spFedora.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selected.setText(parent.getItemAtPosition(position).toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spOracle.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selected.setText(parent.getItemAtPosition(position).toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spScientific.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selected.setText(parent.getItemAtPosition(position).toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spSuse.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selected.setText(parent.getItemAtPosition(position).toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spUbuntu.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selected.setText(parent.getItemAtPosition(position).toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        reload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Warning!")
                        .setMessage("Are you certainly to reinstall the " + selected.getText().toString() + " ?")
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                compl.reinstall(sp.getString("veid", ""),
                                        sp.getString("key", ""),
                                        selected.getText().toString());
                            }
                        }).setNegativeButton("Cancel", null)
                        .show();
            }
        });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_os;
    }

    @Override
    protected void afterCreate(Bundle saveInstanceState) {
        compl = new OsPresenterCompl(this);
        sp = getActivity().getSharedPreferences("config", Context.MODE_PRIVATE);
        String veid = sp.getString("veid", "");
        String key = sp.getString("key", "");
        if (!"".equals(veid) && !"".equals(key)) {
            bar.setVisibility(View.VISIBLE);
            content.setVisibility(View.INVISIBLE);
            compl.getAvaiableOS(veid, key);
        }
    }

    @Override
    public void onGetOS(Os os) {
        Logger.d("获取os列表成功");
        bar.setVisibility(View.GONE);
        content.setVisibility(View.VISIBLE);
        String[] regx = new String[]{"centos", "debian", "fedora", "oracle", "scientific",
                "suse", "ubuntu"};
        //TODO 待改进
        List<AppCompatSpinner> spinners = new ArrayList<>();
        spinners.add(spCentos);
        spinners.add(spDebian);
        spinners.add(spFedora);
        spinners.add(spOracle);
        spinners.add(spScientific);
        spinners.add(spSuse);
        spinners.add(spUbuntu);
        for (int i = 0; i < regx.length; i++) {
            List<String> items = new ArrayList<>();
            Pattern pattern = Pattern.compile(regx[i]);
            for (String system : os.getTemplates()) {
                Matcher matcher = pattern.matcher(system);
                if (matcher.find()) {
                    items.add(system);
                }
            }
            spinners.get(i).setAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, items));
        }
    }

    @Override
    public void onReinstall() {
        Toast.makeText(getActivity(), "reinstalling", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onFaild(int code) {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Logger.d("os destroy");
    }
}
