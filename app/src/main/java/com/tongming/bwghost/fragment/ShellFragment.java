package com.tongming.bwghost.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.orhanobut.logger.Logger;
import com.tongming.bwghost.R;
import com.tongming.bwghost.base.BaseFragment;
import com.tongming.bwghost.interfaces.IShellView;
import com.tongming.bwghost.presenter.ShellPresenterCompl;
import com.tongming.bwghost.utils.CommonUtil;

import butterknife.BindView;

/**
 * Created by Tongming on 2016/7/19.
 */
public class ShellFragment extends BaseFragment implements IShellView {
    /*@BindView(R.id.tv_command_cd)
    TextView tvCommandCd;
    @BindView(R.id.et_command)
    EditText etCommand;
    @BindView(R.id.tv_command_result)
    TextView tvCommandResult;*/
    @BindView(R.id.rl_content)
    LinearLayout content;
    private EditText etCommand;
    private String currentDir;

    @Override
    protected void initViews() {
        currentDir = "/";
        setCommand();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_shell;
    }

    @Override
    protected void afterCreate(Bundle saveInstanceState) {

    }

    private void setCommand() {
        View command = getActivity().getLayoutInflater().inflate(R.layout.item_command, null);
        TextView tvCd = (TextView) command.findViewById(R.id.tv_command_cd);
        tvCd.setText("[root  " + currentDir + "]#");
        etCommand = (EditText) command.findViewById(R.id.et_command);
        content.addView(command);
        CommonUtil.showSoftInput(getActivity(), etCommand);
        etCommand.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE || (event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
                    ShellPresenterCompl compl = new ShellPresenterCompl(ShellFragment.this);
                    String[] config = CommonUtil.getConfig(getActivity());
                    String command = etCommand.getText().toString();
                    String[] split = command.split(" ");
                    if (split[0].equals("cd")) {
                        //切换路径
                        compl.cd(config[0], config[1], currentDir, split[1]);
                    } else {
                        //带上绝对路径
                        //如何判断是否需要路径
                        compl.exec(config[0], config[1], command);
                    }
                    return true;
                }
                return false;
            }
        });
    }

    @Override
    public void onCommand(String message) {
        Logger.d(message);
        TextView tvCommandResult = new TextView(getActivity());
        tvCommandResult.setText(message);
        tvCommandResult.setTextColor(Color.WHITE);
        tvCommandResult.setTextSize(16);
        content.addView(tvCommandResult);
        setCommand();
    }

    @Override
    public void onCd(String cd) {
        currentDir = cd;
        setCommand();
    }

    @Override
    public void onFail(int code) {

    }
}
