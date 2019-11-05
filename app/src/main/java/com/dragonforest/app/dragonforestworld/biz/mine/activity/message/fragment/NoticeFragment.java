package com.dragonforest.app.dragonforestworld.biz.mine.activity.message.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dragonforest.app.dragonforestworld.R;
import com.dragonforest.app.dragonforestworld.biz.mine.activity.message.adapter.MessageAdapter;
import com.dragonforest.app.dragonforestworld.biz.mine.activity.message.bean.MMessage;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author 韩龙林
 * @date 2019/10/8 20:09
 */
public class NoticeFragment extends Fragment {

    private RecyclerView recycleView_message;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.app_fragment_msg,container,false);
        initView(v);
        initData();
        return v;
    }

    private void initView(View v) {
        recycleView_message = v.findViewById(R.id.recycleView_message);
        initRecycleView(recycleView_message);
    }

    private void initData() {
        List<MMessage> list=new ArrayList<>();
        for (int i = 0; i <5 ; i++) {
            MMessage mMessage=new MMessage("工作通知[大有]","me","10月份打卡已出",new Date(),1);
            list.add(mMessage);
        }
        ((MessageAdapter)recycleView_message.getAdapter()).setData(list);
    }

    private void initRecycleView(RecyclerView recycleView) {
        List<MMessage> list=new ArrayList<>();
        MessageAdapter adapter=new MessageAdapter(list);
        recycleView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recycleView.setAdapter(adapter);
    }
}
