package com.cn.zhbj74.base.imp;

import android.app.Activity;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.cn.zhbj74.base.BasePager;

/**
 * 政务页
 */
public class GovPager extends BasePager {

    public GovPager(Activity mActivity) {
        super(mActivity);
    }

    @Override
    public void initData() {
        tv_title.setText("人口管理");
        ib_menu.setVisibility(View.VISIBLE);
        TextView tv_content = new TextView(mActivity);
        tv_content.setTextColor(Color.RED);
        tv_content.setText("政务");
        tv_content.setGravity(Gravity.CENTER);
        fl_content.addView(tv_content);
    }
}
