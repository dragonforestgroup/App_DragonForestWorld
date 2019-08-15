package com.dragonforest.app.dragonforestworld.welcome.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.dragonforest.app.dragonforestworld.R;

import org.w3c.dom.Text;

/**
 * 单张图片的fragment
 *
 * @author 韩龙林
 * @date 2019/8/15 14:44
 */
public class WelcomeFragment extends Fragment {

    private ImageView img_bg;
    private ImageView img_logo;
    private TextView tv_title;
    private TextView tv_content;

    int imgBgId;
    int imgLogoId;
    String title;
    String content;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle arguments = getArguments();
        if (arguments != null) {
            imgBgId = arguments.getInt("img_bg");
            imgLogoId = arguments.getInt("img_logo");
            title = arguments.getString("title");
            content = arguments.getString("content");
        }
    }

    public static WelcomeFragment newInstance(int img_bg, int img_logo, String title, String content) {
        WelcomeFragment imageFragment = new WelcomeFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("img_bg", img_bg);
        bundle.putInt("img_logo", img_logo);
        bundle.putString("title", title);
        bundle.putString("content", content);
        imageFragment.setArguments(bundle);
        return imageFragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(container.getContext()).inflate(R.layout.app_fragment_welcome, container, false);
        initView(view);
        initData();
        return view;
    }

    private void initView(View view) {
        img_bg=view.findViewById(R.id.img_bg);
        img_logo=view.findViewById(R.id.img_logo);
        tv_title=view.findViewById(R.id.tv_title);
        tv_content=view.findViewById(R.id.tv_content);
    }

    private void initData() {
        img_bg.setImageResource(imgBgId);
        img_logo.setImageResource(imgLogoId);
        tv_title.setText(title+"");
        tv_content.setText(content+"");
    }
}
