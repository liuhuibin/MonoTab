package com.huibin.monotab;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.TextView;

import com.huibin.monotab.component.ViewPagerIndicator;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends FragmentActivity implements View.OnClickListener{

    private TextView mCommentTab ;
    private TextView mMemoryTab ;
    private ViewPagerIndicator mIndicator ;
    private ViewPager mViewPager ;

    private List<Fragment> mTabs = new ArrayList<>() ;
    private FragmentPagerAdapter mAdapter ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        CommentFragment commentTab = CommentFragment.newInstance("评论") ;
        mTabs.add(commentTab);
        MemoryFragment memoryTab = MemoryFragment.newInstance("回忆墙") ;
        mTabs.add(memoryTab) ;

        mAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public int getCount() {
                return mTabs.size();
            }

            @Override
            public Fragment getItem(int position) {
                return mTabs.get(position);
            }
        };

        mCommentTab = (TextView) findViewById(R.id.tv_tab_comment);
        mCommentTab.setOnClickListener(this);
        mMemoryTab = (TextView) findViewById(R.id.tv_tab_memory);
        mMemoryTab.setOnClickListener(this);
        mIndicator = (ViewPagerIndicator) findViewById(R.id.vpi_indicator);
        mViewPager = (ViewPager) findViewById(R.id.viewpager);
        mIndicator.bindViewPager(mViewPager);
        mViewPager.setAdapter(mAdapter);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.tv_tab_comment: //点击了 评论
                if (mViewPager.getCurrentItem()!=0) {
                    mIndicator.setStartPosition();
                }
                break;
            case R.id.tv_tab_memory: //点击了 回忆墙
                if (mViewPager.getCurrentItem()!=1) {
                    mIndicator.setEndPosition();
                }
                break;
        }
    }
}
