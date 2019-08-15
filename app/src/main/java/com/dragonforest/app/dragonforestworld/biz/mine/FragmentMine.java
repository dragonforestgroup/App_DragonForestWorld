package com.dragonforest.app.dragonforestworld.biz.mine;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dragonforest.app.dragonforestworld.R;
import com.dragonforest.app.module_common.beans.UserInfo;
import com.dragonforest.app.module_common.utils.LogUtil;
import com.dragonforest.app.module_common.utils.LoginUtil;
import com.dragonforest.app.module_common.utils.NavigationUtil;
import com.dragonforest.app.module_common.utils.ToastUtils;


/**
 * 我的界面
 *
 * @author 韩龙林
 * @date 2019/8/2 16:14
 */
public class FragmentMine extends Fragment implements View.OnClickListener {

    private TextView tv_name;
    private TextView tv_email;
    private TextView tv_exit_login;

    private LinearLayout ll_my_publish;
    private LinearLayout ll_my_offer;
    private LinearLayout ll_my_stars;
    private LinearLayout ll_my_collect;
    private LinearLayout ll_my_share;
    private LinearLayout ll_contact_us;

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
        ll_my_publish = v.findViewById(R.id.ll_my_publish);
        ll_my_offer = v.findViewById(R.id.ll_my_offer);
        ll_my_stars = v.findViewById(R.id.ll_my_stars);
        ll_my_collect = v.findViewById(R.id.ll_my_collect);
        ll_my_share = v.findViewById(R.id.ll_my_share);
        ll_contact_us = v.findViewById(R.id.ll_contact_us);

        tv_exit_login.setOnClickListener(this);
        ll_my_publish.setOnClickListener(this);
        ll_my_offer.setOnClickListener(this);
        ll_my_stars.setOnClickListener(this);
        ll_my_collect.setOnClickListener(this);
        ll_my_share.setOnClickListener(this);
        ll_contact_us.setOnClickListener(this);
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
                NavigationUtil.navigation(getActivity(), true, "com.dragonforest.app.module_login.LoginActivity");

            }
        });
        builder.show();
    }

    /**
     * 默认操作 提示正在开发中
     */
    private void defaultProcess() {
        ToastUtils.show("该功能正在开发中...", getActivity());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_exit_login:
                showExistDialog();
                break;
            case R.id.ll_my_publish:
                defaultProcess();
                break;
            case R.id.ll_my_offer:
                defaultProcess();
                break;
            case R.id.ll_my_stars:
                defaultProcess();
                break;
            case R.id.ll_my_collect:
                defaultProcess();
                break;
            case R.id.ll_my_share:
                shareApp();
                break;
            case R.id.ll_contact_us:
                defaultProcess();
                break;
            default:
                break;
        }
    }

    private void shareApp() {
        try {
            com.dragonforest.app.module_share.util.ShareUtil.getInstance()
                    .with(getActivity())
                    .shareType(com.dragonforest.app.module_share.util.ShareUtil.SHARE_WEBPAGE)
                    .title("DragonForest")
                    .text("代码世界")
                    .url("https://blog.csdn.net/qq_23992393")
                    .shareTo(com.dragonforest.app.module_share.util.ShareUtil.SHARE_PLATFORM_QQ);
        } catch (Exception e) {
            e.printStackTrace();
            ToastUtils.show("分享模块缺失！" + e.getMessage(), getActivity());
        }
    }
}
