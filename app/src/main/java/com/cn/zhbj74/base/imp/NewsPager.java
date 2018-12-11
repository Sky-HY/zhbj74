package com.cn.zhbj74.base.imp;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.cn.zhbj74.MainActivity;
import com.cn.zhbj74.base.BaseMenuDetailPager;
import com.cn.zhbj74.base.BasePager;
import com.cn.zhbj74.base.imp.menu.InteractMenuDetailPager;
import com.cn.zhbj74.base.imp.menu.NewsMenuDetailPager;
import com.cn.zhbj74.base.imp.menu.PhotosMenuDetailPager;
import com.cn.zhbj74.base.imp.menu.TopicMenuDetailPager;
import com.cn.zhbj74.domain.NewsMenu;
import com.cn.zhbj74.fragment.LeftMenuFragment;
import com.cn.zhbj74.global.ConstanValues;
import com.cn.zhbj74.utils.LogUtil;
import com.cn.zhbj74.utils.Md5Util;
import com.cn.zhbj74.utils.ToastUtils;
import com.google.gson.Gson;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 新闻页
 */
public class NewsPager extends BasePager {

    private static final String TAG = "NewsPager";
    private NewsMenu mNewsData;
    private List<BaseMenuDetailPager> mMenuDetailPagers;
    private int mCurrentMenuDetailPagersPosition = 0;

    public NewsPager(Activity mActivity) {
        super(mActivity);
    }

    @Override
    public void initData() {
        tv_title.setText("新闻");
        ib_menu.setVisibility(View.VISIBLE);
        TextView tv_content = new TextView(mActivity);
        tv_content.setTextColor(Color.RED);
        tv_content.setText("新闻中心");
        tv_content.setGravity(Gravity.CENTER);
        fl_content.addView(tv_content);

        // 判断本地是否有缓存，如果有就读取
        File file = new File(mActivity.getFilesDir(), "/" + Md5Util.encoder(ConstanValues.CATEGORY_URL));
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
                LogUtil.i(TAG, "获取了本地缓存json数据");
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
        // 访问服务器获取数据
        getDataFromServer();

    }

    /**
     * 连接服务器获取json数据
     */
    private void getDataFromServer() {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(ConstanValues.CATEGORY_URL).build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                // 调回主线程显示吐司
                mActivity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ToastUtils.showToast("连接服务器超时...", Toast.LENGTH_LONG);
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String result = response.body().string();
                LogUtil.i(TAG, "联网获取json数据:" + result);
                mActivity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        // 解析json数据
                        processData(result);
                    }
                });

                // 将数据缓存到本地
                FileOutputStream fos = null;
                try {
                    fos = mActivity.openFileOutput(Md5Util.encoder(ConstanValues.CATEGORY_URL), Context.MODE_PRIVATE);
                    fos.write(result.getBytes("utf-8"));
                } catch (Exception e) {
                    e.printStackTrace();
                    LogUtil.i(TAG, "缓存数据失败");
                } finally {
                    if (fos != null) {
                        fos.close();
                    }
                }
            }
        });

    }

    /**
     * 利用gson解析json数据
     *
     * @param result json字符串
     */
    private void processData(String result) {
        Gson gson = new Gson();
        mNewsData = gson.fromJson(result, NewsMenu.class);
        LogUtil.i(TAG, "json解析:" + mNewsData.toString());

        //将数据传递给leftMenuFragment
        LeftMenuFragment leftMenuFragment = ((MainActivity) mActivity).getLeftMenuFragment();
        leftMenuFragment.setMenuData(mNewsData.data);

        // 加载侧滑菜单标签页
        mMenuDetailPagers = new ArrayList<>();
        // 将数据也传递给新闻标签子页
        mMenuDetailPagers.add(new NewsMenuDetailPager(mActivity, mNewsData.data.get(0).children));
        mMenuDetailPagers.add(new TopicMenuDetailPager(mActivity));
        mMenuDetailPagers.add(new PhotosMenuDetailPager(mActivity, ib_list));
        mMenuDetailPagers.add(new InteractMenuDetailPager(mActivity));

        // 初始设置第一个标签被选中(新闻标签)
        setCurrentDetailPager(mCurrentMenuDetailPagersPosition);
    }

    /**
     * 动态切换菜单详情页
     *
     * @param position 菜单详情页索引(新闻，专题，组图，互动)
     */
    public void setCurrentDetailPager(int position) {
        // 获取子标题页面
        BaseMenuDetailPager pager = mMenuDetailPagers.get(position);
        View view = pager.rootView;
        pager.initData();
        // 替换布局
        fl_content.removeAllViews();
        fl_content.addView(view);
        String title = mNewsData.data.get(position).title;
        tv_title.setText(title);
        // 设置当前专题的选中索引
        mCurrentMenuDetailPagersPosition = position;

        if (pager instanceof PhotosMenuDetailPager){
            ib_list.setVisibility(View.VISIBLE);
        }else{
            ib_list.setVisibility(View.GONE);
        }
    }
}
