package com.dragonforest.app.dragonforestworld.biz.mine.activity.message.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.dragonforest.app.dragonforestworld.R;
import com.dragonforest.app.dragonforestworld.biz.mine.activity.message.bean.MMessage;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @author 韩龙林
 * @date 2019/10/9 09:19
 */
public class MessageAdapter extends RecyclerView.Adapter {
    List<MMessage> list;

    public MessageAdapter(List<MMessage> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.app_item_msg_notice, viewGroup, false);
        MessageHolder holder = new MessageHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        ((MessageHolder) viewHolder).bind(i);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void setData(List<MMessage> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    class MessageHolder extends RecyclerView.ViewHolder {
        ImageView img_head;
        TextView tv_from_name;
        TextView tv_content;
        TextView tv_date;
        TextView tv_msg_num;

        public MessageHolder(@NonNull View itemView) {
            super(itemView);
            img_head = itemView.findViewById(R.id.img_head);
            tv_from_name = itemView.findViewById(R.id.tv_from_name);
            tv_content = itemView.findViewById(R.id.tv_content);
            tv_date = itemView.findViewById(R.id.tv_date);
            tv_msg_num = itemView.findViewById(R.id.tv_msg_num);
        }

        public void bind(int position) {
            MMessage mMessage = list.get(position);
            img_head.setImageResource(mMessage.getHead());
            tv_from_name.setText(mMessage.getFrom());
            tv_content.setText(mMessage.getContent());
            Date date = mMessage.getDate();
            DateFormat dateFormat=new SimpleDateFormat("yyyy/MM/DD HH:mm");
            String dateStr = dateFormat.format(date);
            tv_date.setText(dateStr);
            tv_msg_num.setText(mMessage.getStatus()+"");
        }
    }
}
