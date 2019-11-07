package com.dragonforest.app.module_message.messageOuter.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dragonforest.app.module_message.R;
import com.dragonforest.app.module_message.database.MessageDBHelper;
import com.dragonforest.app.module_message.database.MessageModel;
import com.dragonforest.app.module_message.database.MessageType;
import com.dragonforest.app.module_message.event.MessageStatusEvent;
import com.dragonforest.app.module_message.messageInter.MessageHistoryActivity;
import com.dragonforest.app.module_message.messageOuter.adapter.MessageOuterAdapter;
import com.dragonforest.app.module_message.messageOuter.bean.MessageOuterModel;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.LinkedList;
import java.util.List;

/**
 * @author 韩龙林
 * @date 2019/10/8 20:09
 */
public class GroupChatFragment extends Fragment {

    private RecyclerView recycleView_message;

    int messageType = MessageType.TYPE_GROUP_CHAT;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.msg_fragment, container, false);
        initView(v);
        initData();
        return v;
    }

    private void initView(View v) {
        recycleView_message = v.findViewById(R.id.recycleView_message);
        initRecycleView(recycleView_message);
    }

    private void initRecycleView(RecyclerView recycleView) {
        LinkedList<MessageOuterModel> list = new LinkedList<>();
        MessageOuterAdapter adapter = new MessageOuterAdapter(list);
        recycleView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recycleView.setAdapter(adapter);

        adapter.setOnMesssageOuterItemClickListener(new MessageOuterAdapter.OnMesssageOuterItemClickListener() {
            @Override
            public void onItemClick(MessageOuterModel messageOuterModel) {
                Intent intent = new Intent(getActivity(), MessageHistoryActivity.class);
                intent.putExtra("messageOuterModel", messageOuterModel);
                startActivity(intent);
            }
        });
    }

    private void initData() {
        // 获取所有工作通知topic
        List<String> allSendClientIDs = MessageDBHelper.getAllSendClientIDs(messageType);
        // 初始化外层message
        LinkedList<MessageOuterModel> messageOuterModels = new LinkedList<>();
        for (String sendClientID : allSendClientIDs) {
            MessageModel newestMessage = MessageDBHelper.getNewestMessage(messageType,sendClientID);
            if (newestMessage != null) {
                int unReadMessageNum = MessageDBHelper.getUnReadMessageNum(messageType,sendClientID);
                MessageOuterModel messageOuterModel = new MessageOuterModel(messageType,sendClientID, newestMessage, unReadMessageNum);
                messageOuterModels.add(messageOuterModel);
            }
        }
        ((MessageOuterAdapter) recycleView_message.getAdapter()).setData(messageOuterModels);
    }

    // 消息状态改变
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageStatusChanged(MessageStatusEvent messageStatusEvent) {
        if (messageStatusEvent != null) {
            String sendClientID = messageStatusEvent.getSendClientID();
            if (sendClientID != null) {
                MessageModel newestMessage = MessageDBHelper.getNewestMessage(messageType,sendClientID);
                int type = messageStatusEvent.getType();
                if (sendClientID != null && type == messageType) {
                    int unReadMessageNum = MessageDBHelper.getUnReadMessageNum(messageType,sendClientID);
                    MessageOuterModel messageOuterModel = new MessageOuterModel(messageType,sendClientID, newestMessage, unReadMessageNum);
                    // 更新
                    ((MessageOuterAdapter) recycleView_message.getAdapter()).update(messageOuterModel);
                }
            }
        }
    }
}
