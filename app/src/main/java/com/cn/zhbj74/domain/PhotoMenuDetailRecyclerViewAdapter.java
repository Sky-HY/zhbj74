package com.cn.zhbj74.domain;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.cn.zhbj74.R;
import com.cn.zhbj74.utils.BitmapUtils;
import com.cn.zhbj74.utils.MyApplication;

import java.util.ArrayList;

/**
 * 组图页面的RecyclerView的适配器
 */
public class PhotoMenuDetailRecyclerViewAdapter extends RecyclerView.Adapter<PhotoMenuDetailRecyclerViewAdapter.ViewHolder> {

    private ArrayList<PhotosBean.PhotoNews> mNewsList;
    private BitmapUtils mBitmapUtils;

    public PhotoMenuDetailRecyclerViewAdapter(ArrayList<PhotosBean.PhotoNews> newsList) {
        mNewsList = newsList;
        mBitmapUtils = new BitmapUtils();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_photos, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        PhotosBean.PhotoNews photoNews = mNewsList.get(position);
        // 设置图片
        // Glide.with(MyApplication.getContext()).load(photoNews.listimage).placeholder(R.drawable.topnews_item_default).error(R.drawable.topnews_item_default).into(holder.iv_photo);
        // 自定义图片3级缓存机制

        // 设置标记,将该imageView与url地址绑定在一起
        holder.iv_photo.setTag(photoNews.listimage);
        mBitmapUtils.display(photoNews.listimage, holder.iv_photo);
        holder.tv_title.setText(photoNews.title);
    }

    @Override
    public int getItemCount() {
        return mNewsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView tv_title;
        public ImageView iv_photo;

        public ViewHolder(View itemView) {
            super(itemView);
            tv_title = itemView.findViewById(R.id.tv_title);
            iv_photo = itemView.findViewById(R.id.iv_photo);
        }
    }
}
