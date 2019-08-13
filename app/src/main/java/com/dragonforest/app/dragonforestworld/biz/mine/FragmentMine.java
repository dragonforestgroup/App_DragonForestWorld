package com.dragonforest.app.dragonforestworld.biz.mine;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.dragonforest.app.dragonforestworld.R;
import com.dragonforest.app.module_common.beans.UserInfo;
import com.dragonforest.app.module_common.utils.LogUtil;
import com.dragonforest.app.module_common.utils.LoginUtil;

/**
 * 我的界面
 *
 * @author 韩龙林
 * @date 2019/8/2 16:14
 */
public class FragmentMine extends Fragment {

    private TextView tv_name;
    private TextView tv_email;
    private TextView tv_exit_login;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = LayoutInflater.from(getActivity()).inflate(R.layout.app_fragment_mine, container, false);
        LogUtil.E("FragmentMine", "onCreateView()");
        initView(v);
        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        LogUtil.E("FragmentMine", "onResume()");
        initData();
    }

    private void initView(View v) {
        tv_name = v.findViewById(R.id.tv_person_name);
        tv_email = v.findViewById(R.id.tv_person_email);
        tv_exit_login = v.findViewById(R.id.tv_exit_login);
        tv_exit_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showExistDialog();
            }
        });
    }

    private void initData() {
        UserInfo cacheUserInfo = LoginUtil.getCacheUserInfo(getActivity());
        if (cacheUserInfo != null) {
            tv_name.setText(cacheUserInfo.getUserName() == null ? "" : cacheUserInfo.getUserName());
            tv_email.setText(cacheUserInfo.getUserEmail() == null ? "" : cacheUserInfo.getUserEmail());
        }
    }

    private void showExistDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("退出登录");
        builder.setMessage("确认要退出登录吗？");
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                LoginUtil.saveUserInfo(getActivity(), null);
                gotoLogin();
            }
        });
        builder.show();
    }

    private void gotoLogin() {
        try {
            Class<?> aClass = Class.forName("com.dragonforest.app.module_login.LoginActivity");
            Intent intent = new Intent();
            intent.setClass(getActivity(), aClass);
            getActivity().finish();
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(getActivity(), "登录模块缺失", Toast.LENGTH_SHORT).show();
        }
    }
}
