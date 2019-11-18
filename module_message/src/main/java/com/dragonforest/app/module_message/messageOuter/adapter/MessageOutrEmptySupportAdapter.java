package com.dragonforest.app.module_message.messageOuter.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dragonforest.app.module_message.R;
import com.dragonforest.app.module_message.messageOuter.bean.MessageOuterModel;

import java.util.LinkedList;

/**
 * @author DragonForest
 * @date 2019/11/11 09:30
 */
public class MessageOutrEmptySupportAdapter extends MessageOuterAdapter {

    private final static int EMPTY = 0;
    private final static int NOT_EMPTY = 1;

    public MessageOutrEmptySupportAdapter(LinkedList<MessageOuterModel> list) {
        super(list);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        if (i == EMPTY) {
            View emptyView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.msg_layout_empty, viewGroup, false);
            return new EmptyViewHolder(emptyView);
        }
        return super.onCreateViewHolder(viewGroup, i);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        if (viewHolder instanceof EmptyViewHolder) {
            return;
        }
        super.onBindViewHolder(viewHolder, i);
    }

    @Override
    public int getItemCount() {
        if (super.getItemCount() > 0) {
            return super.getItemCount();
        }
        return 1;
    }

    @Override
    public int getItemViewType(int position) {
            if (super.getItemCount() > 0) {
                return NOT_EMPTY;
            }
        return EMPTY;
    }

    class EmptyViewHolder extends RecyclerView.ViewHolder {

        public EmptyViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
