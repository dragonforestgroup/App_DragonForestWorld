package com.dragonforest.app.dragonforestworld.test;

import android.content.Context;
import android.support.v7.app.AppCompatDelegate;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

/**
 * 测试 替换所有的TextView
 *
 * @author 韩龙林
 * @date 2019/8/16 09:50
 */
public class MyFactory implements LayoutInflater.Factory2 {
    private AppCompatDelegate mDelegate;

    public void setmDelegate(AppCompatDelegate mDelegate) {
        this.mDelegate = mDelegate;
    }

    @Override
    public View onCreateView(View parent, String name, Context context, AttributeSet attrs) {
        TextView textView=null;
        View v=mDelegate.createView(parent,name,context,attrs);
        if(v instanceof TextView){
            ((TextView)v).setText("我被替换了？");
        }
        return textView;
    }

    @Override
    public View onCreateView(String name, Context context, AttributeSet attrs) {
        return null;
    }
}
