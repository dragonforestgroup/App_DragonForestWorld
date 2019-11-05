package com.dragonforest.app.module_plan.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.dragonforest.app.module_common.beans.PlanInfo;
import com.dragonforest.app.module_plan.R;

import java.util.List;

/**
 * @author 韩龙林
 * @date 2019/9/29 18:25
 */
public class PlanListAdapter extends BaseAdapter {

    private List<PlanInfo> list;
    private Context context;

    public PlanListAdapter(List<PlanInfo> planInfos, Context context) {
        this.list = planInfos;
        this.context = context;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        EventViewHolder holder=null;
        if(convertView==null){
            convertView= LayoutInflater.from(context).inflate(R.layout.plan_item_event,parent,false);
            holder=new EventViewHolder(convertView);
            convertView.setTag(holder);
        }else{
            holder=(EventViewHolder) convertView.getTag();
        }
        if(holder!=null){
            PlanInfo planInfo = list.get(position);
            holder.tv_date.setText(planInfo.getYear()+"/"+planInfo.getMonth()+"/"+planInfo.getDay()+" "+planInfo.getHour()+":"+planInfo.getMinute());
            holder.tv_event.setText(planInfo.getThing()+"");
        }
        return convertView;
    }

    public void setData(List<PlanInfo> list){
        this.list=list;
        notifyDataSetChanged();
    }

    public List<PlanInfo> getData(){
        return list;
    }

    public void clear(){
        list.clear();
        notifyDataSetChanged();
    }

    static class EventViewHolder {
        TextView tv_date;
        TextView tv_event;

        public EventViewHolder(View v){
            tv_date=v.findViewById(R.id.tv_date);
            tv_event=v.findViewById(R.id.tv_event);
        }

    }
}
