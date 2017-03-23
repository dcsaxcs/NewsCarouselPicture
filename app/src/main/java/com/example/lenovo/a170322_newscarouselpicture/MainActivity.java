package com.example.lenovo.a170322_newscarouselpicture;

import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class MainActivity extends AppCompatActivity {

    @InjectView(R.id.vp_main_Carousel)
    ViewPager mVpMainCarousel;
    @InjectView(R.id.tv_main_title)
    TextView mTvMainTitle;
    @InjectView(R.id.ll_main_dot_vessel)
    LinearLayout mLlMainDotVessel;
    int[] vpPicture = {R.drawable.icon_1, R.drawable.icon_2, R.drawable.icon_3,
            R.drawable.icon_4, R.drawable.icon_5,R.drawable.icon_6,R.drawable.icon_7};
  View view;
    String[] title = {"第1个图片", "第2个图片", "第3个图片", "第4个图片", "第5个图片","第6个图片","第7个图片",};
    private int pos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);
        vpAdapter();
        vpListener();
        initDot();
        mTvMainTitle.setText(title[0]);
        //这里要-3,否则图片显示的位置就不对.
        int middle = Integer.MAX_VALUE/2;
        int farst = middle%vpPicture.length;//一定要使用%,而不是/
       mVpMainCarousel.setCurrentItem( middle-farst);


    }

    private void initDot() {

        for (int i = 0; i < vpPicture.length; i++) {
            LinearLayout.LayoutParams lp_dot = new LinearLayout.LayoutParams(10, 10);
            lp_dot.rightMargin = 8;
            View view_dot = new View(this);
            view_dot.setLayoutParams(lp_dot);
            view_dot.setBackgroundResource(R.drawable.selector_dot);
            if (i == 0) {
                view_dot.setSelected(true);
                view = view_dot;//要给你设置的中间变量附默认的值.
            }
            mLlMainDotVessel.addView(view_dot);

        }
    }

    private void vpListener() {
        mVpMainCarousel.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                //这里必须要使用独自的pos,否则点滑的时候,刚开始的几次会出错.
                int pos = position % vpPicture.length;
                view.setSelected(false);
                View childAt = mLlMainDotVessel.getChildAt(pos);
                childAt.setSelected(true);
                view = childAt;
                mTvMainTitle.setText(title[pos]);

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void vpAdapter() {
        mVpMainCarousel.setAdapter(new PagerAdapter() {
            @Override
            public int getCount() {

                return Integer.MAX_VALUE;
            }

            @Override
            public boolean isViewFromObject(View view, Object object) {
                return view == object;
            }

            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                pos = position % vpPicture.length;
                ImageView iv_vpPicture = new ImageView(MainActivity.this);
                iv_vpPicture.setScaleType(ImageView.ScaleType.FIT_XY);//设置图片填充窗体的属性
                iv_vpPicture.setImageResource(vpPicture[pos]);
                container.addView(iv_vpPicture);

                return iv_vpPicture;
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                container.removeView((View) object);//大多数都是这一行代码.
            }
        });
    }
}
