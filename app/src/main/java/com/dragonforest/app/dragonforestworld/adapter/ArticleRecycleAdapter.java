package com.dragonforest.app.dragonforestworld.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.dragonforest.app.dragonforestworld.R;
import com.dragonforest.app.dragonforestworld.beans.Article;

import java.util.List;

/**
 * @author 韩龙林
 * @date 2019/8/13 19:12
 */
public class ArticleRecycleAdapter extends RecyclerView.Adapter {
    List<Article> dataList;

    private int defaultLayoutId = R.layout.app_item_article1;

    private OnItemListener onItemListener;

    public ArticleRecycleAdapter(List<Article> dataList) {
        this.dataList = dataList;
    }

    public ArticleRecycleAdapter(List<Article> dataList, int layoutId) {
        this.dataList = dataList;
        this.defaultLayoutId = layoutId;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(defaultLayoutId, viewGroup, false);
        ArticleViewHolder holder = new ArticleViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        ((ArticleViewHolder) viewHolder).bindView(i);
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public void setOnItemListener(OnItemListener onItemListener) {
        this.onItemListener = onItemListener;
    }

    class ArticleViewHolder extends RecyclerView.ViewHolder {
        ImageView img_publisher;
        TextView tv_publisher;
        TextView tv_publish_time;
        TextView tv_title;
        ImageView img_title;


        public ArticleViewHolder(@NonNull View itemView) {
            super(itemView);
            img_publisher = itemView.findViewById(R.id.img_publisher);
            img_title = itemView.findViewById(R.id.img_title);
            tv_publisher = itemView.findViewById(R.id.tv_publisher);
            tv_publish_time = itemView.findViewById(R.id.tv_pushlish_time);
            tv_title = itemView.findViewById(R.id.tv_title);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(onItemListener!=null){
                        onItemListener.onItemClick(dataList.get(getAdapterPosition()),getAdapterPosition());
                    }
                }
            });
        }

        public void bindView(int position) {
            Article article = dataList.get(position);
            tv_title.setText(article.getTitle() + "");
            tv_publisher.setText(article.getPublisher() + "");
            tv_publish_time.setText(article.getTime() + "");
            if (article.getImgUrl() != null & !article.getImgUrl().equals("")) {
                // 显示网络加载图片
                Glide.with(img_title).load(article.getImgUrl()).into(img_title);
            } else if (article.getImgId() != 0) {
                img_title.setImageResource(article.getImgId());
            }
        }
    }

    public interface OnItemListener{
        void onItemClick(Article article,int position);
    }
}
