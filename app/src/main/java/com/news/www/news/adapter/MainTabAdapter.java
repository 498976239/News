package com.news.www.news.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.news.www.news.Fragment.FirstPagerFragment;

import java.util.List;

/**
 * Created by Administrator on 16-12-3.
 */
public class MainTabAdapter extends FragmentPagerAdapter {
    private List<FirstPagerFragment> mList_fragment;
    private String[] mList_title;
    public MainTabAdapter(FragmentManager fm, List<FirstPagerFragment> list_fragment,String[] list_title) {
        super(fm);
        mList_fragment = list_fragment;
        mList_title = list_title;
    }

    @Override
    public Fragment getItem(int position) {
        return mList_fragment.get(position);
    }

    @Override
    public int getCount() {
        return mList_fragment.size();
    }
    //此方法用来显示tab上的名字
    @Override
    public CharSequence getPageTitle(int position) {
        return mList_title[position];
    }
}
