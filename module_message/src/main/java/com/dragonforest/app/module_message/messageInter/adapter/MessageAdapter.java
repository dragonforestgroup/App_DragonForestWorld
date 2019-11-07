package com.dragonforest.app.module_message.messageInter.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dragonforest.app.module_message.R;
import com.dragonforest.app.module_message.database.MessageModel;

import java.text.SimpleDateFormat;
import java.util.List;

/**
 * @author DragonForest
 * @date 2019/10/29 16:47
 */
public class MessageAdapter extends RecyclerView.Adapter {
    List<MessageModel> data;
    Context context;
    public MessageItemListener messageItemListener;
    MessageItemLongListener messageItemLongListener;

    public MessageAdapter(List<MessageModel> data, Context context) {
        this.data = data;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.msg_item_message, viewGroup, false);
        MessageViewHodler messageViewHodler = new MessageViewHodler(v);
        return messageViewHodler;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        ((MessageViewHodler) viewHolder).bind(data.get(i));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }


    class MessageViewHodler extends RecyclerView.ViewHolder {
        View view;
        TextView tv_content;
        TextView tv_date;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd hh:mm");

        public MessageViewHodler(@NonNull View itemView) {
            super(itemView);
            view = itemView;
            tv_content = itemView.findViewById(R.id.tv_content);
            tv_date = itemView.findViewById(R.id.tv_date);
        }

        public void bind(final MessageModel messageModel) {
            tv_content.setText(messageModel.getMessage() + "");
            tv_date.setText(sdf.format(messageModel.getDate()) + "");
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (messageItemListener != null) {
                        messageItemListener.onItemClick(messageModel);
                    }
                }
            });
            view.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    if (messageItemLongListener != null) {
                        messageItemLongListener.onItemLongClick(messageModel);
                    }
                    return false;
                }
            });
        }
    }

    public void setData(List<MessageModel> data){
        this.data=data;
        notifyDataSetChanged();
    }
    public void addItem(MessageModel messageModel) {
        this.data.add(messageModel);
        notifyDataSetChanged();
    }

    public interface MessageItemListener {
        void onItemClick(MessageModel messageModel);
    }

    public interface MessageItemLongListener {
        void onItemLongClick(MessageModel messageModel);
    }

    public void setMessageItemListener(MessageItemListener messageItemListener) {
        this.messageItemListener = messageItemListener;
    }

    public void setMessageItemLongListener(MessageItemLongListener messageItemLongListener) {
        this.messageItemLongListener = messageItemLongListener;
    }
}
