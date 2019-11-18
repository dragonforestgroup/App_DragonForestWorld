package com.dragonforest.app.dragonforestworld.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.dragonforest.app.dragonforestworld.R;
import com.dragonforest.app.dragonforestworld.beans.Article;
import com.dragonforest.app.module_common.utils.ClipboardUtil;
import com.dragonforest.app.module_common.utils.StatusBarUtil;
import com.dragonforest.app.module_common.utils.ToastUtils;

import java.io.Serializable;

public class WebArticleDetailActivity extends AppCompatActivity {
    private WebView webView;
    private Toolbar toolbar;
    private ProgressBar progressBar_loading;

    /**
     * 需要传入的数据
     */
    private Article data = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.app_activity_article_detail);
        getData();
        initView();

        StatusBarUtil.getInstance().setTransparent(this);
    }

    private void getData() {
        Intent intent = getIntent();
        Serializable data = intent.getSerializableExtra("data");
        if (data != null) {
            if (data instanceof Article) {
                this.data = (Article) data;
            }
        }
    }

    public static void launch(Context context, Article article) {
        Intent intent = new Intent(context, WebArticleDetailActivity.class);
        intent.putExtra("data", article);
        context.startActivity(intent);
    }

    private void initView() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar acbar = getSupportActionBar();
        if (acbar != null) {
            acbar.setDisplayHomeAsUpEnabled(true);
            acbar.setHomeAsUpIndicator(R.drawable.ic_arrow_back_white_24dp);
        }
        webView = (WebView) findViewById(R.id.webView);
        initWebView(webView);
        progressBar_loading = (ProgressBar) findViewById(R.id.progressBar);
        progressBar_loading.setMax(100);
    }

    private void initWebView(WebView webView) {
        webView.setWebViewClient(new WebViewClient());
        webView.getSettings().setJavaScriptEnabled(true);
        if (data == null) {
            ToastUtils.show("该文章不存在", this);
        } else {
            if (data.getLink() == null) {
                webView.loadUrl("https://blog.csdn.net/qq_23992393/article/details/98070524");
            } else {
                webView.loadUrl(data.getLink());
            }
            if (data.getPublisher() == null) {
                toolbar.setTitle("精品文章");
            } else {
                toolbar.setTitle(data.getPublisher());
            }
        }
        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                progressBar_loading.setProgress(newProgress);
                if (newProgress == 100) {
                    progressBar_loading.setVisibility(View.GONE);
                } else {
                    progressBar_loading.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.web_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            case R.id.action_collect:
                defaultProcess();
                break;
            case R.id.action_star:
                defaultProcess();
                break;
            case R.id.action_share:
                defaultProcess();
                break;
            case R.id.action_copy:
                copyToPlate();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void copyToPlate() {
        if (this.data != null) {
            String link = this.data.getLink();
            ClipboardUtil.copyTextToPlate(getApplicationContext(), link);
            ToastUtils.show("复制链接成功！", this);
        } else {
            ToastUtils.show("复制链接失败！", this);
        }
    }

    private void defaultProcess() {
        ToastUtils.show("正在开发中...", this);
    }
}
