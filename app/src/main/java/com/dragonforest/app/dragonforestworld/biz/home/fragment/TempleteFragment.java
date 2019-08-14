package com.dragonforest.app.dragonforestworld.biz.home.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dragonforest.app.dragonforestworld.R;
import com.dragonforest.app.dragonforestworld.activity.WebArticleDetailActivity;
import com.dragonforest.app.dragonforestworld.adapter.ArticleRecycleAdapter;
import com.dragonforest.app.dragonforestworld.beans.Article;
import com.dragonforest.app.module_common.utils.LogUtil;
import com.dragonforest.app.module_common.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 韩龙林
 * @date 2019/8/14 09:48
 */
public class TempleteFragment extends Fragment {
    private RecyclerView recyclerView_recommend;
    private SwipeRefreshLayout swipeRefreshLayout;

    // 类别
    private String category = "";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = LayoutInflater.from(getActivity()).inflate(R.layout.app_home_fragment_templete, container, false);
        LogUtil.E("TempleteFragment", "onCreateView()");
        initView(v);
        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        LogUtil.E("TempleteFragment", "onResume()");
        initData();
    }

    private void initView(View v) {
        recyclerView_recommend = v.findViewById(R.id.recycleView_recommend);
        initRecycleView(recyclerView_recommend);
        swipeRefreshLayout = v.findViewById(R.id.swipeRefreshLayout);
        initSwipeRefreshLayout(swipeRefreshLayout);
    }

    private void initSwipeRefreshLayout(SwipeRefreshLayout swipe) {
        swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                ToastUtils.showCenter("已更新10条", getActivity());
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    private void initRecycleView(RecyclerView recyclerView) {
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    private void initData() {
        try {
            Bundle arguments = getArguments();
            category = arguments.getString("category");
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (!"".equals(category)) {
            // 显示相应类别的信息
            if(category.equals("android")){
                initAndroid();
            }else if(category.equals("java")){
                initJava();
            }else{
                ToastUtils.show("暂无内容",getActivity());
            }
        }
    }

    private void initAndroid() {
        List<Article> list = Article.getAndroidData();
        ArticleRecycleAdapter adapter = new ArticleRecycleAdapter(list,R.layout.app_item_article2);
        recyclerView_recommend.setAdapter(adapter);
        adapter.setOnItemListener(new ArticleRecycleAdapter.OnItemListener() {
            @Override
            public void onItemClick(Article article, int position) {
                WebArticleDetailActivity.launch(getActivity(),article);
            }
        });
    }

    private void initJava() {
        List<Article> list = Article.getJavaData();
        ArticleRecycleAdapter adapter = new ArticleRecycleAdapter(list,R.layout.app_item_article3);
        recyclerView_recommend.setAdapter(adapter);
        adapter.setOnItemListener(new ArticleRecycleAdapter.OnItemListener() {
            @Override
            public void onItemClick(Article article, int position) {
                WebArticleDetailActivity.launch(getActivity(),article);
            }
        });
    }

}
