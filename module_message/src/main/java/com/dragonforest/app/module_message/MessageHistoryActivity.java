package com.dragonforest.app.module_message;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.dragonforest.app.module_message.adapter.MessageAdapter;
import com.dragonforest.app.module_message.database.MessageModel;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.litepal.LitePal;

import java.util.ArrayList;
import java.util.List;

public class MessageHistoryActivity extends AppCompatActivity {

    private RecyclerView recyclerViewMsg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.msg_activity_history);
        recyclerViewMsg = findViewById(R.id.recyclerViewMsg);
        initRecyclerView(recyclerViewMsg);
        initData();
    }


    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    private void initRecyclerView(RecyclerView recyclerView) {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        List<MessageModel> messageModels = new ArrayList<>();
        MessageAdapter adapter = new MessageAdapter(messageModels, this);
        recyclerView.setAdapter(adapter);
        adapter.setMessageItemListener(new MessageAdapter.MessageItemListener() {
            @Override
            public void onItemClick(MessageModel messageModel) {
                Intent intent = new Intent(MessageHistoryActivity.this, MessageDetailActivity.class);
                intent.putExtra("message", messageModel);
                startActivity(intent);
            }
        });
        adapter.setMessageItemLongListener(new MessageAdapter.MessageItemLongListener() {
            @Override
            public void onItemLongClick(MessageModel messageModel) {
                showDeleteDialog(messageModel);
            }
        });
    }

    private void initData() {
        List<MessageModel> messageModels = LitePal.findAll(MessageModel.class);
        ((MessageAdapter) recyclerViewMsg.getAdapter()).setData(messageModels);
    }

    private void showDeleteDialog(final MessageModel messageModel) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("是否删除?");
        builder.setTitle("确认");
        builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                messageModel.delete();
                initData();
                Toast.makeText(MessageHistoryActivity.this, "删除成功！", Toast.LENGTH_SHORT).show();
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.show();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MessageModel messageModel) {
        /* Do something */
        ((MessageAdapter) recyclerViewMsg.getAdapter()).addItem(messageModel);
    }
}
