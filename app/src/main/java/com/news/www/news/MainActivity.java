package com.news.www.news;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.news.www.news.Fragment.FirstPagerFragment;
import com.news.www.news.adapter.MainTabAdapter;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private FirstPagerFragment mFirstPagerFragment;
    private List<FirstPagerFragment> mFirstPagerFragments;
    private String[] mList_title;//存放标题
    private MainTabAdapter mAdapter_title;//标题的适配器
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Fresco.initialize(this);//初始化Fresco
        iniDate();
        init();
        initTablayout();

    }

    private void iniDate() {
        mList_title = getResources().getStringArray(R.array.tab_title);
        mFirstPagerFragments = new ArrayList<>();
        /*通过标题数目来确定Fragment的个数*/
        for (int i = 0; i <mList_title.length ; i++) {
            FirstPagerFragment first = new FirstPagerFragment();
            mFirstPagerFragments.add(first);
        }
    }

    private void init(){
        toolbar = (Toolbar) findViewById(R.id.toolBar);
        mTabLayout = (TabLayout) findViewById(R.id.tal);
        mViewPager = (ViewPager) findViewById(R.id.view_pager);
        mAdapter_title = new MainTabAdapter(getSupportFragmentManager(),mFirstPagerFragments,mList_title);
        mViewPager.setAdapter(mAdapter_title);
      //  TabLayout绑定viewpager
        mTabLayout.setupWithViewPager(mViewPager);
    }
    public void initTablayout(){
        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = tab.getPosition();
                for (int i = 0; i <mFirstPagerFragments.size() ; i++) {
                    mFirstPagerFragments.get(position).setPosition(position);
                }
                mViewPager.setCurrentItem(position);
                /*我们这里是使用ViewPager和TabLayout绑定，其实可以不要写这句
                * 但是如果是（ViewPager）和其他组件一起使用，就需要些这句来根据position来改变布局*/
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }
}
