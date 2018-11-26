package com.cn.zhbj74.fragment;

import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.cn.zhbj74.MainActivity;
import com.cn.zhbj74.R;
import com.cn.zhbj74.base.BasePager;
import com.cn.zhbj74.base.imp.NewsPager;
import com.cn.zhbj74.domain.NewsMenu;

import java.util.List;

/**
 * 菜单页面的fragment
 */
public class LeftMenuFragment extends BaseFragment {

    private ListView lv_menu;
    // 侧边栏网络数据对象
    public List<NewsMenu.NewsMenuData> mNewsMenuData;
    // ListView当前选中的条目
    public int mCurrentItemId = 0;
    // 父activity的控件
    private DrawerLayout dl_root;
    private MyAdapter mAdapter;

    @Override
    public View initUI(LayoutInflater inflater) {
        View view = inflater.inflate(R.layout.fragment_left_menu, null, false);
        dl_root = mActivity.findViewById(R.id.dl_root);
        lv_menu = view.findViewById(R.id.lv_menu);
        // listView条目的点击事件
        lv_menu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // 改变选中条目的索引
                mCurrentItemId = position;
                // 通知适配器刷新数据
                mAdapter.notifyDataSetChanged();
                // 让侧边栏收缩回去
                dl_root.closeDrawer(GravityCompat.START);
                // 设置新闻标签页数据
                setCurrentDetailPager(position);
            }
        });
        return view;
    }

    /**
     * 给新闻标签页设置数据
     *
     * @param position json数据的索引
     */
    private void setCurrentDetailPager(int position) {
        ContentFragment contentFragment = ((MainActivity) mActivity).getContentFragment();
        NewsPager pager = (NewsPager) contentFragment.pagerLists.get(1);
        pager.setCurrentDetailPager(position);
    }

    @Override
    public void initData() {

    }

    // 给ListView的集合设置数据
    public void setMenuData(List<NewsMenu.NewsMenuData> data) {
        mNewsMenuData = data;
        mAdapter = new MyAdapter();
        lv_menu.setAdapter(mAdapter);
    }


    private class MyAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return mNewsMenuData.size();
        }

        @Override
        public Object getItem(int position) {
            return mNewsMenuData.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = LayoutInflater.from(mActivity).inflate(R.layout.fragment_left_menu_list_item, parent, false);
            TextView tv_menu_item = view.findViewById(R.id.tv_menu_item);
            tv_menu_item.setText(mNewsMenuData.get(position).title);
            if (mCurrentItemId == position) {
                tv_menu_item.setEnabled(true);
            } else {
                tv_menu_item.setEnabled(false);
            }
            return view;
        }
    }
}
