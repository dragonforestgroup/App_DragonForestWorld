package com.dragonforest.app.module_plan.activity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

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

public class PlanActivityAddPlan extends AppCompatActivity {

    private TextView tv_title;
    private ImageView img_back;
    private EditText ed_plan_content;
    private TextView tv_date;
    private TextView tv_time;
    private Button btn_add;

    private PlanInfo currentPlan = new PlanInfo();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.plan_activity_addplan);
        StatusBarUtil.getInstance().setTransparent(this);
        initView();
    }

    private void initView() {

        tv_title = findViewById(R.id.tv_title);
        img_back = findViewById(R.id.img_back);
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        ed_plan_content = findViewById(R.id.ed_plan_content);
        tv_date = findViewById(R.id.tv_date);
        tv_time = findViewById(R.id.tv_time);
        btn_add = findViewById(R.id.btn_add);
        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitPlan();
            }
        });
        tv_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onChooseDate();
            }
        });
        tv_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onChooseTime();
            }
        });
    }

    private void onChooseDate() {
        Date date=new Date();
        new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                tv_date.setText(getDateStr(year) + "/" + getDateStr(month + 1) + "/" + getDateStr(dayOfMonth));
                currentPlan.setDate(year,month+1,dayOfMonth);
            }
        }, currentPlan.getYear(), currentPlan.getMonth(), currentPlan.getDay()).show();
    }

    private void onChooseTime() {
        Date date = new Date();
        new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                tv_time.setText(getDateStr(hourOfDay) + ":" + getDateStr(minute));
                currentPlan.setTime(hourOfDay,minute,0);
            }
        }, currentPlan.getHour(), currentPlan.getMinute(), true).show();
    }

    private void submitPlan() {
        String planContent = ed_plan_content.getText().toString();
        currentPlan.setType(1);
        currentPlan.setLevel(1);
        currentPlan.setThing(planContent);
        Toast.makeText(this, "添加了 " + currentPlan.getYear() + "/" + currentPlan.getMonth() + "/" + currentPlan.getDay() + " "+currentPlan.getHour()+":"+currentPlan.getMinute()+"的计划:" + currentPlan.getThing(), Toast.LENGTH_SHORT).show();
    }

    public String getDateStr(int t){
        if(t>9){
            return t+"";
        }else{
            return "0"+t;
        }
    }
}
