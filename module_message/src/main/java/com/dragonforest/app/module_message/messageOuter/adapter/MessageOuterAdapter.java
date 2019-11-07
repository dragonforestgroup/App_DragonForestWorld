package com.dragonforest.app.module_message.messageOuter.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.dragonforest.app.module_message.R;
import com.dragonforest.app.module_message.messageOuter.bean.MessageOuterModel;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * @author DragonForest
 * @date 2019/11/7 09:29
 */
public class MessageOuterAdapter extends RecyclerView.Adapter {
    LinkedList<MessageOuterModel> list;
    OnMesssageOuterItemClickListener onMesssageOuterItemClickListener;

    public MessageOuterAdapter(LinkedList<MessageOuterModel> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.msg_item_outer_notice, viewGroup, false);
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

    public void setData(LinkedList<MessageOuterModel> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    public void update(MessageOuterModel messageOuterModel) {
        boolean isExist = false;
        for (int i = 0; i < list.size(); i++) {
            MessageOuterModel outerModel = list.get(i);
            if (outerModel.getSendClientID().equals(messageOuterModel.getSendClientID())) {
                list.set(i, messageOuterModel);
                changeTop(i);
                isExist = true;
                break;
            }
        }
        // 如果不存在，添加
        if(!isExist){
            list.addFirst(messageOuterModel);
        }
        notifyDataSetChanged();
    }

    /**
     * 置顶
     * @param i
     */
    private void changeTop(int i){
        if(list.size()==1){
            return;
        }
        MessageOuterModel first = list.get(0);
        if(list.get(i).getLastMessage().getDate().after(first.getLastMessage().getDate())){
            MessageOuterModel tempMessageOuterModel = list.get(i);
            list.remove(i);
            list.addFirst(tempMessageOuterModel);
        }

    }

    class MessageHolder extends RecyclerView.ViewHolder {
        View v;
        ImageView img_head;
        TextView tv_from_name;
        TextView tv_content;
        TextView tv_date;
        TextView tv_msg_num;

        public MessageHolder(@NonNull View itemView) {
            super(itemView);
            v = itemView;
            img_head = itemView.findViewById(R.id.img_head);
            tv_from_name = itemView.findViewById(R.id.tv_from_name);
            tv_content = itemView.findViewById(R.id.tv_content);
            tv_date = itemView.findViewById(R.id.tv_date);
            tv_msg_num = itemView.findViewById(R.id.tv_msg_num);
        }

        public void bind(int position) {
            final MessageOuterModel mMessage = list.get(position);
//            img_head.setImageResource(mMessage.getHead());
            tv_from_name.setText(mMessage.getSendClientID());
            tv_content.setText(mMessage.getLastMessage().getMessage());
            Date date = mMessage.getLastMessage().getDate();
            DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm");
            String dateStr = dateFormat.format(date);
            tv_date.setText(dateStr);

            int unReadNum = mMessage.getUnReadNum();
            if(unReadNum>10){
                tv_msg_num.setText("...");
            }else{
                tv_msg_num.setText(unReadNum+"");
            }
            if (mMessage.getUnReadNum() == 0) {
                tv_msg_num.setVisibility(View.GONE);
            } else {
                tv_msg_num.setVisibility(View.VISIBLE);
            }
            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onMesssageOuterItemClickListener != null) {
                        onMesssageOuterItemClickListener.onItemClick(mMessage);
                    }
                }
            });
        }
    }

    public void setOnMesssageOuterItemClickListener(OnMesssageOuterItemClickListener onMesssageOuterItemClickListener) {
        this.onMesssageOuterItemClickListener = onMesssageOuterItemClickListener;
    }

    public interface OnMesssageOuterItemClickListener {
        void onItemClick(MessageOuterModel messageOuterModel);
    }
}
