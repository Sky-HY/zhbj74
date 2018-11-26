package com.cn.zhbj74.domain;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.cn.zhbj74.R;
import com.cn.zhbj74.utils.MyApplication;

import java.util.ArrayList;

/**
 * RecyclerView Adapter
 */
public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.ViewHolder> {
    public static final int TYPE_HEAD = 1;
    public static final int TYPE_NORMAL = 0;

    private ArrayList<NewsTabBean.NewsData> mNewsLists;
    private View mHeadView;

    public MyRecyclerViewAdapter(ArrayList<NewsTabBean.NewsData> news) {
        mNewsLists = news;
    }

    /**
     * 设置头布局
     */
    public void setHeadView(View headView) {
        mHeadView = headView;
        notifyItemInserted(0);
    }

    /**
     * 获取头布局
     */
    public View getHeadView() {
        return mHeadView;
    }

    /**
     * 判断改索引位置的条目类型
     */
    @Override
    public int getItemViewType(int position) {
        if (mHeadView != null && position == 0) {
            return TYPE_HEAD;
        } else {
            return TYPE_NORMAL;
        }
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_HEAD) {
            return new ViewHolder(mHeadView);
        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.listview_news_item, parent, false);
            return new ViewHolder(view);
        }

    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if (getItemViewType(position) == TYPE_HEAD) {
            // 插入的头条目,什么也不做
            return;
        } else {
            // 实际的索引位置
            int pos = getRealPosition(holder);
            NewsTabBean.NewsData newsData = mNewsLists.get(pos);
            Glide.with(MyApplication.getContext()).load(newsData.listimage).placeholder(R.drawable.topnews_item_default).error(R.drawable.topnews_item_default).into(holder.iv_icon);
            holder.tv_title.setText(newsData.title);
            holder.tv_date.setText(newsData.pubdate);

        }
    }

    @Override
    public int getItemCount() {
        return mHeadView == null ? mNewsLists.size() : mNewsLists.size() + 1;
    }

    /**
     * 获取条目实际索引
     */
    public int getRealPosition(RecyclerView.ViewHolder holder) {
        // 获取当前条目索引
        int position = holder.getLayoutPosition();
        if (mHeadView == null) {
            return position;
        } else {
            return position - 1;
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_title;
        public TextView tv_date;
        public ImageView iv_icon;

        public ViewHolder(View itemView) {
            super(itemView);
            if (itemView == mHeadView) {
                return;
            }
            tv_title = itemView.findViewById(R.id.tv_title);
            tv_date = itemView.findViewById(R.id.tv_date);
            iv_icon = itemView.findViewById(R.id.iv_icon);
        }
    }
}
