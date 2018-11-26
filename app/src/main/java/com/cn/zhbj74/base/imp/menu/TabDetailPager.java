package com.cn.zhbj74.base.imp.menu;

import android.app.Activity;
import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.cn.zhbj74.R;
import com.cn.zhbj74.domain.MyRecyclerViewAdapter;
import com.cn.zhbj74.domain.NewsMenu;
import com.cn.zhbj74.domain.NewsTabBean;
import com.cn.zhbj74.global.ConstanValues;
import com.cn.zhbj74.utils.LogUtil;
import com.cn.zhbj74.utils.Md5Util;
import com.google.gson.Gson;
import com.viewpagerindicator.CirclePageIndicator;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 新闻标签详情页的基类
 */
public class TabDetailPager {
    private static final String TAG = "TabDetailPager";
    private Activity mActivity;
    private NewsMenu.NewsTabData mTabData;
    public View view;
    private ViewPager vp_top_news;
    private String mUrl;
    private NewsTabBean mNewsTabBean;
    private TextView tv_title;
    private CirclePageIndicator indicator;
    private RecyclerView rv_newslist;
    private View pager_top_news;

    public TabDetailPager(Activity activity, NewsMenu.NewsTabData tabData) {
        mActivity = activity;
        mTabData = tabData;
        mUrl = ConstanValues.SERVER_URL + mTabData.url;
        LogUtil.i(TAG, "URL:" + mUrl);
        initUI();
    }

    public View initUI() {
        // 列表新闻
        view = LayoutInflater.from(mActivity).inflate(R.layout.pager_tab_detail, null, false);
        rv_newslist = view.findViewById(R.id.rv_newslist);
        // 头条新闻
        pager_top_news = LayoutInflater.from(mActivity).inflate(R.layout.pager_top_news, null, false);
        tv_title = pager_top_news.findViewById(R.id.tv_title);
        indicator = pager_top_news.findViewById(R.id.indicator);
        vp_top_news = pager_top_news.findViewById(R.id.vp_top_news);
        return view;
    }

    public void initData() {
        getDataFromServer();
        // 判断本地是否有缓存，如果有就读取
        File file = new File(mActivity.getFilesDir(), "/" + Md5Util.encoder(mUrl));
        if (file.exists()) {
            BufferedInputStream bis = null;
            try {
                bis = new BufferedInputStream(new FileInputStream(file));
                ByteArrayOutputStream baso = new ByteArrayOutputStream();
                int len = -1;
                byte bys[] = new byte[1024];
                while ((len = bis.read(bys)) != -1) {
                    baso.write(bys, 0, len);
                }
                // 解析本地缓存的json
                processData(baso.toString("utf-8"));
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (bis != null) {
                    try {
                        bis.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    /**
     * 从服务器获取数据
     */
    private void getDataFromServer() {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(mUrl).build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                LogUtil.w(TAG, "无法连接服务器");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String result = response.body().string();
                LogUtil.i(TAG, "头条新闻:" + result);
                // 缓存数据
                // 将数据缓存到本地
                FileOutputStream fos = null;
                try {
                    fos = mActivity.openFileOutput(Md5Util.encoder(mUrl), Context.MODE_PRIVATE);
                    fos.write(result.getBytes("utf-8"));
                } catch (Exception e) {
                    e.printStackTrace();
                    LogUtil.i(TAG, "缓存数据失败");
                } finally {
                    if (fos != null) {
                        fos.close();
                    }
                }
                mActivity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        // 解析json数据
                        processData(result);

                    }
                });
            }
        });
    }

    /**
     * 解析json数据
     *
     */
    private void processData(String result) {
        Gson gson = new Gson();
        mNewsTabBean = gson.fromJson(result, NewsTabBean.class);
        vp_top_news.setAdapter(new MyPagerAdapter());
        // 给indicator设置ViewPager
        indicator.setViewPager(vp_top_news);
        indicator.setSnap(true);
        // 设置初始状态标题
        tv_title.setText(mNewsTabBean.data.topnews.get(0).title);
        // 设置小红点初始选中第0个位置
        indicator.onPageSelected(0);
        // 设置切换时标题的内容
        indicator.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }
            @Override
            public void onPageSelected(int position) {
                tv_title.setText(mNewsTabBean.data.topnews.get(position).title);
            }
            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });


        // RecyclerView适配器
        MyRecyclerViewAdapter recyclerViewAdapter = new MyRecyclerViewAdapter(mNewsTabBean.data.news);
        // layoutManager
        LinearLayoutManager manager = new LinearLayoutManager(mActivity);
        rv_newslist.setLayoutManager(manager);
        rv_newslist.setAdapter(recyclerViewAdapter);
        // 给RecyclerView数据适配器设置头布局
        recyclerViewAdapter.setHeadView(pager_top_news);
    }

    // ViewPager数据适配器
    private class MyPagerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return mNewsTabBean.data.topnews.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            ImageView imageView = new ImageView(mActivity);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            // 获取图片网络URL
            String imagerUrl = mNewsTabBean.data.topnews.get(position).topimage;
            LogUtil.i(TAG, "imageUrl:" + imagerUrl);
            // 开源项目设置图片
            Glide.with(mActivity).load(imagerUrl).placeholder(R.drawable.topnews_item_default).error(R.drawable.topnews_item_default).diskCacheStrategy(DiskCacheStrategy.ALL).into(imageView);
            container.addView(imageView);
            return imageView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }
}
