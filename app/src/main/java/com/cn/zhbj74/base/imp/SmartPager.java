package com.cn.zhbj74.base.imp;

import android.app.Activity;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.cn.zhbj74.base.BasePager;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 首页的
 */
public class SmartPager extends BasePager {

    public SmartPager(Activity mActivity) {
        super(mActivity);
    }

    @Override
    public void initData() {
        tv_title.setText("生活");
        ib_menu.setVisibility(View.VISIBLE);
        TextView tv_content = new TextView(mActivity);
        tv_content.setTextColor(Color.RED);
        tv_content.setText("智慧服务");
        tv_content.setGravity(Gravity.CENTER);
        fl_content.addView(tv_content);

    }


}
