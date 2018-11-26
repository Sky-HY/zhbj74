package com.cn.zhbj74;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.cn.zhbj74.global.ConstanValues;
import com.cn.zhbj74.utils.LogUtil;
import com.cn.zhbj74.utils.MyApplication;
import com.cn.zhbj74.utils.SpUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 新手引导界面
 */
public class GuideActivity extends AppCompatActivity {
    private static final String TAG = "GuideActivity";
    private ViewPager vp_pager;
    private Button btn_start;
    private int[] mImageIds;
    private List<ImageView> mImageViewLists;
    private LinearLayout ll_container;
    // 小红点
    private ImageView iv_red_point;
    // 小红点需要移动的距离
    private int mPointDis;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);

        initUI();
        initData();
        // 设置适配器
        vp_pager.setAdapter(new MyAdapter());
        // viewPager滑动监听
        vp_pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                // 移动小红点
                float leftMargin = mPointDis * positionOffset + position * mPointDis;
                RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
                params.leftMargin = (int) leftMargin;
                iv_red_point.setLayoutParams(params);
            }

            @Override
            public void onPageSelected(int position) {
                // 显示按钮
                if (position == mImageViewLists.size() - 1) {
                    btn_start.setVisibility(View.VISIBLE);
                } else {
                    btn_start.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        // 监听layout方法执行完的
        // 视图树
        iv_red_point.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                iv_red_point.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                mPointDis = ll_container.getChildAt(1).getLeft() - ll_container.getChildAt(0).getLeft();
                LogUtil.i(TAG, "小红点需要移动的距离 = " + mPointDis);
            }
        });

    }

    private void initData() {
        mImageIds = new int[]{R.drawable.guide_1, R.drawable.guide_2, R.drawable.guide_3};
        mImageViewLists = new ArrayList<>();
        ImageView imageView;
        for (int i = 0; i < mImageIds.length; i++) {
            // 设置轮播图片
            imageView = new ImageView(MyApplication.getContext());
            imageView.setBackgroundResource(mImageIds[i]);
            mImageViewLists.add(imageView);

            ImageView point = new ImageView(MyApplication.getContext());
            point.setImageResource(R.drawable.shape_point_gray);
            if (i > 0) {
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                params.leftMargin = 25;
                point.setLayoutParams(params);
            }
            ll_container.addView(point);
        }
    }

    private void initUI() {
        vp_pager = findViewById(R.id.vp_pager);
        btn_start = findViewById(R.id.btn_start);
        ll_container = findViewById(R.id.ll_container);
        iv_red_point = findViewById(R.id.iv_red_point);
        // 跳转到主页
        btn_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SpUtils.putBoolean(MyApplication.getContext(), ConstanValues.IS_FIRST_ENTER, false);
                Intent intent = new Intent(MyApplication.getContext(), MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    // ViewPage适配器
    private class MyAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return mImageViewLists.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            ImageView imageView = mImageViewLists.get(position);
            container.addView(imageView);
            return imageView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }
}
