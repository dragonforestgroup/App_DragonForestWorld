package com.dragonforest.app.dragonforestworld.biz.recommend;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.dragonforest.app.dragonforestworld.R;
import com.dragonforest.app.dragonforestworld.adapter.ArticleRecycleAdapter;
import com.dragonforest.app.dragonforestworld.beans.Article;
import com.dragonforest.app.module_common.utils.LogUtil;
import com.stx.xhb.xbanner.XBanner;
import com.stx.xhb.xbanner.entity.LocalImageInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * 我的界面
 *
 * @author 韩龙林
 * @date 2019/8/2 16:14
 */
public class FragmentRecommend extends Fragment {

    private XBanner xBanner;
    private RecyclerView recyclerView_recommend;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = LayoutInflater.from(getActivity()).inflate(R.layout.app_fragment_recommend, container, false);
        LogUtil.E("FragmentRecommend", "onCreateView()");
        initView(v);
        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        LogUtil.E("FragmentRecommend", "onResume()");
        initData();
    }

    private void initView(View v) {
        xBanner = v.findViewById(R.id.xbanner);
        initXbanner(xBanner);
        recyclerView_recommend = v.findViewById(R.id.recycleView_recommend);
        initRecycleView(recyclerView_recommend);
    }

    private void initXbanner(XBanner xBanner) {
        xBanner.loadImage(new XBanner.XBannerAdapter() {
            @Override
            public void loadBanner(XBanner banner, Object model, View view, int position) {
                LocalImageInfo localImageInfo = (LocalImageInfo) model;
                Glide.with(getActivity()).load(localImageInfo.getXBannerUrl()).into((ImageView) view);
            }
        });
        List<LocalImageInfo> localImageInfos = new ArrayList<>();
        localImageInfos.add(new LocalImageInfo(R.drawable.app_splash));
        xBanner.setBannerData(localImageInfos);
        xBanner.setAutoPlayAble(true);
    }

    private void initRecycleView(RecyclerView recyclerView) {
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        List<Article> list = new ArrayList<>();
        for (int i = 0; i < 15; i++) {
            list.add(new Article("韩龙林", "鸿蒙发布了+" + i, "1小时前", "", ""));
        }
        ArticleRecycleAdapter adapter = new ArticleRecycleAdapter(list);
        recyclerView.setAdapter(adapter);
    }

    private void initData() {

    }
}
