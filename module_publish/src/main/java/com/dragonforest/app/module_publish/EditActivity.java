package com.dragonforest.app.module_publish;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.dragonforest.app.module_common.utils.ToastUtils;

public class EditActivity extends AppCompatActivity {

    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        // 设置进入动画
        overridePendingTransition(R.anim.edit_in_anim, R.anim.edit_stay);
        initView();
    }

    @Override
    public void finish() {
        super.finish();
        // 设置退出动画
        overridePendingTransition(0, R.anim.edit_out_anim);
    }

    private void initView() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar acbar = getSupportActionBar();
        if (acbar != null) {
            acbar.setDisplayHomeAsUpEnabled(true);
            acbar.setHomeAsUpIndicator(R.drawable.ic_arrow_back_white_24dp);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.edit_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == android.R.id.home) {
            finish();
        } else if (itemId == R.id.action_edit_save) {
            defaultProcess();
        } else if (itemId == R.id.action_edit_delete) {
            defaultProcess();
        } else if (itemId == R.id.action_edit_publish) {
            defaultProcess();
        }
        return super.onOptionsItemSelected(item);
    }

    private void defaultProcess() {
        ToastUtils.show("正在开大中...", this);
    }
}
