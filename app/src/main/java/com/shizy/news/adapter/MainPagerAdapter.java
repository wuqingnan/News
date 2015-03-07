package com.shizy.news.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.shizy.news.fragment.SuperAwesomeCardFragment;

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
	public int getCount() {
		return TITLES.length;
	}

	@Override
	public Fragment getItem(int position) {
		return SuperAwesomeCardFragment.newInstance(position);
	}

}