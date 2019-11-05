package com.dragonforest.app.module_plan.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.codbking.calendar.CaledarAdapter;
import com.codbking.calendar.CalendarBean;
import com.codbking.calendar.CalendarDateView;
import com.codbking.calendar.CalendarUtil;
import com.codbking.calendar.CalendarView;
import com.dragonforest.app.module_common.beans.PlanInfo;
import com.dragonforest.app.module_common.utils.StatusBarUtil;
import com.dragonforest.app.module_plan.R;
import com.dragonforest.app.module_plan.adapter.PlanListAdapter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PlanActivityMain extends AppCompatActivity {

    CalendarDateView calendarDateView;
    TextView tv_title;
    ImageView img_back;
    ListView listView;
    FloatingActionButton fab_add;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.plan_activity_main);
        StatusBarUtil.getInstance().setTransparent(this);
        initView();
        initData();
    }

    private void initView() {
        calendarDateView = findViewById(R.id.calendarDateView);
        initCalendar(calendarDateView);
        listView = findViewById(R.id.listview);
        initListView(listView);
        tv_title = findViewById(R.id.tv_title);
        img_back = findViewById(R.id.img_back);
        fab_add = findViewById(R.id.fab_add);
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        fab_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(PlanActivityMain.this,PlanActivityAddPlan.class));
            }
        });
    }

    private void initListView(ListView listView) {
        List<PlanInfo> planInfoList = new ArrayList<>();
        PlanListAdapter planListAdapter = new PlanListAdapter(planInfoList, this);
        listView.setAdapter(planListAdapter);

        TextView tv_empty=new TextView(this);
        tv_empty.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        tv_empty.setText("今天还没有计划哦 ^_^!!");
        tv_empty.setTextSize(20);
        tv_empty.setGravity(Gravity.CENTER);
        tv_empty.setTextColor(Color.RED);
        tv_empty.setBackgroundColor(Color.WHITE);
        ((ViewGroup)listView.getParent()).addView(tv_empty);
        listView.setEmptyView(tv_empty);
    }

    private void initData() {
        int[] data = CalendarUtil.getYMD(new Date());
        tv_title.setText(data[0] + "/" + data[1] + "/" + data[2]);

        List<PlanInfo> planInfoList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            PlanInfo planInfo = new PlanInfo();
            planInfo.setDate(1994, 11, 2 + i);
            planInfo.setTime(12 + i, 24, 15);
            planInfo.setThing("今天上山打老虎" + i);
            planInfo.setLevel(1);
            planInfo.setType(2);
            planInfoList.add(planInfo);
        }
        ((PlanListAdapter) listView.getAdapter()).setData(planInfoList);
    }

    private void initCalendar(CalendarDateView mCalendarDateView) {
        mCalendarDateView.setAdapter(new CaledarAdapter() {
            @Override
            public View getView(View convertView, ViewGroup parentView, CalendarBean bean) {

                if (convertView == null) {
                    convertView = LayoutInflater.from(parentView.getContext()).inflate(R.layout.plan_item_xiaomi, null);
                }

                TextView chinaText = (TextView) convertView.findViewById(R.id.chinaText);
                TextView text = (TextView) convertView.findViewById(R.id.text);

                text.setText("" + bean.day);
                if (bean.mothFlag != 0) {
                    text.setTextColor(0xff9299a1);
                } else {
                    text.setTextColor(0xff444444);
                }
                chinaText.setText(bean.chinaDay);

                return convertView;
            }
        });

        mCalendarDateView.setOnItemClickListener(new CalendarView.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int postion, CalendarBean bean) {
                tv_title.setText(bean.year + "/" + bean.moth + "/" + bean.day);
                ((PlanListAdapter)listView.getAdapter()).clear();
            }
        });

    }
}
