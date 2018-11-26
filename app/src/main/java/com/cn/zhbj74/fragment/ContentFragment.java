package com.cn.zhbj74.fragment;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;

import com.cn.zhbj74.R;
import com.cn.zhbj74.base.BasePager;
import com.cn.zhbj74.base.imp.GovPager;
import com.cn.zhbj74.base.imp.HomePager;
import com.cn.zhbj74.base.imp.NewsPager;
import com.cn.zhbj74.base.imp.SettingPager;
import com.cn.zhbj74.base.imp.SmartPager;
import com.cn.zhbj74.ui.NoScrollViewPager;

import java.util.ArrayList;
import java.util.List;

/**
 * 内容页面的fragment
 */
public class ContentFragment extends BaseFragment {

    private NoScrollViewPager vp_viewPager;
    private RadioGroup rg_group;
    public List<BasePager> pagerLists;
    // 父activity的控件
    private DrawerLayout dl_root;

    @Override

    public View initUI(LayoutInflater inflater) {
        View view = inflater.inflate(R.layout.fragment_content, null, false);
        vp_viewPager = view.findViewById(R.id.vp_viewPager);
        rg_group = view.findViewById(R.id.rg_group);
        // 拿到父activity的控件
        dl_root = mActivity.findViewById(R.id.dl_root);
        return view;
    }

    @Override
    public void initData() {
        // 给viewPager设置数据
        pagerLists = new ArrayList<>();
        pagerLists.add(new HomePager(mActivity));
        pagerLists.add(new NewsPager(mActivity));
        pagerLists.add(new SmartPager(mActivity));
        pagerLists.add(new GovPager(mActivity));
        pagerLists.add(new SettingPager(mActivity));
        vp_viewPager.setAdapter(new MyPagerAdapter());
        // 单选条目监听
        rg_group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rb_home:
                        // viewPager调到指定界面
                        vp_viewPager.setCurrentItem(0, false); // 参数2表示是否需要滑动动画
                        break;
                    case R.id.rb_news:
                        vp_viewPager.setCurrentItem(1, false);
                        break;
                    case R.id.rb_smart:
                        vp_viewPager.setCurrentItem(2, false);
                        break;
                    case R.id.rb_gov:
                        vp_viewPager.setCurrentItem(3, false);
                        break;
                    case R.id.rb_setting:
                        vp_viewPager.setCurrentItem(4, false);
                        break;
                }
            }
        });

        // 手动给第一个页面设置数据
        pagerLists.get(0).initData();
        // 手动让第一个无法显示侧滑菜单
        dl_root.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        // ViewPager设置切换事件监听
        vp_viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                // 加载当前页的数据
                pagerLists.get(position).initData();
                // 锁定侧滑菜单
                if (position == 0 || position == 4) {
                    dl_root.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
                } else {
                    dl_root.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    // ViewPager数据适配器
    private class MyPagerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return pagerLists.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View view = pagerLists.get(position).mRootView;
            container.addView(view);
            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }
}
