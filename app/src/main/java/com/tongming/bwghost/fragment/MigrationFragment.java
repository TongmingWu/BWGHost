package com.tongming.bwghost.fragment;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.AppCompatRadioButton;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.tongming.bwghost.R;
import com.tongming.bwghost.interfaces.IMigrationView;
import com.tongming.bwghost.base.BaseFragment;
import com.tongming.bwghost.bean.Location;
import com.tongming.bwghost.presenter.MigrationPresenterCompl;

import butterknife.BindView;

/**
 * Created by Tongming on 2016/7/19.
 */
public class MigrationFragment extends BaseFragment implements IMigrationView {
    AppCompatRadioButton rbAmsterdam;
    @BindView(R.id.rg_location)
    RadioGroup rgLocation;
    @BindView(R.id.btn_migration)
    Button btnMigration;
    @BindView(R.id.tv_current_location)
    TextView current;
    private SharedPreferences sp;
    private String location;

    @Override
    protected void initViews() {
        final MigrationPresenterCompl compl = new MigrationPresenterCompl(this);
        sp = getActivity().getSharedPreferences("config", Context.MODE_PRIVATE);
        compl.getLocations(sp.getString("veid", ""), sp.getString("key", ""));
        rgLocation.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton button = (RadioButton) group.findViewById(checkedId);
                location = button.getText().toString().split(" ")[0];
            }
        });
        btnMigration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(getActivity())
                        .setTitle("Migration")
                        .setMessage("Are you certainly to migrate to " + location + " ?")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                compl.migration(sp.getString("veid", ""),
                                        sp.getString("key", ""),
                                        location);
                            }
                        }).setNegativeButton("Cancel", null).show();
            }
        });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_migration;
    }

    @Override
    protected void afterCreate(Bundle saveInstanceState) {

    }


    @Override
    public void onGetLocation(Location location) {
        current.setText("Current Location : " + location.getCurrentLocation());
        for (int i = 0; i < location.getLocations().size(); i++) {
            String locate = convertDesc(location.getLocations().get(i));
            AppCompatRadioButton button = new AppCompatRadioButton(getActivity());
            RadioGroup.LayoutParams params = new RadioGroup.LayoutParams(
                    RadioGroup.LayoutParams.MATCH_PARENT, RadioGroup.LayoutParams.WRAP_CONTENT
            );
            params.topMargin = 30;
            params.bottomMargin = 30;
            button.setLayoutParams(params);
            button.setText(locate);
            rgLocation.addView(button);
        }
    }

    private String convertDesc(String l) {
        String locate = "";
        switch (l) {
            case "USCA_2":
                locate = l + " : " + "US: Los Angeles, California (IPv4+IPv6)";
                break;
            case "USCA_FMT":
                locate = l + " : " + "US: Fremont, California (IPv4+IPv6)";
                break;
            case "USAZ_2":
                locate = l + " : " + "US: Phoenix, Arizona (IPv4+IPv6)";
                break;
            case "USFL_2":
                locate = l + " : " + "US: Jacksonville, Florida (IPv4+IPv6)";
                break;
            case "EUNL_3":
                locate = l + " : " + "EU: Amsterdam, Netherlands (IPv4+IPv6)";
                break;
        }
        return locate;
    }

    @Override
    public void onMigration() {
        Toast.makeText(getActivity(),"It will migrate in few minute",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onFaild(int code) {

    }
}