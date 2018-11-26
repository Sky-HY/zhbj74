package com.cn.zhbj74.base.imp.menu;

import android.app.Activity;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.cn.zhbj74.base.BaseMenuDetailPager;

/**
 * 菜单详情页-专题
 */
public class TopicMenuDetailPager extends BaseMenuDetailPager {
    public TopicMenuDetailPager(Activity mActivity) {
        super(mActivity);
    }

    @Override
    public View initUI() {
        TextView tv_content = new TextView(mActivity);
        tv_content.setTextColor(Color.RED);
        tv_content.setText("菜单详情页-专题");
        tv_content.setGravity(Gravity.CENTER);
        return tv_content;
    }
}
