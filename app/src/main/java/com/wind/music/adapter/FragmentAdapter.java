package com.wind.music.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.wind.music.fragment.BaseFragment;

import java.util.List;

/**
 * Created by Administrator on 2017/5/8.
 */

public class FragmentAdapter extends FragmentPagerAdapter {
    List<? extends BaseFragment> mFragments;

    public FragmentAdapter(FragmentManager fm, List<? extends BaseFragment> fragments) {
        super(fm);
        setFragments(fragments);
    }

    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }

    public List<? extends BaseFragment> getFragments() {
        return mFragments;
    }

    public void setFragments(List<? extends BaseFragment> fragments) {
        this.mFragments = fragments;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mFragments.get(position).getTitle();
    }
}
