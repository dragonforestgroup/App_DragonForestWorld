package com.dragonforest.app.dragonforestworld.biz.community;

import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.dragonforest.app.dragonforestworld.R;
import com.dragonforest.app.dragonforestworld.activity.SearchActivity;
import com.dragonforest.app.dragonforestworld.activity.WebArticleDetailActivity;
import com.dragonforest.app.dragonforestworld.adapter.ArticleRecycleAdapter;
import com.dragonforest.app.dragonforestworld.beans.Article;
import com.dragonforest.app.module_common.utils.LogUtil;
import com.dragonforest.app.module_common.utils.ToastUtils;

import java.util.List;

/**
 * 社区界面
 *
 * @author 韩龙林
 * @date 2019/8/2 16:14
 */
public class FragmentCommunity extends Fragment {

    private RecyclerView recyclerView_recommend;
    private SwipeRefreshLayout swipeRefreshLayout;
    private Toolbar toolbar;
    private SearchView searchView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = LayoutInflater.from(getActivity()).inflate(R.layout.app_fragment_comunity, container, false);
        LogUtil.E("FragmentCommunity", "onCreateView()");
        initView(v);
        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        LogUtil.E("FragmentCommunity", "onResume()");
        initData();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.search_menu,menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==R.id.action_search){
            Intent intent=new Intent(getActivity(),SearchActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    private void initView(View v) {
        recyclerView_recommend = v.findViewById(R.id.recycleView_recommend);
        initRecycleView(recyclerView_recommend);
        swipeRefreshLayout=v.findViewById(R.id.swipeRefreshLayout);
        initSwipeRefreshLayout(swipeRefreshLayout);
        toolbar=v.findViewById(R.id.toolbar);
        initToolBar(toolbar);
//        toolbar.inflateMenu(R.menu.search_menu);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void initToolBar(Toolbar toolbar) {
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
    }

    private void initSwipeRefreshLayout(SwipeRefreshLayout swipe) {
        swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                ToastUtils.showCenter("已更新10条",getActivity());
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    private void initRecycleView(RecyclerView recyclerView) {
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        List<Article> list = Article.getRecommendData();
        ArticleRecycleAdapter adapter = new ArticleRecycleAdapter(list);
        recyclerView.setAdapter(adapter);

        adapter.setOnItemListener(new ArticleRecycleAdapter.OnItemListener() {
            @Override
            public void onItemClick(Article article, int position) {
                WebArticleDetailActivity.launch(getActivity(),article);
            }
        });
    }

    private void initData() {

    }
}
