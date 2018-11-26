package com.cn.zhbj74.base.imp;

import android.app.Activity;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.cn.zhbj74.base.BasePager;

/**
 * 首页的
 */
public class HomePager extends BasePager {

    public HomePager(Activity mActivity) {
        super(mActivity);
    }

    @Override
    public void initData() {
        tv_title.setText("智慧北京");
        ib_menu.setVisibility(View.GONE);
        TextView tv_content = new TextView(mActivity);
        tv_content.setTextColor(Color.RED);
        tv_content.setText("首页");
        tv_content.setGravity(Gravity.CENTER);
        fl_content.addView(tv_content);
    }

}
