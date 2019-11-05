package com.dragonforest.app.dragonforestworld.biz.mine.activity.message.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dragonforest.app.dragonforestworld.R;

/**
 * @author 韩龙林
 * @date 2019/10/8 19:56
 */
public class AllFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.app_fragment_msg,container,false);
        return v;
    }
}
