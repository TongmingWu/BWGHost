package com.tongming.bwghost.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

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
    private final int CD_ERROR = 722103;
    @BindView(R.id.rl_content)
    LinearLayout content;
    private EditText etCommand;
    private String currentDir;
    private boolean isResponse;

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
                    if (split.length > 1 && split[0].equals("cd")) {
                        //切换路径
                        compl.cd(config[0], config[1], currentDir, split[1]);
                    } else if (split[0].equals("ls") && !"/".equals(currentDir)) {
                        if (split.length > 1) { //ls 后面有参数的情况
                            boolean isRelative = false;
                            if (!split[1].startsWith("/", 0)) {
                                isRelative = true;  //根据参数的最前面是否为"/"判断相对还是绝对
                            }
                            //如果参数是相对路径
                            if (isRelative) {
                                compl.exec(config[0], config[1], split[0] + " " + currentDir + "/" + split[1]);
                            } else {
                                compl.exec(config[0], config[1], split[0] + " " + split[1]);
                            }
                        } else {
                            //单单只有ls的情况
                            compl.exec(config[0], config[1], split[0] + " " + currentDir);
                        }
                    } else {
                        //带上绝对路径
                        //如何判断是否需要路径
                        compl.exec(config[0], config[1], command);
                    }
                    isResponse = true;
                    return true;
                }
                return false;
            }
        });
    }

    private void addResult(String message) {
        TextView tvCommandResult = new TextView(getActivity());
        tvCommandResult.setText(message);
        tvCommandResult.setTextColor(Color.WHITE);
        tvCommandResult.setTextSize(16);
        content.addView(tvCommandResult);
    }

    @Override
    public void onCommand(String message) {
        if (isResponse) {
            addResult(message);
            setCommand();
            isResponse = false;
        }
    }

    @Override
    public void onCd(String cd) {
        if (isResponse) {
            currentDir = cd;
            setCommand();
            isResponse = false;
        }
    }

    @Override
    public void onFail(int code, String message) {
        switch (code) {
            case CD_ERROR:
                if (isResponse) {
                    addResult(message);
                    setCommand();
                    isResponse = false;
                }
                break;
        }
    }
}
