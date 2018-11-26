package com.cn.zhbj74.base.imp.menu;

import android.app.Activity;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.cn.zhbj74.R;
import com.cn.zhbj74.base.BaseMenuDetailPager;
import com.cn.zhbj74.domain.NewsMenu;
import com.cn.zhbj74.utils.LogUtil;
import com.viewpagerindicator.TabPageIndicator;

import java.util.ArrayList;

/**
 * 菜单详情页-新闻
 */
public class NewsMenuDetailPager extends BaseMenuDetailPager implements View.OnClickListener {
    private static final String TAG = "NewsMenuDetailPager";
    private ViewPager vp_news_menu_detail;
    private ArrayList<NewsMenu.NewsTabData> mNewsTabDatas;
    private ArrayList<TabDetailPager> mTabDetailLists;
    // 记录当前标签的索引
    public static int mCurrentPagerItemId = 0;
    private TabPageIndicator indicator;
    private ImageButton ib_next_tab;


    public NewsMenuDetailPager(Activity mActivity, ArrayList<NewsMenu.NewsTabData> children) {
        super(mActivity);
        this.mNewsTabDatas = children;
    }


    @Override
    public View initUI() {
        View view = LayoutInflater.from(mActivity).inflate(R.layout.pager_news_menu_detail, null, false);
        vp_news_menu_detail = view.findViewById(R.id.vp_news_menu_detail);
        // ViewPager指示器
        indicator = view.findViewById(R.id.indicator);
        ib_next_tab = view.findViewById(R.id.ib_next_tab);
        ib_next_tab.setOnClickListener(this);
        return view;
    }

    @Override
    public void initData() {
        // 创建新闻标签子页面，添加到集合里
        mTabDetailLists = new ArrayList<>();
        TabDetailPager tabDetailPager;
        for (int i = 0; i < mNewsTabDatas.size(); i++) {
            tabDetailPager = new TabDetailPager(mActivity, mNewsTabDatas.get(i));
            mTabDetailLists.add(tabDetailPager);
        }

        vp_news_menu_detail.setAdapter(new MyPagerAdapter());
        // 绑定viewPager与指示器
        indicator.setViewPager(vp_news_menu_detail);
        // pager滑动监听
        indicator.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mCurrentPagerItemId = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        // 跳转到指定条目
        indicator.setCurrentItem(mCurrentPagerItemId);

    }

    @Override
    public void onClick(View v) {
        int currentItem = vp_news_menu_detail.getCurrentItem();
        vp_news_menu_detail.setCurrentItem(++currentItem);
    }

    private class MyPagerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return mNewsTabDatas.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            TabDetailPager tabDetailPager = mTabDetailLists.get(position);
            tabDetailPager.initData();
            View view = tabDetailPager.view;
            container.addView(view);
            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        // 重写此方法以配合ViewPager指示器
        @Override
        public CharSequence getPageTitle(int position) {
            return mNewsTabDatas.get(position).title;
        }
    }
}
