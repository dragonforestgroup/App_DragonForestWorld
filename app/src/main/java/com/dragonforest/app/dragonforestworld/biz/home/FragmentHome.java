package com.dragonforest.app.dragonforestworld.biz.home;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dragonforest.app.dragonforestworld.R;
import com.dragonforest.app.dragonforestworld.biz.home.adapter.HomeFragmentPagerAdapter;
import com.dragonforest.app.dragonforestworld.biz.home.fragment.TempleteFragment;
import com.dragonforest.app.module_common.utils.LogUtil;
import com.dragonforest.app.module_common.view.coordinatorTablayout.CoordinatorTabLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * 我的界面
 *
 * @author 韩龙林
 * @date 2019/8/2 16:14
 */
public class FragmentHome extends Fragment {

    private CoordinatorTabLayout coordinatorTabLayout;
    private ViewPager viewPager;
    private String[] titles = {"android", "java"};
    private int[] colorArray=new int[]{R.color.colorBlueForLoginTitle,R.color.colorBlueForLoginTitle};
    private int[] imgArray = new int[] {R.drawable.app_bg_android, R.drawable.app_bg_java};

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = LayoutInflater.from(getActivity()).inflate(R.layout.app_fragment_home, container, false);
        LogUtil.E("FragmentHome", "onCreateView()");
        initView(v);
        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        LogUtil.E("FragmentHome", "onResume()");
        initData();
    }

    private void initView(View v) {
        viewPager = v.findViewById(R.id.viewPager_home);
        initViewPager(viewPager);
        coordinatorTabLayout = v.findViewById(R.id.coordinatortablayout);
        initCoordinatorTablayout(coordinatorTabLayout);
    }

    private void initCoordinatorTablayout(CoordinatorTabLayout cdt) {
        cdt.setTitle("Get what you want")
                .setImageArray(imgArray,colorArray)
                .setBackEnable(true)
                .setupWithViewPager(viewPager);
    }

    private void initViewPager(ViewPager vp) {
        List<Fragment> fragmentList = new ArrayList<>();
        for (int i = 0; i < titles.length; i++) {
            TempleteFragment templeteFragment = new TempleteFragment();
            Bundle bundle = new Bundle();
            bundle.putString("category", titles[i]);
            templeteFragment.setArguments(bundle);
            fragmentList.add(templeteFragment);
        }
        HomeFragmentPagerAdapter adapter = new HomeFragmentPagerAdapter(getChildFragmentManager(), fragmentList, titles);
        vp.setAdapter(adapter);
    }


    private void initData() {

    }
}
