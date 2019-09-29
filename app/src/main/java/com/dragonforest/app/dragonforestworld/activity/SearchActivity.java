package com.dragonforest.app.dragonforestworld.activity;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.dragonforest.app.dragonforestworld.R;
import com.dragonforest.app.module_common.utils.StatusBarUtil;

public class SearchActivity extends AppCompatActivity {

    private SearchView searchView;
    private Toolbar toolbar;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 设置进入动画
        overridePendingTransition(R.anim.app_search_in, R.anim.app_search_stay);
        setContentView(R.layout.app_activity_search);
        StatusBarUtil.getInstance().setTransparent(this);

        initView();
        Log.e("TAG", "onCreate: 进入SearchActivity中");
    }

    private void initView() {
        toolbar = findViewById(R.id.toolbar);
        initToolBar(toolbar);
    }

    private void initToolBar(Toolbar tbar) {
        tbar.setTitle("");
        setSupportActionBar(tbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_menu, menu);
        MenuItem item = menu.findItem(R.id.action_search);
        item.setActionView(new SearchView(this));
        searchView = (SearchView) item.getActionView();
        initSearchView(searchView);
        return super.onCreateOptionsMenu(menu);
    }

    private void initSearchView(SearchView sView) {
        sView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                Log.e("TAG", "关闭SearchView");
                finish();
                return false;
            }
        });
        // 设置一出来就显示搜索框
        sView.setIconified(false);
        // 设置提交按钮
        sView.setSubmitButtonEnabled(true);

        SearchView.SearchAutoComplete et = sView.findViewById(R.id.search_src_text);
        et.setHint("请输入文章或作者");
        et.setHintTextColor(Color.WHITE);
    }

    @Override
    public void finish() {
        super.finish();
        Log.e("TAG", " 退出SearchActivity中");
        overridePendingTransition(R.anim.app_search_stay, R.anim.app_search_out);
    }
}
