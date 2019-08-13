package com.dragonforest.app.dragonforestworld.biz.home;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dragonforest.app.dragonforestworld.R;
import com.dragonforest.app.module_common.utils.LogUtil;

/**
 * 我的界面
 *
 * @author 韩龙林
 * @date 2019/8/2 16:14
 */
public class FragmentHome extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = LayoutInflater.from(getActivity()).inflate(R.layout.app_fragment_home, container,false);
        LogUtil.E("FragmentHome","onCreateView()");
        initView(v);
        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        LogUtil.E("FragmentHome","onResume()");
        initData();
    }

    private void initView(View v) {

    }

    private void initData() {

    }
}
