package com.dragonforest.app.dragonforestworld.biz.mine.activity;

import android.content.DialogInterface;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.dragonforest.app.dragonforestworld.R;

public class ContactUsActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private EditText ed_contact_us;
    private Button btn_contact_us;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.app_activity_contact_us);
        initView();
    }

    private void initView() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar acbar = getSupportActionBar();
        if (acbar != null) {
            acbar.setDisplayHomeAsUpEnabled(true);
            acbar.setHomeAsUpIndicator(R.drawable.ic_arrow_back_white_24dp);
        }
        ed_contact_us=findViewById(R.id.ed_contact_us);
        btn_contact_us=findViewById(R.id.btn_contact_us);
        btn_contact_us.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitContactUs();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void submitContactUs() {
        String content=ed_contact_us.getText().toString().trim();
        if(!"".equals(content)){
            showDialog("留言成功","是否继续留言？");
            ed_contact_us.setText("");
        }
    }

    private void showDialog(String title,String msg){
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setTitle(title);
        builder.setMessage(msg);
        builder.setNegativeButton("继续留言", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.setPositiveButton("结束留言，返回主页面", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                finish();
            }
        });
        builder.show();
    }
}
