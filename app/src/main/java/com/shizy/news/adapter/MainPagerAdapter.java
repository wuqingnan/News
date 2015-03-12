package com.shizy.news.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.shizy.news.fragment.MaterialStyleFragment;
import com.shizy.news.fragment.PullToRefreshFragment;
import com.shizy.news.fragment.StoreHouseFragment;

public class MainPagerAdapter extends FragmentPagerAdapter {

	private final String[] TITLES = { "头条", "娱乐", "热点", "体育", "北京", "财经", "科技",
			"段子", "图片", "汽车", "时尚", "轻松一刻" };

	public MainPagerAdapter(FragmentManager fm) {
		super(fm);
	}

	@Override
	public CharSequence getPageTitle(int position) {
		return TITLES[position];
	}

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return StoreHouseFragment.newInstance(position);
            case 1:
                return MaterialStyleFragment.newInstance(position);
            default:
                return PullToRefreshFragment.newInstance(position);
        }
    }

	@Override
	public int getCount() {
		return TITLES.length;
	}

}