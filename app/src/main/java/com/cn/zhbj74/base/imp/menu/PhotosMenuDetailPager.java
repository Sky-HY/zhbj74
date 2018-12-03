package com.cn.zhbj74.base.imp.menu;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;

import com.cn.zhbj74.R;
import com.cn.zhbj74.base.BaseMenuDetailPager;
import com.cn.zhbj74.domain.PhotoMenuDetailRecyclerViewAdapter;
import com.cn.zhbj74.domain.PhotosBean;
import com.cn.zhbj74.global.ConstanValues;
import com.cn.zhbj74.utils.LogUtil;
import com.cn.zhbj74.utils.Md5Util;
import com.google.gson.Gson;

import org.jetbrains.annotations.NotNull;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 菜单详情页-组图
 */
public class PhotosMenuDetailPager extends BaseMenuDetailPager implements View.OnClickListener {
    private static final String TAG = "PhotosMenuDetailPager";
    private RecyclerView rv_photo_list;
    private ArrayList<PhotosBean.PhotoNews> mNewsList;
    private LinearLayoutManager mLinearLayoutManager;
    private GridLayoutManager mGridLayoutManager;
    private ImageButton ib_list;

    public PhotosMenuDetailPager(Activity mActivity, ImageButton ib_list) {
        super(mActivity);
        this.ib_list = ib_list;
        ib_list.setVisibility(View.VISIBLE);
        // 线性布局的LayoutManager
        mLinearLayoutManager = new LinearLayoutManager(mActivity);
        // 网格布局的LayoutManager
        mGridLayoutManager = new GridLayoutManager(mActivity, 2);
        ib_list.setOnClickListener(this);
    }

    @Override
    public View initUI() {
        View view = View.inflate(mActivity, R.layout.pager_photos_menu_detail, null);
        rv_photo_list = view.findViewById(R.id.rv_photo_list);
        return view;
    }

    @Override
    public void initData() {
        // 从服务器获取数据
        getDataFromServer();
        // 判断本地是否有缓存，如果有就读取
        File file = new File(mActivity.getFilesDir(), "/" + Md5Util.encoder(ConstanValues.PHOTO_URL));
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
        Request request = new Request.Builder().url(ConstanValues.PHOTO_URL).build();
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
                    fos = mActivity.openFileOutput(Md5Util.encoder(ConstanValues.PHOTO_URL), Context.MODE_PRIVATE);
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
     * @param result json字符串
     */
    private void processData(String result) {
        Gson gson = new Gson();
        PhotosBean photosBean = gson.fromJson(result, PhotosBean.class);
        mNewsList = photosBean.data.news;
        setAdapter();
    }

    /**
     * 给RecyclerView设置数据
     */
    private void setAdapter() {
        PhotoMenuDetailRecyclerViewAdapter adapter = new PhotoMenuDetailRecyclerViewAdapter(mNewsList);

        rv_photo_list.setAdapter(adapter);
        rv_photo_list.setLayoutManager(mLinearLayoutManager);
    }


    private boolean isListView = true;

    @Override
    public void onClick(View v) {
        if (isListView) {
            // 切换为GridView
            rv_photo_list.setLayoutManager(mGridLayoutManager);
            ib_list.setImageResource(R.drawable.icon_pic_list_type);
            isListView = false;
        } else {
            // 切换为listView
            rv_photo_list.setLayoutManager(mLinearLayoutManager);
            ib_list.setImageResource(R.drawable.icon_pic_grid_type);
            isListView = true;
        }
    }
}
