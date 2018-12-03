package com.cn.zhbj74.base;

import android.app.Activity;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;

import com.cn.zhbj74.R;

/**
 * ViewPager子页面的基类
 */
public abstract class BasePager {
    public Activity mActivity;
    public View mRootView;
    public FrameLayout fl_content;
    public TextView tv_title;
    public ImageButton ib_menu,ib_list;
    // MainActivity的DrawerLayout
    private DrawerLayout dl_root;
    public BasePager(Activity mActivity) {
        this.mActivity = mActivity;
        initUI();
    }

    public void initUI() {
        mRootView = LayoutInflater.from(mActivity).inflate(R.layout.base_pager, null, false);
        fl_content = mRootView.findViewById(R.id.fl_content);
        tv_title = mRootView.findViewById(R.id.tv_title);
        ib_menu = mRootView.findViewById(R.id.ib_menu);
        dl_root = mActivity.findViewById(R.id.dl_root);
        ib_list = mRootView.findViewById(R.id.ib_list);


        // menu键设置点击事件
        ib_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 开起侧滑菜单
                dl_root.openDrawer(GravityCompat.START);
            }
        });
    }

    public abstract void initData();


}
