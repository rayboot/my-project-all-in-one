package com.rayboot.letsball.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.rayboot.letsball.fragment.TeamMatchFragment;

public class TeamDetailAdapter extends SampleFragmentAdapter {
	private String[] titles;
	private String[] detailUrls;

	public TeamDetailAdapter(FragmentManager fm) {
		super(fm);
		// TODO Auto-generated constructor stub
	}

	public TeamDetailAdapter(FragmentManager fm, String[] titles,
			String[] detailUrls) {
		// TODO Auto-generated constructor stub
		super(fm);
		this.titles = titles;
		this.detailUrls = detailUrls;
	}

	@Override
	public Fragment getItem(int position) {
		return TeamMatchFragment.newInstance(position, detailUrls[position]);

	}

	@Override
	public int getCount() {
		return titles.length;
	}

	@Override
	public CharSequence getPageTitle(int position) {
		// TODO Auto-generated method stub
		return titles[position];
	}

}
